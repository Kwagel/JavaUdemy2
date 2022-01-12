package JUnitChallenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {
	
	@Test
	void everyNthChar() {
		Utilities util = new Utilities();
		assertArrayEquals(new char[]{'e', 'l'}, util.everyNthChar(new char[]{'h','e','l','l','o'}, 2) );
	}
	
	@Test
	void everyNthChar_NGreaterThanLength() {
		Utilities util = new Utilities();
		assertArrayEquals(new char[]{'h','e','l','l','o'}, util.everyNthChar(new char[]{'h','e','l','l','o'}, 6) );
	}
	@Test
	void removePairs() {
		Utilities util = new Utilities();
		assertEquals("ABCDEF", util.removePairs("AABCDDEFF"));
		assertEquals("ABCABDEF", util.removePairs("ABCCABDEFF"));
		assertNull( util.removePairs(null),"Did not return null when receiving null");
		assertEquals("A",util.removePairs("A"));
		assertEquals("",util.removePairs(""));
	}
	
	@Test
	void converter() {
		Utilities util = new Utilities();
		assertEquals(300, util.converter(10,5) );
	}
	
	@Test
	void nullIfOddLength() {
		Utilities util = new Utilities();
		assertNull(util.nullIfOddLength("odd"));
		assertEquals("even",util.nullIfOddLength("even"));
	}
}