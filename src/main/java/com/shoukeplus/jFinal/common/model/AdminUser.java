package com.shoukeplus.jFinal.common.model;

import com.google.common.base.Joiner;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.shoukeplus.jFinal.common.model.base.BaseAdminUser;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class AdminUser extends BaseAdminUser<AdminUser> {
	public static final AdminUser dao = new AdminUser();
	public Page<AdminUser> page(Integer pageNumber, Integer pageSize) {
		return super.paginate(pageNumber, pageSize, "select * ", "from sk_admin_user order by in_time desc");
	}

	public AdminUser findByUsername(String username) {
		return super.findFirstByCache(AppConstants.CURRENTADMINUSERCACHE,AppConstants.CURRENTADMINUSERCACHEKEY+"_"+username,"select * from sk_admin_user where username = ?", username);
	}

	public void correlationRole(Integer userId, Integer[] roles) {
		//先删除已经存在的关联
		Db.update("delete from sk_user_role where uid = ?", userId);
		//建立新的关联关系
		for (Integer rid : roles) {
			UserRole userRole = new UserRole();
			userRole.set("uid", userId)
					.set("rid", rid)
					.save();
		}
	}
	public List<AdminUser> findAll(){
		return super.find("select * from sk_admin_user order by username");
	}
	public List<AdminUser> findByIds(List<Integer> ids){
		if(CollectionUtils.isEmpty(ids)){
			return new ArrayList<>();
		}
		String idsStr = Joiner.on(",").join(ids);
		return super.find("select * from sk_admin_user where id in ("+idsStr+") order by username");
	}
}
