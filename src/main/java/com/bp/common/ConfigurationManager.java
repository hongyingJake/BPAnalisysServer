package com.bp.common;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ��ȡ�����ļ�������
 * 
 * @author aaa
 *
 */
public class ConfigurationManager {
	private String ConfigFileName;
	/**
	 * ������
	 * @param FileName �����ĵ�����
	 */
	public ConfigurationManager(String FileName)
	{
		this.ConfigFileName=FileName;
	}
	/**
	 * Ĭ�Ϲ�����  �����ļ�Ĭ������config.xml
	 */
	public ConfigurationManager()
	{
		this.ConfigFileName="config.xml";
	}
	/**
	 * ��ȡ�����ļ���Ϣ
	 * 
	 * @param nodeName
	 *            xml�ĵ��ڵ������
	 * @return
	 */
	public  String ReadConfiByNodeName(String nodeName) {
		String nodeValue = "";
		Element element = null;
		String filePath=this.getClass().getClassLoader().getResource(this.ConfigFileName).getFile();
		File f =new File(filePath);
		//���¿�ֱ�ӻ�ȡ�ļ�����Ϣ
		//InputStream stream= this.getClass().getClassLoader().getResourceAsStream("config.xml");
		if (!f.exists()) {
			LogWritter.LogWritterInfo(ConfigurationManager.class, LogEnum.Error, "ϵͳ�����ļ�config.xmlδ�ҵ���ϵͳ·����"+filePath);
			return "";
		}
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		try {
			// ����documentBuilderFactory����
			dbf = DocumentBuilderFactory.newInstance();
			// ����db������documentBuilderFatory�����÷���documentBuildr����
			db = dbf.newDocumentBuilder();

			// �õ�һ��DOM�����ظ�document����
			Document dt = db.parse(f);
			// �õ�һ��elment��Ԫ��
			element = dt.getDocumentElement();
			// ��ø�Ԫ���µ��ӽڵ�
			NodeList childNodes = element.getChildNodes();
			// ������Щ�ӽڵ�
			for (int i = 0; i < childNodes.getLength(); i++) {
				// ���ÿ����Ӧλ��i�Ľ��
				Node node = childNodes.item(i);
				if (nodeName.equals(node.getNodeName())) {
					nodeValue = node.getTextContent();
					break;
				}
			}
			return nodeValue;
		} catch (Exception e) {
			LogWritter.LogWritterInfo(ConfigurationManager.class, LogEnum.Error, "�������ļ�config.xml��ȡ������Ϣ�쳣��");
			return "";
		}
	}
}