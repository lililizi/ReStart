package org.seckill.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;


/**
 * @Author: 力子
 * @Description:
 * @Date: Created in 14:58 2016/10/26.
 */
@Configuration
//@PropertySource("jdbc.properties")
@MapperScan("org.seckill.dao")
@EnableTransactionManagement
public class DataConfig  {
    /**
     * 使用C3p0连接池123
     * @return
     */
    @Bean
    public ComboPooledDataSource dataSource(){
        ComboPooledDataSource dataSource=new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/seckill");
        dataSource.setUser("root");
        dataSource.setPassword("568355");
        dataSource.setMaxPoolSize(30);//连接池最大最小连接数量
        dataSource.setMinPoolSize(10);
        dataSource.setAutoCommitOnClose(false);//关闭连接后不自动commit
        dataSource.setCheckoutTimeout(1000);//获取连接超时时间
        dataSource.setAcquireRetryAttempts(2);//获取连接失败重试次数
        return dataSource;
    }

    /**
     * 配置SQLSessionFactoryBean
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
         SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());//注入连接池
      bean.setConfigLocation(new ClassPathResource("/mybatis-config.xml"));//配置mybatis全局配置文件
        bean.setTypeAliasesPackage("org.seckill.domain");
        /*// 设置查找器
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 自动扫描mybatis文件
        bean.setMapperLocations(resolver.getResources("classpath:org/seckill/dao*//*.xml"));*/
        //扫描mapper.xml
       // bean.setMapperLocations(new ClassPathResource[]{new ClassPathResource("classpath:mapper/*.xml")});
        return bean;
    }
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager transactionManager=new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}
