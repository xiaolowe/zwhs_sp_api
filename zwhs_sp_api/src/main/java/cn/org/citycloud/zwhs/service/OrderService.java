package cn.org.citycloud.zwhs.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.org.citycloud.zwhs.bean.Order;
import cn.org.citycloud.zwhs.entity.StoreServiceOrder;
import cn.org.citycloud.zwhs.repository.StoreServiceOrderDao;

@Component
public class OrderService
{
    
    @Autowired
    private StoreServiceOrderDao storeOrderDao;
    
    public Page<StoreServiceOrder> getOrderList(Order order)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "orderId");
        Pageable pageable = new PageRequest(order.getPage() - 1, order.getPageSize(), sort);
        Specification<StoreServiceOrder> spec = new Specification<StoreServiceOrder>()
        {
            
            @Override
            public Predicate toPredicate(Root<StoreServiceOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb)
            {
                Predicate predicate = cb.conjunction();
                Path<Integer> providerId = root.get("providerId");
                predicate = cb.and(predicate, cb.equal(providerId, order.getProviderId()));
                int status = order.getStatus();
                if (status != -1)
                {
                    Path<Integer> orderStatus = root.get("orderStatus");
                    predicate = cb.and(predicate, cb.equal(orderStatus, status));
                }
                int oId = order.getOrderId();
                if (oId != 0)
                {
                    Path<Integer> orderId = root.get("orderId");
                    predicate = cb.and(predicate, cb.equal(orderId, oId));
                }
                // 服务名称
                String serviceName = order.getServiceName();
                if (StringUtils.isNotBlank(serviceName))
                {
                    Path<String> serviceNamePath = root.get("serviceName");
                    predicate = cb.and(predicate, cb.like(serviceNamePath, "%" + serviceName + "%"));
                }
                
                // 服务类型
                int serviceType = order.getServiceType();
                if (serviceType != -1)
                {
                    Path<Integer> serviceTypePath = root.get("serviceType");
                    predicate = cb.and(predicate, cb.equal(serviceTypePath, serviceType));
                }
                
                String memName = order.getMemName();
                if (StringUtils.isNotBlank(memName))
                {
                    Path<String> contactsName = root.get("contactsName");
                    predicate = cb.and(predicate, cb.like(contactsName, "%" + memName + "%"));
                }
                String cyName = order.getCompanyName();
                if (StringUtils.isNotBlank(cyName))
                {
                    Path<String> companyName = root.get("companyName");
                    predicate = cb.and(predicate, cb.like(companyName, "%" + cyName + "%"));
                }
                if (StringUtils.isNotBlank(order.getStartTime()) && StringUtils.isNotBlank(order.getEndTime()))
                {
                    Path<Date> addTime = root.get("addTime");
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date stime = new Date();
                    Date etime = new Date();
                    try
                    {
                        stime = df.parse(order.getStartTime() + " 00:00:00");
                        etime = df.parse(order.getEndTime() + " 23:59:59");
                    }
                    catch (ParseException e)
                    {
                    }
                    
                    predicate = cb.and(predicate, cb.between(addTime, stime, etime));
                    
                }
                return query.where(predicate).getRestriction();
            }
            
        };
        return storeOrderDao.findAll(spec, pageable);
    }
}
