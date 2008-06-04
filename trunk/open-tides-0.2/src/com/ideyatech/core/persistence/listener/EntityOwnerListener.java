/**
 * 
 */
package com.ideyatech.core.persistence.listener;

import javax.persistence.PrePersist;

import com.ideyatech.core.bean.BaseProtectedEntity;
import com.ideyatech.core.bean.user.SessionUser;
import com.ideyatech.core.util.AcegiUtil;
import com.ideyatech.core.util.StringUtil;

/**
 * This listener is set owner value to user who created the object.
 * @author allantan
 * 
 */
public class EntityOwnerListener {
	@PrePersist
	public void setOwner(BaseProtectedEntity entity) {
		if (StringUtil.isEmpty(entity.getOwner())) {
			SessionUser user = AcegiUtil.getSessionUser();
			entity.setOwner(user.getUsername());
		}
	}
}
