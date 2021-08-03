package com.moxi.agenttool.ui.main.fragment;

import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * @Description:
 * @Author: mengweijin
 * @Date: Create in 2017/11/13 19:41
 * @Modified:
 */
public class XmlAndJsonUtils {

    /**
     * 使用fastjson
     * @param object 集合或者对象
     * @return
     */
    public static String formatFastJSON(Object object){
        return com.alibaba.fastjson.JSONObject.toJSONString(object,
                // 输出key时是否使用双引号,默认为true
                SerializerFeature.QuoteFieldNames,
                // Enum输出name()或者original,默认为false
                SerializerFeature.WriteEnumUsingToString,
                // 结果是否格式化,默认为false
                SerializerFeature.PrettyFormat,
                // 是否输出值为null的字段,默认为false
                SerializerFeature.WriteMapNullValue,
                // 字符类型字段如果为null,输出为"",而非null
                SerializerFeature.WriteNullStringAsEmpty,
                // Boolean字段如果为null,输出为false,而非null
                // SerializerFeature.WriteNullBooleanAsFalse,
                // 消除对同一对象循环引用的问题，默认为false
                SerializerFeature.DisableCircularReferenceDetect,
                // List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty);
    }



    /**
     * format xml
     * @param xml
     * @return
     * @throws TransformerException
     */
    public static String formatXml(String xml) {

        try(BufferedReader reader = new BufferedReader(new StringReader(xml));
            StringWriter writer = new StringWriter()){

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new StreamSource(reader), new StreamResult(writer));
            return writer.toString();
        } catch (TransformerException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}