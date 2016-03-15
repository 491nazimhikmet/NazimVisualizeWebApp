package cmpe.boun.NazimVisualize.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cmpe.boun.NazimVisualize.VisualOperations.OnurLineGorseli;

public class BaseServlet extends HttpServlet{
	
	private String message;

	  public void init() throws ServletException
	  {
	      // Do required initialization
	      message = "Hello World";
	  }

	  public void doGet(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
		  System.out.println("baseServletGiris");
		  
		  OnurLineGorseli embed = new OnurLineGorseli(400,400,6161,"güneşi içenlerin türküsü",true,true);
		  embed.init();
		  
		  
		  while(!embed.finished){
			  
		  }
		  System.out.println("linegorseliServletCıkış");
		  
	      // Set response content type
	      response.setContentType("text/html");

	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	      out.println(embed.savedFileName);
	  }
	  
	  public void destroy()
	  {
	      // do nothing.
	  }
	
}
