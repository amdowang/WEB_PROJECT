package com.jst.common.util;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 反射的Utils函数集合.
 * 
 * 提供侵犯隐私的直接读取filed的能力.
 */
public class BeanUtils {

	private static final Log log = LogFactory.getLog(BeanUtils.class);

	private BeanUtils() {
	}

	/**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 */
	public static Object getFieldValue(Object object, String fieldName)
			throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		// 取消Java语言权限访问检查,true不检查,false是检查
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		Object result = null;
		try {
			// 取消java 语言权限访问检查后,才能 get 以private 修改的变量，否则不行
			result = field.get(object);
		} catch (IllegalAccessException e) {
			log.error("不可能抛出的异常{}" + e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 */
	public static void setFieldValue(Object object, String fieldName,
			Object value) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			log.error("不可能抛出的异常:{}" + e.getMessage());
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 */
	public static Field getDeclaredField(Object object, String fieldName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * 循环向上转型,获取类的DeclaredField.
	 */
	@SuppressWarnings("unchecked")
	public static Field getDeclaredField(Class clazz, String fieldName)
			throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
				// 若Field不在当前类定义,抛出异常.存在就return
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
				System.out.println("--");
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + fieldName);
	}

	/**
	 * copye对象中的属性，null值不copy
	 * 
	 * @param des
	 * @param src
	 * @throws Exception
	 */
	public static void copyPropertysWithoutNull(Object des, Object src)
			throws Exception {
		Class<?> clazz = des.getClass();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			Field[] srcfields = superClass.getDeclaredFields();
			// 若Field不在当前类定义,抛出异常.存在就return
			for (Field field : srcfields) {
				try {
					Field srcField = superClass.getDeclaredField(field
							.getName());
					srcField.setAccessible(true);
					Object srcValue = srcField.get(src);
					if (srcValue != null){
						srcField.set(des, srcValue);
					}
				} catch (NoSuchFieldException e) {
					// Field不在当前类定义,继续向上转型
					System.out.println("--");
				}
			}
		}
	}

}
