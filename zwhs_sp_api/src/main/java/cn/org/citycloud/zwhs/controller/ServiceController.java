package cn.org.citycloud.zwhs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.zwhs.bean.ServiceBean;
import cn.org.citycloud.zwhs.bean.ServiceSearch;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.constants.ErrorCodes;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.ServiceInfo;
import cn.org.citycloud.zwhs.exception.BusinessErrorException;
import cn.org.citycloud.zwhs.repository.RegionInfoDao;
import cn.org.citycloud.zwhs.repository.ServiceCoverageAreaDao;
import cn.org.citycloud.zwhs.repository.ServiceInfoDao;
import cn.org.citycloud.zwhs.repository.ServiceOutletsDao;
import cn.org.citycloud.zwhs.repository.ServiceProviderDao;
import cn.org.citycloud.zwhs.repository.ServiceStoreDao;
import cn.org.citycloud.zwhs.service.ServiceProviderService;

/**
 * 
 * 服务管理控制器
 * 
 * @author lanbo
 * @version [V1.0, 2016年1月26日]
 * @since [B2C/V1.0]
 */
@RestController
public class ServiceController extends BaseController
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
    
    @Autowired
    private ServiceProviderService spService;
    
    /**
     * 获取服务信息库列表
     * 
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public Object getServices(@Valid ServiceSearch search)
    {
        
        Page<ServiceInfo> serviceList = spService.findServiceInfoList(search, getStoreId());
        
        return serviceList;
    }
    
    /**
     * 创建服务
     * 
     * @throws BusinessErrorException
     */
    @RequestMapping(value = "/service", method = RequestMethod.POST)
    public void createServices(@RequestBody ServiceBean service)
        throws BusinessErrorException
    {
        // 检查覆盖区域
        if (Constants.SERVICE_TYPE_CALL == service.getType() && service.getServiceCoverage().size() == 0)
        {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "创建的服务没有选择覆盖区域。");
        }
        
        // 检查服务网点
        if (Constants.SERVICE_TYPE_STORE == service.getType() && service.getServiceOutlets().size() == 0)
        {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "创建的服务没有创建服务网点。");
        }
        
        spService.createServices(service, getStoreId());
        
    }
    
    /**
     * 修改服务 只能修改 服务价格
     */
    // @RequestMapping(value = "/service/{serviceId}", method = RequestMethod.PUT)
    // public Object editServices()
    // {
    //
    // return null;
    // }
    
    /**
     * 更新某个服务信息
     * 
     * @param id
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/service/{serviceId}", method = RequestMethod.PUT)
    public Object updateSrvById(@PathVariable int id, @RequestParam Map<String, Object> paramMap)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        return resultMap;
    }
    
    /**
     * 上/下架某个服务
     * 
     * @param id
     * @param status
     * @return
     * @throws BusinessErrorException
     */
    @RequestMapping(value = "/service/{id}/{serviceStatus}", method = RequestMethod.PUT)
    public void updateSrvById(@PathVariable int id, @PathVariable int serviceStatus)
        throws BusinessErrorException
    {
        ServiceInfo info = serviceInfoDao.findOne(id);
        
        if (info != null)
        {
            // 审核未通过 不能上下架操作
            if (Constants.SERVICE_VERIFY_PASS != info.getServiceVerify())
            {
                throw new BusinessErrorException(ErrorCodes.SERVICE_CODE_ERROR, "审核未通过的服务不能上下架。");
            }
            
            if (Constants.SERVICE_STATE_NORMAL == serviceStatus || Constants.SERVICE_STATE_OFFLINE == serviceStatus)
            {
                info.setServiceState(serviceStatus);
                
                serviceInfoDao.save(info);
            }
        }
    }
}
