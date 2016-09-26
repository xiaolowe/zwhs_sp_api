/*
 * 文 件 名:  StoreServiceOrderDao.java
 * 版    权:  CCDC Copyright 2016,  All rights reserved
 * 描    述:  服务商平台
 * 修 改 人:  Lanbo
 * 创建时间:  2016年1月27日
 */
package cn.org.citycloud.zwhs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.StoreServiceOrder;

/**
 * 服务订单Dao
 * 
 * @author lanbo
 * @version [V1.0, 2016年1月27日]
 * @since [B2C/V1.0]
 */
public interface StoreServiceOrderDao extends JpaRepository<StoreServiceOrder, Integer>,
    JpaSpecificationExecutor<StoreServiceOrder>
{
    public List<StoreServiceOrder> findByProviderId(int providerId);
    
    public StoreServiceOrder findByOrderIdAndProviderId(int orderId, int providerId);
}
