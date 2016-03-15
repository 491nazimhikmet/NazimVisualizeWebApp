package cmpe.boun.NazimVisualize.Controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cmpe.boun.NazimVisualize.Base.BaseException;
import cmpe.boun.NazimVisualize.Base.BaseOperationResponse;
import cmpe.boun.NazimVisualize.DAO.UserDAO;
import cmpe.boun.NazimVisualize.Model.User;

@Controller
public class BaseController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);
	

		@RequestMapping(value = { "/", "/giris" }, produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
		public String welcomePage(Model model) {
	       model.addAttribute("user", new User()); 
	       model.addAttribute("user2", new User()); 
	       return "redirect:/anasayfa";
		}
		
		@RequestMapping(value = { "/anasayfa" }, method = RequestMethod.GET)
		public String anasayfa(Model model) {
		    model.addAttribute("user", new User()); 
		    model.addAttribute("user2", new User()); 
	       return "welcomePage";
		}
	 
	 
		@RequestMapping(value = "/login", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
		public String loginPage(@ModelAttribute("user2")User user,Model model,HttpServletRequest request,HttpServletResponse response)
			   											throws Exception {
	      
		   String enteredPassword = user.getPassword();
		   ApplicationContext context = 
			    	new ClassPathXmlApplicationContext("Spring-Module.xml");
			   UserDAO userdao = (UserDAO) context.getBean("userDAO");
		   
		   BaseOperationResponse opResponse = userdao.getPasswordByUserName(user.getUserName());
		   String hashedPass = opResponse.getMessage();
		
		   if(!opResponse.isSuccess()){
			   model.addAttribute("isLogged", false);
			   return "welcomePage";
		   }
		   BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		   CharSequence passseq = enteredPassword;
		   
		   if(!passwordEncoder.matches(passseq, hashedPass)){
			   model.addAttribute("isLogged", false);
			   model.addAttribute("user", new User());
			   return "welcomePage";
		   }else{
			   HttpSession session = request.getSession();
			   User dbUser = userdao.getUserByUserName(user.getUserName());
			   session.setAttribute("user", dbUser);
			   session.setAttribute("userNameSurname", (dbUser.getName()+ " "+dbUser.getSurname()).toUpperCase());
			   session.setAttribute("usreId", dbUser.getUserId());
			   session.setAttribute("userType", dbUser.getType());
			   session.setMaxInactiveInterval(30*60);

			   //response.sendRedirect("anasayfa");
			   return "redirect:/";
		   }
		   
	       
	   }
	 
	   
	   @RequestMapping(value = "/addMee", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	   public String addMee(@ModelAttribute("user")User user,Model model) {
	       
		   boolean success = true;
		   String addErrormessage = "";
		   
		   BaseOperationResponse response = new BaseOperationResponse();
		   
		   if(success == true){
			   
			   try{
				   ApplicationContext context = 
				    	new ClassPathXmlApplicationContext("Spring-Module.xml");
				   UserDAO userdao = (UserDAO) context.getBean("userDAO");
				   response = userdao.insert(user);
			   }catch(Exception e){
				   response.setMessage("Kaydedilirken hata oluştu");
				   response.setSuccess(false);
				   new BaseException("Kaydedilirken hata oluştu",e);
			   }
			   
		   }
		   
		   model.addAttribute("isSuccess", response.isSuccess());
		   model.addAttribute("user2", new User());
		   
		   if(response.isSuccess()){
			   model.addAttribute("addErrormessage","Kullanıcı başarı ile kaydedilmiştir"); 
		   }else{
			   model.addAttribute("addErrormessage",response.getMessage());
		   }
		   
		   user.setPassword("");
	       
	       return "welcomePage";
	   }
	 
	   @RequestMapping(value = "/notAuthorized", method = RequestMethod.GET)
	   public String notAuthorized(Model model) {
	       model.addAttribute("title", "Access Denied!");
	 
	       model.addAttribute("message", "Bu işlemi yapmaya yetkiniz yok!");
	       
	       return "notAuthorized";
	   }
	   
	   @RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
		public String logout(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
	       
		   HttpSession session = request.getSession();
		   if(session != null){
			   session.invalidate();
		   }
		   
	       response.sendRedirect("giris");
	       return "welcomePage";
		}
	   
	   @RequestMapping(value = { "/adminOperations" }, method = RequestMethod.GET)
		public String adminOperations(Model model) throws IOException {
	       
	       return "adminOperations";
		}
	   

}

