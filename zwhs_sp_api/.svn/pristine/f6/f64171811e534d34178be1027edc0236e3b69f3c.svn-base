package cn.org.citycloud.zwhs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.citycloud.zwhs.entity.GoodsClass;

public interface GoodsClassDao extends JpaRepository<GoodsClass, Integer> {
	
	public List<GoodsClass> findByGcParentId(int gcParentId);
}
