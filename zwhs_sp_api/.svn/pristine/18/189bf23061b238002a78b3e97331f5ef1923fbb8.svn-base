package cn.org.citycloud.zwhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.ServiceStore;

public interface ServiceStoreDao extends JpaRepository<ServiceStore, Integer>,
		JpaSpecificationExecutor<ServiceStore> {
	public ServiceStore findByStoreId(int storeId);
	
	public ServiceStore findByStoreIdAndServiceState(int storeId,int serviceState);
}
