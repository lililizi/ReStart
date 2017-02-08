--秒杀执行存储过程
DELIMITER $$--console；转换为$$
--参数：in 输入参数  out 输出参数
--count_count()返回上一条修改类型sql的影响行数
--ROW_COUNT:0表示未修改》0修改行数《0error
CREATE PROCEDURE `seckill`.`execute_seckill`
(in v_seckill_id bigint,in v_phone bigint,
in v_kill_time TIMESTAMP ,out r_result int)
BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION ;
    insert ignore INTO success_kill
    (seckill_id,user_phone,create_time)
    values (v_seckill_id,v_phone,v_kill_time);
    SELECT ROW_COUNT() into insert_count;
    if(insert_count=0)THEN
      ROLLBACK ;
      set r_result=-1;
    ELSEIF  (insert_count<0)THEN
      ROLLBACK ;
      set r_result=-2;
    ELSE
      UPDATE seckill SET NUMBER =NUMBER -1
      WHERE seckill_id=v_seckill_id
      AND end_time>v_kill_time
      AND start_time<v_kill_time
      AND number >0;
      SELECT ROW_COUNT() into insert_count;
        IF(insert_count=0)THEN
        ROLLBACK ;
      set r_result=0;
       ELSEIF  (insert_count<0)THEN
      ROLLBACK ;
      set r_result=-2;
       ELSE
       COMMIT ;
       set r_result=1;
       END  IF;
    END  IF;
END;
$$
--存储过程结束
DELIMITER ;
set @r_result=-3;
--执行存储过程
call execute_seckill(1003,13455566611122,now(),@r_result);
--获取结果
SELECT @r_result;

--存储过程
--1.存储过程优化：事务行级锁持有的时间
--2.不要过度依赖
--3.简单的逻辑可以应用
--4.QPS：一个秒杀单6000/qps
