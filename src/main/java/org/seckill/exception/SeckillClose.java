package org.seckill.exception;

/**秒杀关闭异常(Runtime)
 * @Author: 力子
 * @Description:
 * @Date: Created in 2:30 2016/10/29.
 */
public class SeckillClose extends RuntimeException{
    public SeckillClose(String message) {
        super(message);
    }

    public SeckillClose(String message, Throwable cause) {
        super(message, cause);
    }
}
