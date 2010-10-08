/**
 * 
 */
package org.opentides.persistence.listener;

import javax.persistence.PrePersist;

import org.opentides.bean.user.SessionUser;

import org.opentides.bean.BaseProtectedEntity;
import org.opentides.util.AcegiUtil;
import org.opentides.util.StringUtil;



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
