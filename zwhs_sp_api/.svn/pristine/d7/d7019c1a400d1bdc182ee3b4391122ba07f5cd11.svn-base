package cn.org.citycloud.zwhs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.citycloud.zwhs.entity.MaterialWarehouse;
import cn.org.citycloud.zwhs.repository.MaterialWarehouseDao;

@Component
@Transactional
public class MaterialWarehouseService {

	@Autowired MaterialWarehouseDao materialWarehouseDao;
	
	/**
	 * 获取素材库
	 * @param pageable
	 * @return
	 */
	public Iterable<MaterialWarehouse> getMaterial(Pageable pageable) {
		Page<MaterialWarehouse> goodClass = materialWarehouseDao.findAll(pageable);
		return goodClass;
	}
	
	/**
	 * 获取素材信息
	 * @param id
	 * @return
	 */
	public MaterialWarehouse getMaterialWarehouseById(int id) {
		return (MaterialWarehouse) materialWarehouseDao.findOne(id);
	}
}
