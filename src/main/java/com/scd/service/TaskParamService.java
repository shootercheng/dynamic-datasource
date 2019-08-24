package com.scd.service;

import com.scd.model.po.TaskParam;

/**
 * @author chengdu
 * @date 2019/8/24.
 */
public interface TaskParamService {

    int saveTaskParam(TaskParam taskParam);

    String queryScheduleEx(Integer id);
}
