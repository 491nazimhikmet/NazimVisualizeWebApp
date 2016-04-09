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
import cmpe.boun.NazimVisualize.VisualOperations.WordFrequencyGraph;

@Controller
public class GorselGetirController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(GorselGetirController.class);

	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");

	WorkDao workdao = (WorkDao) context.getBean("WorkDao");
	WorkLineDao worklinedao = (WorkLineDao) context.getBean("WorkLineDao");

	@RequestMapping(value = "/randomLineGorselleriServlet", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void BaseServlet(@ModelAttribute("siirId") String siirId,HttpServletResponse response)throws Exception {

		System.out.println("randomLineGorselleriServlet");

		OnurLineGorseli embed = new OnurLineGorseli(1920,1080,Integer.parseInt(siirId), "", true, true);
		embed.init();

		while (!embed.finished) {

		}
		System.out.println("randomLineGorselleriServlet");

		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println(embed.savedFileName);


	}

	@RequestMapping(value = "/WordCloudServlet", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void WordCloudServlet(@ModelAttribute("siirId") String siirId,HttpServletResponse response)throws Exception {

		System.out.println("WordCloudServletGiriş");
		  
		  WordCramCloud embed = new WordCramCloud(1920,1080,Integer.parseInt(siirId));
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
	
	@RequestMapping(value = "/WordFrequencyGraphServlet",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void WordFrequencyGraphServlet(@ModelAttribute("searchText") String searchText,HttpServletResponse response)throws Exception {

		System.out.println("WordFrequencyGraphServlet");

		WordFrequencyGraph embed = new WordFrequencyGraph(1920, 1080, searchText);
		embed.init();

		while (!embed.finished) {

		}
		System.out.println("WordFrequencyGraphServlet");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String json = new Gson().toJson(embed.saveFileName);
		
		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println(json);


	}
	
	
}
