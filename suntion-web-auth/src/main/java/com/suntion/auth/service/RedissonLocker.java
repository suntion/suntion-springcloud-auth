package com.suntion.auth.service;

import java.util.concurrent.TimeUnit;

/**
 * @author Suntion
 */
public interface RedissonLocker {

    /**
     * 获取锁，如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。
     *
     * @param lockKey 锁
     */
    void lock(String lockKey);

    /**
     * 释放锁
     *
     * @param lockKey 锁
     */
    void unlock(String lockKey);

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     *
     * @param lockKey 锁
     * @param timeout 超时时间
     */
    void lock(String lockKey, int timeout);

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     *
     * @param lockKey 锁
     * @param unit    时间单位 TimeUnit
     * @param timeout 超时时间
     */
    void lock(String lockKey, TimeUnit unit, int timeout);

    /**
     * 尝试获取锁，获取到立即返回true,未获取到立即返回false
     *
     * @param lockKey 锁
     * @return 获取锁结果
     */
    boolean tryLock(String lockKey);


    /**
     * 尝试获取锁，在等待时间内获取到锁则返回true,否则返回false,如果获取到锁，则要么执行完后程序释放锁，
     * 要么在给定的超时时间leaseTime后释放锁
     *
     * @param lockKey   锁
     * @param waitTime  等待时间
     * @param leaseTime 超时时间
     * @param unit      时间单位
     * @return 获取锁结果
     * @throws InterruptedException
     */
    boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit)
            throws InterruptedException;

    /**
     * 锁是否被任意一个线程锁持有
     *
     * @param lockKey 锁
     * @return 结果
     */
    boolean isLocked(String lockKey);


}
