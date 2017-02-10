package cmpe.boun.NazimVisualize.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import cmpe.boun.NazimVisualize.Model.AffectiveResult;

public class AffectiveDao extends DBConnection{
	private Connection conn;
	
	public DataSource dataSource;
	
	private Statement stmt;
	
	public void setDataSource(DataSource dataSource) throws SQLException {
		this.dataSource = dataSource;
		conn = dataSource.getConnection();
		stmt = conn.createStatement();
	}
	
	public List<AffectiveResult> getResultsByWorkId(int workID) throws SQLException{
		String query="SELECT * FROM `affectiveresults` WHERE `type` = 1 and textId in(select lineId from workLine where workId ="+Integer.toString(workID)+" )order by textId";

		List<AffectiveResult> returnlist = Extractors.extractAffectiveResults(this.getStmt().executeQuery(query));
		
		return returnlist; 
	}
	
	public List<AffectiveResult> getEcevitResultsByWorkId(int workID) throws SQLException{
		String query="SELECT * FROM `ecevit_affectiveresults` WHERE `type` = 1 and textId in(select sentenceId from ecevit_sentence where workId ="+Integer.toString(workID)+" )order by textId";

		List<AffectiveResult> returnlist = Extractors.extractAffectiveResults(this.getStmt().executeQuery(query));
		
		return returnlist; 
	}
	
	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
}
