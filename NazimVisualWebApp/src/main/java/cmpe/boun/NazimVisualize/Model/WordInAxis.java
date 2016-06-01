package cmpe.boun.NazimVisualize.Model;

public class WordInAxis {
	String word;
	int x;
	int y;
	int nf;
	
	public WordInAxis(){
		word = "";
		x = 0;
		y = 0;
		nf = 0;
	}

	public int getNf() {
		return nf;
	}

	public void setNf(int nf) {
		this.nf = nf;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
