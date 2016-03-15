package cmpe.boun.NazimVisualize.VisualOperations;

public class WordPoint {
	private int posX;
	private int posY;
	private int radius;
	
	public WordPoint(int x, int y, int r){
		setPosX(x);
		setPosY(y);
		setRadius(r);
	}
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	
}
