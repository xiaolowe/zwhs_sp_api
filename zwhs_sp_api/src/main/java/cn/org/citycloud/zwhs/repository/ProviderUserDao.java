package cn.org.citycloud.zwhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.ProviderUser;

public interface ProviderUserDao extends JpaRepository<ProviderUser, Integer>,
		JpaSpecificationExecutor<ProviderUser> {

	public ProviderUser findByUserPhoneAndUserPwd(String userPhone,
			String userPwd);

	public ProviderUser findByUserPhone(String userPhone);
}
