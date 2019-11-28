-- 还款计划日期 +1
update borrow_repayment set br_time = date_add(br_time,INTERVAL 1 DAY ) where order_id in (
  157964153355501568,
  149524491079254016,
  155162762010427392,
  146634851041148928
  );

