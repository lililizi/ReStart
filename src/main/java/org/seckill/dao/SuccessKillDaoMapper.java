package org.seckill.dao;

import org.seckill.domain.SuccessSecKill;

/**
 * @Author: 力子
 * @Description:
 * @Date: Created in 14:41 2016/10/26.
 */
public interface SuccessKillDaoMapper {
    /**
     * 插入购买明细,可过滤重复
     * @param SeccessKillId
     * @param userphone
     * @return 插入的结果及数量（行数）
     */
    int insertSuccessKill(long SeccessKillId,long userphone);

    /**
     *  根据id查询SuccessKill并携带产品对象实体
     * @param seckillId
     * @return
     */
    SuccessSecKill queryByIdWithSecKill(long seckillId,long userphone);

}
