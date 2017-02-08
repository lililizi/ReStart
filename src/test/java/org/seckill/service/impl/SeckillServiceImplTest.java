package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.config.DataConfig;
import org.seckill.config.RootConfig;
import org.seckill.domain.Seckill;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClose;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 力子
 * @Description:
 * @Date: Created in 16:18 2016/10/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class, RootConfig.class})
public class SeckillServiceImplTest {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list=seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id=1000;
        Seckill seckill=seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id=1001;
        Exposer exposer=seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){
            logger.info("exposer={}", exposer);
            long phone=158029798029L;
           try{
               SeckillExecution execution=seckillService.excuteSeckill(id,phone,exposer.getMd5());
               logger.info("execution={}",execution);
           }catch (RepeatKillException e)
           {
               logger.equals(e);
           }catch (SeckillClose e){
               logger.equals(e);
           }

        }else{
                logger.warn("exposer={}",exposer);

        }

            /*
        * exposed=true,
         * md5='d9239b32a2616ece252643e58e196e89',
        * seckillId=1000,
        * now=0, start=0, end=0}
        *
        * */
    }

    @Test
    public void excuteSeckill() throws Exception {
        long id=1000;
        long phone=158029798029L;
        String md5="d9239b32a2616ece252643e58e196e89";
        SeckillExecution execution=seckillService.excuteSeckill(id,phone,md5);
        logger.info("execution={}",execution);
    }
    @Test
    public void pro(){
        long seckillId=1000;
        long phone =13519117425L;
        Exposer exposer=seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution se=seckillService.excuteSeckillPro(seckillId,phone,md5);
            logger.info(se.getStateInfo());
        }

    }
}