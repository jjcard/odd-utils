package blaa.blarg.tired;

import java.util.HashMap;

public class OddStringUtil {
	private static final HashMap<String, String> special1337words = OddStringUtil.get1337words();
	
	public static String getAcronym(String toTurn){
		return getAcronym(toTurn, true);
	}
	/**
	 * turns given phrase into Acronym with First letter of each word, unless the word should be skipped as in the case of 'the', 'a', etc or if the word is a number, in case it is added
	 * to the Acronym whole. ex. Big F'ing Gun 9000 becomes BFG9000 (if usePeriods is set to false).
	 * @param toTurn
	 * @param usePeriods
	 * @return
	 */
	public static String getAcronym(String phrase, boolean usePeriods){
		if (OddStringUtil.isEmpty(phrase)){
			return "";
		}
		
		StringBuilder re = new StringBuilder();
		
		String [] words = phrase.split(" ");
		
		for (String word: words){
			if(OddStringUtil.skipForAcronym(word)){
				continue;
			} else {
				if (OddStringUtil.isNumeric(word)){
					re.append(word);
				} else{
					re.append(Character.toUpperCase(word.charAt(0)));
					if (usePeriods){
						re.append('.');	
					}
				}
				
			}
		}
		return re.toString();
		
		
		
	}
	private static boolean skipForAcronym(String word){
		if (word.equalsIgnoreCase("the") || word.equalsIgnoreCase("a") 
				||word.equalsIgnoreCase("an")){
			return true;
		}
		return false;
	}
	public static boolean isNumeric(String string){
		if (OddStringUtil.isEmpty(string)){
			return false;
		}
		string = string.trim();
		char [] chars = string.toCharArray();
		for (char C: chars){
			if (!Character.isDigit(C)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isEmpty(String e){
		return e == null || e.trim().length() == 0;
	};
	public static String[] breakUpWords(String string){
		return string.split(" ");
	}
	
	/**
	 * returns the 1337 form of a phrase, replacing key word with their 1337 equivalents
	 * and replacing the rest character by character, 'e' with '3', etc.
	 * @param phrase
	 * @return
	 */
	public static String get1337(String phrase){
		StringBuilder re = new StringBuilder();
		String [] words = OddStringUtil.breakUpWords(phrase);
		
		for (String word: words){
			if (isSpecial1337word(word)){
				re.append(getSpecial1337word(word));
			} else {
				re.append(transformWordTo1337(word));
			}
			re.append(" ");
		}
		
		return re.toString();
	}
	
	private static String transformWordTo1337(String word){
		String returnWord = word;
		
		returnWord = returnWord.replace('e', '3');
		returnWord = returnWord.replace('l', '1');
		returnWord = returnWord.replace('o', '0');
		return returnWord;
	}
	private static boolean isSpecial1337word(String word){
		return special1337words.containsKey(word);
	}
	private static String getSpecial1337word(String word){
		return special1337words.get(word);
	}
	private static HashMap<String, String> get1337words(){
		
		HashMap<String, String> swords;
		String[] args = {"elite", "1337", "the", "teh"};
		
		swords = getHashMap(args);
		
		
		
		return swords;
	}
	private static HashMap<String, String> getHashMap(String ...args){
		HashMap<String, String> hashMap = new HashMap<String, String>();
		if (args.length %2 != 0){
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = 0; i < args.length; i+= 2){
			hashMap.put(args[i], args[i+1]);
		}
		
		return hashMap;
	}
	
	public static void main(String[] args){
		
		String test = OddStringUtil.get1337("the elite cow jumped over the moon");
		System.out.println(test);
	}

}
