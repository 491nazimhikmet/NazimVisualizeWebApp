package cmpe.boun.NazimVisualize.VisualOperations;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cmpe.boun.NazimVisualize.DAO.WordDao;
import cmpe.boun.NazimVisualize.Model.TermFreqYear;
import processing.core.PApplet;

public class WordFrequencyGraph extends PApplet {
	int width;
	int height;
	String term;
	
	public String saveFileName;

	public WordFrequencyGraph(int width, int height, String term) {
		this.width = width;
		this.height = height;
		this.term = term;
		saveFileName = Math.random() * 255 + "c.png";
	}

	public void setup() {
		size(width, height);
		//smooth();
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
		background(40);
		noStroke();

		ArrayList<TermFreqYear> yearFreq;
		try {
			yearFreq = new ArrayList<TermFreqYear>(wordDao.getFreqOverYearsOfTerm(term));
			//System.out.println("size" + yearFreq + " term : " +term);
			int[] maxFreqMinMaxYear = getMaxFreqMinMaxYear(yearFreq);
			//System.out.println(maxFreqMinMaxYear[0] + " " + maxFreqMinMaxYear[1] + " " +maxFreqMinMaxYear[2] );
			float textSize = map(width, 0, 3200, 10, 30);
			margin = (int)map(height, 0, 2000, 0, 200);
			rectX = map(width, 0, 3200, 0, 10);
			for (int i = 0; i < yearFreq.size(); i++) {
				TermFreqYear currentYear = yearFreq.get(i);
				if (currentYear.getYear() == 0) {
					textSize(textSize);
					textAlign(LEFT, TOP);
					textLeading(textSize); 
					text("Zamanı bulunamayanların sıklığı : " + currentYear.getFrequency(),margin,margin-100);

					continue;
				}
				
				float xPosDataOne = map(currentYear.getYear(), maxFreqMinMaxYear[1]-1, maxFreqMinMaxYear[2], 0 + margin, width - margin);
				float yPosDataOne = map(currentYear.getFrequency(), 0, maxFreqMinMaxYear[0], 0, height - (int)(1.3 * margin));
				
				
				//System.out.println(xPosDataOne + " "+yPosDataOne + " " + textSize);
					
				String year = currentYear.getYear() + "";
				String newYear = "";
				for(int l = 0; l<year.length(); l++){
					newYear += year.charAt(l)+"\n";
				}
				fill(226, 229, 100);
				rectMode(CORNER);
				rect(xPosDataOne, height - margin, rectX, -1 * yPosDataOne);
				
				textSize(textSize);
				textAlign(LEFT, TOP);
				textLeading(textSize); 
				text(newYear,xPosDataOne,height - margin);
				
				textAlign(LEFT, BOTTOM);
				textSize(textSize);
				text(currentYear.getFrequency(),xPosDataOne,height-margin-yPosDataOne);
				
			}
			
			finishUp();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int[] getMaxFreqMinMaxYear(ArrayList<TermFreqYear> yearFreq) {
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
