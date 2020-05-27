package co.edu.usbcali.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.bank.dto.ClientLoginDTO;
import co.edu.usbcali.bank.service.ClientLogin;

@RestController
@RequestMapping("/api/client/auth/")
@CrossOrigin("*")
public class ClientLoginController {
	@Autowired
	ClientLogin clientLogin;

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody ClientLoginDTO loginDTO) {
		try {
			String response = clientLogin.login(loginDTO);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseError("400", e.getMessage()));
		}
	}
}
