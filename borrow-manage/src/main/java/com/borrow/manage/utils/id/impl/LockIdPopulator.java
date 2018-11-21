package com.borrow.manage.utils.id.impl;


import com.borrow.manage.utils.id.BasePopulator;
import com.borrow.manage.utils.id.Id;
import com.borrow.manage.utils.id.IdMeta;
import com.borrow.manage.utils.id.Timer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockIdPopulator extends BasePopulator {

    private Lock lock = new ReentrantLock();

    public LockIdPopulator() {
        super();
    }

    public void populateId(Timer timer, Id id, IdMeta idMeta) {
        lock.lock();
        try {
            super.populateId(timer, id, idMeta);
        } finally {
            lock.unlock();
        }
    }

}
