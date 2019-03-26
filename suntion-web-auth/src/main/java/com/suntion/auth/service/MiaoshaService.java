package com.suntion.auth.service;

/**
 * @author Suntion
 */
public interface MiaoshaService {

    /**
     * 商品秒杀
     *
     * @param goodid 商品
     * @param userid 秒杀用户
     * @return 秒杀结果
     */
    boolean miaosha(String goodid, String userid);
}
