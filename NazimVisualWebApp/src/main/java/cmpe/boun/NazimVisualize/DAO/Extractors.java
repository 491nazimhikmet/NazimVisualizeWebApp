package cmpe.boun.NazimVisualize.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cmpe.boun.NazimVisualize.Model.AffectiveResult;
import cmpe.boun.NazimVisualize.Model.Book;
import cmpe.boun.NazimVisualize.Model.PlaceWordLocation;
import cmpe.boun.NazimVisualize.Model.TermFreqBook;
import cmpe.boun.NazimVisualize.Model.TermFreqPlace;
import cmpe.boun.NazimVisualize.Model.TermFreqYear;
import cmpe.boun.NazimVisualize.Model.User;
import cmpe.boun.NazimVisualize.Model.Word;
import cmpe.boun.NazimVisualize.Model.WordWithParsedForm;
import cmpe.boun.NazimVisualize.Model.WordYeardFreq;
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
			word.setDisambiguated(rs.getString("disambiguated"));
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
	
	public static List<TermFreqYear> extractTermFreqYear(ResultSet rs) throws SQLException{
		List<TermFreqYear> result = new ArrayList<TermFreqYear>();
		
		while(rs.next()){
			TermFreqYear yearFreq = new TermFreqYear();
			yearFreq.setYear(rs.getInt("year"));
			yearFreq.setFrequency(rs.getInt("frequency"));
			
			result.add(yearFreq);
		}
		
		
		return result;
	}
	
	public static List<TermFreqPlace> extractTermFreqPlace(ResultSet rs) throws SQLException{
		List<TermFreqPlace> result = new ArrayList<TermFreqPlace>();
		
		while(rs.next()){
			TermFreqPlace placeFreq = new TermFreqPlace();
			placeFreq.setPlace(rs.getString("location"));
			placeFreq.setFrequency(rs.getInt("frequency"));
			
			result.add(placeFreq);
		}
		
		return result;
	}
	
	public static List<PlaceWordLocation> extractWordWithFreqPlace(ResultSet rs) throws SQLException{
		List<PlaceWordLocation> result = new ArrayList<PlaceWordLocation>();
		
		while(rs.next()){
			PlaceWordLocation placeFreq = new PlaceWordLocation();
			placeFreq.setPlace(rs.getString("location"));
			placeFreq.setFrequency(rs.getInt("frequency"));
			placeFreq.setWord(rs.getString("disambiguated"));
			
			result.add(placeFreq);
		}
		
		return result;
	}
	
	public static List<WordYeardFreq> extractWordWithFreqYear(ResultSet rs) throws SQLException{
		List<WordYeardFreq> result = new ArrayList<WordYeardFreq>();
		
		while(rs.next()){
			WordYeardFreq placeFreq = new WordYeardFreq();
			placeFreq.setYear(rs.getInt("year"));
			placeFreq.setFrequency(rs.getInt("frequency"));
			placeFreq.setWord(rs.getString("disambiguated"));
			
			result.add(placeFreq);
		}
		
		return result;
	}
	
	public static List<TermFreqBook> extractTermFreqBook(ResultSet rs) throws SQLException{
		List<TermFreqBook> result = new ArrayList<TermFreqBook>();
		
		while(rs.next()){
			TermFreqBook bookFreq = new TermFreqBook();
			bookFreq.setBookId(rs.getInt("bookId"));
			bookFreq.setFrequency(rs.getInt("frequency"));
			bookFreq.setBookName(rs.getString("name"));

			result.add(bookFreq);
		}
		
		return result;
	}
	
	public static List<WordWithParsedForm> extractWordsWithParsedForm(ResultSet rs) throws SQLException{
		List<WordWithParsedForm> result = new ArrayList<WordWithParsedForm>();
		
		while(rs.next()){
			WordWithParsedForm word = new WordWithParsedForm();
			word.setBold(rs.getBoolean("isBold"));
			word.setFont(rs.getString("font"));
			word.setItalic(rs.getBoolean("isItalic"));
			word.setText(rs.getString("text"));
			word.setWordFinish(rs.getDouble("wordFinish"));
			word.setWordID(rs.getInt("wordId"));
			word.setWordStart(rs.getDouble("wordStart"));
			word.setWorkLineID(rs.getInt("workLineId"));
			word.setParsedForm(rs.getString("parsedForm"));
			word.setDisambiguated(rs.getString("disambiguated"));	
			
			result.add(word);
		}
		
		return result;
	}
	
	public static List<AffectiveResult> extractAffectiveResults(ResultSet rs) throws SQLException{
		
		List<AffectiveResult> result = new ArrayList<AffectiveResult>();
		
		while(rs.next()){
			AffectiveResult affRes = new AffectiveResult();
			
			affRes.setId(rs.getInt("textId"));
			affRes.setType(rs.getInt("type"));
			affRes.setText(rs.getString("text"));
			affRes.setArousal(rs.getString("arousal"));
			affRes.setDominance(rs.getString("dominance"));
			affRes.setValence(rs.getString("valence"));
			
			result.add(affRes);
		}
		
		return result;
	}
	
	public static List<Integer> extractYears(ResultSet rs) throws SQLException{
		
		List<Integer> result = new ArrayList<Integer>();
		
		while(rs.next()){			
			Integer cur = rs.getInt("year");
		
			result.add(cur);
		}
		
		return result;
	}
	
	public static List<String> extractLocations(ResultSet rs) throws SQLException{
		
		List<String> result = new ArrayList<String>();
		
		while(rs.next()){			
			String cur = rs.getString("location");
		
			result.add(cur);
		}
		
		return result;
	}
}
