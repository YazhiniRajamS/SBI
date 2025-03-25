package com.example.NetconfClient1;

import java.io.FileWriter;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Service
public class NetconfClient {
	public String getNetconfConfig(String messageId) {
		try {
			return createNetconfMessage(messageId);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return messageId;
	}
	
	// method for edit-config
	public String editNetconfConfig(String messageId,String newConfig) {
		try {
			return createEditNetconfMessage(messageId,newConfig);
		} catch (Exception e) {
			e.printStackTrace();
			return "<error>Failed to generate NETCONF edit XML</error>";
			
		}
	}
	
	//generates Xml for get-config
	
	private String createNetconfMessage(String messageId) throws ParserConfigurationException {
		return generateXml(messageId,"get-config",null);
	}
	//Generate XML for edit-config
	private String createEditNetconfMessage(String messageId,String newConfig) throws ParserConfigurationException {
		return generateXml(messageId,"edit-config",newConfig);
	}
	
	
	public String getConfig(@RequestParam(defaultValue = "101") String messageId) throws ParserConfigurationException, TransformerException {
		return createNetconfMessage(messageId);
	}
	public static String escapeXML(String input) {
		return input.replaceAll("&", "&amp;")
				.replaceAll("<", "&1t;")
				.replaceAll(">", "&gt;")
				.replaceAll("/", "&quot;")
				.replaceAll("'", "&apos;");
	}
	//common method to generate xml
	//public static String createNetcofMessage(String messageId, String newConfig) throws ParserConfigurationException, TransformerException {
		private String generateXml(String messageId,String operation,String configData) throws ParserConfigurationException {
		String safeMessageId= escapeXML(messageId);
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document doc = builder.newDocument();
		
		org.w3c.dom.Element rpc = doc.createElement("rpc");
		rpc.setAttribute("messageId", safeMessageId);
		doc.appendChild(rpc);
		
		org.w3c.dom.Element operationElement = doc.createElement(operation);
		rpc.appendChild(operationElement);
		
		if ("edit-confif".equals(operation)) {
			org.w3c.dom.Element target = doc.createElement("target");
			operationElement.appendChild(target);
			
			org.w3c.dom.Element running = doc.createElement("running");
			target.appendChild(running);
			
			org.w3c.dom.Element config = doc.createElement("config");
			config.setTextContent(escapeXML(configData));
			operationElement.appendChild(config);
		} else {
			org.w3c.dom.Element source= doc.createElement("source");
			operationElement.appendChild(source);
			
			org.w3c.dom.Element running = doc.createElement("running");
			source.appendChild(running);
		}
		
		//org.w3c.dom.Element getconfig = doc.createElement("get-config");
		//rpc.appendChild(getconfig);
		
		//org.w3c.dom.Element source = doc.createElement("source");
		//getconfig.appendChild(source);
		
		//org.w3c.dom.Element running = doc.createElement("running");
		//source.appendChild(running);
		
		try {
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();	
		transformer.setOutputProperty(OutputKeys.INDENT,"yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"No");
		
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		
		return writer.toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	public static void saveXmlToFile(String filePath,String xmlContent) throws IOException{
		try (FileWriter fileWriter = new FileWriter(filePath)){
			fileWriter.write(xmlContent);
		}
		
	}
	public static void main(String[] args) {
		
		try {
			System.out.println("Starting NetconfClient");
			NetconfClient netconfClient = new NetconfClient();
			
			String netconfMessage = netconfClient.getNetconfConfig("101");
			//String netconfMessage = createNetconfMessage("101");
			//System.out.println("Generated NETCONF XML Message:\n"  + netconfMessage);
			
			//String filePath = "netconf_message.xml";
			//saveXmlToFile(filePath,netconfMessage);
			
			System.out.println("Generated NETCONF Message");
			System.out.println(netconfMessage);
			System.out.println(netconfClient);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		// TODO Auto-generated method stub

	}

}
