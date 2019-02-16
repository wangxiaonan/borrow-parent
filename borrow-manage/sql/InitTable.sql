-- auto-generated definition
CREATE TABLE bo_order_audit
(
  id          BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled    TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid        VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark      VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  order_id    BIGINT                             NOT NULL
  COMMENT '订单编号',
  auth_name   VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '审核名称',
  audit_key   VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '审核项key',
  audit_value VARCHAR(500) DEFAULT ''            NOT NULL
  COMMENT '审核项的value值',
  audit_state INT(3) DEFAULT '1'                 NOT NULL
  COMMENT '1通过 2 未通过',
  audit_time  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '审核时间',
  CONSTRAINT bo_order_audit_uuid_uindex
  UNIQUE (uuid)
)
  COMMENT '借款订单审核项表' ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


  -- auto-generated definition
CREATE TABLE bo_order_cost
(
  id          BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled    TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid        VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark      VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  order_id    BIGINT                             NOT NULL
  COMMENT '订单号',
  cost_key    VARCHAR(100)                       NOT NULL
  COMMENT '订单费用key',
  cost_amount DECIMAL(18, 2)                     NOT NULL
  COMMENT '费用金额',
  cost_desc   VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '费用描述',
  CONSTRAINT bo_order_cost_uuid_uindex
  UNIQUE (uuid)
)
  COMMENT '订单费用' ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
CREATE INDEX bo_order_cost_order_id_cost_key_index
  ON bo_order_cost (order_id, cost_key);

-- auto-generated definition
CREATE TABLE bo_order_pay_record
(
  id            BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled      TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid          VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user   INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user   INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark        VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  order_id      BIGINT                             NOT NULL
  COMMENT '订单编号',
  order_pay_id  BIGINT                             NOT NULL
  COMMENT '还款记录ID',
  pay_time      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '还款时间',
  pay_price     DECIMAL(18, 2)                     NOT NULL
  COMMENT '还款金额',
  pay_type      INT(3)                             NOT NULL
  COMMENT '1 正常还款 2 提前还款',
  pay_type_desc VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '描述',
  CONSTRAINT bo_order_pay_record_uuid_uindex
  UNIQUE (uuid),
  CONSTRAINT bo_order_pay_record_order_pay_id_uindex
  UNIQUE (order_pay_id)
)
  COMMENT '还款记录表' ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX bo_order_pay_record_order_id_index
  ON bo_order_pay_record (order_id);

-- auto-generated definition
CREATE TABLE bo_product_rate
(
  id          BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled    TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid        VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark      VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  p_uid       VARCHAR(32) DEFAULT ''             NOT NULL
  COMMENT '产品uid',
  p_code      VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '产品code',
  rate_key    VARCHAR(50)                        NOT NULL
  COMMENT '费用key',
  rate_value  VARCHAR(500) DEFAULT ''            NOT NULL
  COMMENT '费用利率值',
  rate_desc   VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '费率描述',
  CONSTRAINT bo_product_rate_uuid_uindex
  UNIQUE (uuid)
)
  COMMENT '产品利率表' ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX bo_product_rate_p_uid_index
  ON bo_product_rate (p_uid);

-- auto-generated definition
CREATE TABLE borrow_order
(
  id              BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled        TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid            VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user     INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time     DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user     INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark          VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  order_id        BIGINT                             NOT NULL
  COMMENT '订单编号',
  bues_type       INT(3) DEFAULT '1'                 NOT NULL
  COMMENT '贷款业务: 1 车贷',
  user_uid        VARCHAR(32)                        NOT NULL
  COMMENT '用户uid',
  product_uid     VARCHAR(32)                        NOT NULL
  COMMENT '产品uid',
  b_car_uid       VARCHAR(32) DEFAULT ''             NOT NULL
  COMMENT '客户车uid',
  product_name    VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '产品名称',
  p_code          VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '产品code',
  plate_number    VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '车牌号',
  salesman_uid    VARCHAR(32) DEFAULT ''             NOT NULL
  COMMENT '销售人员uid',
  sign_time       DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '销售时间',
  bo_price        DECIMAL(18, 2)                     NOT NULL
  COMMENT '借款金额',
  bo_finish_price DECIMAL(18, 2)                     NULL
  COMMENT '完成金额',
  bo_expect       INT(3)                             NOT NULL
  COMMENT '借款期限',
  bo_expect_unit  INT(3) DEFAULT '2'                 NOT NULL
  COMMENT '借款期限单位 年、月、日',
  bo_paytype      INT(3) DEFAULT '0'                 NOT NULL
  COMMENT '还款方式',
  bo_is_state     INT(3) DEFAULT '0'                 NOT NULL
  COMMENT '借款状态: 1 待放款 2 放款中 3已取消  4 已放款 5 还款中 6 已还款',
  bo_is_finish    INT(3) DEFAULT '0'                 NOT NULL
  COMMENT '放款是否完成  0未完成  1 已完成',
  bo_finish_time  DATETIME                           NULL
  COMMENT '放款日期',
  bo_pay_source   VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '还款来源',
  bo_pay_state    INT DEFAULT '0'                    NOT NULL
  COMMENT '还款状态:1正常 2 逾期',
  first_pay_time  DATETIME                           NULL
  COMMENT '首次还款日',
  last_pay_time   DATETIME                           NULL
  COMMENT '最后还款日',
  bo_pay_expect   INT(3) DEFAULT '0'                 NOT NULL
  COMMENT '已还期数',
  CONSTRAINT borrow_order_uuid_uindex
  UNIQUE (uuid),
  CONSTRAINT borrow_order_order_id_uindex
  UNIQUE (order_id)
)
  COMMENT '借款订单表' ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX borrow_order_user_uid_salesman_uid_index
  ON borrow_order (user_uid, salesman_uid);

-- auto-generated definition
CREATE TABLE borrow_product
(
  id            BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled      TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid          VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user   INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user   INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark        VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  p_name        VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '产品名称',
  buss_type     INT(3) DEFAULT '1'                 NOT NULL
  COMMENT '业务类型:车贷',
  p_type        INT(4) DEFAULT '0'                 NOT NULL
  COMMENT '产品类型',
  p_code        VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '产品编号',
  p_expect      INT DEFAULT '0'                    NOT NULL
  COMMENT '产品期限',
  p_expect_unit INT DEFAULT '2'                    NOT NULL
  COMMENT ' 1年 2月 3日',
  p_price_min   DECIMAL(18, 2) DEFAULT '0.00'      NOT NULL
  COMMENT '最低借款金额',
  p_price_max   DECIMAL(18, 2) DEFAULT '0.00'      NOT NULL
  COMMENT '最高借款金额',
  p_pay_type    INT(4) DEFAULT '0'                 NOT NULL
  COMMENT '还款方式 1:一次性还款',
  p_accept_type INT(4) DEFAULT '0'                 NOT NULL
  COMMENT '收款方式',
  p_enabled     INT(1) DEFAULT '1'                 NULL
  COMMENT '是否启用',
  CONSTRAINT borrow_product_uuid_uindex
  UNIQUE (uuid),
  CONSTRAINT borrow_product_p_code_uindex
  UNIQUE (p_code)
)
  COMMENT '借款产品表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- auto-generated definition
CREATE TABLE borrow_repayment
(
  id                  BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled            TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid                VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user         INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time         DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user         INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time         DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark              VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  repay_id            BIGINT                             NOT NULL
  COMMENT '分期还款订单号',
  order_id            BIGINT                             NOT NULL
  COMMENT '借款订单号',
  user_uid            VARCHAR(32)                        NOT NULL
  COMMENT '用户uid',
  repay_amount        DECIMAL(18, 2)                     NOT NULL
  COMMENT '还款金额',
  capital_amount      DECIMAL(18, 2)                     NOT NULL
  COMMENT '本金金额',
  br_time             DATE                               NOT NULL
  COMMENT '计划还款时间',
  br_repay_time       DATETIME                           NULL
  COMMENT '实际还款时间',
  repay_expect        INT(3)                             NOT NULL
  COMMENT '所属期数',
  interest_amount     DECIMAL(18, 2) DEFAULT '0.00'      NOT NULL
  COMMENT '利息金额',
  punish_amount       DECIMAL(18, 2) DEFAULT '0.00'      NOT NULL
  COMMENT '违约金',
  service_fee         DECIMAL(18, 2) DEFAULT '0.00'      NOT NULL
  COMMENT '平台服务费',
  early_pay_fee       DECIMAL(18, 2) DEFAULT '0.00'      NOT NULL
  COMMENT '提前还款服务费',
  repay_status        INT(3)                             NOT NULL
  COMMENT '1 未还款 2 已还款',
  bo_repay_status     INT(3)                             NOT NULL
  COMMENT '1.正常 2 逾期',
  repay_type          INT(3) DEFAULT '0'                 NOT NULL
  COMMENT '还款方式 1 正常还款  2提前还款 3 逾期还款',
  repay_finish_amount DECIMAL(18, 2)                     NULL
  COMMENT '实际支付的金额',
  CONSTRAINT borrow_repayment_uuid_uindex
  UNIQUE (uuid),
  CONSTRAINT borrow_repayment_repay_id_uindex
  UNIQUE (repay_id)
)
  COMMENT '还款计划表' ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX borrow_repayment_order_id_index
  ON borrow_repayment (order_id);

-- auto-generated definition
CREATE TABLE borrow_salesman
(
  id           BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled     TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid         VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user  INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user  INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark       VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  sales_name   VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '销售人员姓名',
  sales_mobile VARCHAR(20) DEFAULT ''             NOT NULL
  COMMENT '手机号',
  sales_idard  VARCHAR(20) DEFAULT ''             NOT NULL
  COMMENT '身份证号',
  CONSTRAINT borrow_salesman_uuid_uindex
  UNIQUE (uuid)
)
  COMMENT '销售人员' ENGINE=InnoDB DEFAULT CHARSET=utf8;


  -- auto-generated definition
CREATE TABLE user_car
(
  id           BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled     TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid         VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user  INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user  INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark       VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  car_name     VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '车名称',
  plate_number VARCHAR(100)                       NOT NULL
  COMMENT '车牌号',
  user_uid     VARCHAR(32)                        NOT NULL
  COMMENT '用户uid',
  CONSTRAINT user_car_uuid_uindex
  UNIQUE (uuid)
)
  COMMENT '用户车信息表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- auto-generated definition
CREATE TABLE user_info
(
  id            BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled      TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid          VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user   INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user   INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark        VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  user_name     VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '用户名',
  idcard        VARCHAR(20) DEFAULT ''             NOT NULL
  COMMENT '身份证号',
  mobile        VARCHAR(20) DEFAULT ''             NOT NULL
  COMMENT '手机号',
  credit_status INT(4) DEFAULT '0'                 NOT NULL
  COMMENT '征信状态  1 优 2 良 3 差 4 极差',
  credit_dec    VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '征信描述',
  industry      VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '属于行业',
  work_nature   VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '工作性质',
  user_earns    VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT '收入',
  CONSTRAINT user_info_uuid_uindex
  UNIQUE (uuid),
  CONSTRAINT user_info_idcard_uindex
  UNIQUE (idcard)
)
  COMMENT '用户表' ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX user_info_mobile_index
  ON user_info (mobile);

-- auto-generated definition
CREATE TABLE task_lock
(
  id          BIGINT AUTO_INCREMENT
  COMMENT '主键'
    PRIMARY KEY,
  disabled    TINYINT(1) DEFAULT '0'             NOT NULL
  COMMENT '删除标志',
  uuid        VARCHAR(32)                        NOT NULL
  COMMENT 'uuid',
  create_user INT DEFAULT '0'                    NOT NULL
  COMMENT '审核人',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
  COMMENT '创建时间',
  update_user INT DEFAULT '0'                    NOT NULL
  COMMENT '更新用户',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
  COMMENT '更新时间',
  remark      VARCHAR(100) DEFAULT ''            NOT NULL
  COMMENT '备注',
  type        INT(3)                             NOT NULL
  COMMENT '任务类型 1 逾期计算',
  exc_time    BIGINT                             NOT NULL
  COMMENT '执行日期 unix',
  staus       INT(3)                             NOT NULL
  COMMENT '状态 1进行中 2成功 3 失败',
  ip          VARCHAR(50) DEFAULT ''             NOT NULL
  COMMENT 'ip地址',
  CONSTRAINT task_lock_type_exc_time_pk
  UNIQUE (type, exc_time)
)
  COMMENT '任务执行表' ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO rc_loan.borrow_product (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_name, buss_type, p_type, p_code, p_expect, p_expect_unit, p_price_min, p_price_max, p_pay_type, p_accept_type, p_enabled) VALUES (0, 'CB72BF939C904601BD6401DA60244B9F', 0, '2018-09-15 09:40:40', 0, '2018-09-20 10:44:02', '', '车满金1号', 1, 0, '0001', 12, 2, 0.00, 99999999.00, 1, 0, 1);
INSERT INTO rc_loan.borrow_product (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_name, buss_type, p_type, p_code, p_expect, p_expect_unit, p_price_min, p_price_max, p_pay_type, p_accept_type, p_enabled) VALUES (0, '7706E93B48354838AA4612D378A624F5', 0, '2018-09-15 09:40:40', 0, '2018-09-21 21:28:51', '', '车满金2号', 1, 0, '0002', 12, 2, 0.00, 99999999.00, 1, 0, 1);


INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '120941E717724539863C83B0CC862A19', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', 'CB72BF939C904601BD6401DA60244B9F', '0001', 'EARLY_SERVICE_RATE', '0.01', '前期服务费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '0246A959BFFF4DDCB2C6CD4AA4B6F260', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', 'CB72BF939C904601BD6401DA60244B9F', '0001', 'MONTH_SERVICE_RATE', '0.015', '月服务费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, 'D2B43E8D1EF54B37A917C725B6600BA2', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', 'CB72BF939C904601BD6401DA60244B9F', '0001', 'MONTH_ACCRUAL_RATE', '0.005', '月利息率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '04852CFB9FC147B784E94B1E63336340', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', 'CB72BF939C904601BD6401DA60244B9F', '0001', 'GUARANTEE_VIOLATE_RATE', '0.00005', '担保方违约费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, 'BBD21493D7B448DD9F807893315CAD41', 0, '2018-09-15 09:57:20', 0, '2018-09-17 15:00:38', '', 'CB72BF939C904601BD6401DA60244B9F', '0001', 'SERVICE_VIOLATE_RATE', '0.00005', '平台违约金费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '2E0011E6DF834116887F04DC3BA8A8BE', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', 'CB72BF939C904601BD6401DA60244B9F', '0001', 'EARLY_PAY_RATE', '0.01', '提前还款费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '362196FA8F4F4705BF6BF079D2DA09FA', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', '7706E93B48354838AA4612D378A624F5', '0002', 'EARLY_SERVICE_RATE', '0.01', '前期服务费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '334DE2E060F9454A859A9B4E7FDCCC8E', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', '7706E93B48354838AA4612D378A624F5', '0002', 'MONTH_SERVICE_RATE', '0.015', '月服务费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '19EC73960A114EA2ABF9579E7198347B', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', '7706E93B48354838AA4612D378A624F5', '0002', 'MONTH_ACCRUAL_RATE', '0.005', '月利息率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, 'F2BD10C910BC4A128CB360B67AFE3DE9', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', '7706E93B48354838AA4612D378A624F5', '0002', 'GUARANTEE_VIOLATE_RATE', '0.00005', '担保方违约费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '65BC156C3F624295A626B5F3509361C9', 0, '2018-09-15 09:57:20', 0, '2018-09-17 16:29:57', '', '7706E93B48354838AA4612D378A624F5', '0002', 'SERVICE_VIOLATE_RATE', '0.00005', '平台违约金费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '64C16F4E03474BF598DEA83ECD8B3FF5', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', '7706E93B48354838AA4612D378A624F5', '0002', 'EARLY_PAY_RATE', '0.01', '提前还款费率');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '00D06056B5D24902BC139C0DABDF8445', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', '7706E93B48354838AA4612D378A624F5', '0002', 'GPS_COST', '100', 'GPS费');
INSERT INTO rc_loan.bo_product_rate (disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES (0, '9C0230A80743442D872A9EA298A1297A', 0, '2018-09-15 09:57:20', 0, '2018-09-17 11:40:41', '', 'CB72BF939C904601BD6401DA60244B9F', '0001', 'GPS_COST', '200', 'GPS费');


