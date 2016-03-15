package cmpe.boun.NazimVisualize.VisualOperations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cmpe.boun.NazimVisualize.DAO.WordDao;
import cmpe.boun.NazimVisualize.DAO.WorkDao;
import cmpe.boun.NazimVisualize.Model.Word;
import cmpe.boun.NazimVisualize.Model.Work;
import processing.core.PApplet;

public class Test extends PApplet {
	
	
	public Test(int width,int height){
		displayWidth = width;
		displayHeight = height;

	}
	
	int posId = 0;
	int currentWorkId = 0;
	List<Work> 	workList;
	String currentWorkName = "";
	
	public void setup () {
		size (displayWidth, displayHeight);
		frameRate(150);
		
		WorkDao workDao = new WorkDao();
		try {
			workList = new ArrayList<Work>( workDao.getAllWorks());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public void draw(){
		background(226,225,225);
		
		
		currentWorkId = workList.get(posId).getWorkID();
		currentWorkName = workList.get(posId).getName();
		posId++;
		WordDao wordDao = new WordDao();
		
		try {
			
			List<Word> wordList = wordDao.getWordsOfAWork(currentWorkId);
			System.out.println(currentWorkId);
			ArrayList<WordPoint> wordPointList = new ArrayList<WordPoint>();
			
			for(int i = 0; i< wordList.size(); i++){
				Word word = wordList.get(i);
				fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
				int x = (int) Math.floor((displayWidth-20) * Math.random());
				int y = (int) Math.floor((displayHeight-20) * Math.random());
				int r = 5;
				
				ellipse(x,y,r,r);
				wordPointList.add(i,new WordPoint(x,y,r));
			}
			
			noFill();
			beginShape();
			
			textSize(32);
			text(currentWorkName, 30, 30);
			delay(1500);
			
			
			for(int i = 0; i< wordPointList.size(); i++){
				
				
				if(i>0){
					if(wordList.get(i).getWorkLineID() != wordList.get(i-1).getWorkLineID()){
						endShape();
						beginShape();
						//fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
						curveVertex(wordPointList.get(i).getPosX(),wordPointList.get(i).getPosY());
					}else{
						//fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
						curveVertex(wordPointList.get(i).getPosX(),wordPointList.get(i).getPosY());
					}
				}else{
					//fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
					curveVertex(wordPointList.get(i).getPosX(),wordPointList.get(i).getPosY());
				}
				
				

			}
			endShape();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private int wordAsciiSum(String word){
		int asciiSum = 0;
		
		for(int i = 0; i< word.length(); i++){
			asciiSum += word.charAt(i);
		}
		
		return asciiSum;
	}
	
}
