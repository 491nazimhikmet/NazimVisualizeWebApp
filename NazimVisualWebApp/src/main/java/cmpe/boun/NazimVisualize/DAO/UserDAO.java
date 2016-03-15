package cmpe.boun.NazimVisualize.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cmpe.boun.NazimVisualize.Base.BaseException;
import cmpe.boun.NazimVisualize.Base.BaseOperationResponse;
import cmpe.boun.NazimVisualize.Model.User;

public class UserDAO extends User{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public User getUserByUserName(String userName) throws Exception{
		String sql = "SELECT * FROM `user` WHERE `userName` = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			User user = Extractors.extractUser(rs).get(0);
			
			ps.close();
			rs.close();
						
			return user;
			
		} catch (Exception e) {
					
			throw new BaseException("Kullanici Şifresi getirirken hata oluştu ",e);
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new BaseException("Kullanici Şifresi getirme kapatılırken hata oluştu");
				}
			}
		}
	}
	
	public BaseOperationResponse insert(User user) throws Exception{
		boolean success = true;
		String addErrormessage = "";
		
		try{
			if(user.getName().isEmpty()){
				   success = false;
				   addErrormessage = "Adı boş olmamalıdır";
			   }else if(user.getSurname().isEmpty()){
				   success = false;
				   addErrormessage = "Soyadı boş olmamalıdır";
			   }else if(user.getUserName().isEmpty()){
				   success = false;
				   addErrormessage = "Kullanıcı Adı boş olmamalıdır";
			   }else if(user.getPassword().isEmpty()){
				   success = false;
				   addErrormessage = "Parola boş olmamalıdır";
			   }else if(user.getEmail().isEmpty()){
				   success = false;
				   addErrormessage = "Email boş olmamalıdır";
			   }else if(user.getPassword().length() < 6 || user.getPassword().length() > 15 ){
				   success = false;
				   addErrormessage = "Parola en az 6 en fazla 15 karakter olmamalıdır";
			   }else if(userNameExists(user.getUserName())){
				   success = false;
				   addErrormessage = "Kullanıcı adı daha önce kullanılmıştır.";
			   }else if(userMailExists(user.getEmail())){
				   success = false;
				   addErrormessage = "Kullanıcı maili daha önce kullanılmıştır.";
			   }
			   else{
				   user.setPassword(hashPassword(user.getPassword()));
			   }
			
				System.out.println("TyPe Issss :  "+user.getIsActivated());
				
			   if(user.getIsActivated()){
				   //mail at
			   }
			   
			   user.setIsActivated(false);
			   
			   String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	           java.util.regex.Matcher m = p.matcher(user.getEmail());
	           if(!m.matches()){
	        	   success = false;
				   addErrormessage = "Geçerli bir email adresi giriniz";
	           }
		}catch(Exception e){
			throw new BaseException("Kontroller yapılırken hata oluştu");
		}
		
		String sql = "INSERT INTO `user`(`name`, `surname`, `type`, `password`,"
				+ " `userName`, `email`, `isActivated`, `createdAt`, `updatedAt`) VALUES"
				+ "(?,?,?,?,?,?,?,?,?) ";
		Connection conn = null;
		
		if(success == true){
			try {
				Date current = new Date();
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, user.getName());
				ps.setString(2, user.getSurname());
				ps.setInt(3, 1);
				ps.setString(4, user.getPassword());
				ps.setString(5, user.getUserName());
				ps.setString(6, user.getEmail());
				ps.setBoolean(7, false);
				ps.setDate(8, new java.sql.Date(current.getTime()));
				ps.setDate(9, new java.sql.Date(current.getTime()));
				ps.executeUpdate();
				ps.close();
				
			} catch (Exception e) {
				success = false;
				addErrormessage = "Kaydetme işlemi sırasında bir hata oluştu";
				
				throw new BaseException("Kaydetme işlemi sırasında bir hata oluştu ",e);
				
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						throw new BaseException("Kaydetme işlemi sırasında bir hata oluştu");
					}
				}
			}
		}
		
		BaseOperationResponse response = new BaseOperationResponse();
		response.setSuccess(success);
		response.setMessage(addErrormessage);
		return response;
		
	}
	
	public BaseOperationResponse getPasswordByUserName(String userName) throws Exception{
		String sql = "SELECT * FROM `user` WHERE `userName` = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			String returnString = "";
			
			BaseOperationResponse response = new BaseOperationResponse();
			
			if(rs.next()){
				returnString = rs.getString("password");
				response.setSuccess(true);
			}
			
			ps.close();
			rs.close();
			
			response.setMessage(returnString);
			
			return response;
			
		} catch (Exception e) {
					
			throw new BaseException("Kullanici Şifresi getirirken hata oluştu ",e);
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new BaseException("Kullanici Şifresi getirme kapatılırken hata oluştu");
				}
			}
		}
	}

	public boolean userNameExists(String userName) throws Exception{
		String sql = "SELECT * FROM `user` WHERE `userName` = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
						
			if(rs.next()){
				return true;
			}
			
			ps.close();
			rs.close();
						
			return false;
			
		} catch (Exception e) {
					
			throw new BaseException("Kullanici Şifresi getirirken hata oluştu ",e);
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new BaseException("Kullanici Şifresi getirme kapatılırken hata oluştu");
				}
			}
		}
	}
	
	public boolean userMailExists(String userName) throws Exception{
		String sql = "SELECT * FROM `user` WHERE `email` = ?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
						
			if(rs.next()){
				return true;
			}
			
			ps.close();
			rs.close();
						
			return false;
			
		} catch (Exception e) {
					
			throw new BaseException("Kullanici Şifresi getirirken hata oluştu ",e);
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new BaseException("Kullanici Şifresi getirme kapatılırken hata oluştu");
				}
			}
		}
	}
	
	public static String hashPassword( String password)
	throws Exception{
		 
	       try {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(password);
				return hashedPassword;
	       } catch( Exception e ) {
	           throw new BaseException( "Password Hashlenirken problem oluştu",e);
	       }
	   }
	
}
