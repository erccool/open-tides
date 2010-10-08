package org.opentides.persistence.listener;

import java.util.Date;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;

import org.opentides.bean.user.BaseUser;

import org.apache.log4j.Logger;
import org.opentides.bean.AuditLog;
import org.opentides.bean.BaseEntity;
import org.opentides.util.HibernateUtil;



public class AuditLogListener {
	
	private static Logger _log = Logger.getLogger(AuditLogListener.class);
	
	@PostLoad
	public void loadObject(AuditLog log) {
		try {
			log.setObject( HibernateUtil.getSessionFactory().openSession()
				.load(log.getEntityClass(), log.getEntityId()) );
			log.setUser((BaseUser)HibernateUtil.getSessionFactory().openSession()
					.load(BaseUser.class, log.getUserId()) );
		} catch (Throwable e) {
			// Suppress error caused by auditing
			_log.error(e,e);
		}
	}
	
	@PrePersist
	public void setDates(BaseEntity entity) {
		try {
			// set dateCreated and dateUpdated fields
			Date now = new Date();
			if (entity.getCreateDate() == null) {
				entity.setCreateDate(now);
			}
			entity.setUpdateDate(now);
		} catch (Throwable e) {
			// Suppress error caused by auditing
			_log.error(e,e);
		}
	}
}
