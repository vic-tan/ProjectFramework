package com.tanlifei.common.bean;

import java.io.Serializable;

/**
 * 发表图片
* @ClassName: ImgBean
* @Description: 用一句话描述该文件做什么
* @author tanlifei 
* @date 2015年3月4日 上午11:24:22
*
 */
public class BasePhotoBean implements Serializable {

	/**
	* @Fields serialVersionUID : 用一句话描述该文件做什么
	*/

	private static final long serialVersionUID = 1L;
	private String url;
	private String thumbnail_url;

	public BasePhotoBean() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}

	public BasePhotoBean(String url, String thumbnail_url) {
		this.url = url;
		this.thumbnail_url = thumbnail_url;
	}

	@Override
	public String toString() {
		return "ImgsBean [url=" + url + "]";
	}

}
