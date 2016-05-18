package cmpe.boun.NazimVisualize.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import cmpe.boun.NazimVisualize.Model.Work;
import cmpe.boun.NazimVisualize.Model.WorkLine;

public class WorkDao extends DBConnection{
	
	private Connection conn;
	
	public DataSource dataSource;
	
	private Statement stmt;
	
	public void setDataSource(DataSource dataSource) throws SQLException {
		this.dataSource = dataSource;
		conn = dataSource.getConnection();
		stmt = conn.createStatement();
	}
	
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
		String query = "Select w.workId, w.name, w.bookId, w.locationOfComp, w.pageNum,w.title, "+
						"IF(w.year is null, b.Year,w.year) as year "+
						"from `work` w inner join book b on w.bookId = b.bookId "+
							"where workId in( "+
							"Select distinct(workId) from workLine where lineId in ( "+
								"SELECT distinct(`workLineId`) FROM `word` "
								+ "WHERE lower(disambiguated)like lower('"+search+"')))";
		return Extractors.extractWork(this.getStmt().executeQuery(query));
	}
	
	public List<Work> getAllWorks() throws SQLException{
		String query = "SELECT * FROM Work";
		return Extractors.extractWork(this.getStmt().executeQuery(query));
	}
	
	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
}

