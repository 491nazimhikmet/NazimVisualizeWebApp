package cmpe.boun.NazimVisualize.Controller;

import java.io.IOException;
import java.util.ArrayList;
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

import cmpe.boun.NazimVisualize.DAO.AffectiveDao;
import cmpe.boun.NazimVisualize.DAO.BookDao;
import cmpe.boun.NazimVisualize.DAO.WordDao;
import cmpe.boun.NazimVisualize.DAO.WorkDao;
import cmpe.boun.NazimVisualize.DAO.WorkLineDao;
import cmpe.boun.NazimVisualize.Model.AffectiveResult;
import cmpe.boun.NazimVisualize.Model.TermFreqBook;
import cmpe.boun.NazimVisualize.Model.TermFreqPlace;
import cmpe.boun.NazimVisualize.Model.TermFreqYear;
import cmpe.boun.NazimVisualize.Model.WordWithParsedForm;
import cmpe.boun.NazimVisualize.Model.Work;
import cmpe.boun.NazimVisualize.Model.WorkLine;

@Controller
public class SiirAramaController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(SiirAramaController.class);

	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	
	WorkDao workdao = (WorkDao) context.getBean("WorkDao");
	WorkLineDao worklinedao = (WorkLineDao) context.getBean("WorkLineDao");
	WordDao wordDao = (WordDao) context.getBean("WordDao");

	@RequestMapping(value = "/searchSiir", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void searchSiir(@ModelAttribute("searchText") String searchText, HttpServletResponse response)
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
		
		List<Work> workSonuclar = workdao.getWorksByWordName(search);

		String json = new Gson().toJson(workSonuclar);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
	@RequestMapping(value = "/getSiir", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getSiir(@ModelAttribute("siirId") String siirId, HttpServletResponse response)
			throws Exception {

		System.out.println(siirId);
		
		List<WorkLine> workLines = worklinedao.getWorkLineOfAWork(Integer.parseInt(siirId));

		String siir = "";
		for(WorkLine line : workLines){
			siir += line.getLine() + "<br>";
		}
		
		String json = new Gson().toJson(siir);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
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

	}
	
	
	@RequestMapping(value = "/getWordFrequencyOverPlaceData", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getWordFrequencyOverPlaceData(@ModelAttribute("searchText") String searchText, HttpServletResponse response)
			throws Exception {

		System.out.println("getWordFrequencyOverPlaceData "+ searchText);
		
		WordDao wordDao2 = (WordDao) context.getBean("WordDao");
		
		ArrayList<TermFreqPlace> placeFreq = new ArrayList<TermFreqPlace>(wordDao2.getFreqOverPlaceOfTerm(searchText));
		
		String json = new Gson().toJson(placeFreq);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
	@RequestMapping(value = "/getWordFrequencyOverBookData", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getWordFrequencyOverBookData(@ModelAttribute("searchText") String searchText, HttpServletResponse response)
			throws Exception {

		System.out.println("getWordFrequencyOverBookData "+ searchText);
		
		BookDao bookDao = (BookDao) context.getBean("BookDao");
		
		ArrayList<TermFreqBook> placeFreq = new ArrayList<TermFreqBook>(bookDao.getBookCounterOfWord(searchText));
		
		bookDao.closeConnection();
		
		String json = new Gson().toJson(placeFreq);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
	@RequestMapping(value = "/getWorkLinesOfWork", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getWorkLinesOfWork(@ModelAttribute("siirId") String siirId, HttpServletResponse response)
			throws Exception {

		int workID = Integer.parseInt(siirId);
		
		System.out.println("getWorkLinesOfWork "+ siirId);
		
		ApplicationContext context2 = new ClassPathXmlApplicationContext("Spring-Module.xml");
		WorkLineDao workLineDao2 = (WorkLineDao) context2.getBean("WorkLineDao");
		
		ArrayList<WorkLine> workLines = new ArrayList<WorkLine>(workLineDao2.getWorkLineOfAWork(workID));
		
		workLineDao2.closeConnection();
		String json = new Gson().toJson(workLines);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
	

	
}
