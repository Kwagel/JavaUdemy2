package JUnitChallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {
	private Utilities util;
	
	@BeforeEach
	public void setup() {
		util = new Utilities();
	}
	
	@Test
	void everyNthChar() {
		assertArrayEquals(new char[]{'e', 'l'}, util.everyNthChar(new char[]{'h', 'e', 'l', 'l', 'o'}, 2));
	}
	
	@Test
	void everyNthChar_NGreaterThanLength() {
		assertArrayEquals(new char[]{'h', 'e', 'l', 'l', 'o'}, util.everyNthChar(new char[]{'h', 'e', 'l', 'l', 'o'}, 6));
	}
	
	@Test
	void removePairs() {
		assertEquals("ABCDEF", util.removePairs("AABCDDEFF"));
		assertEquals("ABCABDEF", util.removePairs("ABCCABDEFF"));
		assertNull(util.removePairs(null), "Did not return null when receiving null");
		assertEquals("A", util.removePairs("A"));
		assertEquals("", util.removePairs(""));
	}
	
	@Test
	void converter() {
		assertEquals(300, util.converter(10, 5));
	}
	
	@Test
	void converter_DivideBy0() {
		assertThrows(ArithmeticException.class, () -> util.converter(10, 0)
		);
		
	}
	
	@Test
	void nullIfOddLength() {
		assertNull(util.nullIfOddLength("odd"));
		assertEquals("even", util.nullIfOddLength("even"));
	}
	
	@ParameterizedTest
	@MethodSource("testData")
	void removePairs_Many(String Input, String output) {
		assertEquals(output, util.removePairs(Input));
	}
	
	
	public static Stream<Arguments> testData() {
		return Stream.of(
				Arguments.of("ABCDEFF", "ABCDEF"),
				Arguments.of("AB88EFFG", "AB8EFG"),
				Arguments.of("112233445566", "123456"),
				Arguments.of("ZYZQQB", "ZYZQB"),
				Arguments.of("A", "A")
		);
		
	}
}