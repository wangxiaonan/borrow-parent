package com.borrow.manage.dao.impl;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.dao.TaskLockDao;
import com.borrow.manage.dao.mapper.TaskLockMapper;
import com.borrow.manage.model.dto.TaskLock;
import com.borrow.manage.model.dto.TaskLockExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by wxn on 2018/9/29
 */
@Repository
public class TaskLockDaoImpl implements TaskLockDao {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TaskLockMapper taskLockMapper;

    @Override
    public void insertTaskLock(TaskLock taskLock) {
        logger.info("insertTaskLock: taskLock:", JSON.toJSON(taskLock));
        taskLockMapper.insertSelective(taskLock);
    }

    @Override
    public void updateTaskLock(String uid, TaskLock taskLock) {
        TaskLockExample example = new TaskLockExample();
        example.createCriteria().andUuidEqualTo(uid);
        taskLock.setUpdateTime(new Date());
        taskLockMapper.updateByExampleSelective(taskLock,example);
    }
}
