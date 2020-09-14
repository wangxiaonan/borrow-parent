-- 三个借款标的，8月份应还款状态改为已还

update borrow_order set bo_pay_expect = 8 where order_id = 183730923810848768;
update borrow_repayment set repay_status = 2 , repay_type =1 where order_id = 183730923810848768 and repay_expect in (3,4,5,6,7,8);

update borrow_order set bo_pay_expect = 8 where order_id = 181792348445868032;
update borrow_repayment set repay_status = 2 , repay_type =1 where order_id = 181792348445868032 and repay_expect in (3,4,5,6,7,8);




