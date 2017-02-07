package org.seckill.exception;



/**重复秒杀异常(RunTime)
 * @Author: 力子
 * @Description:
 * @Date: Created in 2:28 2016/10/29.
 */
public class RepeatKillException extends SecKillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
