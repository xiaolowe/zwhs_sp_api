package cn.org.citycloud.zwhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.StoreUser;

public interface StoreUserDao extends JpaRepository<StoreUser, Integer>,
		JpaSpecificationExecutor<StoreUser> {
	
	public StoreUser findByUserPhoneAndUserPwd(String userPhone,String userPwd);
	
	public StoreUser findByUserPhone(String userPhone);
}
