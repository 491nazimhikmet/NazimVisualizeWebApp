package cmpe.boun.NazimVisualize.Controller;

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
import cmpe.boun.NazimVisualize.DAO.EcevitCalismalarDAO;
import cmpe.boun.NazimVisualize.DAO.EcevitSentenceDAO;
import cmpe.boun.NazimVisualize.DAO.EcevitWordDAO;
import cmpe.boun.NazimVisualize.Model.AffectiveResult;
import cmpe.boun.NazimVisualize.Model.EcevitSentence;
import cmpe.boun.NazimVisualize.Model.EcevitWordWithParsed;
import cmpe.boun.NazimVisualize.Model.EcevitWork;
import cmpe.boun.NazimVisualize.Model.WordWithParsedForm;

@Controller
public class EcevitYaziAramaController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(EcevitYaziAramaController.class);

	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	
	EcevitCalismalarDAO ecevitCalismalarDao = (EcevitCalismalarDAO) context.getBean("EcevitCalismalarDao");
	
	EcevitSentenceDAO ecevitSentenceDAO = (EcevitSentenceDAO) context.getBean("EcevitSentenceDao");
	
	EcevitWordDAO ecevitWordDAO = (EcevitWordDAO) context.getBean("EcevitWordDao");

	@RequestMapping(value = "/searchYazi", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void searchYazi(@ModelAttribute("searchText") String searchText, HttpServletResponse response)
			throws Exception {

		//String searchText = data;
		System.out.println("aranan kelime  : "+searchText);
		
		String search = "";
		
		if(searchText.startsWith("{") && searchText.endsWith("}")){
			searchText = searchText.substring(1, searchText.length()-1);
			
			String[] parts = searchText.split(",");
			
			for(int i =0 ; i< parts.length; i++){
				if(i != parts.length-1){
					search += " lower('"+ parts[i] + "') , "; 
				}else{
					search += " lower('"+ parts[i] + "')";
				}
			}
		}else{
			search = " lower('"+ searchText + "')";
		}
		
		List<EcevitWork> workSonuclar = ecevitCalismalarDao.getWorksByWordName(search);

		String json = new Gson().toJson(workSonuclar);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
/*
	@RequestMapping(value = "/getWordFrequencyData", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getWordFrequencyData(@ModelAttribute("searchText") String searchText, HttpServletResponse response)
			throws Exception {

		System.out.println("getWordFrequencyData "+ searchText);
		
		ApplicationContext context2 = new ClassPathXmlApplicationContext("Spring-Module.xml");
		
		WordDao wordDao3 = (WordDao) context2.getBean("WordDao");
		
		ArrayList<TermFreqYear> yearFreq = new ArrayList<TermFreqYear>(wordDao3.getFreqOverYearsOfTerm(searchText));
		
		wordDao3.closeConnection();
		
		String json = new Gson().toJson(yearFreq);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}*/
	
	@RequestMapping(value = "/getSentecesOfEcevitWork", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getSentecesOfEcevitWork(@ModelAttribute("workId") String workId, HttpServletResponse response)
			throws Exception {

		int workID = Integer.parseInt(workId);
		
		System.out.println("getSentecesOfEcevitWork "+ workId);
				
		ArrayList<EcevitSentence> workLines = new ArrayList<EcevitSentence>(ecevitSentenceDAO.getSentecesOfEcevitWork(workID));
		
		String json = new Gson().toJson(workLines);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	@RequestMapping(value = "/getEcevitWordsOfWorkWithParsedForm", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getWordsOfWorkWithParsedForm(@ModelAttribute("workId") String workId, HttpServletResponse response)
			throws Exception {

		int workID = Integer.parseInt(workId);
		
		System.out.println("getWordsOfWorkWithParsedForm "+ workId);
		
		List<EcevitWordWithParsed> wordObj = new ArrayList<EcevitWordWithParsed>(ecevitWordDAO.getWordsWithParsedForm(workID));
		
		String json = new Gson().toJson(wordObj);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}

	@RequestMapping(value = "/getEcevitAffectiveResultsOfWork", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getEcevitAffectiveResultsOfWork(@ModelAttribute("workId") String workId, HttpServletResponse response)
			throws Exception {

		int workID = Integer.parseInt(workId);
		
		System.out.println("getAffectiveResultsOfWork "+ workId);
		
		AffectiveDao affectiveDao = (AffectiveDao) context.getBean("AffectiveDao");
		
		ArrayList<AffectiveResult> workLines = new ArrayList<AffectiveResult>(affectiveDao.getEcevitResultsByWorkId(workID));
		
		affectiveDao.closeConnection();
		
		String json = new Gson().toJson(workLines);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}

	
}
