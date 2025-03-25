package com.example.NetconfClient1;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.netconf.model.AuthController;

@RestController
@RequestMapping("/netconf")

public class NetconfController {
	private final AuthController authController;
	public NetconfController(AuthController authController) {
		this.authController = new AuthController();
		
	}
	
	private static final Logger logger = LoggerFactory.getLogger(NetconfController.class);
	private final String sessionId = UUID.randomUUID().toString();
	
	public NetconfController() {
		this.authController = new AuthController();
		logger.info("New NetconfController instance create with session ID :{}",sessionId);
	}
	
	
	
	@GetMapping(value ="/get", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> get(@RequestBody Map<String,String> request ) {
		String messageId = request.get("messageId");
		logger.info("Session ID: {} - GET operation invoked", sessionId);
		
			String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		            "<rpc messageId=\""+ messageId + "\">\n" +
					"     <get>\n"+
		            "        <filter type =\"subtree\">\n" +
					"             <!--Add specific filter elements here -->\n"+
					"            <filter/>\n"+
					"     <get>\n"+
		            "</rpc>";
			return ResponseEntity.ok(response);
		} 
		//messageId = request.get("messageId");
	
	@PostMapping(value= "/get-config",produces=MediaType.APPLICATION_ATOM_XML_VALUE)
	//@GetMapping(value="/get-config",produces = MediaType.APPLICATION_ATOM_XML_VALUE)
	public ResponseEntity<String> getConfig(@RequestBody Map<String,String> request ) {
		logger.info("Session ID :{} - GET-CONFIG operation", sessionId);
		String messageId = request.get("messageId");
		String operation = request.get("operation");
		
		
		if ("get-config".equals(operation)) {
			String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		            "<rpc messageId=\""+ messageId + "\">\n" +
					"     <get-config>\n"+
		            "          <source>\n" +
					"            <running/>\n"+
		            "          <source>\n"+
					"     <get-config>\n"+
		            "</rpc>";
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.badRequest().body("Invalid");
		}
		//messageId = request.get("messageId");
	}
	@PostMapping(value = "/edit-config",produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> editconfig(@RequestBody Map<String,String> request ) {
		String messageId = request.get("messageId");
		String operation = request.get("operation");
		String configData = request.get("configData");
		
		logger.info("Session ID: {} - EDIT-CONFIG operation invoked", sessionId,configData);
		
		

		if ("edit-config".equals(operation)) {
			String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		            "<rpc messageId=\""+ messageId + "\">\n" +
					"     <edit-config>\n"+
		            "          <data>\n" +
					"            "+ configData + "\n" +
		            "          <data>\n"+
					"     <edit-config>\n"+
		            "</rpc>";
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.badRequest().body("Invalid");
		}
	}
	
	
	//private NetconfClient netconfClient = new NetconfClient();
	
	//public NetconfController(NetconfClient netconfClient) {
		//this.netconfClient=netconfClient;
	//}
	
	//@GetMapping(value = "/get-config", produces = "application/xml")
	//public String getconfig(@RequestParam(defaultValue = "101") String messageId) {
	//	return netconfClient.getNetconfConfig(messageId);
	//}
	//@GetMapping("/get-config")
	//public String getconfig(@RequestParam(defaultValue = "101") String messageId) {
		//return netconfClient.getNetconfConfig(messageId);
	//}
	
	//@PutMapping("/edit-config")
	//public String editConfig(@RequestParam(defaultValue = "102") String messageId,
		//	@RequestBody String newConfig) {
		//return netconfClient.editNetconfConfig(messageId, newConfig);
		
	//}

	//public static void main(String[] args) {
		// TODO Auto-generated method stub

	//}

}
