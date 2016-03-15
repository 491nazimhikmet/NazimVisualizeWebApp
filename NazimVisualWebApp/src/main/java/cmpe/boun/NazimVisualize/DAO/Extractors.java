package cmpe.boun.NazimVisualize.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cmpe.boun.NazimVisualize.Model.Book;
import cmpe.boun.NazimVisualize.Model.User;
import cmpe.boun.NazimVisualize.Model.Word;
import cmpe.boun.NazimVisualize.Model.Work;
import cmpe.boun.NazimVisualize.Model.WorkLine;


public class Extractors {
	
	public Extractors(){}
	
	public static List<User> extractUser(ResultSet rs) throws SQLException{
		List<User> result = new ArrayList<User>();
		
		while(rs.next()){
			User user = new User();
			
			user.setUserId(rs.getInt("userId"));
			user.setEmail(rs.getString("email"));
			user.setIsActivated(rs.getBoolean("isActivated"));
			user.setName(rs.getString("name"));
			user.setSurname(rs.getString("surname"));
			user.setPassword(rs.getString("password"));
			user.setType(rs.getInt("type"));
			user.setUserName(rs.getString("userName"));
			
			result.add(user);
		}
		
		return result;
	}
	
	public static List<WorkLine> extractWorkLine(ResultSet rs) throws SQLException{
		List<WorkLine> result = new ArrayList<WorkLine>();
		
		while(rs.next()){
			WorkLine workLine = new WorkLine();
			workLine.setLineID(rs.getInt("lineId"));
			workLine.setLine(rs.getString("line"));
			workLine.setLineStart(rs.getDouble("lineStart"));
			workLine.setLineFinish(rs.getDouble("lineFinish"));
			workLine.setWorkID(rs.getInt("workId"));
			
			result.add(workLine);
		}
		
		return result;
	}
	
	public static List<Work> extractWork(ResultSet rs) throws SQLException{
		List<Work> result = new ArrayList<Work>();
		
		while(rs.next()){
			Work work = new Work();
			work.setBookID(rs.getInt("BookId"));
			work.setName(rs.getString("Name"));
			work.setLocationOfComp(rs.getString("LocationOfComp"));
			work.setYear(rs.getString("Year"));
			work.setWorkID(rs.getInt("WorkId"));
			work.setPageNum(rs.getInt("pageNum"));
			work.setTitle(rs.getString("Title"));
			
			result.add(work);
		}
		
		return result;
	}
	public static List<Word> extractWord(ResultSet rs) throws SQLException{
		List<Word> result = new ArrayList<Word>();
		
		while(rs.next()){
			Word word = new Word();
			word.setBold(rs.getBoolean("isBold"));
			word.setFont(rs.getString("font"));
			word.setItalic(rs.getBoolean("isItalic"));
			word.setText(rs.getString("text"));
			word.setWordFinish(rs.getDouble("wordFinish"));
			word.setWordID(rs.getInt("wordId"));
			word.setWordStart(rs.getDouble("wordStart"));
			word.setWorkLineID(rs.getInt("workLineId"));
				
			result.add(word);
		}
		
		return result;
	}
	
	public static List<Book> extractBook(ResultSet rs) throws SQLException{
		List<Book> result = new ArrayList<Book>();
		
		while(rs.next()){
			Book book = new Book();
			book.setBookID(rs.getInt("bookId"));
			book.setName(rs.getString("Name"));
			book.setLocation(rs.getString("Location"));
			book.setType(rs.getInt("type"));
			book.setYear(rs.getString("Year"));
			
			result.add(book);
		}
		
		return result;
	}
	
	
}
