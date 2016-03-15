package cmpe.boun.NazimVisualize.VisualOperations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cmpe.boun.NazimVisualize.DAO.UserDAO;
import cmpe.boun.NazimVisualize.DAO.WordDao;
import cmpe.boun.NazimVisualize.Model.Word;
import processing.core.PApplet;

public class OnurLineGorseli extends PApplet{

	int width;
	int height;
	boolean isCurve;
	boolean fillFlag;
	
	public String savedFileName;
	
	public OnurLineGorseli(int width,int height,int currentWorkId,String currentWorkName,boolean isCurve,boolean fillFlag){
		this.width = width;
		this.height = height;
		this.currentWorkId = currentWorkId;
		this.currentWorkName = currentWorkName;
		this.isCurve = isCurve;
		this.fillFlag = fillFlag;
		savedFileName = 255*Math.random()+"a.png";
	}
	
	int currentWorkId = 0;
	String currentWorkName = "";
	
	public void setup () {
		size (width, height);
		//frameRate(150);
		noLoop();
	}
	
	private void finishUp() {
		
		save(savedFileName);
		this.destroy();
		noLoop();
	}

	public void draw(){
		background(226,225,225);
		
		if(!fillFlag){
			fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
			textSize(45);//print word near circle
			text("Kelimeler gelişigüzel dağıtıldı ve aynı satırdaki kelimeler oklarla birbirlerine bağlandı", 10, height-30);
			height = height-30;
		}
		
		ApplicationContext context = 
		    	new ClassPathXmlApplicationContext("Spring-Module.xml");
		WordDao wordDao = (WordDao) context.getBean("WordDao");
		   		
		try {
			
			List<Word> wordList = wordDao.getWordsOfAWork(currentWorkId);
			//System.out.println(currentWorkId);
			ArrayList<WordPoint> wordPointList = new ArrayList<WordPoint>();
			
			for(int i = 0; i< wordList.size(); i++){//for each word draw a circle with different color
				Word word = wordList.get(i);
				fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
				int x = (int) Math.floor((width-60) * Math.random());
				int y = (int) Math.floor((height-60) * Math.random());
				int r = 5;
				
				if(!fillFlag){
					textSize(30);//print word near circle
					text(word.getText(), x+5, y+5);
				}
				ellipse(x,y,r,r);
				wordPointList.add(i,new WordPoint(x,y,r));
			}
			
			noFill();
			beginShape();
			
			delay(1500);
			
			
			for(int i = 0; i< wordPointList.size(); i++){
				
				if(!isCurve){
					if(i>0){
						if(wordList.get(i).getWorkLineID() == wordList.get(i-1).getWorkLineID()){
							if(fillFlag){
								fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
							}
							line(wordPointList.get(i).getPosX(),wordPointList.get(i).getPosY(),wordPointList.get(i-1).getPosX(),wordPointList.get(i-1).getPosY());
						}
					}
				}else{
				
					if(i>0){
						if(wordList.get(i).getWorkLineID() != wordList.get(i-1).getWorkLineID()){
							if(fillFlag){
								fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
							}
							curveVertex(wordPointList.get(i-1).getPosX(),wordPointList.get(i-1).getPosY());
							endShape();
							beginShape();
							if(fillFlag){
								fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
							}
							curveVertex(wordPointList.get(i).getPosX(),wordPointList.get(i).getPosY());
							curveVertex(wordPointList.get(i).getPosX(),wordPointList.get(i).getPosY());
						}else{
							if(fillFlag){
								fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
							}
							curveVertex(wordPointList.get(i).getPosX(),wordPointList.get(i).getPosY());
						}
					}else{
						if(fillFlag){
							fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
						}
						curveVertex(wordPointList.get(i).getPosX(),wordPointList.get(i).getPosY());
						curveVertex(wordPointList.get(i).getPosX(),wordPointList.get(i).getPosY());
					}
				
				}
				

			}
			endShape();
			finishUp();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
