package cmpe.boun.NazimVisualize.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import cmpe.boun.NazimVisualize.DAO.WorkDao;
import cmpe.boun.NazimVisualize.DAO.WorkLineDao;
import cmpe.boun.NazimVisualize.Model.Work;
import cmpe.boun.NazimVisualize.Model.WorkLine;
import cmpe.boun.NazimVisualize.VisualOperations.OnurLineGorseli;
import cmpe.boun.NazimVisualize.VisualOperations.WordCramCloud;

@Controller
public class GorselGetirController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(GorselGetirController.class);

	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");

	WorkDao workdao = (WorkDao) context.getBean("WorkDao");
	WorkLineDao worklinedao = (WorkLineDao) context.getBean("WorkLineDao");

	@RequestMapping(value = "/BaseServlet", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public void BaseServlet(HttpServletResponse response)throws Exception {

		System.out.println("baseServletGiris");

		OnurLineGorseli embed = new OnurLineGorseli(400, 400, 6161, "güneşi içenlerin türküsü", true, true);
		embed.init();

		while (!embed.finished) {

		}
		System.out.println("linegorseliServletCıkış");

		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println(embed.savedFileName);


	}

	@RequestMapping(value = "/WordCloudServlet", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public void WordCloudServlet(HttpServletResponse response)throws Exception {

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
}
