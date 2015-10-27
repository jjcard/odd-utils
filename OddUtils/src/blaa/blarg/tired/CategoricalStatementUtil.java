package blaa.blarg.tired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public final class CategoricalStatementUtil {
	private static final String A_PATTERN = "([Aa][Ll][Ll] ).*( [Aa][Rr][Ee] ).*";
	private static final String E_Pattern = "([Nn][Oo] ).*( [Aa][Rr][Ee] ).*";
	private static final String I_PATTERN = "([Ss][Oo][Mm][Ee] ).*( [Aa][Rr][Ee] )([^Nn][^Oo][^Tt]).*";
	private static final String O_PATTERN = "([Ss][Oo][Mm][Ee] ).*( [Aa][Rr][Ee] [Nn][Oo][Tt]).*";
	public static enum CategoricalType{
		A(A_PATTERN, false, true, false),
		E(E_Pattern, true, true, true),
		I(I_PATTERN, false, false, false),
		O(O_PATTERN, true, false, true);
		private final String regex;
		private final Pattern pattern;
		private final boolean isNegative;
		private final boolean isSubjectDistributed;
		private final boolean isPredicateDistributed;
		CategoricalType(String regex, boolean isNegative, boolean isSubjectDistributed, boolean isPredicateDistributed){
			this.regex = regex;
			this.pattern = Pattern.compile(regex);
			this.isNegative = isNegative;
			this.isPredicateDistributed = isPredicateDistributed;
			this.isSubjectDistributed = isSubjectDistributed;
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
		public boolean isNegative(){
			return isNegative;
		}
		public boolean isPositive(){
			return !isNegative;
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

		public boolean isSubjectDistributed() {
			return isSubjectDistributed;
		}

		public boolean isPredicateDistributed() {
			return isPredicateDistributed;
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
		public boolean isSubjectDistributed() {
			return getType().isSubjectDistributed();
		}

		public boolean isPredicateDistributed() {
			return getType().isPredicateDistributed();
		}
	}
	public static class Syllogism{
		private Statement major;
		private Statement minor;
		private Statement conclusion;
		private String majorTerm;
		private String minorTerm;
		private String middleTerm;
		
		public Syllogism(Statement major, Statement minor, Statement conclusion, String majorTerm, String minorTerm, String middleTerm){
			this.major = major;
			this.minor = minor;
			this.conclusion = conclusion;
			this.majorTerm = majorTerm;
			this.minorTerm = minorTerm;
			this.middleTerm = middleTerm;
		}
		public Statement getMajor(){
			return major;
		}
		public Statement getMinor(){
			return minor;
		}
		public Statement getConclusion(){
			return conclusion;
		}
		public String getMajorTerm(){
			return majorTerm;
		}
		public String getMinorTerm(){
			return minorTerm;
		}
		public String getMiddleTerm(){
			return middleTerm;
		}
	}
	public static class SyllogismParseException extends Exception{
		private static final long serialVersionUID = -5143237608118286565L;
		public SyllogismParseException(){
			super();
		}
		public SyllogismParseException(Throwable t){
			super(t);
		}
		public SyllogismParseException(String message){
			super(message);
		}
		public SyllogismParseException(String message, Throwable t){
			super(message, t);
		}
	}
	private static final Set<Character> endPuncuation = new HashSet<>(Arrays.asList('.', '!'));
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
		if (endPuncuation.contains(predicate.charAt(predicate.length()-1))){
			predicate = predicate.substring(0, predicate.length()-1);
		}
		
		return new Statement(type, statement, subject, predicate);
	}
	public static Syllogism compileSyllogism(String statementMajor, String statementMinor, String statementConclusion) throws SyllogismParseException{
		Statement major = compileStatement(statementMajor);
		Statement minor = compileStatement(statementMinor);
		Statement conclusion;
		if (statementConclusion.toUpperCase().startsWith("THEREFORE ")){
			 conclusion = compileStatement(statementConclusion.substring(10));
		} else {
			 conclusion = compileStatement(statementConclusion);
		}
		
		if (major == null|| minor == null || conclusion == null){
			String message = "";
			if (major == null){
				message += "major";
			}
			if (minor == null){
				message += message.isEmpty()? "minor": ", minor";
			}
			if (conclusion == null){
				message += message.isEmpty()? "conclusion": ", conclusion";
			}
			throw new SyllogismParseException("The following statements have invalid syntax: " + message);
		}
		String majorTerm = conclusion.getPredicate();
		String minorTerm = conclusion.getSubject();
		String middleTerm = null;
		if (!major.getPredicate().equalsIgnoreCase(majorTerm) && !major.getPredicate().equalsIgnoreCase(minorTerm)){
			middleTerm = major.getPredicate();
		} else {
			if (major.getSubject().equalsIgnoreCase(majorTerm) || major.getSubject().equalsIgnoreCase(minorTerm)){
				throw new SyllogismParseException("No Middle Term found");
			}
			middleTerm = major.getSubject();
		}
		
		return new Syllogism(major, minor, conclusion, majorTerm, minorTerm, middleTerm);
	}
	/**
	 * Tests the Syllogism using the Rules Method
	 * @param syllogism
	 */
	public static void testSyllogism(Syllogism syllogism){
		int numNegativeClaims = 0;
		if (syllogism.getMajor().getType().isNegative()){
			numNegativeClaims++;
		}
		if (syllogism.getMinor().getType().isNegative()){
			numNegativeClaims++;
		}
		if (syllogism.getConclusion().getType().isNegative()){
			assertSyllogism("number of negative claims in premises does not match conclusion", numNegativeClaims==1);
		} else {
			//assert numNegativeClaims==0
			assertSyllogism("number of negative claims in premises does not match conclusion", numNegativeClaims==0);
		}
		
		String middleTerm = syllogism.getMiddleTerm();
		boolean isMiddleTermDistributed = false;
		if (middleTerm.equalsIgnoreCase(syllogism.getMajor().getPredicate())){
			isMiddleTermDistributed = syllogism.getMajor().isPredicateDistributed();
		} else{
			isMiddleTermDistributed = syllogism.getMajor().isSubjectDistributed();
		}
		//only have to check if not already found
		if (!isMiddleTermDistributed){
			if (middleTerm.equalsIgnoreCase(syllogism.getMinor().getPredicate())){
				isMiddleTermDistributed = syllogism.getMinor().isPredicateDistributed();
			} else{
				isMiddleTermDistributed = syllogism.getMinor().isSubjectDistributed();
			}
		}
		assertSyllogism("Middle term must be distributed in at least one term", isMiddleTermDistributed);
	}
	private static void assertSyllogism(String message, boolean b){
		if (!b){
			System.out.println("Assertion failed!");
			System.err.println(message);
		}
		
	}

}
