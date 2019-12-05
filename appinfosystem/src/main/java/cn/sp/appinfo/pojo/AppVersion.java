package cn.sp.appinfo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 版本
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class    AppVersion {
	private Integer id;//主键id
	private Integer appId;//appId
	private String versionNo;//版本号
	private String versionInfo;//版本描述
	private Integer publishStatus;//发布状态id
	private String downloadLink;//apk文件下载链接
	private BigDecimal versionSize;//版本大小
	private Integer createdBy;//创建者
	private Date creationDate;//创建时间
	private Integer modifyBy;//更新者
	private Date modifyDate;//更新时间
	private String apkLocPath;//apk文件的服务器存储路径
	private String appName;//APP软件名称
	private String publishStatusName;//发布状态名称
	private String apkFileName;//上传的apk文件名称
	

}
