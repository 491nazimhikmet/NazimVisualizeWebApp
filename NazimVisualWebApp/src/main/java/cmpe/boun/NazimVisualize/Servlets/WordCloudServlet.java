package cmpe.boun.NazimVisualize.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cmpe.boun.NazimVisualize.VisualOperations.OnurLineGorseli;
import cmpe.boun.NazimVisualize.VisualOperations.WordCramCloud;

public class WordCloudServlet extends HttpServlet{
	
	public void init() throws ServletException
	  {
	      // Do required initialization
	  }

	  public void doGet(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
		  System.out.println("WordCloudServletGiriş");
		  
		  WordCramCloud embed = new WordCramCloud(400,400,6161);
		  embed.init();
		  
		  while(!embed.finished){
			  
		  }
		  System.out.println("WordCloudServletCıkış");
	      // Set response content type
	      response.setContentType("text/html");

	      // Actual logic goes here.
	      PrintWriter out = response.getWriter();
	      out.println(embed.saveFileName);
	  }
	  
	  public void destroy()
	  {
	      // do nothing.
	  }
}
