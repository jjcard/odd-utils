package blaa.blarg.tired.test;

import static org.junit.Assert.*;

import org.junit.Test;

import blaa.blarg.tired.CategoricalStatementUtil.CategoricalTypes;

public class CategoricalStatementUtilTest {
	private static final String testPhrase = "All test are tests that pass.";
	private static final  String notPhrase = "No test are tests that pass.";
	private static final  String somePhrase = "Some tests are tests that pass.";
	private static final  String someNotPhrase = "Some tests are not tests that pass.";
	@Test
	public void aTest() {
		
		assertTrue(CategoricalTypes.A.matches(testPhrase));
		assertFalse(CategoricalTypes.A.matches(notPhrase));
		assertFalse(CategoricalTypes.A.matches(somePhrase));
		assertFalse(CategoricalTypes.A.matches(someNotPhrase));
	}
	@Test
	public void eTest() {
		assertFalse(CategoricalTypes.E.matches(testPhrase));
		assertTrue(CategoricalTypes.E.matches(notPhrase));
		assertFalse(CategoricalTypes.E.matches(somePhrase));
		assertFalse(CategoricalTypes.E.matches(someNotPhrase));
	}
	
	@Test
	public void iTest() {
		assertFalse(CategoricalTypes.I.matches(testPhrase));
		assertFalse(CategoricalTypes.I.matches(notPhrase));
		assertTrue(CategoricalTypes.I.matches(somePhrase));
		assertFalse(CategoricalTypes.I.matches(someNotPhrase));
	}
	@Test
	public void oTest() {
		assertFalse(CategoricalTypes.O.matches(testPhrase));
		assertFalse(CategoricalTypes.O.matches(notPhrase));
		assertFalse(CategoricalTypes.O.matches(somePhrase));
		assertTrue(CategoricalTypes.O.matches(someNotPhrase));
	}

}
