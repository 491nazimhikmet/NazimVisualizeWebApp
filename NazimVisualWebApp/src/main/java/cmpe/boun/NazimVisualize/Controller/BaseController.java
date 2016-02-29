package cmpe.boun.NazimVisualize.Controller;

import java.security.Principal;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cmpe.boun.NazimVisualize.Base.BaseException;
import cmpe.boun.NazimVisualize.Base.BaseOperationResponse;
import cmpe.boun.NazimVisualize.DAO.UserDAO;
import cmpe.boun.NazimVisualize.Model.User;

@Controller
public class BaseController {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);
	

	@RequestMapping(value = { "/", "/welcome" }, produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
	   public String welcomePage(Model model) {
	       model.addAttribute("title", "Welcome");
	       model.addAttribute("message", "This is welcome page!");
	       model.addAttribute("user", new User()); 
	       return "index";
	   }
	 
	   @RequestMapping(value = "/admin", method = RequestMethod.GET)
	   public String adminPage(Model model) {
	       model.addAttribute("title", "Admin");
	       model.addAttribute("message", "Admin Page - This is protected page!");
	       return "adminPage";
	   }
	 
	   @RequestMapping(value = "/login", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	   public String loginPage(@ModelAttribute("user")User user,Model model) throws Exception {
	      
		   String enteredPassword = user.getPassword();
		   ApplicationContext context = 
			    	new ClassPathXmlApplicationContext("Spring-Module.xml");
			   UserDAO userdao = (UserDAO) context.getBean("userDAO");
		   
		   BaseOperationResponse response = userdao.getPasswordByUserName(user.getUserName());
		   String hashedPass = response.getMessage();
		
		   if(!response.isSuccess()){
			   model.addAttribute("isLogged", false);
			   return "index";
		   }
		   BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		   CharSequence passseq = enteredPassword;
		   
		   if(!passwordEncoder.matches(passseq, hashedPass)){
			   model.addAttribute("isLogged", false);
			   return "index";
		   }else{
			   model.addAttribute("message", "girdi lan");
			   model.addAttribute("title", "oldu lan");
			   return "welcomePage";
		   }
		   
	       
	   }
	 
	   @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	   public String logoutSuccessfulPage(Model model) {
	       model.addAttribute("title", "Logout");
	       return "logoutSuccessfulPage";
	   }
	 
	   @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	   public String loginPage(Model model, Principal principal) {
	       model.addAttribute("title", "User Info");
	 
	       // Sau khi user login thanh cong se co principal
	       String userName = principal.getName();
	 
	       model.addAttribute("message",
	               "User Info - This is protected page!. Hello " + userName);
	 
	       return "userInfoPage";
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
		   
		   
		   if(response.isSuccess()){
			   model.addAttribute("addErrormessage","Kullanıcı başarı ile kaydedilmiştir");
			   user = new User();
		   }else{
			   model.addAttribute("addErrormessage",response.getMessage());
		   }
	       
	       logger.debug("girdiiii");
	       
	       return "index";
	   }
	 
	   @RequestMapping(value = "/403", method = RequestMethod.GET)
	   public String accessDenied(Model model, Principal principal) {
	       model.addAttribute("title", "Access Denied!");
	 
	       if (principal != null) {
	           model.addAttribute("message", "Hi " + principal.getName()
	                   + "<br> You do not have permission to access this page!");
	       } else {
	           model.addAttribute("msg",
	                   "You do not have permission to access this page!");
	       }
	       return "403Page";
	   }
	   

}

