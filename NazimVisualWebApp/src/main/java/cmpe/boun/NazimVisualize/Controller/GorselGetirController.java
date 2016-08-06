package cmpe.boun.NazimVisualize.Controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import cmpe.boun.NazimVisualize.DAO.AffectiveDao;
import cmpe.boun.NazimVisualize.DAO.WordDao;
import cmpe.boun.NazimVisualize.DAO.WorkDao;
import cmpe.boun.NazimVisualize.DAO.WorkLineDao;
import cmpe.boun.NazimVisualize.Model.AffectiveResult;
import cmpe.boun.NazimVisualize.Model.WordWithParsedForm;
import cmpe.boun.NazimVisualize.VisualOperations.OnurLineGorseli;
import cmpe.boun.NazimVisualize.VisualOperations.WordCramCloud;
import cmpe.boun.NazimVisualize.VisualOperations.WordFrequencyGraph;

@Controller
public class GorselGetirController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(GorselGetirController.class);

	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	ApplicationContext context3 = new ClassPathXmlApplicationContext("Spring-Module.xml");
	
	WordDao wordDao4 = (WordDao) context3.getBean("WordDao");

	WorkDao workdao = (WorkDao) context.getBean("WorkDao");
	WorkLineDao worklinedao = (WorkLineDao) context.getBean("WorkLineDao");

	/*
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
	public void WordFrequencyGraphServlet(@ModelAttribute("searchText") String searchText,
											@ModelAttribute("drawType") String drawType, //1: zaman, 2:mekan, 3:kitap
											HttpServletResponse response)throws Exception {

		System.out.println("WordFrequencyGraphServlet");
		
		//int type = Integer.parseInt(drawType);
		
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

	}*/
	
	
	@RequestMapping(value = "/getWordsOfWorkWithParsedForm", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getWordsOfWorkWithParsedForm(@ModelAttribute("siirId") String siirId, HttpServletResponse response)
			throws Exception {

		int workID = Integer.parseInt(siirId);
		
		System.out.println("getWordsOfWorkWithParsedForm "+ siirId);


		
		List<WordWithParsedForm> wordObj = new ArrayList<WordWithParsedForm>(wordDao4.getWordsWithParsedForm(workID));
		
		String json = new Gson().toJson(wordObj);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}

	
	@RequestMapping(value = "/getAffectiveResultsOfWork", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getAffectiveResultsOfWork(@ModelAttribute("siirId") String siirId, HttpServletResponse response)
			throws Exception {

		int workID = Integer.parseInt(siirId);
		
		System.out.println("getAffectiveResultsOfWork "+ siirId);
		
		
		AffectiveDao affectiveDao = (AffectiveDao) context.getBean("AffectiveDao");
		
		ArrayList<AffectiveResult> workLines = new ArrayList<AffectiveResult>(affectiveDao.getResultsByWorkId(workID));
		
		String json = new Gson().toJson(workLines);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
	
}
