package cn.org.citycloud.zwhs.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.org.citycloud.zwhs.entity.StoreOrder;

public interface StoreOrderDao extends JpaRepository<StoreOrder, Integer>,
		JpaSpecificationExecutor<StoreOrder> {
	
	public List<StoreOrder> findByStoreId(int storeId);
	
	public StoreOrder findByOrderId(int orderId);
	
	@Query(value="select sum(so.order_amount) as amount from store_order so where so.store_id=?1 and so.order_status>10",nativeQuery=true)
	public BigDecimal getAllAmount(int storeId);
	
	@Query(value="select count(*) as amount from store_order so where so.store_id=?1",nativeQuery=true)
	public int getSize(int storeId);
}
