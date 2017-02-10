package cmpe.boun.NazimVisualize.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cmpe.boun.NazimVisualize.Model.AffectiveResult;
import cmpe.boun.NazimVisualize.Model.Book;
import cmpe.boun.NazimVisualize.Model.EcevitSentence;
import cmpe.boun.NazimVisualize.Model.EcevitWord;
import cmpe.boun.NazimVisualize.Model.EcevitWordWithParsed;
import cmpe.boun.NazimVisualize.Model.EcevitWork;
import cmpe.boun.NazimVisualize.Model.ParsedWordsWork;
import cmpe.boun.NazimVisualize.Model.ParsedWordsWorkPlace;
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

	public static List<ParsedWordsWork> extractParsedWordsWork(ResultSet rs) throws SQLException{
		List<ParsedWordsWork> result = new ArrayList<ParsedWordsWork>();
		
		while(rs.next()){
			ParsedWordsWork word = new ParsedWordsWork();
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
			word.setWorkId(rs.getInt("workId"));
			word.setWorkName(rs.getString("name"));
			result.add(word);
		}
		
		return result;
	}
	
	public static List<ParsedWordsWorkPlace> extractParsedWordsWorkPlace(ResultSet rs) throws SQLException{
		List<ParsedWordsWorkPlace> result = new ArrayList<ParsedWordsWorkPlace>();
		
		while(rs.next()){
			ParsedWordsWorkPlace word = new ParsedWordsWorkPlace();
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
			word.setWorkId(rs.getInt("workId"));
			word.setWorkName(rs.getString("name"));
			word.setLocation(rs.getString("location"));
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
	
	public static List<EcevitWork> extractEcevitWork(ResultSet rs) throws SQLException{
		List<EcevitWork> result = new ArrayList<EcevitWork>();
		
		while(rs.next()){
			EcevitWork ec = new EcevitWork();		
			ec.setWorkID(rs.getInt("workId"));
			ec.setPageId(rs.getInt("pageId"));
			ec.setHeader(rs.getString("header"));
			ec.setSource(rs.getString("source"));
			ec.setDate(rs.getString("date"));
			ec.setLocation(rs.getString("location"));
			ec.setFullText(rs.getString("fullText"));
			ec.setFiles(rs.getString("files"));
			ec.setCollection(rs.getString("collection"));
			ec.setTags(rs.getString("tags"));
			ec.setCitation(rs.getString("citation"));
			
			result.add(ec);
		}
		
		return result;
	}
	
	public static List<EcevitSentence> extractEcevitSentences(ResultSet rs) throws SQLException{
		List<EcevitSentence> result = new ArrayList<EcevitSentence>();
		
		while(rs.next()){
			EcevitSentence sentence = new EcevitSentence();
			sentence.setSentenceId(rs.getInt("sentenceId"));
			sentence.setText(rs.getString("text"));
			sentence.setArousal(rs.getString("arousal"));
			sentence.setDominance(rs.getString("dominance"));
			sentence.setTokenized_text(rs.getString("tokenized_text"));
			sentence.setValence(rs.getString("valence"));
			sentence.setWorkID(rs.getInt("workId"));
			
			result.add(sentence);
		}
		
		return result;
	}
	
	public static List<EcevitWord> extractEcevitWord(ResultSet rs) throws SQLException{
		List<EcevitWord> result = new ArrayList<EcevitWord>();
		
		while(rs.next()){
			EcevitWord word = new EcevitWord();
			word.setWordID(rs.getInt("wordId"));
			word.setWord(rs.getString("word"));
			word.setSentenceId(rs.getInt("sentenceId"));
			word.setArousal(rs.getString("arousal"));
			word.setDominance(rs.getString("dominance"));		
			word.setValence(rs.getString("valence"));
			word.setDisambiguated(rs.getString("disambiguated"));
			
			
			result.add(word);
		}
		
		return result;
	}
	
	public static List<EcevitWordWithParsed> extractEcevitWordsWithParsedForm(ResultSet rs) throws SQLException{
		List<EcevitWordWithParsed> result = new ArrayList<EcevitWordWithParsed>();
		
		while(rs.next()){
			EcevitWordWithParsed word = new EcevitWordWithParsed();
			word.setWordID(rs.getInt("wordId"));
			word.setWord(rs.getString("word"));
			word.setSentenceId(rs.getInt("sentenceId"));
			word.setArousal(rs.getString("arousal"));
			word.setDominance(rs.getString("dominance"));		
			word.setValence(rs.getString("valence"));
			word.setDisambiguated(rs.getString("disambiguated"));
			word.setParsedForm(rs.getString("parsedForm"));
			
			result.add(word);
		}
		
		return result;
	}
}
