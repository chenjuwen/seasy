package com.seasy.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jUtilTest {
	private static Map<String, ConfigBean> configMap = new HashMap<>();
	
	public static void main(String[] args) throws Exception{
        loadConfig();
        getConfigMap().values().forEach(c -> {
        	System.out.println(c.getId() + ", " + c.getLatitude() + ", " + c.getLongitude() + ", " + c.getAddress() + ", " + c.getComments());
        });
        
        ConfigBean configBean = new ConfigBean();
        configBean.setId("1585634486665");
        configBean.setLatitude(1.11);
        configBean.setLongitude(2.22);
        configBean.setAddress("address3");
        configBean.setComments("comments4");
//        updateConfig(configBean);
        

        ConfigBean configBean2 = new ConfigBean();
        configBean2.setLatitude(3.33);
        configBean2.setLongitude(4.44);
        configBean2.setAddress("55");
        configBean2.setComments("66\n77");
//        addConfig(configBean2);
        
        deleteConfig("1585636639278");
	}

	public static void deleteConfig(String id) {
		try{
            Document document = openDocument();
            List<Element> list = document.selectNodes("/overlays/overlay[@id='" + id + "']");
            if(list != null && list.size() > 0){
            	Element e = list.get(0);
            	e.getParent().remove(e);
            }

            writeXmlFile(document);
            System.out.println("delete ok");
        }catch (Exception ex){
            ex.printStackTrace();
        }
	}
	
    public static void updateConfig(ConfigBean configBean){
        try{
            Document document = openDocument();

            List<Element> list = document.selectNodes("/overlays/overlay[@id='" + configBean.getId() + "']");
            if(list != null && list.size() > 0){
            	Element e = list.get(0);
            	e.element("latitude").setText(String.valueOf(configBean.getLatitude()));
            	e.element("longitude").setText(String.valueOf(configBean.getLongitude()));
            	e.element("address").setText(configBean.getAddress());
            	
            	//更新CDATA数据时，要先清空再重新添加
            	e.element("comments").clearContent();
            	e.element("comments").addCDATA(configBean.getComments());
            }

            writeXmlFile(document);
            System.out.println("update ok");
            
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

	private static Document openDocument() throws DocumentException, FileNotFoundException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new FileInputStream(new File(getConfigFileFullPath())));
		return document;
	}

	private static void writeXmlFile(Document document)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		
		XMLWriter writer2 = new XMLWriter(new FileOutputStream(new File(getConfigFileFullPath())), format);
		writer2.write(document);
		writer2.close();
	}

    public static void loadConfig(){
        try {
        	configMap.clear();
        	
            Document document = openDocument();
            Element root = document.getRootElement();

            List<Node> nodeList = root.selectNodes("overlay");
            if(nodeList != null){
                for(Node node : nodeList){
                    String id = getAttributeValue(node, "id");
                    String latitude = getNodeText(node.selectSingleNode("latitude"));
                    String longitude = getNodeText(node.selectSingleNode("longitude"));
                    String address = getNodeText(node.selectSingleNode("address"));
                    String comments = getNodeText(node.selectSingleNode("comments"));

                    ConfigBean bean = new ConfigBean();
                    bean.setId(id);
                    bean.setLatitude(Double.parseDouble(latitude));
                    bean.setLongitude(Double.parseDouble(longitude));
                    bean.setAddress(address);
                    bean.setComments(comments);
                    
                    configMap.put(id, bean);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
	
	public static void addConfig(ConfigBean configBean){
        try{
            Document document = openDocument();
            Element root = document.getRootElement();

            Element e = root.addElement("overlay");
            e.addAttribute("id", String.valueOf(System.currentTimeMillis()));
            Element e1 = e.addElement("latitude");
            e1.addText(String.valueOf(configBean.getLatitude()));
            Element e2 = e.addElement("longitude");
            e2.addText(String.valueOf(configBean.getLongitude()));
            Element e3 = e.addElement("address");
            e3.addText(configBean.getAddress());
            Element e4 = e.addElement("comments");
            e4.addCDATA(configBean.getComments());

            writeXmlFile(document);
            System.out.println("add ok");
            
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

	
    public static String getConfigFileFullPath(){
        return System.getProperty("user.dir") + File.separator + "overlays.xml";
    }


    /**
     * 获取节点属性值
     */
    private static String getAttributeValue(Node node, String attrName){
        if(node == null){
            return "";
        }

        String value = node.valueOf("@"+attrName);
        if(value == null){
            return "";
        }else{
            return value.trim();
        }
    }

    private static void setAttributeValue(Element element, String attrName, String attrValue){
        if(element.attribute(attrName) != null){
            element.attribute(attrName).setValue(attrValue);
        }else{
            element.addAttribute(attrName, attrValue);
        }
    }

    /**
     * 获取节点文本内容
     */
    private static  String getNodeText(Node node){
        if(node == null){
            return "";
        }else{
            String value = node.getText();
            if(value == null){
                return "";
            }else{
                return value.trim();
            }
        }
    }

	public static Map<String, ConfigBean> getConfigMap() {
		return configMap;
	}
    
    
}
