package cmpe.boun.NazimVisualize.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import cmpe.boun.NazimVisualize.Model.WorkLine;

public class WorkLineDao extends DBConnection{

private Connection conn;
	
	public DataSource dataSource;
	
	private Statement stmt;
	
	public void setDataSource(DataSource dataSource) throws SQLException {
		this.dataSource = dataSource;
		conn = dataSource.getConnection();
		stmt = conn.createStatement();
	}
	
	public List<WorkLine> getWorkLineOfAWork(int workID) throws SQLException{
		String query = "SELECT * FROM WorkLine WHERE workId = "+Integer.toString(workID);
		return Extractors.extractWorkLine(this.getStmt().executeQuery(query));
	}
	
	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
}
