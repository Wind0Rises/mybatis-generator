package com.liu.generator.host;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @desc 自定义注释配置。
 * @author Liuweian
 * @createTime 2019年8月19日 上午10:29:04
 * @version 1.0.0
 */
public class CustomCommentGenerator extends DefaultCommentGenerator implements CommentGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(CustomCommentGenerator.class);
	
	private Properties properties;
	
	private Properties systemPro;
	
	private boolean suppressDate;
	
	private boolean suppressAllComments;
	
	private String currentDateStr;

	public CustomCommentGenerator() {
		super();
		properties = new Properties();
		systemPro = System.getProperties();
		suppressDate = false;
		suppressAllComments = false;
		currentDateStr = (new SimpleDateFormat("yyyy-MM-dd HH:ss:mm")).format(new Date());
	}

	@Override
	public void addConfigurationProperties(Properties properties) {
		logger.info("________________LL_______________");
	}

	/**
	 * 设置字段的注释。
	 */
	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		if (suppressAllComments) {
			return;
		}
		
		String remakr = introspectedColumn.getRemarks();
		if (remakr == null) {
			remakr = "";
		}
		
		StringBuilder fieldComment = new StringBuilder();
		fieldComment.append("/**").append("\r\n");
		fieldComment.append("     * ").append(remakr).append("\r\n");
		fieldComment.append("     */");
		
		field.addJavaDocLine(fieldComment.toString());
		
		// addJavadocTag(field, false); // generator自定义的注释。
	}

	/**
	 * 设置类的注释。
	 */
	@Override
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
		
		StringBuilder classComment  = new StringBuilder();
		classComment.append("/**").append("\r\n");
		classComment.append(" * @desc ").append(introspectedTable.getFullyQualifiedTable()).append("\r\n");
		classComment.append(" * @author Liuweian").append("\r\n");
		classComment.append(" * @date ").append(currentDateStr).append("\r\n");
		classComment.append(" * @version 1.0.0").append("\r\n");
		classComment.append(" */");
		
		topLevelClass.addJavaDocLine(classComment.toString());
	}


	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
		if (suppressAllComments) {
			return;
		}
		
		StringBuilder methodComment  = new StringBuilder();
		methodComment.append("/**").append("\r\n");
		methodComment.append("     * @desc ").append("\r\n");
		methodComment.append("     * @author Liuweian").append("\r\n");
		methodComment.append("     * @date ").append(currentDateStr).append("\r\n");
		
		methodComment.append("     * @return ").append("\r\n");
		
		// 获取参数。
		List<Parameter> parameterList = method.getParameters();
		if (!parameterList.isEmpty()) {
			Iterator<Parameter> iterator = parameterList.iterator();
			while (iterator.hasNext()) {
				Parameter parameter = iterator.next();
				methodComment.append("     * @parameter ").append(parameter.getName()).append("\r\n");
			}
		}
		
		// 获取异常
		List<FullyQualifiedJavaType> exceptionList = method.getExceptions();
		if (!exceptionList.isEmpty()) {
			Iterator<FullyQualifiedJavaType> iterator = exceptionList.iterator();
			while (iterator.hasNext()) {
				FullyQualifiedJavaType excep = iterator.next();
				methodComment.append("     * @throw ").append(excep.getShortName()).append("\r\n");
			}
		}
		
		methodComment.append("     */");
		
		method.addJavaDocLine(methodComment.toString());
	}
	
	/**
	 * get方法
	 */
	@Override
	public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
	       
	}

	/*
	 * set方法
	 */
	@Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
       
    }
	
	/**
	 * mapper.xml的注释
	 */
	@Override
	public void addComment(XmlElement xmlElement) {
		
		
	}
}
