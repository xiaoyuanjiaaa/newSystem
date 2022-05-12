package com.ruoyi.web.controller.ureport;

import com.ruoyi.system.service.UreportFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
public class ViewByIDController {
	private final Logger log= LoggerFactory.getLogger(getClass());
	@Autowired
	private UreportFileService ureportFileService;
	@RequestMapping("/getView")
	public ModelAndView index(
			@RequestParam(value = "viewName", defaultValue = "person") String viewName,
			HttpSession session){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/ureport/designer?_u="+"yqfk-"+viewName.concat(".ureport.xml")); // 使用重定向
		// mv.setViewName("forward:/ureport/designer?_u=report-sssss.ureport.xml"); // 隐式跳转
		return mv;
	}
}
