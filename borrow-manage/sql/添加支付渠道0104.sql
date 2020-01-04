ALTER TABLE borrow_order ADD pay_channel int(3) DEFAULT 1 NOT NULL COMMENT '1金诚 2易联';
ALTER TABLE borrow_repayment ADD pay_channel int(3) DEFAULT 1 NOT NULL COMMENT '1金诚 2易联';
