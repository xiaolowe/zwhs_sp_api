package cn.org.citycloud.zwhs.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.org.citycloud.zwhs.bean.Commdity;
import cn.org.citycloud.zwhs.bean.Good;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.Brand;
import cn.org.citycloud.zwhs.entity.EvaluateGood;
import cn.org.citycloud.zwhs.entity.GoodsClass;
import cn.org.citycloud.zwhs.entity.GoodsCommon;
import cn.org.citycloud.zwhs.entity.StoreGood;
import cn.org.citycloud.zwhs.repository.BrandDao;
import cn.org.citycloud.zwhs.repository.EvaluateGoodDao;
import cn.org.citycloud.zwhs.repository.GoodsClassDao;
import cn.org.citycloud.zwhs.repository.GoodsCommonDao;
import cn.org.citycloud.zwhs.repository.StoreGoodDao;
import cn.org.citycloud.zwhs.service.StoreGoodService;
import cn.org.citycloud.zwhs.utils.StringUtils;

@Controller
@ResponseBody
public class CommodityController extends BaseController {
	
	@Autowired
	private StoreGoodService storeGoodService;
	
	@Autowired
	private StoreGoodDao storeGoodDao;
	
	@Autowired
	private EvaluateGoodDao evaluateGoodDao;
	
	@Autowired
	private GoodsCommonDao goodsCommonDao;
	
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private GoodsClassDao goodsClassDao;
	
	/**母婴店上架/下架商品查询
	 *
	 * @param paramMap
	 */
	@RequestMapping(value="/commodity/shelve",method=RequestMethod.GET)
	public Object getCommoditys(@Valid Good good){
		
		int page = good.getPage();
		int pageSize = good.getPageSize();
		Sort sort = new Sort(Sort.Direction.DESC,"goodsId");
		Pageable pageable = new PageRequest(page-1, pageSize,sort);
		Specification<StoreGood> spec = new Specification<StoreGood>() {
			@Override
			public Predicate toPredicate(Root<StoreGood> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate = cb.conjunction();
				Path<Integer> storeId = root.get("storeId");
				predicate = cb.and(predicate,cb.equal(storeId, getStoreId()));
				
				Path<Integer> goodsState = root.get("goodsState");
				Path<Date> offlineTime = root.get("offlineTime");
				Date now = new Date();
				int gstate = good.getGoodsState();
				if(gstate==1){					
					predicate = cb.and(predicate,cb.equal(goodsState, good.getGoodsState()));
					predicate = cb.and(predicate,cb.greaterThan(offlineTime, now));
				}else if(gstate==0){
					Predicate predicate2 = cb.and(cb.lessThanOrEqualTo(offlineTime, now),cb.notEqual(goodsState, 10));
					predicate = cb.and(predicate,cb.or(cb.equal(goodsState, good.getGoodsState()),predicate2));
				}else{
					predicate = cb.and(predicate,cb.equal(goodsState, good.getGoodsState()));
				}
				
				
				Path<String> goodsName = root.get("goodsName");
				
				Path<String> brandName = root.get("brandName");
			
				predicate = cb.and(predicate,cb.or(cb.like(goodsName, "%"+good.getSearchValue()+"%"),cb.like(brandName, "%"+good.getSearchValue()+"%")));
				if(good.getGcNo()!=0){
					Path<Integer> gcNo = root.get("gcNo");
					predicate = cb.and(predicate,cb.equal(gcNo, good.getGcNo()));
				}
				Path<Integer> stockNumber = root.get("stockNumber");
				predicate = cb.and(predicate,cb.gt(stockNumber, 0));
				return query.where(predicate).getRestriction();
			}
			
		};
		Page<StoreGood> results =  storeGoodDao.findAll(spec, pageable);
		//对已到下架时间但不是下架状态的实体进行修正
		if(good.getGoodsState()==0){
			for(StoreGood storeGood:results){
				storeGood.setGoodsState((byte)0);
			}
		}
		return results;
		
	}
	
	/**
	 * 告罄商品
	 * @param good
	 * @return
	 */
	@RequestMapping(value="/commodity/exhaust",method=RequestMethod.GET)
	public Object getExCommoditys(@Valid Good good){
		int page = good.getPage();
		int pageSize = good.getPageSize();
		Sort sort = new Sort(Sort.Direction.DESC,"goodsId");
		Pageable pageable = new PageRequest(page-1, pageSize,sort);
		Specification<StoreGood> spec = new Specification<StoreGood>() {
			@Override
			public Predicate toPredicate(Root<StoreGood> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate = cb.conjunction();
				Path<Integer> storeId = root.get("storeId");
				predicate = cb.and(predicate,cb.equal(storeId, getStoreId()));
				
				Path<Integer> goodsState = root.get("goodsState");
				predicate = cb.and(predicate,cb.equal(goodsState, 1));
				Path<Date> offlineTime = root.get("offlineTime");
				Date now = new Date();
				predicate = cb.and(predicate,cb.greaterThan(offlineTime, now));
				Path<String> goodsName = root.get("goodsName");
				
				Path<String> brandName = root.get("brandName");
			
				predicate = cb.and(predicate,cb.or(cb.like(goodsName, "%"+good.getSearchValue()+"%"),cb.like(brandName, "%"+good.getSearchValue()+"%")));
				if(good.getGcNo()!=0){
					Path<Integer> gcNo = root.get("gcNo");
					predicate = cb.and(predicate,cb.equal(gcNo, good.getGcNo()));
				}
				Path<Integer> stockNumber = root.get("stockNumber");
				predicate = cb.and(predicate,cb.equal(stockNumber, 0));
				return query.where(predicate).getRestriction();
			}
			
		};
		return storeGoodDao.findAll(spec, pageable);
	}
	
	/**syl商品库查询
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/commodity_syl",method=RequestMethod.GET)
	public Object getSylCommoditys(@Valid Good good){
		
		int page = good.getPage();
		int pageSize = good.getPageSize();
		Sort sort = new Sort(Sort.Direction.DESC,"goodsId");
		Pageable pageable = new PageRequest(page-1, pageSize,sort);
		Specification<GoodsCommon> spec = new Specification<GoodsCommon>() {
			@Override
			public Predicate toPredicate(Root<GoodsCommon> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate = cb.conjunction();
				Path<String> goodsName = root.get("goodsName");
				
				Path<String> brandName = root.get("brandName");
			
				predicate = cb.and(predicate,cb.or(cb.like(goodsName, "%"+good.getSearchValue()+"%"),cb.like(brandName, "%"+good.getSearchValue()+"%")));
				if(good.getGcNo()!=0){
					Path<Integer> gcNo = root.get("gcNo");
					predicate = cb.and(predicate,cb.equal(gcNo, good.getGcNo()));
				}
				if(good.getStoreId()>0){
					Path<Integer> storeId = root.get("storeId");
					predicate = cb.and(predicate,cb.equal(storeId, good.getStoreId()));
				}else{
					Path<Byte> goodsVerify = root.get("goodsVerify");
					predicate = cb.and(predicate,cb.equal(goodsVerify, 1));
				}			
				return query.where(predicate).getRestriction();
			}
			
		};
		return goodsCommonDao.findAll(spec, pageable);
	}
	
	/**商品申请
	 * 
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/commodity",method=RequestMethod.POST)
	public Object addCommodity(HttpServletResponse resp,@RequestBody @Valid Commdity commdity){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String goodsSn = commdity.getGoodsSn();
			GoodsCommon goodsCommon = goodsCommonDao.findByGoodsSn(goodsSn);
			if(goodsCommon!=null){
				resp.setStatus(403);
				resultMap.put("code", "2");
				resultMap.put("msg", "商品已存在!");
				return resultMap;
			}else{
				goodsCommon = new GoodsCommon();
				BeanUtils.copyProperties(commdity, goodsCommon);
				goodsCommon.setStoreId(getStoreId());
				int brand_no = commdity.getBrandNo();
				Brand brand = brandDao.findOne(brand_no);
				if(brand==null){
					resultMap.put("code", "2");
					resultMap.put("msg", "品牌不存在");
					return resultMap;
				}
				goodsCommon.setBrandName(brand.getBrandName());
				int gc_no = commdity.getGcNo();
				GoodsClass goodsClass = goodsClassDao.findOne(gc_no);
				if(goodsClass==null){
					resultMap.put("code", "2");
					resultMap.put("msg", "分类不存在");
					return resultMap;
				}
				goodsCommon.setGcName(goodsClass.getGcName());
				int gc_lit_no = commdity.getGcLitId();
				GoodsClass goodslit= goodsClassDao.findOne(gc_lit_no);
				if(goodslit==null){
					resultMap.put("code", "2");
					resultMap.put("msg", "分类不存在");
					return resultMap;
				}
				goodsCommon.setGcLitName(goodsClass.getGcName());
				//设置价格
				goodsCommon.setGoodsPrice(new BigDecimal(0));
				goodsCommon.setGoodsMarketprice(new BigDecimal(0));
				goodsCommon.setGoodsCostprice(new BigDecimal(0));	
				//end
				goodsCommon.setContactsName(getUserToken().getUserName());
				Date nowDate = new Date();
				goodsCommon.setInsDate(nowDate);
				goodsCommon.setUpdDate(nowDate);
				goodsCommonDao.save(goodsCommon);
				resultMap.put("code", "1");
				resultMap.put("msg", "申请完成");
				resultMap.put("content", goodsCommon);
				return resultMap;
			}
		} catch (Exception e) {
			// TODO: handle exception
			resp.setStatus(403);
			resultMap.put("code", "2");
			resultMap.put("msg", "申请失败!");
			return resultMap;
		}
		
	}
	
	/**查询某个商品详情
	 * 
	 * @param id
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/commodity/{id}",method=RequestMethod.GET)
	public Object getCommodityById(@PathVariable int id,@RequestParam Map<String, Object> paramMap){
//		Map<String, Object> resultMap = new HashMap<String, Object>();
		return storeGoodDao.findOne(id);
	} 
	
	
	/**查询中物华商某个商品详情
	 * 
	 * @param id
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/commodity_syl/{id}",method=RequestMethod.GET)
	public Object getCommoditySylById(@PathVariable int id){
		return goodsCommonDao.findOne(id);
	} 
	
	/**
	 * 查询某个商品的评论详情
	 * @param cdyId
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/comment/{id}",method=RequestMethod.GET)
	public Object getCommentByCdyId(@PathVariable int id,@RequestParam Map<String, Object> paramMap){
		return evaluateGoodDao.findByGoodsId(id);
	}
	
	/**
	 * 删除某个评论
	 * @param id
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/comment/{id}/{status}",method=RequestMethod.PUT)
	public Object deleteCommentById(@PathVariable int id,@PathVariable byte status,@RequestParam Map<String, Object> paramMap){
		EvaluateGood evaluateGood = evaluateGoodDao.findOne(id);
		evaluateGood.setGevalState(status);
		evaluateGood=evaluateGoodDao.save(evaluateGood);
		return evaluateGood;
	}
	/**
	 * 更新某个商品属性
	 * @param id
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/commodity/{id}",method=RequestMethod.PUT)
	public Object updateCommodityById(@PathVariable int id,@RequestBody Map<String, String> paramMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			StoreGood storeGood = storeGoodDao.findOne(id);
			String sellPrice = paramMap.get("sellPrice");
			storeGood.setGoodsSellprice(new BigDecimal(sellPrice));
			storeGood.setGoodsNumber(Integer.parseInt(paramMap.get("stockNumber")));
			storeGood.setStockNumber(Integer.parseInt(paramMap.get("stockNumber")));
			String time = paramMap.get("offlineTime");	
			storeGood.setOfflineTime(StringUtils.String2Date(time, "yyyy-MM-dd HH:mm:ss"));
			storeGood.setIsRecommend(Byte.parseByte(paramMap.get("isRecommend")));
			if(paramMap.containsKey("goodsBody")){
				storeGood.setGoodsBody(paramMap.get("goodsBody"));
			}
			if(paramMap.containsKey("goodsSpec")){
				storeGood.setGoodsSpec(paramMap.get("goodsSpec"));
			}
			Date now = new Date();
			storeGood.setUpdDate(now);
			storeGood = storeGoodDao.save(storeGood);
			resultMap.put("code", "1");
			resultMap.put("msg", "成功");
			resultMap.put("content", storeGood);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "2");
			resultMap.put("msg","失败");
		}
		
		return resultMap;
	}
	
	/**
	 * 上架某个商品(我的仓库)
	 * @param id
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/commodity/up/{id}/{status}",method=RequestMethod.PUT)
	public Object updateCommodityById(@PathVariable int id,@PathVariable byte status,@RequestBody Map<String, String> paramMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			StoreGood storeGood = storeGoodDao.findOne(id);
			String sellPrice = paramMap.get("sellPrice");
			storeGood.setGoodsSellprice(new BigDecimal(sellPrice));
			storeGood.setGoodsNumber(Integer.parseInt(paramMap.get("stockNumber")));
			storeGood.setStockNumber(Integer.parseInt(paramMap.get("stockNumber")));
			String time = paramMap.get("offlineTime");	
			storeGood.setOfflineTime(StringUtils.String2Date(time, "yyyy-MM-dd HH:mm:ss"));
			storeGood.setGoodsState(status);
			storeGood.setIsRecommend(Byte.parseByte(paramMap.get("isRecommend")));
			if(paramMap.containsKey("goodsBody")){
				storeGood.setGoodsBody(paramMap.get("goodsBody"));
			}
			if(paramMap.containsKey("goodsSpec")){
				storeGood.setGoodsSpec(paramMap.get("goodsSpec"));
			}
			Date now = new Date();
			storeGood.setUpdDate(now);
			storeGood.setInsDate(now);
			storeGood = storeGoodDao.save(storeGood);
			resultMap.put("code", "1");
			resultMap.put("msg", "成功");
			resultMap.put("content", storeGood);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "2");
			resultMap.put("msg","失败");
		}
		
		return resultMap;
	}
	
	/**
	 * 上架某个商品(中物华商商品库)
	 * @param id
	 * @param status
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/commodity/up/{id}/{status}",method=RequestMethod.POST)
	public Object saveCommodity(@PathVariable int id,@PathVariable byte status,@RequestBody Map<String, String> paramMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			GoodsCommon goodsCommon = goodsCommonDao.findOne(id);
			StoreGood storeGood = new StoreGood();
			storeGood = setSGfromGc(storeGood, goodsCommon);
			storeGood.setStoreId(getStoreId());
			String sellPrice = paramMap.get("sellPrice");
			storeGood.setGoodsSellprice(new BigDecimal(sellPrice));
			storeGood.setGoodsNumber(Integer.parseInt(paramMap.get("stockNumber")));
			storeGood.setStockNumber(Integer.parseInt(paramMap.get("stockNumber")));
			String time = paramMap.get("offlineTime");	
			storeGood.setOfflineTime(StringUtils.String2Date(time, "yyyy-MM-dd HH:mm:ss"));
			storeGood.setGoodsState(status);
			storeGood.setIsRecommend(Byte.parseByte(paramMap.get("isRecommend")));
			if(paramMap.containsKey("goodsBody")){
				storeGood.setGoodsBody(paramMap.get("goodsBody"));
			}
			if(paramMap.containsKey("goodsSpec")){
				storeGood.setGoodsSpec(paramMap.get("goodsSpec"));
			}
			storeGood = storeGoodDao.save(storeGood);
			resultMap.put("code", "1");
			resultMap.put("msg", "成功");
			resultMap.put("content", storeGood);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMap.put("code", "2");
			resultMap.put("msg","失败");
		}
		
		return resultMap;
	}
	
	/**
	 * 下架某个商品
	 * @param id
	 * @param status
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/commodity/down/{id}/{status}",method=RequestMethod.PUT)
	public Object updateGoodById(@PathVariable int id,@PathVariable byte status,@RequestBody Map<String, String> paramMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			StoreGood storeGood = storeGoodDao.findOne(id);			
			storeGood.setGoodsState(status);
			storeGood = storeGoodDao.save(storeGood);
			resultMap.put("code", "1");
			resultMap.put("msg", "成功");
			resultMap.put("content", storeGood);
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("code", "2");
			resultMap.put("msg","失败");
		}
		
		return resultMap;
	}
	
	/**
	 * 品牌
	 * @return
	 */
	@RequestMapping(value="/brand",method=RequestMethod.GET)
	public Object getBrand(){
		return brandDao.findAll();
	}
	
	@RequestMapping(value="/brand/1",method=RequestMethod.GET)
	public Object getBrandByName(@RequestParam String brandName){
		return brandDao.findFirst10ByBrandNameContaining(brandName);
	}
	
	/**
	 * 分类
	 * @return
	 */
	@RequestMapping(value="/class",method=RequestMethod.GET)
	public Object getGoodClass(){
		return goodsClassDao.findByGcParentId(0);
	}
	
	/**
	 * 子分类
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/class/{fatherid}",method=RequestMethod.GET)
	public Object getGoodClassByParentId(@PathVariable int fatherid){
		return goodsClassDao.findByGcParentId(fatherid);
	}
	
	
	private StoreGood setSGfromGc(StoreGood storeGood,GoodsCommon goodsCommon){
		storeGood.setBrandName(goodsCommon.getBrandName());
		storeGood.setBrandNo(goodsCommon.getBrandNo());
		storeGood.setGoodsSn(goodsCommon.getGoodsSn());
		storeGood.setGoodsName(goodsCommon.getGoodsName());
		storeGood.setGcNo(goodsCommon.getGcNo());
		storeGood.setGcName(goodsCommon.getGcName());
		storeGood.setGcLitId(goodsCommon.getGcLitId());
		storeGood.setGcLitName(goodsCommon.getGcLitName());
		storeGood.setGoodsSpec(goodsCommon.getGoodsSpec());
		storeGood.setGoodsImage(goodsCommon.getGoodsImage());
		storeGood.setGoodsImageSmall(goodsCommon.getGoodsImageSmall());
		storeGood.setGoodsAttr(goodsCommon.getGoodsAttr());
		storeGood.setGoodsPrice(goodsCommon.getGoodsPrice());
		storeGood.setGoodsMarketprice(goodsCommon.getGoodsMarketprice());
		storeGood.setGoodsCostprice(goodsCommon.getGoodsCostprice());
		storeGood.setGoodsBody(goodsCommon.getGoodsBody());
		Date now  = new Date();
		storeGood.setInsDate(now);
		storeGood.setUpdDate(now);
		return storeGood;
	}
}
