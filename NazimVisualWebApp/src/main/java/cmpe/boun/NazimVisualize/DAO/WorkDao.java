package cmpe.boun.NazimVisualize.DAO;

import java.sql.SQLException;
import java.util.List;

import cmpe.boun.NazimVisualize.Model.Work;
import cmpe.boun.NazimVisualize.Model.WorkLine;

public class WorkDao extends DBConnection{
	
	public Work getWorkByID(int workID) throws SQLException{
		
		String query = "SELECT * FROM WORK WHERE workId = "+Integer.toString(workID);
		
		Work work = Extractors.extractWork(this.getStmt().executeQuery(query)).get(0);
		
		WorkLineDao workLineDao = new WorkLineDao();
		List<WorkLine> workLines = workLineDao.getWorkLineOfAWork(workID);
		work.setWorkLines(workLines);
		
		return work;
	}
	
	public List<Work> getWorksOfABook(int bookID) throws SQLException{
		String query = "SELECT * FROM Work WHERE bookId = "+Integer.toString(bookID);
		return Extractors.extractWork(this.getStmt().executeQuery(query));
	}
	
	public List<Work> getWorksByName(String search) throws Exception{
		String query = "SELECT * FROM Work WHERE lower(Name) like lower('%"+search+"%')";
		return Extractors.extractWork(this.getStmt().executeQuery(query));
	}
	
	public List<Work> getWorksByWordName(String search) throws Exception{
		String query = "Select * from `work` where workId in( "+
							"Select distinct(workId) from workLine where lineId in ( "+
								"SELECT distinct(`workLineId`) FROM `word` WHERE lower(text) like lower('%"+search+"%')))";
		return Extractors.extractWork(this.getStmt().executeQuery(query));
	}
	
	public List<Work> getAllWorks() throws SQLException{
		String query = "SELECT * FROM Work";
		return Extractors.extractWork(this.getStmt().executeQuery(query));
	}
}

