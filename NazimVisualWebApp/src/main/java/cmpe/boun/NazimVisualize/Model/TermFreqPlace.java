package cmpe.boun.NazimVisualize.Model;

public class TermFreqPlace {
	String place;
	int frequency;
	
	public TermFreqPlace(){
		
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	public Integer getYear() {
		int sum = 0 ;
		for(int i= 0; i<place.length();i++){
			sum += (int)place.charAt(i);
		}
		
		if(place.length() == 0)
			return 0;
		
		return sum / place.length();
	}


	
}
