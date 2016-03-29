package cmpe.boun.NazimVisualize.Controller;

import java.io.IOException;
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

@Controller
public class SiirAramaController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(SiirAramaController.class);

	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	
	WorkDao workdao = (WorkDao) context.getBean("WorkDao");
	WorkLineDao worklinedao = (WorkLineDao) context.getBean("WorkLineDao");

	@RequestMapping(value = "/searchSiir", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void searchSiir(@ModelAttribute("searchText") String searchText, HttpServletResponse response)
			throws Exception {

		System.out.println("girdi şlölülğ  : "+searchText);
		
		List<Work> workSonuclar = workdao.getWorksByWordName(searchText);

		String json = new Gson().toJson(workSonuclar);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}
	
	@RequestMapping(value = "/getSiir", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
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
}
