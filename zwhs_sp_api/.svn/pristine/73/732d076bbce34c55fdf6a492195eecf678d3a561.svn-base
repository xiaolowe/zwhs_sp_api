package cn.org.citycloud.zwhs.controller;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.zwhs.bean.Coupon;
import cn.org.citycloud.zwhs.bean.CouponSearch;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.StoreCoupon;
import cn.org.citycloud.zwhs.repository.StoreCouponDao;
import cn.org.citycloud.zwhs.utils.MyUtil;

@RestController
public class CouponController extends BaseController {
	
	@Autowired
	private StoreCouponDao storeCouponDao;
	
	/**
	 * 添加门店优惠劵
	 * @param coupon
	 * @return
	 */
	@RequestMapping(value="/coupon",method=RequestMethod.POST)
	public Object addCoupon(@RequestBody @Valid Coupon coupon){
		Date now = new Date();
		coupon.setInsDate(now);
		coupon.setUpdDate(now);
		coupon.setStoreId(getStoreId());		
		StoreCoupon storeCoupon = new StoreCoupon();
		try {
			coupon.setEffectiveTime(MyUtil.convertMin(coupon.getEffectiveTime()));
			coupon.setExpirationTime(MyUtil.convertMax(coupon.getExpirationTime()));
			BeanUtils.copyProperties(coupon, storeCoupon);
			storeCoupon = storeCouponDao.save(storeCoupon);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return storeCoupon;
	}
	
	/**
	 * 获取门店优惠劵列表
	 * @param couponSearch
	 * @return
	 */
	@RequestMapping(value="/coupon",method=RequestMethod.GET)
	public Object getCoupons(@Valid CouponSearch couponSearch){
		Sort sort = new Sort(Sort.Direction.DESC,"updDate");
		Pageable pageable = new PageRequest(couponSearch.getPage()-1, couponSearch.getPageSize(), sort);
		Specification<StoreCoupon> spec = new Specification<StoreCoupon>() {

			@Override
			public Predicate toPredicate(Root<StoreCoupon> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate = cb.conjunction();
				Path<Integer> storeId = root.get("storeId");
				predicate = cb.and(predicate,cb.equal(storeId, getStoreId()));
				String coupon_name = couponSearch.getCouponName();
				if(coupon_name.length()>0){
					Path<String> couponName = root.get("couponName");
					predicate = cb.and(predicate,cb.like(couponName, "%"+coupon_name+"%"));
				}
				int status = couponSearch.getStatus();
				Path<Date> effectiveTime = root.get("effectiveTime");
				Path<Date> expirationTime = root.get("expirationTime");
				Path<Byte> couponStatus = root.get("couponStatus");
				Date now = new Date();
				if(status==2){
					predicate = cb.and(predicate,cb.greaterThan(effectiveTime, now));
					predicate = cb.and(predicate,cb.equal(couponStatus, 10));
				}
				if(status==3){
					predicate = cb.and(predicate,cb.and(cb.lessThan(effectiveTime, now),cb.greaterThan(expirationTime, now)));
					predicate = cb.and(predicate,cb.equal(couponStatus, 10));
				}
				if(status==4){
					predicate = cb.and(predicate,cb.or(cb.lessThan(expirationTime, now),cb.equal(couponStatus, 20)));
				}
				return query.where(predicate).getRestriction();
			}
		};
		return storeCouponDao.findAll(spec, pageable);
	}
	
	/**
	 * 优惠劵失效
	 * @param couponId
	 * @return
	 */
	@RequestMapping(value="/coupon/{couponId}",method=RequestMethod.DELETE)
	public Object delCoupon(@PathVariable int couponId){
		StoreCoupon storeCoupon = storeCouponDao.findOne(couponId);
		storeCoupon.setCouponStatus((byte)20);
		return storeCouponDao.save(storeCoupon);
	}
}
