/*
 * 文 件 名:  ServiceOrderService.java
 * 版    权:  CCDC Copyright 2016,  All rights reserved
 * 描    述:  服务商平台
 * 修 改 人:  Lanbo
 * 创建时间:  2016年1月27日
 */
package cn.org.citycloud.zwhs.service;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.citycloud.zwhs.bean.CodeSearch;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.entity.ServiceOrderCode;
import cn.org.citycloud.zwhs.entity.StoreServiceOrder;
import cn.org.citycloud.zwhs.repository.ServiceOrderCodeDao;
import cn.org.citycloud.zwhs.repository.StoreServiceOrderDao;

/**
 * 服务订单Service
 * 
 * @author lanbo
 * @version [V1.0, 2016年1月27日]
 * @since [B2C/V1.0]
 */
@Service
@Transactional
public class ServiceOrderService
{
    @Autowired
    private ServiceOrderCodeDao serviceCodeDao;
    
    @Autowired
    private StoreServiceOrderDao serviceOrderDao;
    
    /**
     * 
     * 查询服务验证码记录
     * 
     * @param providerId
     * @return
     */
    public Page<ServiceOrderCode> findServiceOrderCodeList(CodeSearch search)
    {
        
        // 分页排序
        Sort sort = new Sort(Sort.Direction.DESC, "updDate");
        // 分页
        Pageable pageable = new PageRequest(search.getPage() - 1, search.getPageSize(), sort);
        
        // 查询构造器
        Specification<ServiceOrderCode> spec = new Specification<ServiceOrderCode>()
        {
            
            @Override
            public Predicate toPredicate(Root<ServiceOrderCode> root, CriteriaQuery<?> query, CriteriaBuilder cb)
            {
                Predicate predicate = cb.conjunction();
                
                // 服务商ID
                Path<Integer> providerId = root.get("providerId");
                predicate = cb.and(predicate, cb.equal(providerId, search.getProviderId()));
                
                // 使用状态
                Path<Integer> useStatus = root.get("useStatus");
                predicate = cb.and(predicate, cb.equal(useStatus, 1));
                
                return query.where(predicate).getRestriction();
            }
            
        };
        
        return serviceCodeDao.findAll(spec, pageable);
    }
    
    /**
     * 服务券密码确认使用
     */
    public void serviceCodeCheckConfirm(ServiceOrderCode codeInfo)
    {
        // 更新订单状态
        int orderId = codeInfo.getOrderId();
        
        StoreServiceOrder order = serviceOrderDao.findOne(orderId);
        
        codeInfo.setUseStatus(1);
        
        Date now = new Date();
        codeInfo.setUseDate(now);
        codeInfo.setUpdDate(now);
        
        // 更新服务券使用状态
        serviceCodeDao.save(codeInfo);
        
        long countCodes = serviceCodeDao.countByOrderIdAndUseStatus(orderId, 0);
        
        // 服务券全部使用完了
        if (0 == countCodes)
        {
            order.setOrderStatus(Constants.SERVICE_ORDER_USED);
            order.setFinishTime(now);
            order.setUpdDate(now);
            
            serviceOrderDao.save(order);
        }
    }
    
}
