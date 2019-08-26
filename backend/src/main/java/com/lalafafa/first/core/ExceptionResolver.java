package com.lalafafa.first.core;

import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * 异常处理
 * 
 * @author coflich
 *
 */
@RestControllerAdvice
public class ExceptionResolver {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
	
	
	/**
	 * 参数校验错误
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public Result handleValidatorException(ConstraintViolationException e) {
		String msg = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
		return ResultGenerator.genFailResult(msg);
	}
	
	/**
	 * 框架参数校验错误
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result handleValidatorException(MethodArgumentNotValidException e) {
		String msg = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
		return ResultGenerator.genFailResult(msg);
	}
	
	/**
	 * 服务错误
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ ServiceException.class })
	public Result handleServiceException(ServiceException e) {
		return ResultGenerator.genFailResult(e.getMessage());
	}
	
	/**
	 * 服务器异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ ServletException.class })
	public Result handleServletException(ServletException e) {
		logger.error("服务器异常：", e);
		return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR, MessageService.getMessage("error.default.server_wrong"));
	}
	
	/**
	 * 数据库异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public Result handleDatabaseException(Throwable e) {
		logger.error("数据库异常：", e);
		return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
	}
	
	/**
	 * 未经认证
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ BadCredentialsException.class, AuthenticationException.class, UsernameNotFoundException.class })
	public Result handleAuthException(Throwable e) {
		return ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED, MessageService.getMessage("error.default.unauthentication"));
	}
	
	/**
	 * 未被授权
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ AccessDeniedException.class })
	public Result handleUserException(AccessDeniedException e, HttpServletRequest request) {
		String msg = MessageService.getMessage("error.default.forbidden", request.getRequestURI());
		return ResultGenerator.genFailResult(ResultCode.FORBIDDEN, e.getMessage());
	}
	
	/**
	 * API不存在
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public Result handleApiNotException(Throwable e, HttpServletRequest request) {
		String msg = MessageService.getMessage("error.default.api_not_found", request.getRequestURI());
		return ResultGenerator.genFailResult(ResultCode.NOT_FOUND, msg);
	}
	
	/**
	 * 访问资源错误
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result handleMethodNotSupport(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
		String msg = MessageService.getMessage("error.default.request_method_not_supported", request.getRequestURI(), request.getMethod());
		return ResultGenerator.genFailResult(ResultCode.METHOD_NOT_SUPPORTED, msg);
	}
	
	/**
	 * 全局异常
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public Result handleGlobalException(HttpServletRequest request, Throwable e) {
		logger.error("全局异常：", e);
		String msg = MessageService.getMessage("error.default.server_wrong", request.getRequestURI(), e.getMessage());
		return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR, msg);
	}
}