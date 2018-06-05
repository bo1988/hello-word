package com.longhai.controller;

import java.io.IOException;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.longhai.pojo.Account;
import com.longhai.service.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {	
	@Resource 
	private LoginService loginservice;
	
	
	@RequestMapping("/toLogin.do")
	@ResponseBody
	public Map<String, Object> toLogin( Account a) throws IOException {
		
		System.out.println(a);        		 		
		Map<String, Object> map=loginservice.findByAccount(a);
		return map;
	}
	
	@RequestMapping("/toRegister.do")
	@ResponseBody
	public Map<String, Object> toRegister(Account a) {
		
		System.out.println(a);
		Map<String, Object> map=loginservice.addinsertUser(a);
		return map;
	}
}