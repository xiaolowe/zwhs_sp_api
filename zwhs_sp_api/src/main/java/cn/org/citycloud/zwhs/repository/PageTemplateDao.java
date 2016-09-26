package cn.org.citycloud.zwhs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.org.citycloud.zwhs.entity.PageTemplate;

/**
 * PageTemplate
 * 
 * @author lanbo
 *
 */
public interface PageTemplateDao extends JpaRepository<PageTemplate, Integer>, JpaSpecificationExecutor<PageTemplate> {
	
	public List<PageTemplate> findByStoreId(int store_id);
	
	public PageTemplate findByStoreIdAndType(int store_id, int type);

}
