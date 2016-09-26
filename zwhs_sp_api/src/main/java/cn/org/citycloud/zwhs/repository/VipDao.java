package cn.org.citycloud.zwhs.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import cn.org.citycloud.zwhs.entity.WechatMember;

public interface VipDao extends JpaRepository<WechatMember, Integer>,
		JpaSpecificationExecutor<WechatMember> {
	
	Page<WechatMember> findByStores_StoreId(int storeId,Specification<WechatMember> spec,Pageable pageable);
	
	List<WechatMember> findByStores_StoreId(int storeId);
	
	
}
