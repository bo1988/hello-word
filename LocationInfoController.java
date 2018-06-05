package com.longhai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longhai.pojo.Param;
import com.longhai.util.HttpRequst;

@Controller
@RequestMapping("/locationInfo")
public class LocationInfoController {
	//到高德地图获取目的点的详细信息
	@RequestMapping(value="/toLocation.do",produces="application/json;charset=UTF-8")
	@ResponseBody	
	public  String toLocation(  Param param) {	
		
		String params="key=c663b4064db54ab161778b3360e2e6f6&keywords="+param.getKeywords()+"&city="+param.getCity();
		String s=HttpRequst.sendPost("http://restapi.amap.com/v3/assistant/inputtips", params);
		
		s=s.replace("tips","data");
	    s=s.replace("status","flag");
	    s=s.replace("id","di");
	    s=s.replace("in","ni");
	    char[] cs = s.toCharArray();
	    cs[9]='0';
	    s= new String(cs);		
		System.out.println(s);
		return s;
	}
}
