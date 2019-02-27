package textVectorizer;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import org.apache.commons.io.input.ReversedLinesFileReader;

public class Vectorizer{
	
	BufferedReader reader;
	BufferedWriter writer;
	ArrayList<WordOccurence> wordArray = new ArrayList<WordOccurence>();
	
	public void findFiles(String filePath){
		File f = new File(filePath);
		String[] filePaths = f.list();
		for(int i=0; i<filePaths.length; i++){
			File p = new File(f.getPath(), filePaths[i]);
			if(p.isDirectory()){
				findFiles(p.getPath());
			}
			if(p.isFile()){
				System.out.println(p.getName());
				readFile(p);
			}
		}
	}
	
	public void readFile(File file){
		try {
			ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
			//String eol = System.getProperty("line.separator");
			for (int i = 0; i < fileContent.size(); i++) {
				if(fileContent.get(i).equals("")){
					break;
				}
				while (!fileContent.get(i).equals("")){
					fileContent.set(i, null);
					break;
				}
			}
			Files.write(file.toPath(), fileContent, StandardCharsets.UTF_8);
//			int lineNo = 0;
//			this.reader = new BufferedReader(new FileReader(file));
//			this.writer = new BufferedWriter(new FileWriter(file, true));
//			String eol = System.getProperty("line.separator");
//			while((this.reader.readLine()) != eol){
//				this.reader.
//			}
//			ReversedLinesFileReader wordReader = new ReversedLinesFileReader(file);
//			while(lineNo > 0){
//				String line = wordReader.readLine();
//				String[] words = line.split(" ");
//				for(String word : words){
//					addWordToOccurenceArray(word);
//				}
//				lineNo--;
//			}
//			wordReader.close();
//			this.writer = new BufferedWriter(new FileWriter("D:"+File.separator+"AI"+File.separator+"GA"+File.separator+"TextVectorizer"+File.separator+"vectors.txt", true));
//			ArrayList<WordOccurence> temp = this.sortWordsByOccurence(this.wordArray);
//			String result = this.createWordString(temp);
//			this.writer.write(file.getName()+", "+file.getParentFile().getName()+result);
//			this.writer.write(eol);
//			reader.close();
//			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<WordOccurence> sortWordsByOccurence(ArrayList<WordOccurence> wordArray){
		ArrayList<WordOccurence> sorted = new ArrayList<WordOccurence>();
		while(!wordArray.isEmpty()){
			int tempFreq = 0;
			int tempIndex = 0;
			for(int i=0; i<wordArray.size(); i++){
				if (tempFreq < wordArray.get(i).frequency){
					tempFreq = wordArray.get(i).frequency;
					tempIndex = i;
				}
			}
			sorted.add(wordArray.get(tempIndex));
			wordArray.remove(tempIndex);
		}
		return sorted;
	}
	
	public String createWordString(ArrayList<WordOccurence> wordArray){
		StringBuilder builder = new StringBuilder();
		for (WordOccurence word : wordArray){
			builder.append(", \""+word.word+"\", "+word.frequency);
		}
		return builder.toString();
	}
	
	public void addWordToOccurenceArray(String word){
		boolean foundFlag = false;
		word = word.replace(",", "");
		word = word.replace(".", "");
		word = word.replace("-", "");
		word = word.replace("_", "");
		word = word.replace("!", "");
		word = word.replace("@", "");
		word = word.replace("#", "");
		word = word.replace("$", "");
		word = word.replace("%", "");
		word = word.replace("^", "");
		word = word.replace("&", "");
		word = word.replace("=", "");
		word = word.replace(":", "");
		word = word.replace(";", "");
		word = word.replace("'", "");
		word = word.replace("<", "");
		word = word.replace(">", "");
		word = word.replace("|", "");
		word = word.replace("0", "");
		word = word.replace("1", "");
		word = word.replace("2", "");
		word = word.replace("3", "");
		word = word.replace("4", "");
		word = word.replace("5", "");
		word = word.replace("6", "");
		word = word.replace("7", "");
		word = word.replace("8", "");
		word = word.replace("9", "");
		for(int i=0; i<this.wordArray.size(); i++){
			if (word.equals(this.wordArray.get(i).word)){
				this.wordArray.get(i).frequency++;
				foundFlag = true;
			}
		}
		if (!foundFlag){
			this.wordArray.add(new WordOccurence(word));
		}
	}

		public static void main(String[] args){
			Vectorizer vectorizer = new Vectorizer();
			vectorizer.findFiles("D:"+File.separator+"AI"+File.separator+"GA"+File.separator+"TextVectorizer"+File.separator+"toCheck");
		}
	
}

class WordOccurence{
	
	String word;
	int frequency;
	
	WordOccurence(String word){
		this.word = word;
		this.frequency = 1;
	}
	
	public void increaseOccurence(){
		this.frequency++;
	}
	
	@Override
	public boolean equals(Object o){
		if (this.word.equals(((WordOccurence)o).word)){
			return true;
		}
		return false;
	}

}


