package cn.org.citycloud.zwhs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.zwhs.bean.Page;
import cn.org.citycloud.zwhs.bean.Pay;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.repository.CashRecoderDao;
import cn.org.citycloud.zwhs.repository.FinAccDao;
import cn.org.citycloud.zwhs.repository.StoreDao;
import cn.org.citycloud.zwhs.service.PayService;

@RestController
public class PayController extends BaseController
{
    
    @Autowired
    private CashRecoderDao cashRecoderDao;
    
    @Autowired
    private StoreDao storeDao;
    
    @Autowired
    private FinAccDao finAccDao;
    
    @Autowired
    private PayService payService;
    
    /**
     * 历史提款列表
     * 
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/pay/list", method = RequestMethod.GET)
    public Object payList(@Valid Page page)
    {
        Sort sort = new Sort(Sort.Direction.DESC, "applyTime");
        Pageable pageable = new PageRequest(page.getPage() - 1, page.getPageSize(), sort);
        return cashRecoderDao.findByStoreIdAndAccType(getStoreId(), Constants.ACC_TYPE_PROVIDER, pageable);
    }
    
    /**
     * 收款详情
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/pay/{cashNo}", method = RequestMethod.GET)
    public Object payInfo(@PathVariable int cashNo)
    {
        return cashRecoderDao.findByCashNo(cashNo);
    }
    
    /**
     * 申请提款
     * 
     * @return
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void payApply(@RequestBody @Valid Pay pay)
        throws Exception
    {
        pay.setStoreId(getStoreId());
        pay.setApplyUserId(getUserToken().getUserId());
        payService.applyPay(pay);
    }
    
    /**
     * 母婴店财务信息
     * 
     * @param storeId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public Object payMoney()
    {
        return finAccDao.findByAccNoAndAccType(getStoreId(), Constants.ACC_TYPE_PROVIDER);
    }
    
}
