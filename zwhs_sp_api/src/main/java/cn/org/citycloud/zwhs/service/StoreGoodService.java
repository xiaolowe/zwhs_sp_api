package cn.org.citycloud.zwhs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.citycloud.zwhs.entity.StoreGood;
import cn.org.citycloud.zwhs.repository.StoreGoodDao;

@Service
@Transactional
public class StoreGoodService {
	
	@Autowired
	private StoreGoodDao storeGoodDao;
	
	public Iterable<StoreGood> getStoreGoods(Pageable pageable){
		return storeGoodDao.findAll(pageable);
	}
	
	public List<StoreGood> getStoreGoods(){
		return storeGoodDao.findAll();
	}
	
	public Page<StoreGood> getStoreGoods(Byte goodsState,Pageable pageable){
		return storeGoodDao.findByGoodsState(goodsState, pageable);
	}
	
	public Page<StoreGood> getStoreGoods(Map<String, String> paramMap,Pageable pageable){
		
		return storeGoodDao.findByGoodsState(Byte.parseByte(paramMap.get("goodsState")),pageable);

	}
	
}
