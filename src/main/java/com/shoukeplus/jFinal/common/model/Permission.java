package com.shoukeplus.jFinal.common.model;

import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.model.base.BasePermission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Permission extends BasePermission<Permission> {
	public static final Permission dao = new Permission();
	public List<Permission> findByPid(Object pid) {
		return super.find("select * from sk_permission where pid = ?", pid);
	}

	public List<Permission> findAll() {
		List<Permission> permissions = this.findByPid(0);
		for (Permission p : permissions) {
			List<Permission> permissionList = this.findByPid(p.get("id"));
			p.put("childPermission", permissionList);
		}
		return permissions;
	}

	public Set<String> findPermissions(String username) {
		List<Permission> permissions = super.findByCache(AppConstants.SHIROCACHE, AppConstants.PERMISSIONCACHEKEY + username,
				"select p.* from sk_admin_user u, sk_role r, sk_permission p, " +
						"sk_user_role ur, sk_role_permission rp where u.id = ur.uid and r.id = ur.rid and r.id = rp.rid and p.id = rp.pid and u.username = ?", username);
		Set<String> set = new HashSet<String>();
		for (Permission p : permissions) {
			set.add(p.getStr("name"));
		}
		return set;
	}
}
