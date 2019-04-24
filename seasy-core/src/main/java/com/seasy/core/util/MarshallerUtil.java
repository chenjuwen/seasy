package com.seasy.core.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.seasy.interfaces.dto.RolesDTO;

public class MarshallerUtil {
	/**
	 * 将jaxb对象编组(Marshaller)为xml格式的字符串
	 * 
	 * @param jaxbElement jaxb对象，类必须加注解类 @XmlRootElement
	 * @return xmlContent xml格式的字符串
	 */
	public static String marshaller(Object jaxbElement){
		String result = null;
		try{
			JAXBContext context = JAXBContext.newInstance(jaxbElement.getClass());
			Marshaller marshaller = context.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false); //是否省略xml头信息

            StringWriter writer = new StringWriter();  
            marshaller.marshal(jaxbElement, writer);  
            result = writer.toString();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将xml格式的字符串解组(Unmarshaller )为jaxb对象
	 * @param xmlContent xml格式的字符串
	 * @param declaredType jaxb对象类型
	 */
	public static <T> T Unmarshaller(String xmlContent, Class<T> declaredType){
		try{
			JAXBContext context = JAXBContext.newInstance(declaredType);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<T> jaxbElement = unmarshaller.unmarshal(new StreamSource(new StringReader(xmlContent)), declaredType);
			return jaxbElement.getValue();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		RolesDTO role = new RolesDTO();
		role.setId(1L);
		role.setRoleNo("admin");
		role.setRoleName("Administrator");
		role.setRoleDesc("admin role");
		String xmlContent = marshaller(role);
		System.out.println(xmlContent);
		
		role = Unmarshaller(xmlContent, RolesDTO.class);
		System.out.println(role.getId() + ", " + role.getRoleNo() + ", " + role.getRoleName() + ", " + role.getRoleDesc());
	}
	
}
