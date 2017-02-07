package org.seckill.exception;

/**秒杀相关业务异常
 * @Author: 力子
 * @Description:
 * @Date: Created in 2:31 2016/10/29.
 */
public class SecKillException  extends SeckillClose{
    public SecKillException(String message) {
        super(message);
    }

    public SecKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
