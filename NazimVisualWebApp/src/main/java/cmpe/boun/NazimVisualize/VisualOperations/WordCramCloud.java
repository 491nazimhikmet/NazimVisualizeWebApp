package cmpe.boun.NazimVisualize.VisualOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cmpe.boun.NazimVisualize.DAO.WordDao;
import processing.core.PApplet;
import processing.core.PFont;
import wordcram.Anglers;
import wordcram.Placers;
import wordcram.Sizers;
import wordcram.Word;
import wordcram.WordCram;
import zemberek.morphology.apps.TurkishMorphParser;

public class WordCramCloud extends PApplet{
	private int workId;
	int width;
	int height;
	
	public boolean isFinished;
	
	public String saveFileName;
	
	public WordCramCloud(int width,int height,int workId){
		this.workId = workId;
		this.width = width;
		this.height = height;
		saveFileName = Math.random() * 255 + "b.png";
		isFinished = false;
	}
	
	WordCram wordcram;
    
    public void setup() {

            size(width, height); 
            smooth();
            background(171,238,255,(float)0.43);
            colorMode(HSB);
            initWordCram();
    }
    
    private PFont randomFont() {
            String[] fonts = PFont.list();
            String noGoodFontNames = "Dingbats|Standard Symbols L";
            String blockFontNames = "OpenSymbol|Mallige Bold|Mallige Normal|Lohit Bengali|Lohit Punjabi|Webdings";
            Set<String> noGoodFonts = new HashSet<String>(Arrays.asList((noGoodFontNames+"|"+blockFontNames).split("|")));
            String fontName;
            do {
                    fontName = "Segoe Print";//fonts[(int)random(fonts.length)];
            } while (fontName == null || noGoodFonts.contains(fontName));
            System.out.println(fontName);
            return createFont(fontName, 1);
            //return createFont("Molengo", 1);
    }
    
    //PGraphics pg;
    private void initWordCram() {
    	System.out.println("initWordCram");

            wordcram = new WordCram(this)
                                    .fromTextString(getLineOfWork())
                                    .withFonts(randomFont())
                                    .withAngler(Anglers.mostlyHoriz())
                                    .withPlacer(Placers.horizLine())
                                    .withSizer(Sizers.byWeight(5, 150));
        
            
    }
    
    private void finishUp() {
    	System.out.println("finishUp ");
            save(saveFileName);
            isFinished = true;
            this.destroy();
            noLoop();
            
    }
    
    public void draw() {
    	//fill((int) (255*Math.random()),(int) (255*Math.random()),(int) (255*Math.random()));
		//textSize(45);//print word near circle
		//text("Kelimelerin büyüklüğü kullanım sıklığını gösterir.", 10, height-30);
		//height = height-30;
            boolean allAtOnce = true;
            System.out.println("allAtOnce " + allAtOnce);
            if (allAtOnce) {
                    wordcram.drawAll();
                    finishUp();
                    
            }
            else {
                    int wordsPerFrame = 1;
                    while (wordcram.hasMore() && wordsPerFrame-- > 0) {
                            wordcram.drawNext();
                    }
                    
                    if (!wordcram.hasMore()) {
                            finishUp();
                    }
            }
       height += 30;    
    }
    
    public void mouseMoved() {
            /*
            Word word = wordcram.getWordAt(mouseX, mouseY);
            if (word != null) {
                    System.out.println(round(mouseX) + "," + round(mouseY) + " -> " + word.word);
            }
            */
    }
            
    public void mouseClicked() {
            initWordCram();
            loop();
    }
    
    
    private String textFilePath() {
            boolean linux = true;
            String projDir = linux ? "/home/dan/projects/" : "c:/dan/";
            String path = projDir + "eclipse/wordcram/trunk/ideExample/tao-te-ching.txt";
            return path;            
    }
    
    private Word[] alphabet() {
            Word[] w = new Word[26];
            for (int i = 0; i < w.length; i++) {
                    w[i] = new Word(new String(new char[]{(char)(i+65)}), 26-i);
            }
            return w;
    }
	
	private String[] getLineOfWork(){
		
		ApplicationContext context = 
		    	new ClassPathXmlApplicationContext("Spring-Module.xml");
		WordDao wordDao = (WordDao) context.getBean("WordDao");
		
		List<cmpe.boun.NazimVisualize.Model.Word> wordList = new ArrayList<cmpe.boun.NazimVisualize.Model.Word>();
		
		String[] returnArray = null;
		
		try {
			wordList = new ArrayList<cmpe.boun.NazimVisualize.Model.Word>(wordDao.getWordsOfAWork(workId));
		
			returnArray = new String[wordList.size()];
			
			/*TurkishMorphParser parser;
			parser = TurkishMorphParser.createWithDefaults();
			Tokenize tokenize = new Tokenize(parser);*/
			
			for(int i =0 ; i< wordList.size(); i++){
				//returnArray[i] = tokenize.parse(wordList.get(i).getText());
				returnArray[i] = wordList.get(i).getDisambiguated();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return returnArray;
	}
	
}
