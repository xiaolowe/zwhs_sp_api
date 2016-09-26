package cn.org.citycloud.zwhs.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.zwhs.bean.Order;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.constants.ErrorCodes;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.StoreServiceOrder;
import cn.org.citycloud.zwhs.exception.BusinessErrorException;
import cn.org.citycloud.zwhs.repository.StoreOrderDao;
import cn.org.citycloud.zwhs.repository.StoreServiceOrderDao;
import cn.org.citycloud.zwhs.service.OrderService;

@RestController
public class OrderController extends BaseController
{
    
    @Autowired
    private StoreOrderDao storeOrderDao;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private StoreServiceOrderDao serviceOrderDao;
    
    /**
     * 订单列表信息
     * 
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public Object getOrders(@Valid Order order)
    {
        
        order.setProviderId(getStoreId());
        return orderService.getOrderList(order);
    }
    
    /**
     * 某个订单的详情
     * 
     * @param id
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public Object getOrderById(@PathVariable int id)
    {
        
        return serviceOrderDao.findByOrderIdAndProviderId(id, getStoreId());
    }
    
    /**
     * 服务商上门订单派单
     * 
     * @throws BusinessErrorException
     */
    @RequestMapping(value = "/acceptOrder/{id}", method = RequestMethod.POST)
    public void createAcceptOrder(@PathVariable int id)
        throws BusinessErrorException
    {
        StoreServiceOrder order = serviceOrderDao.findByOrderIdAndProviderId(id, getStoreId());
        
        if (order == null)
        {
            throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单不存在。");
        }
        
        if (Constants.SERVICE_ORDER_PAYED != order.getOrderStatus()
            || Constants.SERVICE_TYPE_CALL != order.getServiceType())
        {
            
            throw new BusinessErrorException(ErrorCodes.ORDER_ERROR, "此订单没有支付或者不是上门服务订单。");
            
        }
        
        order.setOrderStatus(Constants.SERVICE_ORDER_ACCEPT);
        Date now = new Date();
        order.setUpdDate(now);
        serviceOrderDao.save(order);
    }
    
}
