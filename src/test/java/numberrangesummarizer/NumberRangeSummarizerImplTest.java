package numberrangesummarizer;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/* Unit tests for NumberRangeSummarizerImpl. */
public class NumberRangeSummarizerImplTest {

    private final NumberRangeSummarizer summarizer = new NumberRangeSummarizerImpl();

    // ---------- collect() tests ----------

    @Test
    void collect_givenValidInputWithDuplicates_shouldReturnSortedUniqueNumbers() {
        Collection<Integer> result = summarizer.collect(" 8, 6, 7 ,8, 12,13, 15");
        assertEquals(Arrays.asList(6, 7, 8, 12, 13, 15), result,
                "Should return sorted unique numbers");
    }

    @Test
    void collect_givenBlankInput_shouldReturnEmptyList() {
        Collection<Integer> result = summarizer.collect("");
        assertTrue(result.isEmpty(), "Empty input should return empty list");
    }

    @Test
    void collect_givenNullInput_shouldReturnEmptyList() {
        Collection<Integer> result = summarizer.collect(null);
        assertTrue(result.isEmpty(), "Null input should return empty list");
    }

    @Test
    void collect_givenWhitespaceOnly_shouldReturnEmptyList() {
        Collection<Integer> result = summarizer.collect("  ,  ,  ");
        assertTrue(result.isEmpty(), 
            "Whitespace-only input should return empty list");
    }

    @Test
    void collect_givenUnorderedInput_shouldReturnNumbersInAscendingOrder() {
        Collection<Integer> result = summarizer.collect("8,3,6,7,1");
        assertEquals(Arrays.asList(1, 3, 6, 7, 8), result,
                "Should sort numbers ascending");
    }

    @Test
    void collect_givenInvalidNumber_shouldThrowNumberFormatException() {
        assertThrows(NumberFormatException.class, 
            () -> summarizer.collect("1,2,abc,4"),
            "Should throw exception for non-numeric input");
    }

    // ---------- summarizeCollection() tests ----------
    @Test
    void summarizeCollection_givenMixedRanges_shouldGroupSequentialNumbers() {
        Collection<Integer> input = Arrays.asList(1, 3, 6, 7, 8, 12, 13, 14, 15, 21, 22, 23, 24, 31);
        String result = summarizer.summarizeCollection(input);
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result,
                "Should group consecutive numbers into ranges");
    }

    @Test
    void summarizeCollection_givenFullySequentialNumbers_shouldReturnSingleRange() {
        Collection<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        String result = summarizer.summarizeCollection(input);
        assertEquals("1-5", result,
                "Should return one range for consecutive numbers");
    }

    @Test
    void summarizeCollection_givenSingleNumber_shouldReturnThatNumber() {
        Collection<Integer> input = Collections.singletonList(5);
        String result = summarizer.summarizeCollection(input);
        assertEquals("5", result,
                "Should return the same number");
    }

    @Test
    void summarizeCollection_givenNonSequentialNumbers_shouldListIndividually() {
        Collection<Integer> input = Arrays.asList(1, 5, 9);
        String result = summarizer.summarizeCollection(input);
        assertEquals("1, 5, 9", result,
                "Should list each number separately");
    }

    @Test
    void summarizeCollection_givenEmptyInput_shouldReturnEmptyString() {
        String result = summarizer.summarizeCollection(Collections.emptyList());
        assertEquals("", result, "Should return empty string for empty list");
    }

    @Test
    void summarizeCollection_givenNullInput_shouldReturnEmptyString() {
        String result = summarizer.summarizeCollection(null);
        assertEquals("", result, "Should return empty string for null input");
    }

    @Test
    void summarizeCollection_givenNegativeNumbers_shouldHandleCorrectly() {
        Collection<Integer> input = Arrays.asList(-5, -4, -3, 1, 2, 3);
        String result = summarizer.summarizeCollection(input);
        assertEquals("-5--3, 1-3", result,
            "Should handle negative number ranges correctly");
    }

    @Test
    void endToEnd_givenSampleInput_shouldProduceSampleOutput() {
        String input = "1,3,6,7,8,12,13,14,15,21,22,23,24,31";
        Collection<Integer> collected = summarizer.collect(input);
        String result = summarizer.summarizeCollection(collected);
        assertEquals("1, 3, 6-8, 12-15, 21-24, 31", result,
            "Should produce expected output from sample input");
    }
}
