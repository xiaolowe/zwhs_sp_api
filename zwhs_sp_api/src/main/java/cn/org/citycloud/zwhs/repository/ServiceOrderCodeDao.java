/*
 * 文 件 名:  ServiceOrderCodeDao.java
 * 版    权:  CCDC Copyright 2016,  All rights reserved
 * 描    述:  服务商平台
 * 修 改 人:  Lanbo
 * 创建时间:  2016年1月27日
 */
package cn.org.citycloud.zwhs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.ServiceOrderCode;

/**
 * 服务消费码Dao
 * 
 * @author lanbo
 * @version [V1.0, 2016年1月27日]
 * @since [B2C/V1.0]
 */
public interface ServiceOrderCodeDao extends JpaRepository<ServiceOrderCode, Integer>,
    JpaSpecificationExecutor<ServiceOrderCode>
{
    public List<ServiceOrderCode> findByProviderIdAndUseStatus(int providerId, int useStatus);
    
    public ServiceOrderCode findByCodeValAndProviderId(String code, int providerId);
    
    public long countByOrderIdAndUseStatus(int orderId, int useStatus);
}
