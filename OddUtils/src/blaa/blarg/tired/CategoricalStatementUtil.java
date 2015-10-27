package blaa.blarg.tired;

import java.util.regex.Pattern;

public final class CategoricalStatementUtil {
	private static final String A_PATTERN = "([Aa][Ll][Ll] ).*( [Aa][Rr][Ee] ).*";
	private static final String E_Pattern = "([Nn][Oo] ).*( [Aa][Rr][Ee] ).*";
	private static final String I_PATTERN = "([Ss][Oo][Mm][Ee] ).*( [Aa][Rr][Ee] )([^Nn][^Oo][^Tt]).*";
	private static final String O_PATTERN = "([Ss][Oo][Mm][Ee] ).*( [Aa][Rr][Ee] [Nn][Oo][Tt]).*";
	public static enum CategoricalTypes{

		A(A_PATTERN),
		E(E_Pattern),
		I(I_PATTERN),
		O(O_PATTERN);
		private final String regex;
		private final Pattern pattern;
		CategoricalTypes(String regex){
			this.regex = regex;
			this.pattern = Pattern.compile(regex);
		}
		
		public boolean matches(String statement){
			return pattern.matcher(statement).matches();
		}
		public String getRegex(){
			return regex;
		}
		public Pattern getPattern(){
			return pattern;
		}
	}

}
