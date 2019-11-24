package com.scd.aop;

import com.scd.annotation.DataSourceRouting;
import com.scd.config.DsConfig;
import com.scd.config.DsInfo;
import com.scd.config.RoutingDataSourceComponent;
import com.scd.exception.RouteDsException;
import com.scd.util.InheritableHeaderValueUtil;
import com.scd.util.LookUpKeyUtil;
import com.scd.util.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author chengdu
 * @date 2019/8/15.
 */
@Component
@Aspect
@Order(4)
public class DsRoutingAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(DsRoutingAop.class);

    @Autowired
    private DsConfig dsConfig;

    @Autowired
    private RoutingDataSourceComponent dataSourceComponent;

    // @within TYPE
    // @annotation METHOD
    @Pointcut(value = "@within(com.scd.annotation.DataSourceRouting)||@annotation(com.scd.annotation.DataSourceRouting)")
    public void routePointcut(){

    }

    @Before(value = "routePointcut()")
    public void doBefore(JoinPoint joinPoint){
        // 获取注解
        Annotation annotation = joinPoint.getSignature().getDeclaringType().getAnnotation(DataSourceRouting.class);
        DataSourceRouting dsRouter = null;
        if (annotation instanceof DataSourceRouting){
            dsRouter = (DataSourceRouting) annotation;
        }
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature){
            MethodSignature methodSignature = (MethodSignature) signature;
            DataSourceRouting dataSourceRouting = methodSignature.getMethod().getAnnotation(DataSourceRouting.class);
            if (dataSourceRouting != null){
                dsRouter = dataSourceRouting;
            }
        }
        if (dsRouter == null){
            throw new RouteDsException(" find route annotation error ");
        }
        // 解析注解
        String dsname = dsRouter.dsname();
        String dsparam = dsRouter.dsparam();

        // 开始路由
        // 路由信息
        if (StringUtils.isEmpty(dsparam)){
            throw new RouteDsException("routing param is empty");
        }
        if (StringUtils.isEmpty(dsname)){
            throw new RouteDsException("routing dsname is empty");
        }
        // 获取请求头中的参数
        String rtParam = getRequestParam(dsparam);
        if (StringUtils.isEmpty(rtParam)){
            throw new RouteDsException("reuqest header routing header is empty");
        }
        Map<String, DsInfo> dsInfoMap = dsConfig.getDatasource();
        DsInfo dsInfo = dsInfoMap.get(dsname);
        String url = dsInfo.getJdbcUrl();
        String template = dsInfo.getTemplate();
        if (StringUtils.isEmpty(template)) {
            throw new RouteDsException("datasource template dburl not configed");
        }
        // 替换为路由库名
        String rtkey = ":" + dsparam;
        rtkey = template.replace(rtkey, rtParam);
        if (!dataSourceComponent.checkDataSourceExist(rtkey)) {
            // 替换为新的url
            url = url.replace(dsInfo.getDburl(), rtkey);
            DataSource dataSource = DataSourceBuilder.create().driverClassName(dsInfo.getDriverClassName()).
                    url(url).username(dsInfo.getUsername()).password(dsInfo.getPassword()).build();

            if (connectDb(dataSource)) {
                dataSourceComponent.addDataSource(rtkey, dataSource);
                LookUpKeyUtil.setLookupKey(rtkey);
            }
        } else{
            LookUpKeyUtil.setLookupKey(rtkey);
        }
    }

    @AfterReturning(value = "routePointcut()", returning = "object")
    public void doAfterReturn(Object object) {
        LOGGER.info("execute result is {}",  object);
        LookUpKeyUtil.removeLookupKey();
    }

    @AfterThrowing(value = "routePointcut()", throwing = "throwable")
    public void doAfterThrow(Throwable throwable) {
        LOGGER.error("datasource route aop error ", throwable);
    }

    private String getRequestParam(String dsparam) {
        HttpServletRequest httpServletRequest = RequestUtil.getHttpRequest();
        if (httpServletRequest == null) {
           return InheritableHeaderValueUtil.getHeaderMap().get().get(dsparam);
        }
        return httpServletRequest.getHeader(dsparam);
    }

    private boolean connectDb(DataSource dataSource) {
        boolean isAlive = false;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            isAlive = true;
        } catch (SQLException e){
            LOGGER.error("Connect Db error, please check config");
            // 终止这次请求
            throw new RouteDsException("Connect Db error, please check config", e.getCause());
        } finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("Close Connection error");
                }
            }
        }
        return isAlive;
    }
}
