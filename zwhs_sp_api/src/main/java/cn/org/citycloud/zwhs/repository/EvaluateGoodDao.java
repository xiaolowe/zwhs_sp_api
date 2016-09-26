package cn.org.citycloud.zwhs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.org.citycloud.zwhs.entity.EvaluateGood;

public interface EvaluateGoodDao extends JpaRepository<EvaluateGood, Integer> {
	
	public List<EvaluateGood> findByGoodsId(int goodsId);
	
}
