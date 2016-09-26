package cn.org.citycloud.zwhs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.GoodsCommon;



public interface GoodsCommonDao extends JpaRepository<GoodsCommon, Integer>,JpaSpecificationExecutor<GoodsCommon> {
	
	public GoodsCommon findByGoodsSn(String goodsSn);
			
}
