package com.liu.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liu.generator.host.LoadFile;

/**
 * @desc 程序入口
 * @author Liuweian
 * @createTime 2019年4月10日 下午5:31:40
 * @version 1.0.0
 */
public class Application {
	
	static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) throws Exception {
		LoadFile loadFile = new LoadFile();
		
		// 获取MyBatisGenerator对象。
		MyBatisGenerator generator = loadFile.getShellCallback(true);
		
		// 生成过程。
		generator.generate(null);
		
		logger.info("代码生成结束！！");
	}
}
