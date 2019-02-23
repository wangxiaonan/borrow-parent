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
        '178444AC11794BED955C70B2D4CF8367',
        0,
        CURRENT_TIMESTAMP,
        0,
        CURRENT_TIMESTAMP,
        '',
        '车贷24期-等额本息',
        1,
        0,
        '0009',
        24,
        2,
        0.00,
        99999999.00,
        3,
        0,
        1);



INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '3DF822CF178D44A488D2040EDD5CCF49', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '178444AC11794BED955C70B2D4CF8367', '0009', 'EARLY_SERVICE_RATE', '0', '前期服务费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '104CDD928196443B90D9BDFF7AB668B8', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '178444AC11794BED955C70B2D4CF8367', '0009', 'MONTH_SERVICE_RATE', '0.007', '月服务费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '8360B9BBB9CF420CA6C11C636613ACC3', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '178444AC11794BED955C70B2D4CF8367', '0009', 'MONTH_ACCRUAL_RATE', '0.007', '月利息率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, 'A597344DE9FC4BEF8E6F2C975448A403', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '178444AC11794BED955C70B2D4CF8367', '0009', 'GUARANTEE_VIOLATE_RATE', '0', '担保方违约费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, 'B140B313423B4B29B4D7E621F3D9B3F9', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '178444AC11794BED955C70B2D4CF8367', '0009', 'SERVICE_VIOLATE_RATE', '0', '平台违约金费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, 'CF3B1747C7B04CBF8CEB1D4BDD8E8855', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '178444AC11794BED955C70B2D4CF8367', '0009', 'EARLY_PAY_RATE', '0.01', '提前还款费率');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '6961571B94AB440BAA6827141AB84373', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '178444AC11794BED955C70B2D4CF8367', '0009', 'GPS_COST', '0', 'GPS费');
INSERT INTO bo_product_rate ( disabled, uuid, create_user, create_time, update_user, update_time, remark, p_uid, p_code, rate_key, rate_value, rate_desc) VALUES ( 0, '7DB19E2CE7104943A27EBCD2D1CEFB75', 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, '', '178444AC11794BED955C70B2D4CF8367', '0009', 'FINE_SERVICE_RATE', '0.0016', '罚息率');

commit ;