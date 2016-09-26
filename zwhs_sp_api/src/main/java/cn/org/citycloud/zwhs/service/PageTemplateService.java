package cn.org.citycloud.zwhs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.org.citycloud.zwhs.entity.PageTemplate;
import cn.org.citycloud.zwhs.repository.PageTemplateDao;

//Spring Bean的标识.
@Component
//类中所有public函数都纳入事务管理的标识.
@Transactional
public class PageTemplateService {
	
	@Autowired
	private PageTemplateDao tplDao;
	
	/**
	 * 获取页面模板信息
	 */
	public PageTemplate getPageTemplateById(int id) {
		
		return (PageTemplate) tplDao.findOne(id);
		
	}
	
	/**
	 * 获取页面模板信息
	 */
	public List<PageTemplate> getPageByStoreId(int id) {
		
		return tplDao.findByStoreId(id);
		
	}
	

}
