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

import cmpe.boun.NazimVisualize.DAO.WordDao;
import cmpe.boun.NazimVisualize.DAO.WorkDao;
import cmpe.boun.NazimVisualize.Model.PlaceWordLocation;
import cmpe.boun.NazimVisualize.Model.WordInAxis;
import cmpe.boun.NazimVisualize.Model.WordYeardFreq;

@Controller
public class DetayliAramaController {

	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(SiirAramaController.class);

	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	ApplicationContext context2 = new ClassPathXmlApplicationContext("Spring-Module.xml");

	WorkDao workdao2 = (WorkDao) context.getBean("WorkDao");

	WorkDao workdao = (WorkDao) context.getBean("WorkDao");
	WordDao wordDao = (WordDao) context.getBean("WordDao");
	
	@RequestMapping(value = "/getAllYears", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getAllYears(@ModelAttribute("searchText") String searchText,HttpServletResponse response)
			throws Exception {

		//String searchText = data;
		System.out.println("getAllYears a girdi");
		
		ApplicationContext context2 = new ClassPathXmlApplicationContext("Spring-Module.xml");
		WorkDao workdao3 = (WorkDao) context.getBean("WorkDao");
		
		List<Integer> years = workdao.getAllYears();

		String json = new Gson().toJson(years);
		
		workdao3.closeConnection();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
	@RequestMapping(value = "/getAllPlaces", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getAllPlaces(@ModelAttribute("searchText") String searchText,HttpServletResponse response)
			throws Exception {

		
		
		//String searchText = data;
		System.out.println("getAllPlaces a girdi");
		
		List<String> places = workdao2.getAllPlaces();

		String json = new Gson().toJson(places);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
	@RequestMapping(value = "/detayliAra", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void detayliAra(@ModelAttribute("type") String type,
			@ModelAttribute("words") String words,
			@ModelAttribute("seciliBasDonemIlk") String seciliBasDonemIlk,
			@ModelAttribute("seciliBasDonemSon") String seciliBasDonemSon,
			@ModelAttribute("seciliBitDonemIlk") String seciliBitDonemIlk,
			@ModelAttribute("seciliBitDonemSon") String seciliBitDonemSon,
			@ModelAttribute("firstPlace") String firstPlace,
			@ModelAttribute("secondPlace") String secondPlace,HttpServletResponse response)
			throws Exception {
	
		System.out.println("detayliAra a girdi");
		
		String[] wordArr = words.split(",");
		
		int proc = Integer.parseInt(type);
		
		List<WordInAxis> points = new ArrayList<WordInAxis>();
		
		
		String inStmt = "";
		for(int i=0; i< wordArr.length; i++){
			wordArr[i] = wordArr[i].trim();
			if(i != wordArr.length-1 ){
				inStmt += "lower('"+wordArr[i].trim()+"'),";
			}else{
				inStmt += "lower('"+wordArr[i].trim()+"')";
			}
			
		}
		
		if(proc == 1){//mekana göre karşılaştırma geldi ise
			
			ArrayList<PlaceWordLocation> placeFreq = new ArrayList<PlaceWordLocation>(wordDao.getTermsWithPlaceUsage(inStmt));
		
			for(int i=0; i< wordArr.length; i++){
				WordInAxis curPoint = new WordInAxis();
				curPoint.setWord(wordArr[i].trim());
				
				for(int j=0; j<placeFreq.size(); j++){
					PlaceWordLocation cur = placeFreq.get(j);
					if(cur.getWord().toLowerCase().equals(wordArr[i].toLowerCase())
							&& cur.getPlace().toLowerCase().equals(firstPlace.toLowerCase())){
						curPoint.setX(cur.getFrequency());
					}
					if(cur.getWord().toLowerCase().equals(wordArr[i].toLowerCase())
							&& cur.getPlace().toLowerCase().equals("")){
						curPoint.setNf(cur.getFrequency());
					}
					if(cur.getWord().toLowerCase().equals(wordArr[i].toLowerCase())
							&& cur.getPlace().toLowerCase().equals(secondPlace.toLowerCase())){
						curPoint.setY(cur.getFrequency());
					}
				}
				
				points.add(curPoint);
			}
			
		}else if(proc == 2){//dönem aralıklarına göre karşılaştırma geldi ise
			int basDonemIlk = Integer.parseInt(seciliBasDonemIlk);
			int basDonemSon = Integer.parseInt(seciliBasDonemSon);
			int bitDonemIlk = Integer.parseInt(seciliBitDonemIlk);
			int bitDonemSon = Integer.parseInt(seciliBitDonemSon);
			
			
			ArrayList<WordYeardFreq> yearFreq = new ArrayList<WordYeardFreq>(wordDao.getTermsWithYearUsage(inStmt));
			
			for(int i=0; i< wordArr.length; i++){
				WordInAxis curPoint = new WordInAxis();
				curPoint.setWord(wordArr[i].trim());
				
				
				int basAralikSum = 0;
				int sonAralikSum = 0;
				int wrodNf = 0;
				for(int j=0; j<yearFreq.size(); j++){
					WordYeardFreq cur = yearFreq.get(j);
					if(cur.getWord().toLowerCase().equals(wordArr[i].toLowerCase()) && cur.getYear() > 0){
						if(cur.getYear() >= basDonemIlk && cur.getYear() <= basDonemSon){
							basAralikSum += cur.getFrequency();
						}
						
						if(cur.getYear() >= bitDonemIlk && cur.getYear() <= bitDonemSon){
							sonAralikSum += cur.getFrequency();
						}

					}
					
					if(cur.getWord().toLowerCase().equals(wordArr[i].toLowerCase()) && cur.getYear() == 0){
						wrodNf += cur.getFrequency();
					}

				}
				
				curPoint.setX(basAralikSum);
				curPoint.setY(sonAralikSum);
				curPoint.setNf(wrodNf);
				
				points.add(curPoint);
			}
			
		}
		
		
		String json = new Gson().toJson(points);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
}
