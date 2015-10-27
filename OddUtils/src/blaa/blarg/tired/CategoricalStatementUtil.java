package blaa.blarg.tired;

import java.util.regex.Pattern;

public final class CategoricalStatementUtil {
	private static final String A_PATTERN = "([Aa][Ll][Ll] ).*( [Aa][Rr][Ee] ).*";
	private static final String E_Pattern = "([Nn][Oo] ).*( [Aa][Rr][Ee] ).*";
	private static final String I_PATTERN = "([Ss][Oo][Mm][Ee] ).*( [Aa][Rr][Ee] )([^Nn][^Oo][^Tt]).*";
	private static final String O_PATTERN = "([Ss][Oo][Mm][Ee] ).*( [Aa][Rr][Ee] [Nn][Oo][Tt]).*";
	public static enum CategoricalType{
		A(A_PATTERN),
		E(E_Pattern),
		I(I_PATTERN),
		O(O_PATTERN);
		private final String regex;
		private final Pattern pattern;
		CategoricalType(String regex){
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
		public static CategoricalType getTypeThatMatches(String statement){
			CategoricalType re = null;
			if (statement != null){
				for (CategoricalType type: values()){
					if (type.matches(statement)){
						re = type;
						break;
					}
				}				
			}
			return re;
		}
	}
	public static class Statement{
		private CategoricalType type;
		private String subject;
		private String predicate;
		private String statement;
		
		public Statement(CategoricalType t, String statement, String subject, String predicate){
			this.type = t;
			this.subject = subject;
			this.predicate = predicate;
			this.statement = statement;
		}
		public CategoricalType getType(){
			return type;
		}
		public String getSubject(){
			return subject;
		}
		public String getPredicate(){
			return predicate;
		}
		public String getStatementText(){
			return statement;
		}
	}
	
	private CategoricalStatementUtil(){
		//private, all util methods
	}
	public static CategoricalType getType(String statement){
		return CategoricalType.getTypeThatMatches(statement);
	}
	/**
	 * Compiles given String into a Statement. Given String must be in standard form.
	 * Only uses basic parsing to get subject and predicate, so first use of 'are' is treated as the switch between terms.
	 * 
	 * 
	 * @param statement
	 * @return Statement or null if not valid statement
	 */
	public static Statement compileStatement(String statement){
		CategoricalType type = getType(statement);
		if (type == null){
			return null;
		}
//		Statement re = null;
		String indexStatement = statement.toUpperCase();
		String subject = null;
		String predicate = null;
		switch (type) {
			case A:
				int areIndex = indexStatement.indexOf(" ARE ");
				subject = statement.substring(4, areIndex);
				predicate = statement.substring(areIndex + 5);
				break;
			case E:
				areIndex = indexStatement.indexOf(" ARE ");
				subject = statement.substring(3, areIndex);
				predicate = statement.substring(areIndex + 5);
				break;
			case I:
				areIndex = indexStatement.indexOf(" ARE ");
				subject = statement.substring(5, areIndex);
				predicate = statement.substring(areIndex + 5);
				break;
			case O:
				areIndex = indexStatement.indexOf(" ARE NOT ");
				subject = statement.substring(5, areIndex);
				predicate = statement.substring(areIndex + 9);
				break;
		}		
		
		return new Statement(type, statement, subject, predicate);
	}

}
