/**
 * 
 */
package controllers.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chamerling
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface APIDefinition {
	
	String name() default "";
	
	String description();
	
	String url() default "";
	
	String input() default "";
	
	String result() default "";
	
	String method() default "GET";
	
	Class<?> clazz();

}
