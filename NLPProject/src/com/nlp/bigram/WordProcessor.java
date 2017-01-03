package com.nlp.bigram;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;


public class WordProcessor {

	private static HashMap<String, Integer> tokenMap = new HashMap<String, Integer>();
	private static HashMap<String, Integer> bigramMap = new HashMap<String, Integer>();
	private static int V = 0;
	private static int N = 0;
	private static int totalBigramCount = 0;
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		new WordProcessor().updateCorpus("I am a boy");
		
	}
	
	
	public WordProcessor() throws FileNotFoundException{
		processCorpus();
	}
	public void processCorpus() throws FileNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("Processing Corpus ....");
		
	      File corpusFile = new File("corpus.txt"); 
			
			
			//String content = scr.next().replaceAll("<.*?>", "");
			Scanner in = new Scanner(corpusFile);
			
			
			while(in.hasNext())
			{
				String word = in.next().replaceAll("\\s", "").replaceAll("[^\\w.’]", "");
				
				if(tokenMap.get(word) == null)
				{
					tokenMap.put(word, new Integer(1));
					V++;
				}
				else
				{
					Integer t = tokenMap.get(word);
					tokenMap.put(word, t+1);
				}
			}
			
			
			
			in = new Scanner(corpusFile);
	        // Bigram Processing
			String currentWord = null, nextWord = null;
			
			if(in.hasNext())
			{
				currentWord = in.next().replaceAll("\\s", "").replaceAll("[^\\w.’]", "").toLowerCase();
			}
			
			//System.out.println(currentWord);
			while(in.hasNext())
			{ //c++;
				 nextWord = in.next().replaceAll("\\s", "").replaceAll("[^\\w.’]", "").toLowerCase();
				 String bigram = currentWord +" "+ nextWord; 
				 currentWord = nextWord;
				 
				 
				if(bigramMap.get(bigram) == null)
				{
					bigramMap.put(bigram, new Integer(1));
					//System.out.println(bigram);
					totalBigramCount++;
				}
				else
				{
					Integer t = bigramMap.get(bigram);

					bigramMap.put(bigram, t+1);
				}
				
			}
			
			System.out.println("Corpus has been processed ....");
			System.out.println("Vocabulary size(V) => " +V);
			System.out.println("Bigram Count => " + totalBigramCount);
			
			System.out.println("Please start writing your article ...");
	}
	public List<String> processWord(String searchWord){
		HashMap<String,Integer> suggestions = new HashMap<String,Integer>();
		
		Iterator it = bigramMap.entrySet().iterator();
		boolean found = false;
		while(it.hasNext()){
			
			Map.Entry pair = (Entry) it.next();
			
			String[] words = pair.getKey().toString().split(" ");
			//System.out.println(words[0]);
			if(searchWord.equalsIgnoreCase(words[0])){
				suggestions.put(words[1],(Integer) pair.getValue());
				found = true;
			}
		}
		
		if(!found){
			System.out.println("No Suggestions!!");
		}
		HashMap<String,Integer> sortedMap = sortByFreqValues(suggestions);
		//System.out.println(sortedMap);
		return new LinkedList(sortedMap.entrySet());
	}
	
	
	@SuppressWarnings("unchecked")
	private static HashMap<String,Integer> sortByFreqValues(HashMap map) { 
	    @SuppressWarnings("rawtypes")
		List list = new LinkedList(map.entrySet());
	    // Defined Custom Comparator here
	    Collections.sort(list, new Comparator() {
	         
			public int compare(Object o1, Object o2) {
	            return -((Comparable) ((Map.Entry) (o1)).getValue())
	               .compareTo(((Map.Entry) (o2)).getValue());
	         }
	    });
	
	    // Here I am copying the sorted list in HashMap
	    // using LinkedHashMap to preserve the insertion order
	    HashMap<String,Integer> sortedHashMap = new LinkedHashMap<String,Integer>();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	           Map.Entry entry = (Map.Entry) it.next();
	           sortedHashMap.put((String)entry.getKey(), (Integer)entry.getValue());
	    } 
	    //System.out.println(sortedHashMap);
	    return sortedHashMap;
	}
	public void updateCorpus(String corpusText) {
		// TODO Auto-generated method stub
		PrintWriter fout = null;
		File file = new File("corpus.txt");
		try {
			fout = new PrintWriter(new FileWriter(file, true));
			fout.append(" " + corpusText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			 if(fout != null){
		            fout.close();
		        } 
		}
		System.out.printf("File is located at %s%n", file.getAbsolutePath());
	}

}
