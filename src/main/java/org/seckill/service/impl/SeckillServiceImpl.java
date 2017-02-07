package org.seckill.service.impl;

import org.seckill.dao.SecKillDaoMapper;
import org.seckill.dao.SuccessKillDaoMapper;
import org.seckill.domain.Seckill;
import org.seckill.domain.SuccessSecKill;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillException;
import org.seckill.exception.SeckillClose;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: 力子
 * @Description:
 * @Date: Created in 2:37 2016/10/29.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private final Logger logger=  LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecKillDaoMapper secKillDaoMapper;
    @Autowired
    private SuccessKillDaoMapper successKillDaoMapper;
    //md5盐值字符串，混淆MD5
    public final String slat="cvhqkjbwefohdvhasdfiw*()&^%@$#!";
    public List<Seckill> getSeckillList() {
        return secKillDaoMapper.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {

        return secKillDaoMapper.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill secKill=secKillDaoMapper.queryById(seckillId);
        if (secKill==null)
            return new Exposer(false,seckillId);
        Date start=secKill.getStartTime();
        Date end=secKill.getEndTime();
        Date now=new Date();
        if (now.getTime()<start.getTime()||now.getTime()>end.getTime())
            return new Exposer(false,seckillId,now.getTime(),start.getTime(),end.getTime());
        //转化特定字符串的过程
        String md5=getMd5(seckillId);
        return new Exposer(true,md5,seckillId);
    }
    private String getMd5(long id){
        String base=id+"/"+slat;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /**
     * 使用注解控制事务方法的优点：
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短 ，不要穿插其他网络操作（例如Rpc/http操作）或者剥离到方法外
     * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作，
     */
    public SeckillExecution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillClose, SecKillException, RepeatKillException {
        if (md5==null||!md5.equals(getMd5(seckillId)))
            throw new SecKillException("seckill data rewrite");
        //执行秒杀逻辑
        try {
            int updateCount = secKillDaoMapper.reduceNumber(seckillId, new Date());
            if (updateCount <= 0)
                throw new SeckillClose("seckill is close");
            else {
                int insertCount = successKillDaoMapper.insertSuccessKill(seckillId, userPhone);
                if (insertCount <= 0)
                    throw new RepeatKillException("seckill repeate");
                else {
                    SuccessSecKill successSecKill = successKillDaoMapper.queryByIdWithSecKill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successSecKill);
                }
            }
        } catch (RepeatKillException e){
            throw e;
        } catch (SeckillClose e){
            throw e;
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常转换为运行期异常  **spring会对运行期异常进行回滚
            throw new SecKillException("seckill innew error");
        }

    }

}
