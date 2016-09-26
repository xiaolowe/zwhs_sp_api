package cn.org.citycloud.zwhs.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.org.citycloud.zwhs.constants.ErrorCodes;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.RegionInfo;
import cn.org.citycloud.zwhs.entity.Store;
import cn.org.citycloud.zwhs.repository.RegionInfoDao;
import cn.org.citycloud.zwhs.repository.StoreDao;
import cn.org.citycloud.zwhs.repository.StoreSgDao;
import cn.org.citycloud.zwhs.exception.BusinessErrorException;

/**
 * 门店相关API
 * @author lanbo
 *
 */
@Controller
@ResponseBody
public class StoreController extends BaseController {
	
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private StoreSgDao storeSgDao;
	@Autowired
	private RegionInfoDao regionInfoDao;
	/**
	 * 更新店铺模板信息
	 * @throws Exception 
	 */
	@RequestMapping(value = "/store", method = RequestMethod.PUT)
	public Object updateStoreInfo(@RequestBody Map<String, String> paramMap) throws Exception {		
		Store store = storeDao.findByStoreId(getStoreId());
		try {
			if(store == null) {
				throw new BusinessErrorException(ErrorCodes.NO_DATA, "此店铺信息不存在！");
			}
			if(paramMap.containsKey("content")){
				store.setContent(paramMap.get("content"));
			}
			if(paramMap.containsKey("storeName")){
				store.setCompanyName(paramMap.get("storeName"));
			}
			
			
			store.setUpdDate(new Date());
			storeDao.save(store);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}				
		return store;
	}
	
	
	/**
	 * 获取店铺信息
	 * @param store_id
	 * @return
	 */
	@RequestMapping(value = "/store", method = RequestMethod.GET)
	public Object getStore(){
		return storeDao.findByStoreId(getStoreId());
	}
	
	/**
	 * 获取店铺等级列表信息
	 * @return
	 */
	@RequestMapping(value="/sg",method=RequestMethod.GET)
	public Object getSg(){
		return storeSgDao.findAll();
	}
	
	/**
	 * 获取某个店铺等级信息
	 * @param sgId
	 * @return
	 */
	@RequestMapping(value="/sg/{sgId}",method=RequestMethod.GET)
	public Object getSgInfo(@PathVariable int sgId){
		return storeSgDao.findOne(sgId);
	}
	
	
	/**
	 * 更新门店logo
	 * @return
	 */
	@RequestMapping(value="/store/logo",method=RequestMethod.PUT)
	public Object updateLogo(@RequestBody Map<String, String> paramMap){
		String companyFax = paramMap.get("storeLogo");
		Store store = storeDao.findOne(getStoreId());
		store.setCompanyFax(companyFax);
		return storeDao.save(store);
	}
}
