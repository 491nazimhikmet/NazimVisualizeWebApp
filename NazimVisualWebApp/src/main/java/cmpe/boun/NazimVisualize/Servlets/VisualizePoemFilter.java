package cmpe.boun.NazimVisualize.Servlets;

import java.util.ArrayList;
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

import com.google.gson.Gson;

public class VisualizePoemFilter implements Filter{
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
		
		System.out.println("url is "+url);
		
		boolean lookRequest = true;
		boolean error = false;
		
		if(containsUrl(url)) {
			lookRequest = true;
		}else{
			lookRequest = false;
		}
		
		String responseStr = "";
			
		if (lookRequest) {
			HttpSession session = request.getSession(false);
			if (null == session) {
				System.out.println("session is null");
				responseStr = "Bu işlemi yapmak için lütfen kullanıcı girişi yapınız" ;
				error = true;
			}else if(session.getAttribute("user") == null){
				System.out.println("user is null");
				responseStr = "Bu işlemi yapmak için lütfen kullanıcı girişi yapınız" ;
				error = true;
			}
		}		
		
		if (error) {
			String json = new Gson().toJson(responseStr);
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			return;
		}
		
		chain.doFilter(req, res);

	}
	public void destroy( ){
	   /* Called before the Filter instance is removed 
	   from service by the web container*/
	}
	
	private boolean containsUrl(String url){
		for(int i=0 ; i< urlList.size(); i++){
			if(url.startsWith(urlList.get(i))){
				return true;
			}
		}
		return false;
	}
}
