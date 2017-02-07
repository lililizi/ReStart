package org.seckill.domain;

import java.util.Date;

/**
 * Created by touch on 2016/10/26.
 */
public class SuccessSecKill {
    private long seckillId;
    private long userphone;
    private short state;
    private Date createTime;

    private Seckill seckill;

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill secKill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessSecKill{" +
                "secKillId=" + seckillId +
                ", userphone=" + userphone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long secKillId) {
        this.seckillId = secKillId;
    }

    public long getUserphone() {
        return userphone;
    }

    public void setUserphone(long userphone) {
        this.userphone = userphone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
