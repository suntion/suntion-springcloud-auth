package com.suntion.auth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.suntion.auth.mapper.AuthUserMapper;
import com.suntion.auth.model.AuthUser;
import com.suntion.auth.service.MiaoshaService;
import com.suntion.auth.service.RedissonLocker;

import java.util.concurrent.TimeUnit;

/**
 * @author Suntion
 */
@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired
    AuthUserMapper authUserMapper;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedissonLocker redissonLocker;

    private static final String GOOD_STOCK = "GOOD_STOK:";
    private static final String GOOD_USER = "GOOD_USER:";
    private static final String GOOD = "GOOD:";


    @Override
    @Transactional
    public boolean miaosha(final String goodid, final String userid) {
        if (StringUtils.isEmpty(goodid)) {
            throw new NullPointerException("goodid not be null");
        }
        if (StringUtils.isEmpty(userid)) {
            throw new NullPointerException("userid not be null");
        }

        // 库存判断
        if (redisTemplate.hasKey(GOOD_STOCK + goodid)) {
            int stock = (Integer) redisTemplate.opsForValue().get(GOOD_STOCK + goodid);
            if (stock <= 0) {
                throw new RuntimeException("无库存");
            }
        }

        //查询是否已经抢过
        if (redisTemplate.hasKey(GOOD_USER + goodid + userid)) {
            throw new RuntimeException("you are already miaosha");
        }


        //查询商品
        AuthUser authUser = null;
        if (redisTemplate.hasKey(GOOD + goodid)) {
            authUser = (AuthUser) redisTemplate.opsForValue().get(GOOD + goodid);
        } else {
            authUser = authUserMapper.selectById(goodid);
            if (authUser == null) {
                throw new NullPointerException("not found good by goodid");
            }
            redisTemplate.opsForValue().set(GOOD + goodid, authUser);
        }

        //库存判断
        if (redisTemplate.hasKey(GOOD_STOCK + goodid).equals(Boolean.FALSE)) {
            redisTemplate.opsForValue().set(GOOD_STOCK + goodid, Integer.parseInt(authUser.getAccount()));
        }

        try {
            //加锁
            redissonLocker.lock("redis_lock." + GOOD_STOCK, TimeUnit.SECONDS, 5);
            int goodsstock = (Integer) redisTemplate.opsForValue().get(GOOD_STOCK + goodid);
            if (goodsstock <= 0) {
                throw new RuntimeException("库存不足");
            }
            redisTemplate.boundValueOps(GOOD_STOCK + goodid).increment(-1);

            //插入记录
            AuthUser miaosha = new AuthUser();
            miaosha.setAccount(userid);
            authUserMapper.insert(miaosha);
            redisTemplate.opsForValue().set(GOOD_USER + goodid + userid, goodid + userid);

            //秒杀完 更新数据库
            if ((goodsstock - 1) <= 0) {
                AuthUser updateStock = authUserMapper.selectById(goodid);
                updateStock.setAccount(goodsstock - 1 + "");
                authUserMapper.updateAllColumnById(updateStock);
            }
        } catch (Exception e) {
            throw new RuntimeException("现在人太多了, 请稍等再试");
        } finally {
            //释放锁
            redissonLocker.unlock("redis_lock." + GOOD_STOCK);
        }
        return true;
    }


}
