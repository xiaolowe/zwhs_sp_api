package cn.org.citycloud.zwhs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.WechatKey;

public interface WechatKeyDao extends JpaRepository<WechatKey, Integer>,
		JpaSpecificationExecutor<WechatKey> {
	public List<WechatKey> findByStoreId(int storeId);
	
	public WechatKey findByStoreIdAndReplyKeyword(int storeId,String replyKeyword);
	
//	public WechatKey findByStoreIdAndReplyType(int storeId,int replyType);
	
	public List<WechatKey> findByStoreIdAndReplyType(int storeId,int replyType);

}
