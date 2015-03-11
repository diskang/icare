/**
 * @Package com.sjtu.icare.modules.test.annotation
 * @Description TODO
 * @date Mar 10, 2015 5:25:23 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.modules.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface JsonToEntity {
	public String value() default "";
	
	
}
