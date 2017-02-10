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
				"						IF(w.year is null, b.Year,w.year) as year,ws.cnt"+
				"						from `work` w inner join book b on w.bookId = b.bookId ,"+
				"							 ( "+
				"							Select wl.workId, count(a.cnt) as cnt from workLine wl,"+
				"                                ( "+
				"								SELECT workLineId, count(*) as cnt FROM `word` "+
				"								 WHERE lower(disambiguated) in ("+search+")"+
				"                                 group by workLineId"+
				"                              	) a"+
				"                                where wl.lineId = a.workLineId"+
				"                                group by wl.workId"+
				"                             ) ws"+
				"                             where w.workId = ws.workId"+
				" and w.workId not in (6503,6504,6502,6505,6229,6506,6420,6877,6411,6735) "+
				"                             order by ws.cnt desc" ;
		return Extractors.extractWork(this.getStmt().executeQuery(query));
	}
	
	public List<Work> getAllWorks() throws SQLException{
		String query = "SELECT * FROM Work order by workId";
		return Extractors.extractWork(this.getStmt().executeQuery(query));
	}
	
	public List<Integer> getAllYears() throws SQLException{
		String query = "select distinct(a.year) from"
					+ " (SELECT distinct(year) FROM `work` "
					+ " UNION"
					+ " SELECT distinct(year) FROM `book`) a "
					+ " WHERE a.year > 0"
					+ " order by a.year";
		return Extractors.extractYears(this.getStmt().executeQuery(query));
	}
	
	public List<String> getAllPlaces() throws SQLException{
		String query = "select distinct(a.location) from "
				+ " (SELECT distinct(locationOfComp) as location FROM `work` "
				+ " UNION "
				+ " SELECT distinct(location) as location FROM `book`) a "
				+ " WHERE location != '' "
				+ " order by a.location";
		return Extractors.extractLocations(this.getStmt().executeQuery(query));
	}

	
	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
}

