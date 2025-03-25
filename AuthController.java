package com.example.netconf.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class AuthController {
	
	private final List<LoginData> loginHistory = new ArrayList<> ();
	private boolean remove;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
		if("admin".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
			String sessionId = UUID.randomUUID().toString();
			loginHistory.add(new LoginData(loginRequest.getUsername(),sessionId));
			return ResponseEntity.ok(sessionId);
		} else {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INVALID CREDENTIALS");
		}
	}
	
	//Logout EndPoint
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("sessionId") String sessionId){
		boolean removed = loginHistory.removeIf(login -> login.getSessionId().equals(sessionId));
		
		return removed ? ResponseEntity.ok("Logged out successfully")
				      : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("INVALID SESSION ID");
	}
	//public boolean isValidSession(String sessionId) {
		//return loginHistory.stream().anyMatch(login.getSessionId().equals(sessionId));
	//}
	
	@GetMapping("/validate")
	public ResponseEntity<String> isValidSession(@RequestHeader("sessionId") String sessionId){
		boolean valid = loginHistory.stream().anyMatch(login -> login.getSessionId().equals(sessionId));
		return valid ? ResponseEntity.ok("Session is valid")
			      : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session is valid");
	}
	
}
