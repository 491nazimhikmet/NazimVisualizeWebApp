package cmpe.boun.NazimVisualize.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import cmpe.boun.NazimVisualize.Model.EcevitWordWithParsed;
import cmpe.boun.NazimVisualize.Model.WordWithParsedForm;

public class EcevitWordDAO extends DBConnection{	
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
	
	public List<EcevitWordWithParsed> getWordsWithParsedForm(int workID) throws SQLException{
		String query = "SELECT w.*,wp.parsedForm FROM ecevit_word w,ecevit_parsedwords wp WHERE w.wordId = wp.wordId and wp.probOrder = 1 and sentenceId IN (SELECT sentenceId from ecevit_sentence WHERE WorkId ="+Integer.toString(workID)+")";

		List<EcevitWordWithParsed> returnlist = Extractors.extractEcevitWordsWithParsedForm(this.getStmt().executeQuery(query));
		 closeConnection();
		 return returnlist; 
	}
}
