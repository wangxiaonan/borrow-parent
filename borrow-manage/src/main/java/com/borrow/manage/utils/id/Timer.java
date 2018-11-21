package com.borrow.manage.utils.id;

import java.util.Date;

public interface Timer {
    long EPOCH = 1420041600000L;

    void init(IdMeta idMeta, IdType idType);

    Date transTime(long time);

    void validateTimestamp(long lastTimestamp, long timestamp);

    long tillNextTimeUnit(long lastTimestamp);

    long genTime();

}
