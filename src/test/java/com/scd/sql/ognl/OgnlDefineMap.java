package com.scd.sql.ognl;

import ognl.OgnlContext;
import ognl.OgnlRuntime;
import ognl.PropertyAccessor;
import org.apache.ibatis.scripting.xmltags.DynamicContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author James
 */
public class OgnlDefineMap extends HashMap<String, Object> {
    static {
        OgnlRuntime.setPropertyAccessor(OgnlDefineMap.class, new ContextAccessor());
    }

    static class ContextAccessor implements PropertyAccessor {
        @Override
        public Object getProperty(Map context, Object target, Object name) {
            Map map = (Map) target;

            Object result = map.get(name);
            if (map.containsKey(name) || result != null) {
                return result;
            }

            Object parameterObject = map.get(DynamicContext.PARAMETER_OBJECT_KEY);
            if (parameterObject instanceof Map) {
                return ((Map)parameterObject).get(name);
            }

            return null;
        }

        @Override
        public void setProperty(Map context, Object target, Object name, Object value) {
            Map<Object, Object> map = (Map<Object, Object>) target;
            map.put(name, value);
        }

        @Override
        public String getSourceAccessor(OgnlContext arg0, Object arg1, Object arg2) {
            return null;
        }

        @Override
        public String getSourceSetter(OgnlContext arg0, Object arg1, Object arg2) {
            return null;
        }
    }
}
