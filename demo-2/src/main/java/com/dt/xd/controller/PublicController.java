package com.dt.xd.controller;

import java.awt.PageAttributes.MediaType;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.portable.InputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dt.xd.service.xdUserService;
import com.dt.xd.service.impl.xdUserServiceImpl;
import com.dt.xd.xdProduct.XdProduct;
import com.dt.xd.xdUser.XdUser;

@Controller
public class PublicController {
	@Resource
	xdUserService XdUserService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("code");
		String imgcode = request.getParameter("imgcode");
		System.out.println("getCommodity====" + request.getParameter("phone"));
		System.out.println("getCommodity====" + request.getParameter("password"));
		int code1 = 0;
		String phone = request.getParameter("phone");
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(imgcode + "=====" + code + "===");
		if (imgcode.toUpperCase().equals(code)) {
			List<XdUser> loginList = XdUserService.ope_login(phone);
			XdUser password = loginList.get(0);
			if (password.getPassword().equals(request.getParameter("password"))) {
				code1 = 1;
				map.put("code", code1);
			} else {
				map.put("code", code1);
			}
		} else {
			map.put("code", code1);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/repassword", method = RequestMethod.POST)
	public Map<String, Object> repassword(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("code");
		String imgcode = request.getParameter("imgcode");
		System.out.println("getCommodity====" + request.getParameter("phone"));
		String phone = request.getParameter("phone");
		int code1 = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		if (imgcode.toUpperCase().equals(code)) {
			List<XdUser> loginList = XdUserService.ope_login(phone);
			XdUser password = loginList.get(0);
			XdUser user = new XdUser();
			user.setId(password.getId());
			user.setPhone(password.getPhone());
			user.setPassword(request.getParameter("password"));
			user.setHeadImg(password.getHeadImg());
			user.setEmail(password.getEmail());
			user.setName(password.getName());
			user.setStatus(password.getStatus());
			user.setTs(password.getTs());
			if (request.getParameter("password").equals(request.getParameter("password1"))) {
				XdUserService.ope_repassword(user);
				code1 = 1;
				map.put("code", code1);
			} else {
				map.put("code", code1);
			}
		} else {
			map.put("code", code1);
		}
		System.out.println(imgcode + "=====" + code + "===");
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Map<String, Object> register(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("code");
		String imgcode = request.getParameter("imgcode");
		String id = request.getParameter("id");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		int code1 = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		if (imgcode.toUpperCase().equals(code)) {
			XdUser user = new XdUser();
			user.setId(id);
			user.setPhone(phone);
			user.setPassword(password);
			XdUserService.insert(user);
			code1 = 1;
			map.put("code", code1);
		} else {
			map.put("code", code1);
		}
		System.out.println(imgcode + "=====" + code + "===");
		return map;
	}

	@RequestMapping("/fenyelike") // @RequestParam 参数是基本数据类型，不赋初始置，容易报错，用此注解赋默认值
	public String fenyelike(Map<String, Object> map,

			@RequestParam(defaultValue = "0") int pageStart,

			@RequestParam(defaultValue = "2") Integer pageSize,

			@RequestParam(defaultValue = "") String username, String id) {
		List<XdProduct> xdProductList = XdUserService.selectByName(pageStart, pageSize, username);
		long count = 0;
		if (username.equals("") || username == null)
			count = XdUserService.getCount();
		else
			count = XdUserService.getCount(username);
		map.put("count", count);
		map.put("xdProductList", xdProductList);
		map.put("pageStart", pageStart);
		map.put("pageSize", pageSize);
		map.put("username", username);
		return "service_product";
	}

	@RequestMapping("/fenyelikeS") // @RequestParam 参数是基本数据类型，不赋初始置，容易报错，用此注解赋默认值
	public String fenyelikeS(Map<String, Object> map,

			@RequestParam(defaultValue = "0") int pageStart,

			@RequestParam(defaultValue = "2") Integer pageSize,

			@RequestParam(defaultValue = "") String username, String id) {
		List<XdProduct> xdProductList = XdUserService.selectByName(pageStart, pageSize, username);
		long count = 0;
		if (username.equals("") || username == null)
			count = XdUserService.getCount();
		else
			count = XdUserService.getCount(username);
		map.put("count", count);
		map.put("xdProductList", xdProductList);
		map.put("pageStart", pageStart);
		map.put("pageSize", pageSize);
		map.put("username", username);
		return "service_orderform";
	}

	@RequestMapping("/insert")
	public String insert(Map<String, Object> map, String id, String username, String productInstruction,
			String productPrice) {
		XdProduct xdproduct = new XdProduct();
		xdproduct.setId(id);
		xdproduct.setUsername(username);
		xdproduct.setProductInstruction(productInstruction);
		xdproduct.setProductPrice(productPrice);
		XdUserService.insert(xdproduct);
		return "insertsuccess";
	}

	@RequestMapping("/fenyelikeE")
	// @RequestParam 参数是基本数据类型，不赋初始置，容易报错，用此注解赋默认值
	public String fenyelike(Map<String, Object> map, @RequestParam(defaultValue = "0") int pageStart,
			@RequestParam(defaultValue = "3") Integer pageSize, @RequestParam(defaultValue = "") String username) {
		List<XdProduct> xdProductList = XdUserService.selectByName(pageStart, pageSize, username);
		long count = 0;
		if (username.equals("") || username == null)
			count = XdUserService.getCount();
		else
			count = XdUserService.getCount(username);
		map.put("count", count);
		map.put("xdProductList", xdProductList);
		map.put("pageStart", pageStart);
		map.put("pageSize", pageSize);
		map.put("username", username);
		return "e-commerce_product";
	}

	@RequestMapping("/fenyelikeO")
	// @RequestParam 参数是基本数据类型，不赋初始置，容易报错，用此注解赋默认值
	public String fenyelikeO(Map<String, Object> map, @RequestParam(defaultValue = "0") int pageStart,
			@RequestParam(defaultValue = "3") Integer pageSize, @RequestParam(defaultValue = "") String username) {
		List<XdProduct> xdProductList = XdUserService.selectByName(pageStart, pageSize, username);
		long count = 0;
		if (username.equals("") || username == null)
			count = XdUserService.getCount();
		else
			count = XdUserService.getCount(username);
		map.put("count", count);
		map.put("xdProductList", xdProductList);
		map.put("pageStart", pageStart);
		map.put("pageSize", pageSize);
		map.put("username", username);
		return "operator_product";
	}

	@RequestMapping("/sss")
	public String sss() {
		return "/fenyelike";
	}

	@RequestMapping("/delete")
	public String deleteByPrimaryKey(String id) {
		// xdUserService x = new xdUserServiceImpl();
		int i = XdUserService.deleteByPrimaryKey(id);
		System.out.println("i=" + i + id);
		if (i == 1)
			return "deletesuccess";
		else
			return "fail";
	}

	@RequestMapping("/update")
	public String updateByPrimaryKey(XdProduct xdProduct) {
		int i = XdUserService.updateByPrimaryKeySelective(xdProduct);
		if (i == 1)
			return "updatesuccess";
		else
			return "fail";
	}

	@RequestMapping("/select")
	public String select(Map<String, Object> map, String id) {
		XdProduct xdProduct = XdUserService.selectByPrimaryKey(id);
		// int i = XdUserService.updateByPrimaryKeySelective(xdProduct);
		if (xdProduct != null) {
			map.put("xdProduct", xdProduct);
			return "updateXdProduct";
		} else
			return "fail";
	}
	@RequestMapping("/expense")
	public String expense() {
		return "service_expenses";
	}
	@RequestMapping("/store")
	public String store() {
		return "service_store";
	}
	@RequestMapping("/setting")
	public String setting() {
		return "service_setting";
	}
	@RequestMapping("/product")
	public String product() {
		return "service_product";
	}
}
