package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class AddLimitOffsetPlugin extends PluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}

	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType offsetType = new FullyQualifiedJavaType(
				"javax.annotation.Generated");
		topLevelClass.addImportedType(offsetType);
		topLevelClass.addAnnotation("@Generated(\"" + introspectedTable.getTableConfiguration().getTableName() + "\")");
		 PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();
		 
		Field limit = new Field();
		limit.setName("limit");
		limit.setVisibility(JavaVisibility.PRIVATE);
		limit.setType(integerWrapper);
		topLevelClass.addField(limit);

		Method limitSet = new Method();
		limitSet.setVisibility(JavaVisibility.PUBLIC);
		limitSet.setName("setLimit");
		limitSet.addParameter(new Parameter(integerWrapper, "limit"));
		limitSet.addBodyLine("this.limit = limit;");
		topLevelClass.addMethod(limitSet);

		Method limitGet = new Method();
		limitGet.setVisibility(JavaVisibility.PUBLIC);
		limitGet.setReturnType(integerWrapper);
		limitGet.setName("getLimit");
		limitGet.addBodyLine("return limit;");
		topLevelClass.addMethod(limitGet);

		Field offset = new Field();
		offset.setName("offset");
		offset.setVisibility(JavaVisibility.PRIVATE);
		offset.setType(integerWrapper);
		topLevelClass.addField(offset);

		Method offsetSet = new Method();
		offsetSet.setVisibility(JavaVisibility.PUBLIC);
		offsetSet.setName("setOffset");
		offsetSet.addParameter(new Parameter(integerWrapper, "offset"));
		offsetSet.addBodyLine("this.offset = offset;");
		topLevelClass.addMethod(offsetSet);

		Method offsetGet = new Method();
		offsetGet.setVisibility(JavaVisibility.PUBLIC);
		offsetGet.setReturnType(integerWrapper);
		offsetGet.setName("getOffset");
		offsetGet.addBodyLine("return offset;");
		topLevelClass.addMethod(offsetGet);

		return true;
	}

	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
            XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement isNotNullElement = new XmlElement("if");
		isNotNullElement.addAttribute(new Attribute("test", "limit > 0"));
		isNotNullElement.addElement(new TextElement("limit ${limit}"));
		element.getElements().add(isNotNullElement);

		isNotNullElement = new XmlElement("if");
		isNotNullElement.addAttribute(new Attribute("test", "offset > 0"));
		isNotNullElement.addElement(new TextElement("offset ${offset}"));
		element.getElements().add(isNotNullElement);
		return true;
	}

}
