package cmpe.boun.NazimVisualize.VisualOperations;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cmpe.boun.NazimVisualize.DAO.WordDao;
import cmpe.boun.NazimVisualize.Model.TermFreqPlace;
import cmpe.boun.NazimVisualize.Model.TermFreqYear;
import processing.core.PApplet;

public class WordFrequencyPlace extends PApplet {
	int width;
	int height;
	String term;
	
	public String saveFileName;

	public WordFrequencyPlace(int width, int height, String term) {
		this.width = width;
		this.height = height;
		this.term = term;
		saveFileName = Math.random() * 255 + "d.png";
	}

	public void setup() {
		size(width, height);
		noLoop();
	}
	
	private void finishUp() {
		save(saveFileName);
		this.destroy();
		noLoop();
	}

	public void draw() {
		ApplicationContext context = 
		    	new ClassPathXmlApplicationContext("Spring-Module.xml");
		WordDao wordDao = (WordDao) context.getBean("WordDao");
		int margin = 150;
		float rectX = 10;
		background(55,112,130);
		noStroke();

		ArrayList<TermFreqPlace> placeFreq;
		try {
			placeFreq = new ArrayList<TermFreqPlace>(wordDao.getFreqOverPlaceOfTerm(term));

			int[] maxFreqMinMaxYear = getMaxFreqMinMaxYear(placeFreq);

			float textSize = map(width, 0, 3200, 10, 30);
			margin = (int)map(height, 0, 2000, 100, 400);
			rectX = map(width, 0, 3200, 0, 10);
			for (int i = 0; i < placeFreq.size(); i++) {
				TermFreqPlace currentPlace = placeFreq.get(i);
				if (currentPlace.getPlace().equals("")) {
					textSize(textSize);
					textAlign(LEFT, TOP);
					textLeading(textSize); 
					text("Aramanın yeri bulunamayan tekrar sayısı : " + currentPlace.getFrequency(),0,0);

					continue;
				}
				
				float xPosDataOne = map(currentPlace.getYear(), maxFreqMinMaxYear[1]-1, maxFreqMinMaxYear[2], 0 + margin, width - margin);
				float yPosDataOne = map(currentPlace.getFrequency(), 0, maxFreqMinMaxYear[0], 0, height - (int)(1.3 * margin));
									
				String year = currentPlace.getPlace();
				
				fill(104, 58, 58);
				rectMode(CORNER);
				rect(xPosDataOne, height - margin, rectX, -1 * yPosDataOne);
				
		
				textAlign(LEFT, BOTTOM);
				textSize(textSize);
				text(currentPlace.getFrequency(),xPosDataOne,height-margin-yPosDataOne);
				
				fill(159, 179, 186);
				pushMatrix() ;
				translate(xPosDataOne,height - margin);
				rotate(-PI/2);
				textSize(textSize);
				textAlign(RIGHT,UP);
				textLeading(textSize);				
				text(year,-5,(float) (rectX*1.5));
				popMatrix() ;
				
			}
			
			finishUp();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int[] getMaxFreqMinMaxYear(ArrayList<TermFreqPlace> yearFreq) {
		int maxFreq = 0;
		int minYear = 10000;
		int maxYear = 0;
		for (int i = 0; i < yearFreq.size(); i++) {
			if (yearFreq.get(i).getFrequency() > maxFreq && yearFreq.get(i).getYear() != 0)
				maxFreq = yearFreq.get(i).getFrequency();
			
			if(yearFreq.get(i).getYear() < minYear && yearFreq.get(i).getYear() != 0)
				minYear = yearFreq.get(i).getYear();
			
			if(yearFreq.get(i).getYear() > maxYear)
				maxYear = yearFreq.get(i).getYear();
		}
		return new int[]{maxFreq,minYear,maxYear};
	}

}
