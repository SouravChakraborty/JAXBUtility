package common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;

/**
 * The Class JAXBUtil.
 * 
 * @author Sourav
 */
public class JAXBUtil {

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(JAXBUtil.class);

	/**
	 * Creates the field method map.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return the map
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Method> createFieldMethodMap(Class clazz) {

		Map<String, Method> fieldMethodMap = new HashMap<String, Method>();

		try {

			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			for (PropertyDescriptor propertyDescriptor : beanInfo
					.getPropertyDescriptors()) {
				fieldMethodMap.put(propertyDescriptor.getName(),
						propertyDescriptor.getReadMethod());
			}

		} catch (IntrospectionException ie) {
			log.info("Exception in method::::: createFieldMethodMap");
		}

		return fieldMethodMap;

	}

	/**
	 * Gets the xml field map.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param jaxbToEntity
	 *            the jaxb to entity
	 * @return the xml field map
	 */
	public static Map<String, String> getXmlFieldMap(Class<?> clazz,
			boolean jaxbToEntity) {

		Map<String, String> fieldMap = new HashMap<String, String>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(JAXBConvertAnnotation.class)) {
				JAXBConvertAnnotation annotation = field
						.getAnnotation(JAXBConvertAnnotation.class);

				if (jaxbToEntity) {
					fieldMap.put(annotation.xmlFieldName(), field.getName());
				} else {
					fieldMap.put(field.getName(), annotation.xmlFieldName());
				}
			}
		}

		return fieldMap;
	}

	/**
	 * Convert date from jaxb to entity.
	 * 
	 * @param date
	 *            the date
	 * @return the date
	 */
	public static Date convertDateFromJaxbToEntity(String date) {
		try {
			SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat dt1 = new SimpleDateFormat("MM-DD-YYYY");

			Date date1 = dt.parse(date);
			return dt1.parse(dt1.format(date1));
		} catch (ParseException pe) {

		}

		return null;
	}

	/**
	 * Convert date from entity to jaxb.
	 * 
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String convertDateFromEntityToJaxb(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = null;
		formattedDate = dt.format(date);
		return formattedDate;

	}

	/**
	 * Convert object.
	 * 
	 * @param object
	 *            the object
	 * @param targetObject
	 *            the target object
	 * @param jaxbToEntity
	 *            the jaxb to entity
	 * @return the object
	 */
	public static Object convertObject(Object object, Object targetObject,
			boolean jaxbToEntity) {

		try {
			Class<?> targetClass = Class
					.forName(((JAXBConvertAnnotation) object.getClass()
							.getAnnotation(JAXBConvertAnnotation.class))
							.className());
			if (null == targetObject) {
				targetObject = targetClass.newInstance();
			}

			Map<String, String> fieldMap = null;
			Class<?> sourceClass = object.getClass();
			Map<String, Method> sourceFieldMethodMap = createFieldMethodMap(sourceClass);

			if (jaxbToEntity) {
				fieldMap = getXmlFieldMap(targetObject.getClass(), jaxbToEntity);
			} else {
				fieldMap = getXmlFieldMap(object.getClass(), jaxbToEntity);
			}

			for (Field field : object.getClass().getDeclaredFields()) {
				Field targetField = null;
				Field sourceField = field;
				if (StringUtils.isNotEmpty(fieldMap.get(sourceField.getName()))) {
					try {
						targetField = targetObject.getClass().getDeclaredField(
								fieldMap.get(sourceField.getName()));
					} catch (NoSuchFieldException exception) {
						continue;
					}

				}

				if (null != targetField) {
					sourceField = field;
					Object value = ReflectionUtils.invokeMethod(
							sourceFieldMethodMap.get(sourceField.getName()),
							object);
					if (null != value) {
						if (targetField.getType().isAssignableFrom(Date.class)) {
							value = convertDateFromJaxbToEntity(value + "");
						}
						if (!jaxbToEntity
								&& field.getType().isAssignableFrom(Date.class)) {
							value = convertDateFromEntityToJaxb((Date) value);
						}

						BeanUtils.setProperty(targetObject,
								targetField.getName(), value);
					}
				}
			}

		} catch (InvocationTargetException ite) {

			log.info("Exception in method::::: convertObject");

		} catch (ClassNotFoundException cnfe) {

			log.info("Exception in method::::: convertObject");

		} catch (InstantiationException ie) {

			log.info("Exception in method::::: convertObject");

		} catch (IllegalAccessException iae) {

			log.info("Exception in method::::: convertObject");

		} catch (IllegalArgumentException iae) {

			log.info("Exception in method::::: convertObject");

		}

		return targetObject;
	}
}
