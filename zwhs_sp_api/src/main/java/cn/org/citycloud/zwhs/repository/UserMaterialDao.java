package cn.org.citycloud.zwhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.UserMaterial;

public interface UserMaterialDao extends JpaRepository<UserMaterial, Integer>,
		JpaSpecificationExecutor<UserMaterial> {

}
