package cmpe.boun.NazimVisualize.VisualOperations;

import java.util.List;

import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;


public class Tokenize {
	
	TurkishMorphParser parser;

    public Tokenize(TurkishMorphParser parser) {
        this.parser = parser;
    }

    public String parse(String word) {
    	word = tokenize(word);
    	word = word.toLowerCase();
    	List<MorphParse> parses = parser.parse(word);
    	
    	//System.out.print(word +"=>\t");
    	//for (MorphParse parse : parses) {
    		/*for(String lemma: parse.getLemmas()){
    			System.out.println(lemma);
    		}*/
    		//System.out.print(parse.dictionaryItem.lemma+" "+parse.dictionaryItem.primaryPos.getStringForm()+ " dsd "+ parse.dictionaryItem.secondaryPos.getStringForm()+"  --  ");
			//System.out.print(parse.dictionaryItem.lemma+"\t");
	    //}
	    //System.out.println();
        
        if(parses.size()<2){
        	return word;
        }else{
        	return parses.get(1).dictionaryItem.lemma.toLowerCase();
        }
        
    }
    
	public String tokenize(String word){
		//remove all hypens from string and concatanate string
		while(word.contains("-")){
			word = word.substring(0,word.indexOf("-"))+word.substring(word.indexOf("-")+1,word.length());
		}
		
		//if the last char of string not a letter or number then remove it
		while(word.length()> 0 && !Character.isLetter(word.charAt(word.length()-1)) && !Character.isDigit(word.charAt(word.length()-1))){
			word = word.substring(0,word.length()-1);
		}
		
		//if the first char of string not a letter or number then remove it
		while(word.length()> 1 && !Character.isLetter(word.charAt(0)) && !Character.isDigit(word.charAt(0))){
			word = word.substring(1,word.length());
		}
				
		return word;	
	}

}
