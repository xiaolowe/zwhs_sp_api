/*
 * 文 件 名:  ServiceSearch.java
 * 版    权:  CCDC Copyright 2016,  All rights reserved
 * 描    述:  服务商平台
 * 修 改 人:  Lanbo
 * 创建时间:  2016年1月26日
 */
package cn.org.citycloud.zwhs.bean;

/**
 * 服务列表查询
 * 
 * @author lanbo
 * @version [V1.0, 2016年1月26日]
 * @since [B2C/V1.0]
 */
public class ServiceSearch
{
    private String serviceName;
    
    private int type = 0;
    
    private int serviceState = -1;
    
    private int serviceVerify = -1;
    
    private int page = 1;
    
    private int pageSize = 10;
    
    /**
     * 获取 serviceName
     * 
     * @return 返回 serviceName
     */
    public String getServiceName()
    {
        return serviceName;
    }
    
    /**
     * 设置 serviceName
     * 
     * @param 对serviceName进行赋值
     */
    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }
    
    /**
     * 获取 type
     * 
     * @return 返回 type
     */
    public int getType()
    {
        return type;
    }
    
    /**
     * 设置 type
     * 
     * @param 对type进行赋值
     */
    public void setType(int type)
    {
        this.type = type;
    }
    
    /**
     * 获取 serviceState
     * 
     * @return 返回 serviceState
     */
    public int getServiceState()
    {
        return serviceState;
    }
    
    /**
     * 设置 serviceState
     * 
     * @param 对serviceState进行赋值
     */
    public void setServiceState(int serviceState)
    {
        this.serviceState = serviceState;
    }
    
    /**
     * 获取 page
     * 
     * @return 返回 page
     */
    public int getPage()
    {
        return page;
    }
    
    /**
     * 设置 page
     * 
     * @param 对page进行赋值
     */
    public void setPage(int page)
    {
        this.page = page;
    }
    
    /**
     * 获取 pageSize
     * 
     * @return 返回 pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }
    
    /**
     * 设置 pageSize
     * 
     * @param 对pageSize进行赋值
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    /**
     * 获取 serviceVerify
     * 
     * @return 返回 serviceVerify
     */
    public int getServiceVerify()
    {
        return serviceVerify;
    }
    
    /**
     * 设置 serviceVerify
     * 
     * @param 对serviceVerify进行赋值
     */
    public void setServiceVerify(int serviceVerify)
    {
        this.serviceVerify = serviceVerify;
    }
    
}
