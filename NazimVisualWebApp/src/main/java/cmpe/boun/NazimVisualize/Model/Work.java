package cmpe.boun.NazimVisualize.Model;

import java.util.ArrayList;
import java.util.List;

public class Work {
	private int workID;
	private String name;
	private String year;
	private int bookID;
	private String locationOfComp;
	private int pageNum;
	private String title;
	
	private List<WorkLine> workLines;
	
	public Work(){
		workLines = new ArrayList<WorkLine>();
	}

	public int getWorkID() {
		return workID;
	}

	public void setWorkID(int workID) {
		this.workID = workID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String getLocationOfComp() {
		return locationOfComp;
	}

	public void setLocationOfComp(String locationOfComp) {
		this.locationOfComp = locationOfComp;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<WorkLine> getWorkLines() {
		return workLines;
	}

	public void setWorkLines(List<WorkLine> workLines) {
		this.workLines = workLines;
	}
	
	public String toString(){
		return this.name;
	}
}
