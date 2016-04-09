package cmpe.boun.NazimVisualize.Model;

import java.util.List;

public class Word {
	private int wordID;
	private String text;
	private double wordStart;
	private double wordFinish;
	private int workLineID;
	
	private boolean isBold;
	private boolean isItalic;
	private String font;
	
	private String disambiguated;
	
	private List<Word> words;

	
	public Word(){
		//words = new ArrayList<Character>();
	}


	public int getWordID() {
		return wordID;
	}


	public String getText() {
		return text;
	}


	public double getWordStart() {
		return wordStart;
	}


	public double getWordFinish() {
		return wordFinish;
	}


	public int getWorkLineID() {
		return workLineID;
	}


	public boolean isBold() {
		return isBold;
	}


	public boolean isItalic() {
		return isItalic;
	}


	public String getFont() {
		return font;
	}


	public List<Word> getWords() {
		return words;
	}


	public void setWordID(int wordID) {
		this.wordID = wordID;
	}


	public void setText(String text) {
		this.text = text;
	}


	public void setWordStart(double wordStart) {
		this.wordStart = wordStart;
	}


	public void setWordFinish(double wordFinish) {
		this.wordFinish = wordFinish;
	}


	public void setWorkLineID(int workLineID) {
		this.workLineID = workLineID;
	}


	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}


	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}


	public void setFont(String font) {
		this.font = font;
	}


	public void setWords(List<Word> words) {
		this.words = words;
	}
	@Override
	public String toString(){
		return text;
	}


	public String getDisambiguated() {
		return disambiguated;
	}


	public void setDisambiguated(String disambiguated) {
		this.disambiguated = disambiguated;
	}
}
