
begin ;
INSERT INTO borrow_product (
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
        '964EFA5807614ECCB6763941465E3AEF',
        0,
        CURRENT_TIMESTAMP,
        0,
        CURRENT_TIMESTAMP,
        '',
        '车贷12期-先息后本',
        1,
        0,
        '0008',
        12,
        2,
        0.00,
        99999999.00,
        2,
        0,
        1);



INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, 'DAD459058FFC4B29A0E4AE431EEDFC1B', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '964EFA5807614ECCB6763941465E3AEF', '0008', 'EARLY_SERVICE_RATE', '0', '前期服务费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, 'C53C123520FE4072834399431317221C', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '964EFA5807614ECCB6763941465E3AEF', '0008', 'MONTH_SERVICE_RATE', '0.009', '月服务费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '2D954E0E1438466E92431E5DB2BCE950', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '964EFA5807614ECCB6763941465E3AEF', '0008', 'MONTH_ACCRUAL_RATE', '0.009', '月利息率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '7F809CC2D5B0424982145C45E4EF3373', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '964EFA5807614ECCB6763941465E3AEF', '0008', 'GUARANTEE_VIOLATE_RATE', '0', '担保方违约费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '8E0A7A3B44DC421BA48DCE3783F16CE9', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '964EFA5807614ECCB6763941465E3AEF', '0008', 'SERVICE_VIOLATE_RATE', '0', '平台违约金费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '956E4418A1D54FE99643211C063DAF50', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '964EFA5807614ECCB6763941465E3AEF', '0008', 'EARLY_PAY_RATE', '0.01', '提前还款费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '3C10BE0B04FC48BCB0E998F55D2075A4', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '964EFA5807614ECCB6763941465E3AEF', '0008', 'GPS_COST', '0', 'GPS费');

commit ;



