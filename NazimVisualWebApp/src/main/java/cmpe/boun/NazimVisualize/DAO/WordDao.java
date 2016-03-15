package cmpe.boun.NazimVisualize.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

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

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	
	
}

