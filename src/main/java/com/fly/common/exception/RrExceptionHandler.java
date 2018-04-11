package com.fly.common.exception;

import com.fly.common.utils.Rr;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * @author liufei
 */
@RestControllerAdvice
public class RrExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(RrException.class)
	public Rr handleRRException(RrException e){
		Rr rr = new Rr();
		rr.put("code", e.getCode());
		rr.put("msg", e.getMessage());

		return rr;
	}
	@ExceptionHandler(DuplicateKeyException.class)
	public Rr handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return Rr.error("数据库中已存在该记录");
	}
	@ExceptionHandler(AuthorizationException.class)
	public Rr handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return Rr.error("没有权限，请联系管理员授权");
	}
	@ExceptionHandler(Exception.class)
	public Rr handleException(Exception e){
		logger.error(e.getMessage(), e);
		return Rr.error();
	}
}
