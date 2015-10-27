package blaa.blarg.tired.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import blaa.blarg.tired.CategoricalStatementUtil;
import blaa.blarg.tired.CategoricalStatementUtil.CategoricalType;
import blaa.blarg.tired.CategoricalStatementUtil.Statement;
import blaa.blarg.tired.CategoricalStatementUtil.Syllogism;

public class CategoricalStatementUtilTest {
	private static final String testPhrase = "All tests are tests that pass.";
	private static final  String notPhrase = "No tests are tests that pass.";
	private static final  String somePhrase = "Some tests are tests that pass.";
	private static final  String someNotPhrase = "Some tests are not tests that pass.";
	
	private static final String s1 = "All tests are tests that pass.";
	private static final String s2 = "All tests that pass are tests that are easy to write.";
	private static final String s3 = "All tests are tests that are easy to write.";
	@Test
	public void aTest() {
		
		assertTrue(CategoricalType.A.matches(testPhrase));
		assertFalse(CategoricalType.A.matches(notPhrase));
		assertFalse(CategoricalType.A.matches(somePhrase));
		assertFalse(CategoricalType.A.matches(someNotPhrase));
	}
	@Test
	public void eTest() {
		assertFalse(CategoricalType.E.matches(testPhrase));
		assertTrue(CategoricalType.E.matches(notPhrase));
		assertFalse(CategoricalType.E.matches(somePhrase));
		assertFalse(CategoricalType.E.matches(someNotPhrase));
	}
	
	@Test
	public void iTest() {
		assertFalse(CategoricalType.I.matches(testPhrase));
		assertFalse(CategoricalType.I.matches(notPhrase));
		assertTrue(CategoricalType.I.matches(somePhrase));
		assertFalse(CategoricalType.I.matches(someNotPhrase));
	}
	@Test
	public void oTest() {
		assertFalse(CategoricalType.O.matches(testPhrase));
		assertFalse(CategoricalType.O.matches(notPhrase));
		assertFalse(CategoricalType.O.matches(somePhrase));
		assertTrue(CategoricalType.O.matches(someNotPhrase));
	}
	@Test
	public void aCompileTest(){
		Statement result = CategoricalStatementUtil.compileStatement(testPhrase);
		assertNotNull(result);
		assertEquals(CategoricalType.A, result.getType());
		assertEquals("tests", result.getSubject());
		assertEquals("tests that pass", result.getPredicate());
	}
	@Test
	public void eCompileTest(){
		Statement result = CategoricalStatementUtil.compileStatement(notPhrase);
		assertNotNull(result);
		assertEquals(CategoricalType.E, result.getType());
		assertEquals("tests", result.getSubject());
		assertEquals("tests that pass", result.getPredicate());
	}
	@Test
	public void iCompileTest(){
		Statement result = CategoricalStatementUtil.compileStatement(somePhrase);
		assertNotNull(result);
		assertEquals(CategoricalType.I, result.getType());
		assertEquals("tests", result.getSubject());
		assertEquals("tests that pass", result.getPredicate());
	}
	@Test
	public void oCompileTest(){
		Statement result = CategoricalStatementUtil.compileStatement(someNotPhrase);
		assertNotNull(result);
		assertEquals(CategoricalType.O, result.getType());
		assertEquals("tests", result.getSubject());
		assertEquals("tests that pass", result.getPredicate());
	}
	@Test
	public void SyllogismTest(){
		Syllogism s = CategoricalStatementUtil.compileSyllogism(s1, s2, s3);
		
		assertNotNull(s);
		assertEquals("tests that are easy to write", s.getMajorTerm());
		assertEquals("tests", s.getMinorTerm());
		assertEquals("tests that pass", s.getMiddleTerm());
		
	}

}
