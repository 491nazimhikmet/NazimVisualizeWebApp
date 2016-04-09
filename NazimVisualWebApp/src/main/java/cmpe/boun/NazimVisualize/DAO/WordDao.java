package cmpe.boun.NazimVisualize.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import cmpe.boun.NazimVisualize.Model.TermFreqYear;
import cmpe.boun.NazimVisualize.Model.Word;


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
			            "            WHERE text like '%"+term+"%' "+
			             "           group by workLineId "+
			              "     ) k "+
			               " where a.lineId = k.workLineId "+
			                " group by workId "+
			            ") t "+
			        "where w.bookId = b.bookId "+
			        "and t.workId = w.workId "+
			    ") k "+
			"group by year";
		return Extractors.extractTermFreqYear(this.getStmt().executeQuery(query));
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	
	
}

