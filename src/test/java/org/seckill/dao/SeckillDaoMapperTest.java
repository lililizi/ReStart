package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.domain.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.seckill.config.DataConfig;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**需配置spring junit整合，junit启动时加载spring容器
 * @Author: 力子
 * @Description:
 * @Date: Created in 15:36 2016/10/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration(classes = DataConfig.class)
public class SeckillDaoMapperTest {
    //注入dao实现类依赖
    @Resource
    private SecKillDaoMapper dao;

    @Test
    public void queryById() throws Exception {
            long id=1000;
        Seckill secKill=dao.queryById(id);
        System.out.println(secKill.toString());
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> secKills=dao.queryAll(1,3);
        for (Seckill s:secKills
             ) {
            System.out.println(s.toString());
        }
    }

    @Test
    public void reduceNumber() throws Exception {
        int i=dao.reduceNumber(1000,new Date());
        System.out.println(i+"-------------------------------------------------------");
    }

}