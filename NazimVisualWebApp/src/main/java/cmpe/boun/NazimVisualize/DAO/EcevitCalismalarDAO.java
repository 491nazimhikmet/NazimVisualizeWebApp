package cmpe.boun.NazimVisualize.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.jdbc.CallableStatement;

import cmpe.boun.NazimVisualize.Model.EcevitWork;

public class EcevitCalismalarDAO extends DBConnection{
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
	
	public List<EcevitWork> getWorksByWordName(String search) throws Exception{
		String query = "select * from ecevit_calismalar ec, "+
						" (Select wl.workId, count(a.cnt) as cnt from ecevit_sentence wl, "+
						  "   	(  "+
						    "     SELECT sentenceId, count(*) as cnt FROM `ecevit_word` "+ 
						      "   WHERE lower(disambiguated) in ("+search+")"+
						       "  group by sentenceId "+
						        " ) a "+
						    " where wl.sentenceId = a.sentenceId "+
						    " group by wl.workId) es   "+
						" where ec.workId = es.workId  "+
						" order by es.cnt desc" ;
		return Extractors.extractEcevitWork(this.getStmt().executeQuery(query));
	}
}
