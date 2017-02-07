package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.config.DataConfig;
import org.seckill.domain.SuccessSecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author: 力子
 * @Description:
 * @Date: Created in 11:26 2016/10/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfig.class)
public class SuccessKillDaoMapperTest {

    @Autowired
    SuccessKillDaoMapper mapper;
    @Test
    public void insertSuccessKill() throws Exception {
        System.out.println(mapper.insertSuccessKill(1000,13519117425L)+"--------------------------------------");
    }

    @Test
    public void queryByIdWithSecKill() throws Exception {
        SuccessSecKill successSecKill=mapper.queryByIdWithSecKill(1000,13519117425L);
        System.out.println(successSecKill.toString()+"---------------------------------------------------------");
    }

}