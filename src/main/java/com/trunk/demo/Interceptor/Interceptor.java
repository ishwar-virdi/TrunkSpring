package com.trunk.demo.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class Interceptor implements HandlerInterceptor {

	@Autowired
	private HttpSession session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object objec0t)
			throws Exception {
		String requestURI = request.getRequestURI();
		if ("/api/v1/login".equals(requestURI) || "/api/v1/token".equals(requestURI)
				|| "/api/v1/register".equals(requestURI) || "/api/v1/userLogin".equals(requestURI)
				|| "/api/v1/userLogout".equals(requestURI)) {
			return true;
		}
		if (session.getAttribute(session.getId()) != null) {
			return true;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		// System.out.println("In postHandle request processing " + "completed by
		// @RestController");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		// System.out.println("In afterCompletion Request Completed");
	}

}