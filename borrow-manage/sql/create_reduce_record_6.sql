-- auto-generated definition
create table bo_overdue_reduce_record
(
  id                   bigint auto_increment
  comment '主键'
    primary key,
  disabled             tinyint(1) default '0'             not null
  comment '删除标志',
  uuid                 varchar(32)                        not null
  comment 'uuid',
  create_user          int default '0'                    not null
  comment '审核人',
  create_time          datetime default CURRENT_TIMESTAMP not null
  comment '创建时间',
  update_user          int default '0'                    not null
  comment '更新用户',
  update_time          datetime default CURRENT_TIMESTAMP not null
  on update CURRENT_TIMESTAMP
  comment '更新时间',
  remark               varchar(100) default ''            not null
  comment '备注',
  reduce_punish_amount decimal(16, 2)                     null
  comment '减免违约金金额',
  reduce_fine_amount   decimal(16, 2)                     null
  comment '减免罚息',
  borrow_id            varchar(64) default ''             not null
  comment '''借款ID''',
  repayment_id         varchar(64) default ''             not null
  comment '还款ID'
)
  comment '减免记录';

create index borrow_id_index
  on bo_overdue_reduce_record (borrow_id);

create index repayment_id_index
  on bo_overdue_reduce_record (repayment_id);



ALTER TABLE borrow_repayment ADD reduce_punish_amount decimal(18,2) DEFAULT 0 NULL COMMENT '累计违约金金额';
ALTER TABLE borrow_repayment ADD reduce_fine_amount decimal(18,2) DEFAULT 0 NOT NULL COMMENT '累计罚息金额';
