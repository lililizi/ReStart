package org.seckill.dao;

import org.seckill.domain.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by touch on 2016/10/26.
 */

public interface SecKillDaoMapper {
    /**
     * 减库存
     * @param secKillId
     * @param killTime
     * @return 如果影响行数>1,表示表示更新的记录行数
     */
    public int reduceNumber(long secKillId, Date killTime);

    /**
     *根据id查询秒杀对象
     * @param secKillId
     * @return
     */
    public Seckill queryById(long secKillId);

    /**
     * 根据偏移量查询商品列表
     * @param offet
     * @param limit
     * @return
     */
    public List<Seckill> queryAll(int offet,int limit);
}
