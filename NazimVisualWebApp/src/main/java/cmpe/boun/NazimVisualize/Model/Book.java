package cmpe.boun.NazimVisualize.Model;

import java.util.ArrayList;
import java.util.List;

public class Book {
	private int bookID;
	private String name;
	private String year;
	private String location;
	private int type;
	
	
	private List<Work> works;
	
	public Book(){
		works = new ArrayList<Work>();
	}

	public int getBookID() {
		return bookID;
	}

	public String getName() {
		return name;
	}

	public String getYear() {
		return year;
	}

	public String getLocation() {
		return location;
	}

	public int getType() {
		return type;
	}

	public List<Work> getWorks() {
		return works;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setWorks(List<Work> works) {
		this.works = works;
	}
}
