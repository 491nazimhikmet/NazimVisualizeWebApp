package cmpe.boun.NazimVisualize.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import cmpe.boun.NazimVisualize.Model.ParsedWordsWork;
import cmpe.boun.NazimVisualize.Model.ParsedWordsWorkPlace;
import cmpe.boun.NazimVisualize.Model.PlaceWordLocation;
import cmpe.boun.NazimVisualize.Model.TermFreqPlace;
import cmpe.boun.NazimVisualize.Model.TermFreqYear;
import cmpe.boun.NazimVisualize.Model.Word;
import cmpe.boun.NazimVisualize.Model.WordWithParsedForm;
import cmpe.boun.NazimVisualize.Model.WordYeardFreq;


public class WordDao extends DBConnection{

	private Connection conn;
	
	public DataSource dataSource;
	
	private Statement stmt;
	
	public void setDataSource(DataSource dataSource) throws SQLException {
		this.dataSource = dataSource;
		conn = dataSource.getConnection();
		stmt = conn.createStatement();
	}
	
	public List<Word> getWordsOfAWorkline(int lineID) throws SQLException{
		String query = "SELECT * FROM Word WHERE workLineId = "+Integer.toString(lineID);
		return Extractors.extractWord(this.getStmt().executeQuery(query));
	}
	
	public List<Word> getWordsOfAWork(Integer workId) throws SQLException{
		String query = "SELECT * FROM Word WHERE workLineId in(select lineId from workLine where workId = "+Integer.toString(workId)+" )";
		return Extractors.extractWord(this.getStmt().executeQuery(query));
	}
	
	public List<WordWithParsedForm> getWordsWithParsedForm(int workID) throws SQLException{
		String query = "SELECT w.*,wp.parsedForm FROM word w,wordParseForms wp WHERE w.wordId = wp.wordId and wp.probOrder = 1 and workLineId IN (SELECT lineId from workline WHERE WorkId ="+Integer.toString(workID)+")";
		//dbde workID de tutalım
		List<WordWithParsedForm> returnlist = Extractors.extractWordsWithParsedForm(this.getStmt().executeQuery(query));
		 closeConnection();
		 return returnlist; 
	}
	
	public List<ParsedWordsWork> getWordsWithParsedFormAllWork() throws SQLException{
		String query = "SELECT w.*,wp.parsedForm,wl.workId,wl.name FROM word w,wordParseForms wp,(SELECT wl.lineId, wl.workId,w.name from workline wl,(select workId,name from work where year != '') w where wl.workId = w.workId ) wl WHERE w.wordId = wp.wordId and wp.probOrder = 1 and w.workLineId = wl.lineId" 
					+"	order by wl.workId";

		List<ParsedWordsWork> returnlist = Extractors.extractParsedWordsWork(this.getStmt().executeQuery(query));
		 closeConnection();
		 return returnlist; 
	}
	
	public List<ParsedWordsWorkPlace> getWordsWithParsedFormAllWorkForPlace() throws SQLException{
		String query =
				"SELECT w.*,wp.parsedForm,wl.workId,wl.name,wl.location	"+
				"  FROM word w,wordParseForms wp,	"+
				"      (SELECT wl.lineId, wl.workId,w.name ,w.location 	"+
				"    from workline wl, 	"+
				"    	(SELECT w.workId, w.name, 	"+
				"        case  	"+
				"           when w.locationOfComp != '' then w.locationOfComp 	"+
				"           else b.location 	"+
				"        end as location 	"+
				"        FROM work w, book b 	"+
				"        WHERE w.bookId = b.bookId 	"+
				"        and (w.locationOfComp != '' or b.location != '')) w 	"+ 
				"    where wl.workId = w.workId ) wl  	"+
				"  WHERE w.wordId = wp.wordId and wp.probOrder = 1 and w.workLineId = wl.lineId	"+ 
				"  order by wl.workId";

		System.out.println(query);
		List<ParsedWordsWorkPlace> returnlist = Extractors.extractParsedWordsWorkPlace(this.getStmt().executeQuery(query));
		 closeConnection();
		 return returnlist; 
	}
	
	/**
	 * burada eğer aranan kelimeyi içeren work ün tarihi bulunamadıysa book un tarihi getiriliyor
	 * @return
	 * @throws SQLException
	 */
	public List<TermFreqYear> getFreqOverYearsOfTerm(String term)throws SQLException{
		String query =
				"Select year,sum(k.cnt) as frequency "+
			    "from ( "+
			     "   select IF(w.year is null or w.year = '',b.year,w.year) as year,t.cnt "+
			      "  from work w,book b, "+
			       "     ( "+
			        "        select workId,sum(k.wrdCnt) as cnt "+ 
			         "       from workLine a , "+
			          "          ( "+
			           "             SELECT workLineId,count(*) as wrdCnt FROM `word` "+
			            "            WHERE lower(disambiguated) like lower('"+term+"') "+
			             "           group by workLineId "+
			              "     ) k "+
			               " where a.lineId = k.workLineId "+
			                " group by workId "+
			            ") t "+
			        "where w.bookId = b.bookId "+
			        "and t.workId = w.workId "+
			    ") k "+
			"group by year";
		//System.out.println(query);
		return Extractors.extractTermFreqYear(this.getStmt().executeQuery(query));
	}

	
	public List<TermFreqPlace> getFreqOverPlaceOfTerm(String term)throws SQLException{
		String query =
				"Select location,sum(k.cnt) as frequency "+
			    "from ( "+
			     "   select IF(w.LocationOfComp is null or w.LocationOfComp = '',b.location,w.LocationOfComp) as location,t.cnt "+
			      "  from work w,book b, "+
			       "     ( "+
			        "        select workId,sum(k.wrdCnt) as cnt "+ 
			         "       from workLine a , "+
			          "          ( "+
			           "             SELECT workLineId,count(*) as wrdCnt FROM `word` "+
			            "            WHERE lower(disambiguated) like lower('"+term+"') "+
			             "           group by workLineId "+
			              "     ) k "+
			               " where a.lineId = k.workLineId "+
			                " group by workId "+
			            ") t "+
			        "where w.bookId = b.bookId "+
			        "and t.workId = w.workId "+
			    ") k "+
			"group by location"+
			 " order by location";
		
		//System.out.println(query);
		return Extractors.extractTermFreqPlace(this.getStmt().executeQuery(query));
	}
	
	public List<PlaceWordLocation> getTermsWithPlaceUsage(String inStmt)throws SQLException{
		String query =
				" Select  k.disambiguated,location,sum(k.cnt) as frequency "+
				" from (    "+
					" select IF(w.LocationOfComp is null or w.LocationOfComp = '',b.location,w.LocationOfComp) as location,t.cnt, t.disambiguated "+
					" from work w,book b,( "+
					    " select workId,disambiguated,sum(k.wrdCnt) as cnt "+ 
					    " from workLine a , ( "+
					        " SELECT disambiguated,workLineId,count(*) as wrdCnt "+
					        " FROM `word` "+
					        " WHERE lower(disambiguated) in ("+inStmt+") "+
					        " group by disambiguated,workLineId ) k  "+
					    " where a.lineId = k.workLineId "+
				        " group by workId,disambiguated ) t "+ 
					" where w.bookId = b.bookId and t.workId = w.workId ) k "+ 
				" group by k.disambiguated,location "+
				" order by location";
		//System.out.println(query);
		return Extractors.extractWordWithFreqPlace(this.getStmt().executeQuery(query));
	}
	
	public List<WordYeardFreq> getTermsWithYearUsage(String inStmt)throws SQLException{
		String query =
					"Select disambiguated,year,sum(k.cnt) as frequency "+
				    " from ( "+
				      "  select t.disambiguated,IF(w.year is null or w.year = '',b.year,w.year) as year,t.cnt "+ 
				      "  from work w,book b,  "+
				       "     (  "+
				        "        select k.disambiguated,workId,sum(k.wrdCnt) as cnt "+  
				        "        from workLine a ,  "+
				         "           ( "+ 
				          "              SELECT disambiguated,workLineId,count(*) as wrdCnt FROM `word` "+ 
				           "             WHERE lower(disambiguated) in ("+inStmt+")  "+
				            "            group by disambiguated,workLineId  "+
				             "      ) k  "+
				              "  where a.lineId = k.workLineId "+ 
				              "   group by k.disambiguated,workId  "+
				           " ) t "+
				       " where w.bookId = b.bookId "+ 
				       " and t.workId = w.workId  "+
				    " ) k "+
				" group by disambiguated,year" ;
		//System.out.println(query);
		return Extractors.extractWordWithFreqYear(this.getStmt().executeQuery(query));
	}
	
	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	
	
}

