package Task2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class StringPermutations {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string to generate its permutations: ");
        String inputString = scanner.nextLine();

        if (inputString.isEmpty()) {
            System.out.println("Input string cannot be empty.");
            scanner.close();
            return;
        }

        System.out.print("Include duplicate permutations? (y/n): ");
        boolean includeDuplicates = scanner.nextLine().equalsIgnoreCase("y");

        long startTime = System.nanoTime();
        List<String> recursivePermutations;
        if (includeDuplicates) {
            recursivePermutations = generatePermutations(inputString);
        } else {
            recursivePermutations = generateUniquePermutations(inputString);
        }
        long endTime = System.nanoTime();
        System.out.println("Recursive Approach Time: " + (endTime - startTime) + " ns");
        System.out.println("Total Permutations (Recursive): " + recursivePermutations.size());
        System.out.println("Permutations (Recursive): " + recursivePermutations);

        startTime = System.nanoTime();
        List<String> nonRecursivePermutations = generateNonRecursivePermutations(inputString);
        endTime = System.nanoTime();
        System.out.println("Non-Recursive Approach Time: " + (endTime - startTime) + " ns");
        System.out.println("Total Permutations (Non-Recursive): " + nonRecursivePermutations.size());
        System.out.println("Permutations (Non-Recursive): " + nonRecursivePermutations);

        scanner.close();
    }

    private static List<String> generatePermutations(String str) {
        List<String> permutations = new ArrayList<>();
        generatePermutationsHelper("", str, permutations);
        return permutations;
    }

    private static void generatePermutationsHelper(String prefix, String remaining, List<String> permutations) {
        int n = remaining.length();
        if (n == 0) {
            permutations.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                generatePermutationsHelper(prefix + remaining.charAt(i),
                                            remaining.substring(0, i) + remaining.substring(i + 1),
                                            permutations);
            }
        }
    }

    private static List<String> generateUniquePermutations(String str) {
        Set<String> uniquePermutations = new HashSet<>();
        generateUniquePermutationsHelper("", str, uniquePermutations);
        return new ArrayList<>(uniquePermutations);
    }

    private static void generateUniquePermutationsHelper(String prefix, String remaining, Set<String> uniquePermutations) {
        int n = remaining.length();
        if (n == 0) {
            uniquePermutations.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                if (i > 0 && remaining.charAt(i) == remaining.charAt(i - 1)) {
                    continue;
                }
                generateUniquePermutationsHelper(prefix + remaining.charAt(i),
                                                  remaining.substring(0, i) + remaining.substring(i + 1),
                                                  uniquePermutations);
            }
        }
    }

    private static List<String> generateNonRecursivePermutations(String str) {
        List<String> permutations = new ArrayList<>();
        int n = str.length();
        int[] indices = new int[n];
        StringBuilder current = new StringBuilder(str);

        permutations.add(current.toString());

        for (int i = 0; i < n; ) {
            if (indices[i] < i) {
                int swapIndex = (i % 2 == 0) ? 0 : indices[i];
                char temp = current.charAt(swapIndex);
                current.setCharAt(swapIndex, current.charAt(i));
                current.setCharAt(i, temp);

                permutations.add(current.toString());

                indices[i]++;
                i = 0; 
            } else {
                indices[i] = 0;
                i++; 
            }
        }

        return permutations;
    }
}
