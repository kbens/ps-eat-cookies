package com.peoplesoft.pt.custom;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 06/01/2015 This filter will prevent unwanted Cookies in the response from PS servlets
 */
public class PSEatCookiesFilter implements Filter {

	private boolean verbose;
	private String cookieToEat;
	
	@Override
	public void init(FilterConfig cfg) throws ServletException {		
		verbose = Boolean.valueOf(cfg.getInitParameter("verbose"));
		cookieToEat = String.valueOf(cfg.getInitParameter("cookieToEat"));
		
    	verbPrint("All '" + cookieToEat + "' cookies will be eaten.");
	}

	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest servReq, ServletResponse servRes,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)servReq;
		HttpServletResponse res = (HttpServletResponse)servRes;
		
		chain.doFilter(req,new PSEatCookiesWrapper(res));
	}	
	
	/**
	 * outputs debug info to standard out if the verbose
	 * init parameter is set to true
	 *
	 *  @param s - String to output
	 */
	private void verbPrint(String s) {
		if (verbose) {
			System.out.print("PSEatCookiesFilter: ");
			System.out.println(s);
		}
	}
	
	/**
	 * HttpServletRequestWrapper that prevents certain cookies from being
	 * added to the servlet response.
	 */
	private class PSEatCookiesWrapper extends HttpServletResponseWrapper {
		 
		public PSEatCookiesWrapper(HttpServletResponse response) {
			super(response);
		}

		@Override
		public void addCookie(Cookie cookie) {
			String cookieName = cookie.getName();

            if (cookieName.equalsIgnoreCase(cookieToEat)) 
            	verbPrint(cookieName + " has been eaten.");
            else
                super.addCookie(cookie);
        }		
	}	
}
