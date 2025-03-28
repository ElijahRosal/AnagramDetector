/**************************************************************/
/* Your Name Elijah Rosal */
/* Student ID: 017203992 */
/* CS 3310, Spring 2025 */
/* Programming Assignment 2 */
/* AnagramFinder: Identifies sets of anagrams from a given file */
/**************************************************************/
import java.io.*;
import java.text.Normalizer;
import java.util.*;

/**
 * Class: Prog2
 * Purpose: Reads words from a file and finds sets of anagrams.
 */
public class Prog2 {
    /**
     * Method: main
     * Purpose: Reads a file, processes words into anagram groups, and prints them.
     * Parameters:
     *   String[] args - command line arguments; expects a filename as input.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Prog2 <filename>");
            return;
        }

        String filename = args[0];
        Map<String, List<String>> anagramGroups = new HashMap<>();
        Set<String> uniqueWords = new HashSet<>(); // Keep track of unique words

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String word;
            while ((word = br.readLine()) != null) {
                word = word.trim().toLowerCase();

                // Normalize accents and remove diacritics
                word = normalizeString(word);

                // Remove apostrophes
                word = word.replace("'", "");

                // If the word is already processed, skip it
                if (!uniqueWords.add(word)) {
                    continue;
                }

                String sortedWord = sortString(word);
                anagramGroups.computeIfAbsent(sortedWord, k -> new ArrayList<>()).add(word);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        int anagramSetCount = 0;

        // Print the anagram groups and count them
        for (List<String> group : anagramGroups.values()) {
            if (group.size() > 1) { // Only count actual anagram sets
                System.out.println(group);
                anagramSetCount++;
            }
        }

        System.out.println("Total anagram sets found: " + anagramSetCount);
    }

    /**
     * Method: sortString
     * Purpose: Sorts the characters of a given string in alphabetical order.
     * Parameters:
     *   String s - the string to be sorted.
     * Returns:
     *   String - the sorted version of the input string.
     */
    private static String sortString(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    /**
     * Method: normalizeString
     * Purpose: Converts accented characters to their base form.
     * Parameters:
     *   String s - the string to normalize.
     * Returns:
     *   String - the normalized string without accents.
     */
    private static String normalizeString(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                         .replaceAll("\\p{M}", ""); // Remove diacritics
    }
}
