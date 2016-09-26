package cn.org.citycloud.zwhs.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.MaterialWarehouse;

public interface MaterialWarehouseDao extends JpaRepository<MaterialWarehouse, Integer>,
		JpaSpecificationExecutor<MaterialWarehouse> {
	public Set<MaterialWarehouse> findByIdIn(List<Integer> ids);
}
