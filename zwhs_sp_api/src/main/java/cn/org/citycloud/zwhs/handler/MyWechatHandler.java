package cn.org.citycloud.zwhs.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.org.citycloud.zwhs.constants.Constants;
import cn.org.citycloud.zwhs.entity.MaterialWarehouse;
import cn.org.citycloud.zwhs.entity.WechatKey;
import cn.org.citycloud.zwhs.repository.MaterialWarehouseDao;
import cn.org.citycloud.zwhs.repository.WechatKeyDao;
import io.github.elkan1788.mpsdk4j.core.WechatHandler;
import io.github.elkan1788.mpsdk4j.vo.event.BasicEvent;
import io.github.elkan1788.mpsdk4j.vo.event.LocationEvent;
import io.github.elkan1788.mpsdk4j.vo.event.MenuEvent;
import io.github.elkan1788.mpsdk4j.vo.event.ScanCodeEvent;
import io.github.elkan1788.mpsdk4j.vo.event.ScanEvent;
import io.github.elkan1788.mpsdk4j.vo.event.SendLocationInfoEvent;
import io.github.elkan1788.mpsdk4j.vo.event.SendPhotosEvent;
import io.github.elkan1788.mpsdk4j.vo.message.Article;
import io.github.elkan1788.mpsdk4j.vo.message.BasicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.ImageMsg;
import io.github.elkan1788.mpsdk4j.vo.message.LinkMsg;
import io.github.elkan1788.mpsdk4j.vo.message.LocationMsg;
import io.github.elkan1788.mpsdk4j.vo.message.NewsMsg;
import io.github.elkan1788.mpsdk4j.vo.message.TextMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VideoMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VoiceMsg;
import io.github.elkan1788.mpsdk4j.vo.push.SentAllJobEvent;
import io.github.elkan1788.mpsdk4j.vo.push.SentTmlJobEvent;

@Component
public class MyWechatHandler implements WechatHandler {
	
	@Autowired
	private WechatKeyDao wechatKeyDao;
	@Autowired
	private MaterialWarehouseDao materialWarehouseDao;

	private int storeId;
	
	@Override
	public BasicMsg defMsg(BasicMsg bm) {
		// TODO Auto-generated method stub
		List<WechatKey> wechatKey  = wechatKeyDao.findByStoreIdAndReplyType(storeId, 3);
		if(wechatKey==null||wechatKey.size()<=0){
			return null;
		}
		int replyType = wechatKey.get(0).getReplyConType();
		//图文回复
		if(replyType==2){
			NewsMsg news = new NewsMsg(bm);
			List<Article> articles = new ArrayList<Article>();
			for(MaterialWarehouse mwh:wechatKey.get(0).getMaterialWarehouses()){
				Article article = new Article();
				article.setTitle(mwh.getArticleTitle());
				article.setDescription(mwh.getMaterialDesc());
				article.setPicUrl(Constants.FILE_URL+mwh.getCoverPic());
				article.setUrl(Constants.API_URL+Constants.API_PATH+"/wx"+"/article/"+mwh.getId());
				articles.add(article);
			}
			news.setArticles(articles);
			return news;
		}else{	//文本回复
			TextMsg rptm = new TextMsg(bm);
			rptm.setContent(wechatKey.get(0).getReplyText());
			return rptm;
		}
	}

	@Override
	public BasicMsg defEvent(BasicEvent be) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicMsg text(TextMsg tm) {
		// TODO Auto-generated method stub
		String keyWord = tm.getContent();
		WechatKey wechatKey  = wechatKeyDao.findByStoreIdAndReplyKeyword(storeId, keyWord);
		if(wechatKey==null){
			return defMsg(tm);
		}
		int replyType = wechatKey.getReplyConType();
		//图文回复
		if(replyType==2){
			NewsMsg news = new NewsMsg(tm);
			List<Article> articles = new ArrayList<Article>();
			for(MaterialWarehouse mwh:wechatKey.getMaterialWarehouses()){
				Article article = new Article();
				article.setTitle(mwh.getArticleTitle());
				article.setDescription(mwh.getMaterialDesc());
				article.setPicUrl(Constants.FILE_URL+mwh.getCoverPic());
				article.setUrl(Constants.API_URL+Constants.API_PATH+"/wx"+"/article/"+mwh.getId());
				articles.add(article);
			}
			news.setArticles(articles);
			return news;
		}else{	//文本回复
			TextMsg rptm = tm;
			rptm.setContent(wechatKey.getReplyText());
			return rptm;
		}
	}

	@Override
	public BasicMsg image(ImageMsg im) {
		// TODO Auto-generated method stub
		return defMsg(im);
		
	}

	@Override
	public BasicMsg voice(VoiceMsg vom) {
		// TODO Auto-generated method stub
		return defMsg(vom);
	}

	@Override
	public BasicMsg video(VideoMsg vim) {
		// TODO Auto-generated method stub
		return defMsg(vim);
	}

	@Override
	public BasicMsg shortVideo(VideoMsg vim) {
		// TODO Auto-generated method stub
		return defMsg(vim);
	}

	@Override
	public BasicMsg location(LocationMsg lm) {
		// TODO Auto-generated method stub
		return defMsg(lm);
	}

	@Override
	public BasicMsg link(LinkMsg lm) {
		// TODO Auto-generated method stub
		return defMsg(lm);
	}

	@Override
	public BasicMsg eClick(MenuEvent me) {
		// TODO Auto-generated method stub
		String eventKey = me.getEventKey();
		String[] keys = eventKey.split("_");
		NewsMsg news = new NewsMsg(me);
		List<Article> articles = new ArrayList<Article>();
		for(String key : keys){
			int keyId = Integer.parseInt(key);
			MaterialWarehouse mwh = materialWarehouseDao.findOne(keyId);
			Article article = new Article();
			article.setTitle(mwh.getArticleTitle());
			article.setDescription(mwh.getMaterialDesc());
			article.setPicUrl(Constants.FILE_URL+mwh.getCoverPic());
			article.setUrl(Constants.API_URL+Constants.API_PATH+"/wx"+"/article/"+mwh.getId());
			articles.add(article);
		}
		news.setArticles(articles);
		return news;
	}

	@Override
	public void eView(MenuEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public BasicMsg eSub(BasicEvent be) {
		// TODO Auto-generated method stub
		List<WechatKey> wechatKey  = wechatKeyDao.findByStoreIdAndReplyType(storeId, 1);
		if(wechatKey==null||wechatKey.size()<=0){
			return null;
		}
		int replyType = wechatKey.get(0).getReplyConType();
		//图文回复
		if(replyType==2){
			NewsMsg news = new NewsMsg(be);
			List<Article> articles = new ArrayList<Article>();
			for(MaterialWarehouse mwh:wechatKey.get(0).getMaterialWarehouses()){
				Article article = new Article();
				article.setTitle(mwh.getArticleTitle());
				article.setDescription(mwh.getMaterialDesc());
				article.setPicUrl(Constants.FILE_URL+mwh.getCoverPic());
				article.setUrl(Constants.API_URL+Constants.API_PATH+"/wx"+"/article/"+mwh.getId());
				articles.add(article);
			}
			news.setArticles(articles);
			return news;
		}else{	//文本回复
			TextMsg rptm = new TextMsg(be);
			rptm.setContent(wechatKey.get(0).getReplyText());
			return rptm;
		}
	}

	@Override
	public void eUnSub(BasicEvent be) {
		// TODO Auto-generated method stub

	}

	@Override
	public BasicMsg eScan(ScanEvent se) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eLocation(LocationEvent le) {
		// TODO Auto-generated method stub

	}

	@Override
	public BasicMsg eScanCodePush(ScanCodeEvent sce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicMsg eScanCodeWait(ScanCodeEvent sce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicMsg ePicSysPhoto(SendPhotosEvent spe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicMsg ePicPhotoOrAlbum(SendPhotosEvent spe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicMsg ePicWeixin(SendPhotosEvent spe) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicMsg eLocationSelect(SendLocationInfoEvent slie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eSentTmplJobFinish(SentTmlJobEvent stje) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eSentAllJobFinish(SentAllJobEvent saje) {
		// TODO Auto-generated method stub

	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

}
