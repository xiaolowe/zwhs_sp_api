package cn.org.citycloud.zwhs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.citycloud.zwhs.entity.Brand;

public interface BrandDao extends JpaRepository<Brand, Integer> {
	
	public List<Brand> findFirst10ByBrandNameContaining(String brandName);
	
}
