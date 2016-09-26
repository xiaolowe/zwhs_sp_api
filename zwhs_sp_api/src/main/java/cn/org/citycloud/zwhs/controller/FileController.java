package cn.org.citycloud.zwhs.controller;


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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.org.citycloud.zwhs.bean.Page;
import cn.org.citycloud.zwhs.bean.StoreImage;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.UserMaterial;
import cn.org.citycloud.zwhs.repository.UserMaterialDao;

@RestController
public class FileController extends BaseController {
		
	@Autowired
	private UserMaterialDao userMaterialDao;
	
	/**
	 * 获取图片列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/image",method=RequestMethod.GET)
	public Object getImages(@Valid Page page){
		int p = page.getPage();
		int pageSize = page.getPageSize();
		Sort sort = new Sort(Sort.Direction.DESC,"updDate");
		Pageable pageable = new PageRequest(p-1,pageSize,sort);
		Specification<UserMaterial> spec = new Specification<UserMaterial>() {

			@Override
			public Predicate toPredicate(Root<UserMaterial> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate predicate = cb.conjunction();
				Path<Integer> storeId = root.get("storeId");
				//防止未认证门店获取图片信息
				predicate = cb.and(predicate,cb.gt(storeId, 0));
				predicate = cb.and(predicate,cb.equal(storeId, getStoreId()));
				return query.where(predicate).getRestriction();
			}
			
		};
		return userMaterialDao.findAll(spec, pageable);
	}
	
	/**
	 * 保存图片
	 * @param storeImage
	 * @return
	 */
	@RequestMapping(value="/image",method=RequestMethod.POST)
	public Object setImage(@RequestBody @Valid StoreImage storeImage,HttpServletResponse response){
		if(getStoreId()<=0){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			response.setStatus(403);
			resultMap.put("code", 2);
			resultMap.put("msg", "门店未认证,请重新认证后再上传");
			return resultMap;
		}else{
			UserMaterial userMaterial = new UserMaterial();
			userMaterial.setStoreId(getStoreId());
			userMaterial.setMaterial(storeImage.getImage());
			Date now = new Date();
			userMaterial.setInsDate(now);
			userMaterial.setUpdDate(now);
			userMaterialDao.save(userMaterial);
			return userMaterial;
		}
		
	}
	
	
	
	
}
