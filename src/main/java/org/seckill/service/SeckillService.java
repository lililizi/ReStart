package org.seckill.service;

import org.seckill.domain.Seckill;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillException;
import org.seckill.exception.SeckillClose;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 力子
 * @Description:业务接口：站在使用者角度设计
 * 参数越简单越好，返回类型要友好 return 类型/异常
 * @Date: Created in 2:10 2016/10/29.
 */

public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     *  秒杀开启时输出秒杀接口地址，否则输出秒杀和系统时间
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userphone
     * @param md5
     */
    SeckillExecution excuteSeckill(long seckillId, long userphone, String md5)throws SeckillClose,SecKillException,RepeatKillException;

    /**
     * 执行秒杀操作by存储过程
     * @param seckillId
     * @param userphone
     * @param md5
     */
    SeckillExecution excuteSeckillPro(long seckillId, long userphone, String md5);

}
