package cmpe.boun.NazimVisualize.DAO;


import java.sql.SQLException;
import java.util.List;

import cmpe.boun.NazimVisualize.Model.Book;



public class BookDao extends DBConnection{
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
	
}
