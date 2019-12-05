package cn.sp.appinfo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 数据字典
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataDictionary {
	private Integer id;//主键id
	private String typeCode;//类型编码
	private String typeName;//类型名称
	private Integer valueId;//类型值ID
	private String valueName;//类型值name
	private Integer createdBy;//创建者
	private Date creationDate;//创建时间
	private Integer modifyBy;//更新者
	private Date modifyDate;//更新时间
	

}
