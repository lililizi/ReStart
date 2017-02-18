package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.domain.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by touch on 2017/2/7.
 */
public class RedisDao {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private final JedisPool pool;
 private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
    public RedisDao(String ip ,int port) {
        this.pool=new JedisPool(ip,port);
    }
    public Seckill getSeckill(long seckillId){
        //redis操作邏輯
        try {
            Jedis jedis=pool.getResource();
            try {
                String key="seckillId:"+seckillId;
                //并沒有内部序列化操作
                //get-》byte【】-》反序列化-》seckill
                //采用自定義序列化
                byte[] bytes=jedis.get(key.getBytes());
                if (bytes!=null){
                    //空對象
                    Seckill seckill=schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    return seckill;
                }
            }finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    public String putSeckill(Seckill seckill){
        //set Object->序列化->byte[]
        try {
            Jedis jedis=pool.getResource();
            try{
                String key="seckillId:"+seckill.getSeckillId();
                byte[] bytes= ProtostuffIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout=3600;
                String Result=jedis.setex(key.getBytes(),timeout,bytes);
                return Result;
            }finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
