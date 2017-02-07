package org.seckill.dto;

import org.seckill.domain.SuccessSecKill;
import org.seckill.enums.SeckillStatEnum;

/**封装秒杀执行后的结果
 * @Author: 力子
 * @Description:
 * @Date: Created in 2:23 2016/10/29.
 */
public class SeckillExecution {
    private long seckillId;
    //秒杀执行结果
    private int state;
    //状态表示
    private String stateInfo;
    //秒杀成功对象
    private SuccessSecKill successSecKill;

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateinfo();
    }

    public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessSecKill successSecKill) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateinfo();
        this.successSecKill = successSecKill;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessSecKill getSuccessSecKill() {
        return successSecKill;
    }

    public void setSuccessSecKill(SuccessSecKill successSecKill) {
        this.successSecKill = successSecKill;
    }
}
