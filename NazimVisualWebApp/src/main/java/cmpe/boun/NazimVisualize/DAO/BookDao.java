package cmpe.boun.NazimVisualize.DAO;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import cmpe.boun.NazimVisualize.Model.Book;
import cmpe.boun.NazimVisualize.Model.TermFreqBook;



public class BookDao extends DBConnection{
	
	
private Connection conn;
	
	public DataSource dataSource;
	
	private Statement stmt;
	
	public void setDataSource(DataSource dataSource) throws SQLException {
		this.dataSource = dataSource;
		conn = dataSource.getConnection();
		stmt = conn.createStatement();
	}
	
	public Book getBookByID(int bookID) throws SQLException{
		String query = "SELECT * FROM BOOK WHERE bookId = "+Integer.toString(bookID);
		
		Book book = Extractors.extractBook(this.getStmt().executeQuery(query)).get(0);
		
		return book;
	}
	
	public List<Book> getBooks() throws SQLException{
		String query = "SELECT * FROM BOOK ";
		
		List<Book> books = Extractors.extractBook(getStmt().executeQuery(query));
		return books;
	}
	
	public List<TermFreqBook> getBookCounterOfWord(String search) throws SQLException{
		
		String query = "select b.bookId,b.name,o.cnt as frequency "
				+ "from book b, "
				+ "(select w.bookId, sum(t.cnt) as cnt "
					+ "from work w,(select workId,sum(k.wrdCnt) as cnt "
						+ "from workLine a ,"
							+ "(SELECT workLineId,count(*) as wrdCnt "
							+ "FROM `word` WHERE lower(disambiguated) like lower('"+search+"') "
							+ " group by workLineId) k "
							+ "where a.lineId = k.workLineId group by workId) t "
							+ "where t.workId = w.workId group by w.bookId) o where b.bookId = o.bookId";
		
		List<TermFreqBook> returnlist = Extractors.extractTermFreqBook(this.getStmt().executeQuery(query));
		
		return returnlist; 
	}
	
	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
	
}
