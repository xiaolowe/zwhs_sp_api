package cn.org.citycloud.zwhs.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.org.citycloud.zwhs.bean.Material;
import cn.org.citycloud.zwhs.bean.MaterialHouse;
import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.core.BaseController;
import cn.org.citycloud.zwhs.entity.MaterialWarehouse;
import cn.org.citycloud.zwhs.exception.BusinessErrorException;
import cn.org.citycloud.zwhs.repository.MaterialWarehouseDao;
import cn.org.citycloud.zwhs.service.MaterialWarehouseService;

@Controller
public class MaterialWarehouseController extends BaseController {

	@Autowired 
	private MaterialWarehouseDao materialWarehouseDao;
	
	@Autowired
	private MaterialWarehouseService materialWarehouseService;
	
	/**
	 * 创建图文
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/material",method=RequestMethod.POST)
	@ResponseBody()
	public Object addMaterial(@RequestBody @Valid MaterialHouse materialHouse){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		MaterialWarehouse materialWarehouse = new MaterialWarehouse();
		BeanUtils.copyProperties(materialHouse, materialWarehouse);
		Date nowDate = new Date();
		materialWarehouse.setStoreId(getStoreId());	
		materialWarehouse.setInsDate(nowDate);
		materialWarehouse.setUpdDate(nowDate);
		materialWarehouse.setMaterialType(Constants.MATERIAL_PERSONAL);//我的素材库
		materialWarehouse = materialWarehouseDao.save(materialWarehouse);
		resultMap.put("code", "1");
		resultMap.put("msg", "添加素材成功！");
		resultMap.put("content", materialWarehouse);
		return resultMap;
	}
	
	/**
	 * 我的图文库列表
	 * @param good
	 * @return
	 */
	@RequestMapping(value="/material_personal",method=RequestMethod.GET)
	@ResponseBody()
	public Object getMaterialPersonal(@Valid Material material){
		int page = material.getPage();
		int pageSize = material.getPageSize();
		Sort sort = new Sort(Sort.Direction.DESC,"updDate");
		Pageable pageable = new PageRequest(page-1, pageSize,sort);
		
		Specification<MaterialWarehouse> spec = new Specification<MaterialWarehouse>() {
			@Override
			public Predicate toPredicate(Root<MaterialWarehouse> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				Path<Integer> materialType = root.get("materialType");
				predicate = cb.and(predicate,cb.equal(materialType, Constants.MATERIAL_PERSONAL));
				Path<Integer> storeId = root.get("storeId");
				predicate = cb.and(predicate,cb.equal(storeId, getStoreId()));			
				String searchValue = material.getSearchValue();
				if(searchValue.length()>0){
					Path<String> articleAuthor = root.get("articleAuthor");
					Path<String> articleComments = root.get("articleComments");
					Path<String> articleTitle = root.get("articleTitle");
					Path<String> materialDesc = root.get("materialDesc");
					predicate = cb.and(predicate,cb.or(cb.like(articleAuthor, "%"+searchValue+"%"),cb.like(articleComments, "%"+searchValue+"%"),cb.like(articleTitle, "%"+searchValue+"%"),cb.like(materialDesc, "%"+searchValue+"%")));
				}
				return query.where(predicate).getRestriction();
			}
			
		};
		return materialWarehouseDao.findAll(spec,pageable);
	}
	
	/**
	 * 我的图文预览
	 * @param id
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/material_personal/{id}",method=RequestMethod.GET)
	@ResponseBody()
	public Object getMaterialPersonalById(@PathVariable int id,@RequestParam Map<String, Object> paramMap){
		return materialWarehouseService.getMaterialWarehouseById(id);
	}
	
	/**
	 * 修改我的图文
	 * @param id
	 * @param paramMap
	 * @return
	 */
	@RequestMapping(value="/material_personal_update/{id}",method=RequestMethod.PUT)
	@ResponseBody()
	public Object updateMaterialPersonalById(@PathVariable int id,@RequestBody @Valid MaterialHouse materialHouse)
			throws BusinessErrorException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		MaterialWarehouse materialWarehouse = materialWarehouseService.getMaterialWarehouseById(id);
		BeanUtils.copyProperties(materialHouse, materialWarehouse);
		Date nowDate = new Date();
		materialWarehouse.setStoreId(getStoreId());
		materialWarehouse.setUpdDate(nowDate);
		materialWarehouseDao.save(materialWarehouse);
		resultMap.put("code", "1");
		resultMap.put("msg", "修改素材成功！");
		resultMap.put("content", materialWarehouse);
		return resultMap;
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value = "/material_personal_del/{id}", method = RequestMethod.DELETE)
	@ResponseBody()
	public void delete(@PathVariable int id) {
		materialWarehouseDao.delete(id);
	}
	
	
	/**
	 * 系统图文库列表
	 * @param good
	 * @return
	 */
	@RequestMapping(value="/material_system",method=RequestMethod.GET)
	@ResponseBody()
	public Object getMaterialSystem(@Valid Material material){
		int page = material.getPage();
		int pageSize = material.getPageSize();
		
		Sort sort = new Sort(Sort.Direction.DESC,"updDate");
		Pageable pageable = new PageRequest(page-1, pageSize,sort);
		
		Specification<MaterialWarehouse> spec = new Specification<MaterialWarehouse>() {
			@Override
			public Predicate toPredicate(Root<MaterialWarehouse> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				Path<Integer> materialType = root.get("materialType");
				predicate = cb.and(predicate,cb.equal(materialType, Constants.MATERIAL_SYSTEM));
				Path<Integer> articleState = root.get("articleState");
				predicate = cb.and(predicate,cb.equal(articleState, Constants.ARTICLE_STATE));
				String searchValue = material.getSearchValue();
				if(searchValue.length()>0){
					Path<String> articleAuthor = root.get("articleAuthor");
					Path<String> articleComments = root.get("articleComments");
					Path<String> articleTitle = root.get("articleTitle");
					Path<String> materialDesc = root.get("materialDesc");
					predicate = cb.and(predicate,cb.or(cb.like(articleAuthor, "%"+searchValue+"%"),cb.like(articleComments, "%"+searchValue+"%"),cb.like(articleTitle, "%"+searchValue+"%"),cb.like(materialDesc, "%"+searchValue+"%")));
				}
				int typeNo = material.getTypeNo();
				if(0!=typeNo){
					Path<Integer> gcNo = root.get("articleClassId");
					predicate = cb.and(predicate,cb.equal(gcNo, typeNo));
				}
				
				return query.where(predicate).getRestriction();
			}
			
		};
		return materialWarehouseDao.findAll(spec,pageable);
	}
	
	@RequestMapping(value="/material_system/{id}",method=RequestMethod.GET)
	@ResponseBody()
	public Object getMaterialSystemyId(@PathVariable int id,@RequestParam Map<String, Object> paramMap){
		return materialWarehouseService.getMaterialWarehouseById(id);
	}
}
