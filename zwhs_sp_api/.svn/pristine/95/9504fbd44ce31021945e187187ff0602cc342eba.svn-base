package cn.org.citycloud.zwhs.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import cn.org.citycloud.zwhs.entity.StoreGood;

public interface StoreGoodDao extends JpaRepository<StoreGood, Integer>,JpaSpecificationExecutor<StoreGood>{
	
	
	public Page<StoreGood> findByGoodsState(Byte goodsState,Pageable pageable);
	
	
	
}
