package cn.org.citycloud.zwhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.WechatMenu;

public interface WechatMenuDao extends JpaRepository<WechatMenu, Integer>,
		JpaSpecificationExecutor<WechatMenu> {
	public WechatMenu findByStoreId(int storeId);
}
