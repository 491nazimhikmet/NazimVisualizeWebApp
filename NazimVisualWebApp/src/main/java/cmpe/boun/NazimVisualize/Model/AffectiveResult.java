package cmpe.boun.NazimVisualize.Model;

public class AffectiveResult {
	private int Id;
	private int type;
	private String text;
	private String valence;
	private String arousal;
	private String dominance;
	
	public AffectiveResult(){}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValence() {
		return valence;
	}

	public void setValence(String velence) {
		this.valence = velence;
	}

	public String getArousal() {
		return arousal;
	}

	public void setArousal(String arousal) {
		this.arousal = arousal;
	}

	public String getDominance() {
		return dominance;
	}

	public void setDominance(String dominance) {
		this.dominance = dominance;
	}
	

}
