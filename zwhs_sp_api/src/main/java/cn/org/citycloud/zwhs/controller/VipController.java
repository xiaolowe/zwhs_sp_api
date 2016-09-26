package cn.org.citycloud.zwhs.controller;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.zwhs.bean.Vip;
import cn.org.citycloud.zwhs.bean.VipSms;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.Store;
import cn.org.citycloud.zwhs.entity.WechatMember;
import cn.org.citycloud.zwhs.repository.VipDao;
import cn.org.citycloud.zwhs.utils.Sms;

@RestController
public class VipController extends BaseController { 
	
	@Autowired
	private VipDao vipDao;
	
	@RequestMapping(value="/vips",method=RequestMethod.GET)
	public Object getVips(@Valid Vip vip){
		Page<WechatMember> vipsPage = null;
		try {
			int page = vip.getPage()-1;
			int pageSize = vip.getPageSize();
			Sort sort = new Sort(Sort.Direction.DESC,"memberId");
			Pageable pageable = new PageRequest(page, pageSize, sort);
			Specification<WechatMember> spec = new Specification<WechatMember>() {

				@Override
				public Predicate toPredicate(Root<WechatMember> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					// TODO Auto-generated method stub
					Predicate predicate = cb.conjunction();
					Join<WechatMember, Store> storeJoin = root.join(root.getModel().getSet("stores",Store.class),JoinType.LEFT);
					Path<Integer> storeId = storeJoin.get("storeId");
					predicate = cb.and(predicate,cb.equal(storeId,getStoreId()));
					Path<String> wechatAliasname = root.get("wechatAliasname");
					String searchValue = vip.getSearchValue();
					if(searchValue.length()>0){
						predicate = cb.and(predicate,cb.like(wechatAliasname, "%"+searchValue+"%"));
					}		
					return query.where(predicate).getRestriction();
				}				
			};
			vipsPage = vipDao.findAll(spec,pageable);
			return vipsPage;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return vipsPage;
	
	}
	
	/**
	 * 获取某个会员信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/vips/{id}",method=RequestMethod.GET)
	public Object getVipById(@PathVariable int id){
		return vipDao.findOne(id);
	}
	
	/**
	 * 更新会员状态
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/vips/{id}/{status}",method=RequestMethod.PUT)
	public Object updateVipState(@PathVariable int id,@PathVariable int status){
		WechatMember wm = vipDao.findOne(id);
		if(status==1||status==0){
			wm.setMemberState((byte)status);
			wm = vipDao.save(wm);
		}
		return wm;
	}
	
	/**
	 * 群发短信
	 * @param vipSms
	 * @return
	 */
	@RequestMapping(value="/vips/sms",method=RequestMethod.POST)
	public Object sendSms(@Valid VipSms vipSms){
		Sms sms = new Sms();
		return sms.sendBatch(vipSms.getPhoneList(), vipSms.getMsg());
	}
}
