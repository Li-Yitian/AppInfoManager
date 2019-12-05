package cn.sp.appinfo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *广告
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPromotion {
	private Integer id;//主键ID
	private Integer appId;//appId
	private String adPicPath;//广告图片存储路径
	private Integer adPV;//广告点击量
	private Integer carouselPosition;//轮播位（1-n）
	private Date startTime;//广告起效时间
	private Date endTime;//广告失效时间
	private Integer createdBy;//创建者
	private Date creationDate;//创建时间
	private Integer modifyBy;//更新者
	private Date modifyDate;//更新时间

}
