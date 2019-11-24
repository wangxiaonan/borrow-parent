-- 订单 145878720974946304 修复 原因 是理财端自己操作已经还款
update borrow_repayment set repay_status = 2 where repay_id in (
148773156344037376,
148773156344038400,
148773156344039424,
148773156344040448,
148773156344041472 );



update borrow_order set bo_pay_expect = 5 where order_id = 145878720974946304;

-- 逾期计算导致订单还款异常。


update borrow_repayment set repay_finish_amount = 0 , repay_status=1 where repay_id = 145903505083729920;
update borrow_order set bo_pay_expect = 5 where order_id = 148771546805043200;