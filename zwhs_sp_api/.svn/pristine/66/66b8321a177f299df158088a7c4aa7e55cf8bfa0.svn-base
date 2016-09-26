/*
 * 文 件 名:  ServiceProviderService.java
 * 版    权:  CCDC Copyright 2016,  All rights reserved
 * 描    述:  服务商平台
 * 修 改 人:  Lanbo
 * 创建时间:  2016年1月26日
 */
package cn.org.citycloud.zwhs.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.citycloud.zwhs.bean.SPCoverage;
import cn.org.citycloud.zwhs.bean.SPOutlets;
import cn.org.citycloud.zwhs.bean.ServiceBean;
import cn.org.citycloud.zwhs.bean.ServiceSearch;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.entity.RegionInfo;
import cn.org.citycloud.zwhs.entity.ServiceCoverageArea;
import cn.org.citycloud.zwhs.entity.ServiceInfo;
import cn.org.citycloud.zwhs.entity.ServiceOutlet;
import cn.org.citycloud.zwhs.entity.ServiceProvider;
import cn.org.citycloud.zwhs.repository.RegionInfoDao;
import cn.org.citycloud.zwhs.repository.ServiceCoverageAreaDao;
import cn.org.citycloud.zwhs.repository.ServiceInfoDao;
import cn.org.citycloud.zwhs.repository.ServiceOutletsDao;
import cn.org.citycloud.zwhs.repository.ServiceProviderDao;
import cn.org.citycloud.zwhs.repository.ServiceStoreDao;

/**
 * 服务商Service
 * 
 * @author lanbo
 * @version [V1.0, 2016年1月26日]
 * @since [B2C/V1.0]
 */
@Service
@Transactional
public class ServiceProviderService
{
    
    @Autowired
    private ServiceInfoDao serviceInfoDao;
    
    @Autowired
    private ServiceStoreDao serviceStoreDao;
    
    @Autowired
    private ServiceProviderDao serviceProviderDao;
    
    @Autowired
    private ServiceCoverageAreaDao coverageAreaDao;
    
    @Autowired
    private ServiceOutletsDao outletsDao;
    
    @Autowired
    private RegionInfoDao regionInfoDao;
    
    public Page<ServiceInfo> findServiceInfoList(ServiceSearch search, int storeId)
    {
        // 分页排序
        Sort sort = new Sort(Sort.Direction.DESC, "serviceId");
        // 分页
        Pageable pageable = new PageRequest(search.getPage() - 1, search.getPageSize(), sort);
        
        // 查询构造器
        Specification<ServiceInfo> spec = new Specification<ServiceInfo>()
        {
            
            @Override
            public Predicate toPredicate(Root<ServiceInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb)
            {
                Predicate predicate = cb.conjunction();
                
                // 服务商ID
                Path<Integer> providerId = root.get("providerId");
                predicate = cb.and(predicate, cb.equal(providerId, storeId));
                
                // 服务名称
                if (StringUtils.isNotBlank(search.getServiceName()))
                {
                    Path<String> serviceName = root.get("serviceName");
                    predicate = cb.and(predicate, cb.like(serviceName, "%" + search.getServiceName() + "%"));
                }
                // 服务分类
                if (search.getType() > 0)
                {
                    Path<Integer> type = root.get("type");
                    predicate = cb.and(predicate, cb.equal(type, search.getType()));
                }
                
                // 服务状态
                if (search.getServiceState() != -1)
                {
                    Path<Integer> serviceState = root.get("serviceState");
                    predicate = cb.and(predicate, cb.equal(serviceState, search.getServiceState()));
                }
                
                // 审核状态
                if (search.getServiceVerify() != -1)
                {
                    Path<Integer> serviceVerify = root.get("serviceVerify");
                    predicate = cb.and(predicate, cb.equal(serviceVerify, search.getServiceVerify()));
                }
                
                return query.where(predicate).getRestriction();
            }
            
        };
        return serviceInfoDao.findAll(spec, pageable);
        
    }
    
    /**
     * 
     * 服务商创建服务
     * 
     * @param service
     */
    public void createServices(ServiceBean service, int storeId)
    {
        
        ServiceInfo sInfo = new ServiceInfo();
        BeanUtils.copyProperties(service, sInfo);
        
        // 默认下线状态
        sInfo.setServiceState(Constants.SERVICE_STATE_OFFLINE);
        
        Date now = new Date();
        
        // 服务商信息
        ServiceProvider spInfo = serviceProviderDao.findOne(storeId);
        
        sInfo.setProviderId(storeId);
        sInfo.setContactsName(spInfo.getProviderOwner());
        sInfo.setContactsPhone(spInfo.getUserPhone());
        sInfo.setServiceAddtime(now);
        
        // 审核状态
        sInfo.setServiceVerify(Constants.SERVICE_VERIFY_NOTPASS);
        
        // 默认分成比例 70:20:10
        sInfo.setProviderRates(new BigDecimal(70));
        sInfo.setStoreRates(new BigDecimal(20));
        sInfo.setCommisRates(new BigDecimal(10));
        
        sInfo.setInsDate(now);
        sInfo.setUpdDate(now);
        
        // 保存服务信息
        sInfo = serviceInfoDao.save(sInfo);
        
        // 保存覆盖区域
        if (Constants.SERVICE_TYPE_CALL == service.getType())
        {
            List<ServiceCoverageArea> areaList = new ArrayList<ServiceCoverageArea>();
            for (SPCoverage coverage : service.getServiceCoverage())
            {
                ServiceCoverageArea scArea = new ServiceCoverageArea();
                BeanUtils.copyProperties(coverage, scArea);
                
                // 省市区县
                RegionInfo prov = regionInfoDao.findByRegionCode(coverage.getRegionProv());
                RegionInfo city = regionInfoDao.findByRegionCode(coverage.getRegionCity());
                RegionInfo area = regionInfoDao.findByRegionCode(coverage.getRegionArea());
                scArea.setRegionProvName(prov.getRegionName());
                scArea.setRegionCityName(city.getRegionName());
                scArea.setRegionAreaName(area.getRegionName());
                
                scArea.setServiceId(sInfo.getServiceId());
                scArea.setInsDate(now);
                scArea.setUpdDate(now);
                
                areaList.add(scArea);
            }
            coverageAreaDao.save(areaList);
        }
        
        // 服务网点
        if (Constants.SERVICE_TYPE_STORE == service.getType())
        {
            List<ServiceOutlet> outletList = new ArrayList<ServiceOutlet>();
            
            for (SPOutlets outlet : service.getServiceOutlets())
            {
                ServiceOutlet sOutlet = new ServiceOutlet();
                BeanUtils.copyProperties(outlet, sOutlet);
                
                sOutlet.setServiceId(sInfo.getServiceId());
                sOutlet.setStatus(1);
                sOutlet.setInsDate(now);
                sOutlet.setUpdDate(now);
                
                outletList.add(sOutlet);
            }
            
            outletsDao.save(outletList);
        }
        
    }
}
