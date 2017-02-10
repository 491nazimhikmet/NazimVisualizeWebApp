package cmpe.boun.NazimVisualize.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import cmpe.boun.NazimVisualize.Model.EcevitSentence;

public class EcevitSentenceDAO extends DBConnection{
	private Connection conn;
	
	public DataSource dataSource;
	
	private Statement stmt;
	
	public void setDataSource(DataSource dataSource) throws SQLException {
		this.dataSource = dataSource;
		conn = dataSource.getConnection();
		stmt = conn.createStatement();
	}	
	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public List<EcevitSentence> getSentecesOfEcevitWork(int workID) throws SQLException{
		String query = "SELECT * FROM ecevit_sentence WHERE workId = "+Integer.toString(workID);
		System.out.println(query);
		return Extractors.extractEcevitSentences(this.getStmt().executeQuery(query));
	}
}
