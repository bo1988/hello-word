package com.longhai.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longhai.pojo.Content;
import com.longhai.pojo.Refresh;
import com.longhai.pojo.ScheduleContent;
import com.longhai.pojo.UpdateOrder;

import com.longhai.service.UserOrderService;
import com.longhai.util.JPush;

@Controller
@RequestMapping("/userorder")
public class UserOrderController {
	@Resource
	private UserOrderService userorderservice;	
	//乘客现叫车下单接口status参数固定为“0”
	@RequestMapping("/toOrder.do")
	@ResponseBody
	public Map<String,Object> insertUserOrder(Content c){
		//输出控制台的内容部署时要删除		
		System.out.println(c);
		Map<String,Object> map=userorderservice.insertUserOrder(c);
		return map;
		
	}
	//乘客预约车下单接口status参数固定为“0”
	@RequestMapping("/toScheduleOrder.do")
	@ResponseBody
	public Map<String,Object> insertUserScheduleOrder(ScheduleContent c){
		//输出控制台的内容部署时要删除		
		System.out.println(c);
		Map<String,Object> map=userorderservice.insertUserScheduleOrder(c);
		return map;
			
		}
	//刷新订单接口
	@RequestMapping("/toRefresh.do")
	@ResponseBody
	public Map<String,Object> selectUserOrder(Refresh r){	
		System.out.println(r);
		Map<String,Object> map=userorderservice.selectUserOrder(r);
		return map;		
	}
	
	//抢单接口status参数固定为“1”
	@RequestMapping("/toUpdate.do")
	@ResponseBody
	public Map<String,Object> updateUserOrder(UpdateOrder u){		
		System.out.println(u);		
		String infomation="1";
		String alert="司机已接单";
		
		Map<String,Object> map=userorderservice.updateUserOrder(u);
		String flag=map.get("flag").toString();
		
		if("0".equals(flag)){
			Map<String,Object> json= userorderservice.Orderpid(u);
			//pid表示乘客的user_id,did表示司机的user_id
			String pid=json.get("pid").toString();
			System.out.println("wjb"+pid);	
			String Orderdetail_id=String.valueOf(u.getOrderdetail_id());
		    JPush.jiguangPush(pid,infomation,Orderdetail_id,alert);
		}
		//计算钱的逻辑未添加
		return map;		
	}
	
	//取消订单接口status为4 暂时未用
	@RequestMapping("/toCancel.do")
	@ResponseBody
	public Map<String,Object> cancelUserOrder(UpdateOrder u){
		System.out.println(u);
		
		Map<String,Object> map=userorderservice.updateUserOrder(u);
		//取消订单时查看改单是否已被抢单，已被强要发推送通知抢单司机该单已取消
		//userorderservice.cancelOrderdid(u);
		return map;		
	}
	
	//更新接到乘客的时间
	@RequestMapping("/toUpdateTwo.do")
	@ResponseBody
	public Map<String,Object>  updateUserOrderTwo (UpdateOrder u){
		System.out.println(u);
		String infomation="2";
		String alert="您已上车";
		
		Map<String,Object> map=userorderservice.updateUserOrderTwo(u);
		String flag=map.get("flag").toString();
		if("0".equals(flag)){
			Map<String,Object> json= userorderservice.Orderpid(u);
			//pid表示乘客的user_id,did表示司机的user_id
			String pid=json.get("pid").toString();
			String Orderdetail_id=String.valueOf(u.getOrderdetail_id());
		    JPush.jiguangPush(pid,infomation,Orderdetail_id,alert);	
		}
		return map;	
		
	}
	//司机到达目的地
	@RequestMapping("/toUpdateThree.do")
	@ResponseBody
	public Map<String,Object> updateUserOrderThree(UpdateOrder u){
		System.out.println(u);
		String infomation="3";
		String alert="您已经到达目的地";
		Map<String,Object> map=userorderservice.updateUserOrderThree(u);
		String flag=map.get("flag").toString();
		if("0".equals(flag)){
			Map<String,Object> json= userorderservice.Orderpid(u);
			//pid表示乘客的user_id,did表示司机的user_id
			String pid=json.get("pid").toString();
			String Orderdetail_id=String.valueOf(u.getOrderdetail_id());
		    JPush.jiguangPush(pid,infomation,Orderdetail_id,alert);			
		}
		return map;	
		
	}
	//支付提醒
	@RequestMapping("/toPayment.do")
	@ResponseBody
	public Map<String,Object> payment(UpdateOrder u){
		System.out.println(u);
		String infomation="4";
		String alert="乘客已经支付完成";	
		//这里将来添加支付逻辑............................
		Map<String,Object> map=userorderservice.payment(u);
		String flag=map.get("flag").toString();
		if("0".equals(flag)){
			Map<String,Object> json= userorderservice.Orderpid(u);
			//pid表示乘客的user_id,did表示司机的user_id
			String pid=json.get("did").toString();
			String Orderdetail_id=String.valueOf(u.getOrderdetail_id());
			JPush.jiguangPush(pid,infomation,Orderdetail_id,alert);
		}		
		return map;			
		
	}
	//司机抢单成功后向乘客发起推送后跳转页面的订单详情
	@RequestMapping("/toOrderInfo.do")
	@ResponseBody
	public Map<String,Object> orderInfo(UpdateOrder u){
		System.out.println(u);
		Map<String,Object> map=userorderservice.orderInfo(u);
		return map;	
			
	}
}








