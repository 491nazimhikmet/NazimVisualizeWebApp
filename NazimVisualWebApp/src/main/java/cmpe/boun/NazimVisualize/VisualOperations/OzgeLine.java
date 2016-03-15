package cmpe.boun.NazimVisualize.VisualOperations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cmpe.boun.NazimVisualize.DAO.WordDao;
import cmpe.boun.NazimVisualize.DAO.WorkDao;
import cmpe.boun.NazimVisualize.DAO.WorkLineDao;
import cmpe.boun.NazimVisualize.Model.Word;
import cmpe.boun.NazimVisualize.Model.WorkLine;
import processing.core.PApplet;


public class OzgeLine extends PApplet {

	int id=6168;
	float zoom = 1;
	boolean flag = false;
	int spaceCount=0;
	List<WorkLine> poetry;
	int width;
	int height;
	
	
	public OzgeLine(int width,int height,int currentWorkId){
		this.width = width;
		this.height = height;
		this.id = currentWorkId;
	}
	
	
	public void setup () {
		size (width, height);	
		stroke(255);     // Set line drawing color to white
		frameRate(50);  
		poetry = circleData(id);
		
	}
		
	
	public void draw(){
				
		if(flag)
			background(255);//circle 
		else
			background(180);
		scale(zoom);
		
		circleDraw(poetry);
		
		if(!flag){
			fill(20);
			textSize(30);//print word near circle
			String tanim = "Daireler sözcüklerin şiirdeki konumuna göre yerleştirilmiştir.\nÇaplarının uzunluğu sözcük uzunluğu ile orantılıdır. "+
					"\nRenkli daireler renk bildiren sıfatları ifade eder.\n* Şiiri görmek için Space tuşuna basınız.\n* Yakınlaştırmak için aşağı ve yukarı yön tuşlarını kullanınız";
			text(tanim, width-900, 30);
		}
	}
	
	public void mouseDragged(){
		//System.out.println("dragged");
	} 
	public void keyPressed() {
		  //id++;
		if(key == CODED){
			
			if(keyCode == UP){
				zoom += .03;
			}
			else if(keyCode == DOWN){
				zoom -= .03;
			}
			else if(keyCode == RIGHT){
				id++;
				poetry = circleData(id);
				flag = false;
			}
			else if(keyCode == LEFT){
				id--;
				poetry = circleData(id);
				flag = false;
			}
		}	
		if (key == ' '){
			spaceCount++;
			if(spaceCount%2 == 1){
				flag = true;
				zoom = (float) 1.2;
			}else 
				flag = false;
				zoom = (float) 1;
		}
	}	

	public List<WorkLine> circleData(int id){
		WorkLineDao workLineDao = new WorkLineDao();
		WorkDao workDao = new WorkDao();
		WordDao wordDao = new WordDao();
		
		try {
		
			List<WorkLine> workLines = workLineDao.getWorkLineOfAWork(id);
				
			for(int i=0;i<workLines.size();i++){				
				List<Word> words = wordDao.getWordsOfAWorkline(workLines.get(i).getLineID());
				
				workLines.get(i).setWords(words);
			}
			return workLines;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public int[] setColor(String word){
		int[] color = {50,50,50};
		
		if(word.contains("beyaz")){
			color[0] =255;
			color[1] =255;
			color[2] =255;
		}else if(word.contains("kızıl")){
			//fill(127,0,0);
			color[0] =127;
			color[1] =0;
			color[2] =0;
		
		}else if(word.contains("sarı")&&(!word.contains("sarık"))){
			//fill(255,255,20);
			color[0] =255;
			color[1] =255;
			color[2] =20;
			
		}else if(word.contains("siyah")||word.contains("kara")){
			//fill(255,255,20);
			color[0] =0;
			color[1] =0;
			color[2] =0;	
		}else if(word.contains("kırmızı")){
			color[0] =255;
			color[1] =0;
			color[2] =0;	
		}else if(word.contains("yeşil")){
			color[0] =0;
			color[1] =140;
			color[2] =0;	
		}else if(word.contains("mavi")){
			color[0] =0;
			color[1] =0;
			color[2] =200;	
		}else if(word.contains("mor")){
			color[0] =100;
			color[1] =0;
			color[2] =100;	
		}
		return color;
	}
	public void circleDraw(List<WorkLine> poetry){
		//System.out.println(poetry.get(0).toString()+"-> ,linenum: "+poetry.size());			
		int x=10; //x coordinate of the ellipse
		int y=30; //y coordinate of the ellipse
		int r=15;
		int[] color = {50,50,50};
		
		int alpha=255;
		
				
		for(int i=0;i<poetry.size();i++){
			
			List<Word> currentLine =poetry.get(i).getWords();	
	
			for(int j=0;j<currentLine.size();j++){
				
				x=(int)currentLine.get(j).getWordStart()*3/2;	
				r=currentLine.get(j).toString().length()*2;//length of the word
				
				if(flag)	
					alpha = 40;
				
				color = setColor(currentLine.get(j).getText());	
				fill(color[0],color[1],color[2],alpha);
				
				ellipse(x,y,r,r);	
				
				//to see poetry
				if(flag){
					alpha = 255;
					textSize(11);
					fill(0,255);
					text(currentLine.get(j).toString(),x,y);	
				}
			}
			y=y+22;
		}
		
	}
}
