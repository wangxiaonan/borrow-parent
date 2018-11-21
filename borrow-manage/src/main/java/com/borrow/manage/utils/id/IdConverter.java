package com.borrow.manage.utils.id;

public interface IdConverter {

    long convert(Id id, IdMeta idMeta);

    Id convert(long id, IdMeta idMeta);

}
