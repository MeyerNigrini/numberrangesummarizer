package numberrangesummarizer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of NumberRangeSummarizer.
 * Parses a comma-delimited string and outputs a summarized range string.
 */
public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    /**
     * Converts a comma-separated string into a sorted, unique list of integers.
     * Returns an empty list for null or blank input.
     */
    @Override
    public Collection<Integer> collect(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(input.split(","))
                .map(String::trim)                    
                .filter(s -> !s.isEmpty())            
                .map(Integer::parseInt)               
                .distinct() // Remove duplicates
                .sorted()                             
                .collect(Collectors.toList());        
    }

    /** Builds a summarized range string from a sorted collection of integers. */
    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        List<Integer> numbers = new ArrayList<>(input);
        StringBuilder summary = new StringBuilder();

        int start = numbers.get(0);
        int previous = start;        

        for (int i = 1; i < numbers.size(); i++) {
            int current = numbers.get(i);

            if (current != previous + 1) {
                appendRange(summary, start, previous);
                start = current;
            }

            previous = current;
        }

        appendRange(summary, start, previous);
        return summary.toString();
    }

    /** Appends either a single number or a range (e.g. "6-8") to the output string. */
    private void appendRange(StringBuilder builder, int start, int end) {
        if (builder.length() > 0) {
            builder.append(", ");
        }

        if (start == end) {
            builder.append(start);
        } else {
            builder.append(start).append("-").append(end);
        }
    }
}
