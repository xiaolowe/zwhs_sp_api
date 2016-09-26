package cn.org.citycloud.zwhs.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.zwhs.bean.CodeSearch;
import cn.org.citycloud.zwhs.bean.FirstPage;
import cn.org.citycloud.zwhs.bean.Order;
import cn.org.citycloud.zwhs.bean.chart.ChartData;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.constants.ErrorCodes;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.FinAcc;
import cn.org.citycloud.zwhs.entity.ServiceOrderCode;
import cn.org.citycloud.zwhs.entity.Store;
import cn.org.citycloud.zwhs.entity.StoreServiceOrder;
import cn.org.citycloud.zwhs.entity.StoreSg;
import cn.org.citycloud.zwhs.exception.BusinessErrorException;
import cn.org.citycloud.zwhs.repository.FinAccDao;
import cn.org.citycloud.zwhs.repository.ServiceOrderCodeDao;
import cn.org.citycloud.zwhs.repository.StoreDao;
import cn.org.citycloud.zwhs.repository.StoreOrderDao;
import cn.org.citycloud.zwhs.repository.StoreSgDao;
import cn.org.citycloud.zwhs.service.OrderService;
import cn.org.citycloud.zwhs.service.ServiceOrderService;

@RestController
public class FirstPageController extends BaseController
{
    
    @Autowired
    private FinAccDao finAccDao;
    
    @Autowired
    private StoreDao storeDao;
    
    @Autowired
    private StoreOrderDao storeOrderDao;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private StoreSgDao storeSgDao;
    
    @Autowired
    private ServiceOrderService sOrderService;
    
    @Autowired
    private ServiceOrderCodeDao sOrderCodeDao;
    
    /**
     * 
     * 服务券密码验证记录列表
     * 
     * @param code
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public Object serviceCodeCheckHist(CodeSearch search)
    {
        search.setProviderId(getStoreId());
        
        return sOrderService.findServiceOrderCodeList(search);
    }
    
    /**
     * 
     * 到店服务码查询
     * 
     * @param code
     * @throws BusinessErrorException
     */
    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public Object serviceCodeCheck(@PathVariable String code)
        throws BusinessErrorException
    {
        
        ServiceOrderCode codeInfo = getServiceOrderCodeInfo(code);
        
        return codeInfo;
    }
    
    /**
     * 
     * 到店服务码确定使用
     * 
     * @param code
     * @throws BusinessErrorException
     */
    @RequestMapping(value = "/code/{code}", method = RequestMethod.POST)
    public void serviceCodeCheckConfirm(@PathVariable String code)
        throws BusinessErrorException
    {
        
        ServiceOrderCode codeInfo = getServiceOrderCodeInfo(code);
        
        sOrderService.serviceCodeCheckConfirm(codeInfo);
        
    }
    
    /**
     * 首页图表
     * 
     * @return
     */
    @RequestMapping(value = "/chart", method = RequestMethod.GET)
    public Object getChart()
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ChartData xAxis = getxaxis();
        resultMap.put("xAxis", xAxis);
        List<ChartData> series = new ArrayList<ChartData>();
        // 获取图表数据
        Order order = new Order();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        order.setEndTime(df.format(now));
        Calendar cl = Calendar.getInstance();
        cl.setTime(now);
        cl.add(Calendar.DAY_OF_MONTH, -6);
        order.setStartTime(df.format(cl.getTime()));
        order.setProviderId(getStoreId());
        order.setPageSize(99999);
        Page<StoreServiceOrder> storeOrders = orderService.getOrderList(order);
        // end
        ChartData orders = new ChartData();
        ChartData amounts = new ChartData();
        orders.setName("订单数");
        orders.setType("line");
        amounts.setName("成交额");
        amounts.setType("line");
        List<Object> orderList = new ArrayList<Object>();
        List<Object> amountList = new ArrayList<Object>();
        List<Date> startTime = getStartData();
        List<Date> endTime = getEndData();
        for (int i = 0; i < startTime.size(); i++)
        {
            int size = 0;
            BigDecimal amount = new BigDecimal(0);
            for (StoreServiceOrder storeOrder : storeOrders)
            {
                Date orderTime = storeOrder.getAddTime();
                if (orderTime.after(startTime.get(i)) && orderTime.before(endTime.get(i)))
                {
                    size++;
                    if (storeOrder.getOrderStatus() > 10)
                    {
                        amount = amount.add(storeOrder.getOrderAmount());
                    }
                    
                }
            }
            orderList.add(size);
            amountList.add(amount);
        }
        orders.setData(orderList);
        amounts.setData(amountList);
        series.add(orders);
        series.add(amounts);
        resultMap.put("series", series);
        return resultMap;
    }
    
    /**
     * 首页数据
     * 
     * @return
     */
    @RequestMapping(value = "/firstPage", method = RequestMethod.GET)
    public Object getInfo()
    {
        int storeId = getStoreId();
        FirstPage firstPage = new FirstPage();
        if (storeId == 0)
        {
        }
        else
        {
            FinAcc finAcc = finAccDao.findByAccNoAndAccType(storeId, Constants.ACC_TYPE_PROVIDER);
            if (finAcc != null)
            {
                firstPage.setAccBal(finAcc.getAccBal());
            }
            
            Store store = storeDao.findByStoreId(storeId);
            if (store != null)
            {
                firstPage.setTotaMember(store.getWechatMembers().size());
                firstPage.setCompanyAddress(store.getCompanyAddress());
                if (store.getSgId() != null)
                {
                    StoreSg storeSg = storeSgDao.findOne((int)store.getSgId());
                    if (storeSg != null)
                    {
                        firstPage.setSgId(storeSg.getSgId());
                        firstPage.setSgName(storeSg.getSgName());
                    }
                }
                
            }
            
            int size = storeOrderDao.getSize(storeId);
            BigDecimal amount = storeOrderDao.getAllAmount(storeId);
            if (amount == null)
                amount = new BigDecimal(0);
            firstPage.setTotalOrder(size);
            firstPage.setTotalAmount(amount);
        }
        
        return firstPage;
    }
    
    private ChartData getxaxis()
    {
        List<Object> datas = new ArrayList<Object>();
        ChartData xAxis = new ChartData();
        xAxis.setName("x");
        xAxis.setType("category");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        datas.add(df.format(now));
        Calendar cl = Calendar.getInstance();
        cl.setTime(now);
        for (int i = 1; i < 7; i++)
        {
            cl.add(Calendar.DAY_OF_MONTH, -1);
            datas.add(df.format(cl.getTime()));
        }
        List<Object> dst = reverseOb(datas);
        xAxis.setData(dst);
        return xAxis;
    }
    
    private List<Date> getStartData()
    {
        List<Date> dates = new ArrayList<Date>();
        Date now = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(now);
        cl.set(Calendar.HOUR_OF_DAY, 0);
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        dates.add(cl.getTime());
        for (int i = 1; i < 7; i++)
        {
            cl.add(Calendar.DAY_OF_MONTH, -1);
            dates.add(cl.getTime());
        }
        List<Date> dst = reverse(dates);
        return dst;
    }
    
    private List<Date> getEndData()
    {
        List<Date> dates = new ArrayList<Date>();
        Date now = new Date();
        Calendar cl = Calendar.getInstance();
        cl.setTime(now);
        cl.set(Calendar.HOUR_OF_DAY, 23);
        cl.set(Calendar.MINUTE, 59);
        cl.set(Calendar.SECOND, 59);
        dates.add(cl.getTime());
        for (int i = 1; i < 7; i++)
        {
            cl.add(Calendar.DAY_OF_MONTH, -1);
            dates.add(cl.getTime());
        }
        List<Date> dst = reverse(dates);
        return dst;
    }
    
    private List<Date> reverse(List<Date> source)
    {
        List<Date> dest = new ArrayList<Date>();
        for (int i = 0; i < source.size(); i++)
        {
            int k = source.size() - 1 - i;
            dest.add(source.get(k));
        }
        return dest;
    }
    
    private List<Object> reverseOb(List<Object> source)
    {
        List<Object> dest = new ArrayList<Object>();
        for (int i = 0; i < source.size(); i++)
        {
            int k = source.size() - 1 - i;
            dest.add(source.get(k));
        }
        return dest;
    }
    
    /**
     * 获取服务券密码
     * 
     * @param code
     * @return
     * @throws BusinessErrorException
     */
    private ServiceOrderCode getServiceOrderCodeInfo(String code)
        throws BusinessErrorException
    {
        if (!StringUtils.isNumeric(code) || code.length() != 12)
        {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "您输入的效验码格式不正确。");
        }
        
        ServiceOrderCode codeInfo = sOrderCodeDao.findByCodeValAndProviderId(code, getStoreId());
        
        // code无法识别
        if (codeInfo == null)
        {
            throw new BusinessErrorException(ErrorCodes.SERVICE_CODE_ERROR, "您输入的效验码无法识别，请检查后重新校验。");
        }
        
        // 已使用
        if (1 == codeInfo.getUseStatus())
        {
            throw new BusinessErrorException(ErrorCodes.SERVICE_CODE_ERROR, "对不起，您输入的效验码已经被使用过了。");
            
        }
        return codeInfo;
    }
}
