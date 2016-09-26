package cn.org.citycloud.zwhs.bean;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 服务Bean
 * 
 * @author lanbo
 * @version [V1.0, 2016年1月26日]
 * @since [B2C/V1.0]
 */
public class ServiceBean
{
    
    /**
     * 服务名称
     */
    @NotEmpty
    private String serviceName;
    
    // Logo
    @NotEmpty
    private String serviceLogo;
    
    // 服务配图
    @NotEmpty
    private String servicePic1;
    
    private String servicePic2;
    
    private String servicePic3;
    
    private String servicePic4;
    
    private String servicePic5;
    
    // 服务内容
    @NotEmpty
    private String remark;
    
    // 服务类型(1:上门;2:到店)
    @Min(1)
    private int type;
    
    // 子分类 暂时不要
    
    // 金额
    @Min(0)
    private BigDecimal servicePrice;
    
    // 营业时间
    @NotNull
    private Time businessStart;
    
    @NotNull
    private Time businessEnd;
    
    // 覆盖区域
    @Valid
    private List<SPCoverage> serviceCoverage = new ArrayList<SPCoverage>();
    
    // 服务网点
    @Valid
    private List<SPOutlets> serviceOutlets = new ArrayList<SPOutlets>();
    
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
     * 获取 serviceLogo
     * 
     * @return 返回 serviceLogo
     */
    public String getServiceLogo()
    {
        return serviceLogo;
    }
    
    /**
     * 设置 serviceLogo
     * 
     * @param 对serviceLogo进行赋值
     */
    public void setServiceLogo(String serviceLogo)
    {
        this.serviceLogo = serviceLogo;
    }
    
    /**
     * 获取 servicePic1
     * 
     * @return 返回 servicePic1
     */
    public String getServicePic1()
    {
        return servicePic1;
    }
    
    /**
     * 设置 servicePic1
     * 
     * @param 对servicePic1进行赋值
     */
    public void setServicePic1(String servicePic1)
    {
        this.servicePic1 = servicePic1;
    }
    
    /**
     * 获取 servicePic2
     * 
     * @return 返回 servicePic2
     */
    public String getServicePic2()
    {
        return servicePic2;
    }
    
    /**
     * 设置 servicePic2
     * 
     * @param 对servicePic2进行赋值
     */
    public void setServicePic2(String servicePic2)
    {
        this.servicePic2 = servicePic2;
    }
    
    /**
     * 获取 servicePic3
     * 
     * @return 返回 servicePic3
     */
    public String getServicePic3()
    {
        return servicePic3;
    }
    
    /**
     * 设置 servicePic3
     * 
     * @param 对servicePic3进行赋值
     */
    public void setServicePic3(String servicePic3)
    {
        this.servicePic3 = servicePic3;
    }
    
    /**
     * 获取 servicePic4
     * 
     * @return 返回 servicePic4
     */
    public String getServicePic4()
    {
        return servicePic4;
    }
    
    /**
     * 设置 servicePic4
     * 
     * @param 对servicePic4进行赋值
     */
    public void setServicePic4(String servicePic4)
    {
        this.servicePic4 = servicePic4;
    }
    
    /**
     * 获取 servicePic5
     * 
     * @return 返回 servicePic5
     */
    public String getServicePic5()
    {
        return servicePic5;
    }
    
    /**
     * 设置 servicePic5
     * 
     * @param 对servicePic5进行赋值
     */
    public void setServicePic5(String servicePic5)
    {
        this.servicePic5 = servicePic5;
    }
    
    /**
     * 获取 remark
     * 
     * @return 返回 remark
     */
    public String getRemark()
    {
        return remark;
    }
    
    /**
     * 设置 remark
     * 
     * @param 对remark进行赋值
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
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
     * 获取 servicePrice
     * 
     * @return 返回 servicePrice
     */
    public BigDecimal getServicePrice()
    {
        return servicePrice;
    }
    
    /**
     * 设置 servicePrice
     * 
     * @param 对servicePrice进行赋值
     */
    public void setServicePrice(BigDecimal servicePrice)
    {
        this.servicePrice = servicePrice;
    }
    
    /**
     * 获取 businessStart
     * 
     * @return 返回 businessStart
     */
    public Time getBusinessStart()
    {
        return businessStart;
    }
    
    /**
     * 设置 businessStart
     * 
     * @param 对businessStart进行赋值
     */
    public void setBusinessStart(Time businessStart)
    {
        this.businessStart = businessStart;
    }
    
    /**
     * 获取 businessEnd
     * 
     * @return 返回 businessEnd
     */
    public Time getBusinessEnd()
    {
        return businessEnd;
    }
    
    /**
     * 设置 businessEnd
     * 
     * @param 对businessEnd进行赋值
     */
    public void setBusinessEnd(Time businessEnd)
    {
        this.businessEnd = businessEnd;
    }
    
    /**
     * 获取 serviceCoverage
     * 
     * @return 返回 serviceCoverage
     */
    public List<SPCoverage> getServiceCoverage()
    {
        return serviceCoverage;
    }
    
    /**
     * 设置 serviceCoverage
     * 
     * @param 对serviceCoverage进行赋值
     */
    public void setServiceCoverage(List<SPCoverage> serviceCoverage)
    {
        this.serviceCoverage = serviceCoverage;
    }
    
    /**
     * 获取 serviceOutlets
     * 
     * @return 返回 serviceOutlets
     */
    public List<SPOutlets> getServiceOutlets()
    {
        return serviceOutlets;
    }
    
    /**
     * 设置 serviceOutlets
     * 
     * @param 对serviceOutlets进行赋值
     */
    public void setServiceOutlets(List<SPOutlets> serviceOutlets)
    {
        this.serviceOutlets = serviceOutlets;
    }
    
}
