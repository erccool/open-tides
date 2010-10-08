package org.hightides.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hightides.annotations.bean.SyncMode;

/**
 * Generates the service class based on open-tides framework
 *
 * @author allanctan
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Service {
	SyncMode synchronizeMode() default SyncMode.CREATE;
}
