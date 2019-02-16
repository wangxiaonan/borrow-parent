
begin ;
INSERT INTO rc_loan.borrow_product (
                                    disabled,
                                    uuid,
                                    create_user,
                                    create_time,
                                    update_user,
                                    update_time,
                                    remark,
                                    p_name,
                                    buss_type,
                                    p_type,
                                    p_code,
                                    p_expect,
                                    p_expect_unit,
                                    p_price_min,
                                    p_price_max,
                                    p_pay_type,
                                    p_accept_type,
                                    p_enabled)
VALUES (
        0,
        '19CBBF6FD2274362918A6F456E846F90',
        0,
        CURRENT_TIMESTAMP,
        0,
        CURRENT_TIMESTAMP,
        '',
        '车贷3期-4%-2%',
        1,
        0,
        '0003',
        3,
        2,
        0.00,
        99999999.00,
        1,
        0,
        1);



INSERT INTO rc_loan.bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '5FBE375800194F6084E790B6FBF1954D', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '19CBBF6FD2274362918A6F456E846F90', '0003', 'EARLY_SERVICE_RATE', '0', '前期服务费率');
INSERT INTO rc_loan.bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '5525239959C34254802793B75DD5FA05', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '19CBBF6FD2274362918A6F456E846F90', '0003', 'MONTH_SERVICE_RATE', '0.04', '月服务费率');
INSERT INTO rc_loan.bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, 'ADD0A8B0FE1241E79236C3FD3A163988', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '19CBBF6FD2274362918A6F456E846F90', '0003', 'MONTH_ACCRUAL_RATE', '0.02', '月利息率');
INSERT INTO rc_loan.bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '6325F0FF5B9B4D28B01E4D801FA47872', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '19CBBF6FD2274362918A6F456E846F90', '0003', 'GUARANTEE_VIOLATE_RATE', '0', '担保方违约费率');
INSERT INTO rc_loan.bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, 'D3EA51276BEB4174AB6B5AB4F79D2C43', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '19CBBF6FD2274362918A6F456E846F90', '0003', 'SERVICE_VIOLATE_RATE', '0', '平台违约金费率');
INSERT INTO rc_loan.bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, 'F6C53BA683EC47FDAD64872D766FF13C', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '19CBBF6FD2274362918A6F456E846F90', '0003', 'EARLY_PAY_RATE', '0.01', '提前还款费率');
INSERT INTO rc_loan.bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, 'BBDDC1379D4F4CBE9156A5CE5D6C4051', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '19CBBF6FD2274362918A6F456E846F90', '0003', 'GPS_COST', '0', 'GPS费');

commit ;



