ALTER TABLE borrow_repayment ADD surety_status int(3) DEFAULT 1 NOT NULL COMMENT '担保状态 1 未担保 2 已担保';
ALTER TABLE borrow_repayment ADD surety_time datetime DEFAULT '1970-01-01 08:00:00' NOT NULL COMMENT '垫付时间';
ALTER TABLE borrow_repayment ADD fine_amount decimal(18,2) DEFAULT 0 NOT NULL COMMENT '罚息';