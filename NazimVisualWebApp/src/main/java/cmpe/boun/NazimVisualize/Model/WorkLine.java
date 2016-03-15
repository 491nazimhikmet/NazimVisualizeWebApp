package cmpe.boun.NazimVisualize.Model;

import java.util.ArrayList;
import java.util.List;

public class WorkLine {
	private int lineID;
	private String line;
	private double lineStart;
	private double lineFinish;
	private int workID;
	
	private List<Word> words;

	
	public WorkLine(){
		words = new ArrayList<Word>();
	}

	public int getLineID() {
		return lineID;
	}

	public void setLineID(int lineID) {
		this.lineID = lineID;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public double getLineStart() {
		return lineStart;
	}

	public void setLineStart(double lineStart) {
		this.lineStart = lineStart;
	}

	public double getLineFinish() {
		return lineFinish;
	}

	public void setLineFinish(double lineFinish) {
		this.lineFinish = lineFinish;
	}

	public int getWorkID() {
		return workID;
	}

	public void setWorkID(int workID) {
		this.workID = workID;
	}
	
	@Override
	public String toString(){
		return line;
	}
	
	public List<Word> getWords() {
		return words;
	}

	
	public void setWords(List<Word> words) {
		this.words = words;
	}
	
}
