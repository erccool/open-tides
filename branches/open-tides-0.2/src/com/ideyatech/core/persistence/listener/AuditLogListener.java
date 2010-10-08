package com.ideyatech.core.persistence.listener;

import java.util.Date;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import com.ideyatech.core.bean.AuditLog;
import com.ideyatech.core.bean.BaseEntity;
import com.ideyatech.core.bean.user.BaseUser;
import com.ideyatech.core.util.HibernateUtil;

public class AuditLogListener {
	@PostLoad
	public void loadObject(AuditLog log) {
		log.setObject( HibernateUtil.getSessionFactory().openSession()
			.load(log.getEntityClass(), log.getEntityId()) );
		log.setUser((BaseUser)HibernateUtil.getSessionFactory().openSession()
				.load(BaseUser.class, log.getUserId()) );
	}
	
	@PrePersist
	public void setDates(BaseEntity entity) {
		// set dateCreated and dateUpdated fields
		Date now = new Date();
		if (entity.getCreateDate() == null) {
			entity.setCreateDate(now);
		}
		entity.setUpdateDate(now);
	}
}
