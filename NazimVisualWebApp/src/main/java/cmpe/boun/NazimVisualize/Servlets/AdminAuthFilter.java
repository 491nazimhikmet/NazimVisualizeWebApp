package cmpe.boun.NazimVisualize.Servlets;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cmpe.boun.NazimVisualize.Model.User;

public class AdminAuthFilter implements Filter{
	private ArrayList<String> urlList;
	
	public void  init(FilterConfig config) throws ServletException{
		String urls = config.getInitParameter("look-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
 
        urlList = new ArrayList<String>();
 
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
 
        }
	}
	public void  doFilter(ServletRequest req, ServletResponse res,FilterChain chain) 
	              						throws java.io.IOException, ServletException {
	
		// Get the IP address of client machine.   
		String ipAddress = req.getRemoteAddr();
	
	   
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String url = request.getServletPath();

		boolean allowedRequest = true;
		
		if(urlList.contains(url)) {
			allowedRequest = false;
		}
			
		if (!allowedRequest) {
			HttpSession session = request.getSession(false);
			if (null == session) {
				response.sendRedirect("notAuthorized");
			}else if(((User)session.getAttribute("user")).getType() != 2){
				response.sendRedirect("notAuthorized");
			}
		}		
		
		
		chain.doFilter(req, res);

	}
	public void destroy( ){
	   /* Called before the Filter instance is removed 
	   from service by the web container*/
	}
}
