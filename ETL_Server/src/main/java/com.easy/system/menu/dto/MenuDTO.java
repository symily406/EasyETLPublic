package com.easy.system.menu.dto;

import com.spring.boot.ext.kit.StrKit;
import com.easy.system.menu.entity.Menu;

/**
*  系统菜单-数据传输对象实体类
 * @author
 * @time
 */
public class MenuDTO extends Menu{
	/**
	 *
	 */
	private static final long serialVersionUID = -6206664523892299146L;
	private String companyId;

	public String getCompanyId() {
		if(StrKit.notBlank(companyId)){
			return companyId;
		}
		if(getUserLeval()!=100){
			return getUserCompanyId();
		}
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	private String staffId;

	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


}
