package com.lagou.io;

import java.io.InputStream;

/**
 * @author 程  林
 * @date 2020-03-25 23:46
 * @description //加载配置文件（sqlMapConfig.xml 和 xxxMapper.xml）
 * @since V1.0.0
 */
public class Resources {

	public static InputStream getResourceAsStream(String path) {

		/*通过类加载器，加载配置文件*/
		InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
		return resourceAsStream;

	}
}
