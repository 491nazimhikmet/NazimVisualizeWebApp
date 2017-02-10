package cmpe.boun.NazimVisualize.Model;

import java.util.List;

public class WordWithParsedForm {

	protected int wordID;
	protected String text;
	protected double wordStart;
	protected double wordFinish;
	protected int workLineID;
	
	protected boolean isBold;
	protected boolean isItalic;
	protected String font;
	
	protected String disambiguated;
	
	protected String parsedForm;
	
	protected List<Word> words;

	
	public WordWithParsedForm(){
		//words = new ArrayList<Character>();
	}


	public int getWordID() {
		return wordID;
	}


	public void setWordID(int wordID) {
		this.wordID = wordID;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public double getWordStart() {
		return wordStart;
	}


	public void setWordStart(double wordStart) {
		this.wordStart = wordStart;
	}


	public double getWordFinish() {
		return wordFinish;
	}


	public void setWordFinish(double wordFinish) {
		this.wordFinish = wordFinish;
	}


	public int getWorkLineID() {
		return workLineID;
	}


	public void setWorkLineID(int workLineID) {
		this.workLineID = workLineID;
	}


	public boolean isBold() {
		return isBold;
	}


	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}


	public boolean isItalic() {
		return isItalic;
	}


	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}


	public String getFont() {
		return font;
	}


	public void setFont(String font) {
		this.font = font;
	}


	public String getDisambiguated() {
		return disambiguated;
	}


	public void setDisambiguated(String disambiguated) {
		this.disambiguated = disambiguated;
	}


	public String getParsedForm() {
		return parsedForm;
	}


	public void setParsedForm(String parsedForm) {
		this.parsedForm = parsedForm;
	}


	public List<Word> getWords() {
		return words;
	}


	public void setWords(List<Word> words) {
		this.words = words;
	}
	
	
}
