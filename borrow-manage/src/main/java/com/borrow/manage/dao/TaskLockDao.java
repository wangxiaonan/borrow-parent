package com.borrow.manage.dao;

import com.borrow.manage.model.dto.TaskLock;

/**
 * Created by wxn on 2018/9/29
 */
public interface TaskLockDao {

    void insertTaskLock(TaskLock taskLock);

    void updateTaskLock(String uid,TaskLock taskLock);
}
