package cmpe.boun.NazimVisualize.DAO;

import java.sql.SQLException;
import java.util.List;

import cmpe.boun.NazimVisualize.Model.WorkLine;

public class WorkLineDao extends DBConnection{

	public List<WorkLine> getWorkLineOfAWork(int workID) throws SQLException{
		String query = "SELECT * FROM WorkLine WHERE workId = "+Integer.toString(workID);
		return Extractors.extractWorkLine(this.getStmt().executeQuery(query));
	}
}
