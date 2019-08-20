package com.liu.generator.host;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.mysql.cj.util.StringUtils;

/**
 * @desc 加载文件,并解析。
 * 		   默认加载资源文件resources文件夹下的generatorConfig.xml文件。
 * @author Liuweian
 * @createTime 2019年4月10日 下午5:34:48
 * @version 1.0.0
 */
public class LoadFile {
	
	private static final String DEFALT_NAME = "generatorConfig.xml";
	
	private List<String> warnings = new ArrayList<String>();
	
	private String fileName;
	
	public LoadFile() {
		this(DEFALT_NAME);
	}
	
	/**
	 * @desc 默认的生成配置文件为：generatorConfig.xml
	 * @author Liuweian
	 * @createTime 2019年8月19日 上午10:24:21
	 * @version 1.0.0
	 */
	public LoadFile(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @desc 获取生成配置文件并转换为File实例对象。
	 * @author Liuweian
	 * @createTime 2019年4月10日 下午5:39:01
	 * @return
	 * @throws Exception 
	 */
	public File getFile() throws Exception {
		if (StringUtils.isNullOrEmpty(this.fileName)) {
			throw new Exception("请指定对应的xml文件");
		}
		
		File file = new File(this.getClass().getClassLoader().
				getResource(this.fileName).getFile());
		if (!file.exists() || !file.isFile()) {
			throw new Exception(fileName + "对应的文件不存在或者不是文件，请检查");
		}
		return file;
	}
	
	/**
	 * @desc 解析xml过程，生成Configuration实例对象。
	 * @author Liuweian
	 * @createTime 2019年4月10日 下午5:40:10
	 * @return
	 * @throws Exception 
	 */
	public Configuration parserFile() throws Exception {
		File file = this.getFile();
		
		Configuration configuration = null;
		ConfigurationParser parser = new ConfigurationParser(warnings);
		
		try {
			configuration = parser.parseConfiguration(file);
		} catch (IOException e) {
			throw new Exception(fileName + "文件解析失败,失败信息为：" + e);
		} catch (XMLParserException e) {
			throw new Exception(fileName + "文件解析失败,失败信息为：" + e);
		}
		
		if (configuration == null) {
			throw new Exception(fileName + "文件解析失败：");
		}
		return configuration;
	}
	
	/**
	 * @desc 
	 * @author Liuweian
	 * @createTime 2019年4月11日 下午5:59:01
	 * @return
	 */
	public MyBatisGenerator getShellCallback(boolean overwrite) {
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = null;  

		try {
			myBatisGenerator = new MyBatisGenerator(this.parserFile(), callback, warnings);
		} catch (Exception e) {
			System.out.println("设置错误，错误信息为：\r\n" + e);
			e.printStackTrace();
		} 
		return myBatisGenerator;
	}
}
