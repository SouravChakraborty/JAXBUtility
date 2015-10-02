package common;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The Interface JAXBConvertAnnotation.
 * 
 * @author Sourav
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface JAXBConvertAnnotation {

	/**
	 * Class name.
	 * 
	 * @return the string
	 */
	String className() default "";

	/**
	 * Field name.
	 * 
	 * @return the string
	 */
	String fieldName() default "";

	/**
	 * Xml field name.
	 * 
	 * @return the string
	 */
	String xmlFieldName() default "";

}
