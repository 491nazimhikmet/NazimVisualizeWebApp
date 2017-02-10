package cmpe.boun.NazimVisualize.Model;

public class EcevitSentence {
	private int sentenceId;
	private String text;
	private int workID;
	private String valence;
	private String dominance;
	private String arousal;
	private String tokenized_text;
	public int getSentenceId() {
		return sentenceId;
	}
	public void setSentenceId(int sentenceId) {
		this.sentenceId = sentenceId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getWorkID() {
		return workID;
	}
	public void setWorkID(int workID) {
		this.workID = workID;
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
	public String getTokenized_text() {
		return tokenized_text;
	}
	public void setTokenized_text(String tokenized_text) {
		this.tokenized_text = tokenized_text;
	}
}
