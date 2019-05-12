package org.muses.jeeplatform.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {

	Logger log = null;

	public BaseController(){
		this.log = getInstance();
	}

	/**
	 * 获取日志对象
	 * @return
	 */
	public Logger getInstance(){
		if(log == null){
			log = LoggerFactory.getLogger(BaseController.class);
		}
		return log;
	}

	public void debug(String message){
		log.debug(message);
	}

	public void info(String message){
		log.info(message);
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}

}
