package blaa.blarg.tired;

import java.util.HashMap;

public class OddStringUtil {
	
	private static final CaseInsensitiveMap special1337words = getSpecial1337Map();
	private static final String[] acronymToSkip = {"the", "a", "an"};
	private static final String EMPTY_STRING = "";
	private static final char PERIOD = '.';
	
	private static final char[] replace1337Chars = {'e', 'E', 'l', 'o', 'O'};
	private static final char[] replace1337with  = {'3', '3', '1', '0', '0'};
	
	private OddStringUtil(){
		//private constructor, since its a util singleton
	}
	/**
	 * Same as calling getAcronym(phrase, true)
	 * @param toTurn
	 * @return
	 * @see {@link getAcronym(String, boolean)}
	 */
	public static String getAcronym(String phrase){
		return getAcronym(phrase, true);
	}
	/**
	 * turns given phrase into Acronym with First letter of each word, unless the word should be skipped as in the case of 'the', 'a', etc or if the word is a number, in case it is added
	 * to the Acronym whole. ex. Big F'ing Gun 9000 becomes BFG9000 (if usePeriods is set to false).
	 * Should only be used on a phrase, whole sentences may not create desired outcome.
	 * @param toTurn
	 * @param usePeriods
	 * @return
	 */
	public static String getAcronym(String phrase, boolean usePeriods){
		if (OddStringUtil.isEmpty(phrase)){
			return EMPTY_STRING;
		}
		StringBuilder re = new StringBuilder();
		
		String [] words = phrase.split("[\\s]");
		
		for (String word: words){
			if( OddStringUtil.skipForAcronym(word)){
				continue;
			} else {
				if (OddStringUtil.isNumeric(word)){
					re.append(word);
				} else{
					re.append(Character.toUpperCase(word.charAt(0)));
					if (usePeriods){
						re.append(PERIOD);	
					}
				}
				
			}
		}
		return re.toString();
		
		
		
	}
	private static boolean skipForAcronym(String word){
		
		for (String skipWord: acronymToSkip){
			if (word.equalsIgnoreCase(skipWord)){
				return true;
			}
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
	/**
	 * Simply checks if the String is null or has 
	 * no non-whitespace characters.
	 * @param e
	 * @return
	 */
	public static boolean isEmpty(String e){
		return e == null || e.trim().length() == 0;
	};
	
	public static String[] BreakUpLines(String string){
		return string.split("[\\n\\t]");
	}
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
		
		String[] lines = OddStringUtil.BreakUpLines(phrase);
		String line;
		int j = 0;
		
		if (lines.length > 0){
			line = lines[j++];
			re.append(OddStringUtil.get1337FromLine(line));
		}
		for (; j < lines.length; j++){
			re.append("\n");
			line = lines[j];
			re.append(OddStringUtil.get1337FromLine(line));
		}
		
		return re.toString();
	}
	private static String get1337FromLine(String line){
		StringBuilder re = new StringBuilder();
		String[] words = OddStringUtil.breakUpWords(line);
		String word; 
		int i = 0;
		if (words.length > 0){
			 word = words[i++];
			if (isSpecial1337word(word)){
				re.append(getSpecial1337word(word));
			} else {
				re.append(transformWordTo1337(word));
			}
			for (;i < words.length; i++){
				re.append(" ");
				 word = words[i];
				if (isSpecial1337word(word)){
					re.append(getSpecial1337word(word));
				} else {
					re.append(transformWordTo1337(word));
				}
				
			}			
		}
		
		return re.toString();
	}
	/**
	 * replaces individual letters in words with their
	 * 1337 equivalents.
	 * @param word
	 * @return
	 */
	private static String transformWordTo1337(String word){
		String returnWord = word;
		
		for (int i = 0; i < replace1337Chars.length; i++){
			returnWord = returnWord.replace(replace1337Chars[i], replace1337with[i]);
		}
		
		return returnWord;
	}
	private static boolean isSpecial1337word(String word){
		return special1337words.containsAnyKey(word);
	}
	private static String getSpecial1337word(String word){
		return special1337words.getAny(word);
	}

	private static CaseInsensitiveMap getSpecial1337Map(){
		CaseInsensitiveMap s1337Words = new CaseInsensitiveMap();
		
		String[] caseInsensitiveWords = {"elite", "1337", "the", "teh"};
		s1337Words.put(caseInsensitiveWords, true);
		
		//adding LOL or else it would become 101
		String[] caseSensitiveWords = {"THE", "TEH", "The", "Teh", "LOL", "LOL", "lol", "lol"};
		s1337Words.put(caseSensitiveWords, false);
		
		
		
		
		return s1337Words;
	}
	
	private static class CaseInsensitiveMap extends HashMap<String, String>{
		

		private static final long serialVersionUID = 1L;

		public String put(String key, String value){
			return put(key.toLowerCase(), value, true);
		}
		
		public String put(String key, String value, boolean caseInsensitive){
			if (caseInsensitive){
				return super.put(key.toLowerCase(), value);
			} else {
				return super.put(key, value);
			}
		}
		public String get(String key, boolean caseInsensitive){
			if (caseInsensitive){
				return super.get(key.toLowerCase());
			} else {
				return super.get(key);
			}
		}
		
		/**
		 * tries to find an exact match before trying
		 * to find the caseInsensitive match.
		 * @param key
		 * @return
		 */
		public String getAny(String key){
			String re = super.get(key);
			
			if (re == null){
				re = super.get(key.toLowerCase());
			}
			return re;
		}
		
		public void put(String[] keyValuePairs, boolean caseInsensitive){
			if (keyValuePairs.length %2 != 0){
				throw new IndexOutOfBoundsException();
			}
			
			for (int i = 0; i < keyValuePairs.length; i+= 2){
				put(keyValuePairs[i], keyValuePairs[i+1], caseInsensitive);
			}
		}
		/**
		 * tries to find the exact key match before
		 * trying to find the casInsensitive match.
		 * @param key
		 * @return
		 */
		public boolean containsAnyKey(String key){
			boolean found = super.containsKey(key);
			
			if (!found){
				found = super.containsKey(key.toLowerCase());
			}
			
			return found;
			
		}
		public boolean containsKey(String key, boolean caseInsensitive){
			if (caseInsensitive){
				return super.containsKey(key.toLowerCase());
			} else {
				return super.containsKey(key);
			}
		}
		public CaseInsensitiveMap(String[] keyValuePairs, boolean caseInsensitive){
			super();
			put(keyValuePairs, caseInsensitive);
		}
		
		public CaseInsensitiveMap(){
			super();
		}
	}
}
