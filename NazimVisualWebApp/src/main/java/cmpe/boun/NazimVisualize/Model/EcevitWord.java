package cmpe.boun.NazimVisualize.Model;

public class EcevitWord {
	private int wordID;
	private String word;
	private String valence;
	private String dominance;
	private String arousal;
	private int sentenceId;
	private String disambiguated;
	public int getWordID() {
		return wordID;
	}
	public void setWordID(int wordID) {
		this.wordID = wordID;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getValence() {
		return valence;
	}
	public void setValence(String valence) {
		this.valence = valence;
	}
	public String getDominance() {
		return dominance;
	}
	public void setDominance(String dominance) {
		this.dominance = dominance;
	}
	public String getArousal() {
		return arousal;
	}
	public void setArousal(String arousal) {
		this.arousal = arousal;
	}
	public int getSentenceId() {
		return sentenceId;
	}
	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
	}
	public String getDisambiguated() {
		return disambiguated;
	}
	public void setDisambiguated(String disambiguated) {
		this.disambiguated = disambiguated;
	}
	
	
}
