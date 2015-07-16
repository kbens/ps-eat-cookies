package com.peoplesoft.pt.custom;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

	private int logFence;
	private String cookiesToEat;
	private List<String> cookiesToEatList;
	
	@Override
	public void init(FilterConfig cfg) throws ServletException {		
		logFence = Integer.parseInt(cfg.getInitParameter("logFence"));
		cookiesToEat = String.valueOf(cfg.getInitParameter("cookiesToEat"));
		
		cookiesToEatList = Arrays.asList(cookiesToEat.split("\\s*,\\s*"));

		log("Filter enabled", 0);
    	for (String cookie : cookiesToEatList) {
    		log("All '" + cookie + "' cookies will be eaten.", 0);
		}
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
	private void log(String print, Integer level ) {
		if (logFence >= level) {
			System.out.print("PSEatCookiesFilter: ");
			System.out.println(print);
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

            if (cookiesToEatList.contains(cookieName)) 
            	log(cookieName + " has been eaten.", 1);
            else
                super.addCookie(cookie);
        }		
	}	
}
