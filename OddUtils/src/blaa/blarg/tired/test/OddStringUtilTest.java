package blaa.blarg.tired.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import blaa.blarg.tired.OddStringUtil;

public class OddStringUtilTest {

	@Test
	public void AcronymTests() {
		String phrase = "Big F'ing Gun 9000";
		String expectedAcronymFalse = "BFG9000";
		String expectedAcronymTrue = "B.F.G.9000";
		
		
		assertEquals(expectedAcronymFalse, OddStringUtil.getAcronym(phrase, false));
		
		
		assertEquals(expectedAcronymTrue, OddStringUtil.getAcronym(phrase, true));
		assertEquals(OddStringUtil.getAcronym(phrase, true), OddStringUtil.getAcronym(phrase));
		
		
	}

	
	@Test
	public void AncronymToSkipTests(){
		String phrase = "and the cow jumped over an asteroid";
		String expectedAncronymFalse = "ACJOA";
		String expectedAncronymTrue = "A.C.J.O.A.";
		

		
		assertEquals(expectedAncronymFalse, OddStringUtil.getAcronym(phrase, false));
		
		assertEquals(expectedAncronymTrue, OddStringUtil.getAcronym(phrase));
		
		assertEquals(expectedAncronymTrue, OddStringUtil.getAcronym(phrase, true));
	}
	
	@Test
	public void breakUpWordsTest(){
		String phrase = "The cow jumped over the moon. We don't know if, or when, it will return.";
		String [] expected = {"The", "cow", "jumped", "over", "the", "moon.", "We", "don't", "know", 
			"if,", "or", "when,", "it", "will", "return."};
		String[] breakUpWords = OddStringUtil.breakUpWords(phrase);
		
		assertArrayEquals(expected, breakUpWords);
	}
	
	@Test
	public void isEmptyTest(){
		String isNull = null;
		
		String blank = "";
		
		String multiLineBlank = "   ";
		
		String almostBlank = "    a";
		
		assertTrue(OddStringUtil.isEmpty(blank));
		
		assertTrue(OddStringUtil.isEmpty(multiLineBlank));
		
		assertTrue(OddStringUtil.isEmpty(isNull));
		
		assertFalse(OddStringUtil.isEmpty(almostBlank));
	}
	@Test
	public void L337SpecialWordTest(){
		assertEquals("1337", OddStringUtil.get1337("elite"));
		
		assertEquals("teh", OddStringUtil.get1337("the"));
	}
	
	@Test
	public void L337PhraseTest(){
		String phrase = "The cow jumped over the moon.";
		
		String expectedPhrase = "Teh c0w jump3d 0v3r teh m00n.";
		assertEquals(expectedPhrase, OddStringUtil.get1337(phrase));
		
		
	}
	
	@Test
	public void L337ALLCAPSTEST(){
		//Internet loves all caps
		String phrase = "THE COW JUMPED OVER THE MOON!!";
		String expectedReturn = "TEH C0W JUMP3D 0V3R TEH M00N!!";
		
		assertEquals(expectedReturn, OddStringUtil.get1337(phrase));
	}
	
	@Test
	public void l337MultiLineTest(){
		String phrase = "I respect your opinion.\nlol just kidding.";
		String expectedReturn = "I r3sp3ct y0ur 0pini0n.\nlol just kidding.";
		assertEquals(expectedReturn,OddStringUtil.get1337(phrase));
	}

}
