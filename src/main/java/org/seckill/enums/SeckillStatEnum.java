package org.seckill.enums;

/**
 * 使用枚举表示常量数据字典
 * @Author: 力子
 * @Description:
 * @Date: Created in 3:10 2016/10/29.
 */
public enum SeckillStatEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");
    private int state;
    private String stateinfo;

    public int getState() {
        return state;
    }

    public String getStateinfo() {
        return stateinfo;
    }

    SeckillStatEnum(int state, String stateinfo) {

        this.state = state;
        this.stateinfo = stateinfo;
    }
    public static SeckillStatEnum stateof(int index){
        for (SeckillStatEnum s:values()) {
            if(s.getState()==index)
                return s;
        }
        return null;
    }
}
