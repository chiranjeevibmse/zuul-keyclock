package com.chiru;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceAController {
	@GetMapping("/getA")
	public String getAResource(){
		return "Resource A";
	}
}
