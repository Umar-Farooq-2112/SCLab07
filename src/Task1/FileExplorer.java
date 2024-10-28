package Task1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileExplorer {

    private static List<String> foundFiles = new ArrayList<>();
    public static List<String> getFoundFiles(){
    	return foundFiles;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the directory path: ");
        String directoryPath = scanner.nextLine();

        System.out.print("Enter all file names (comma separated): ");
        String fileNamesInput = scanner.nextLine();
        String[] filesToSearch = fileNamesInput.split(",");

        System.out.print("Should the search be case insensitive? (y/n): ");
        boolean caseInsensitive = scanner.nextLine().equalsIgnoreCase("y");
        scanner.close();

        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("No Directory Found");
            return;
        }

        for (String fileName : filesToSearch) {
            foundFiles.clear();
            searchFile(directory, fileName.trim(), caseInsensitive);
            displayResults(fileName.trim());
        }

    }

    public static void searchFile(File directory, String fileName, boolean caseInsensitive) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFile(file, fileName, caseInsensitive);
                } else {
                    if (isMatch(file.getName(), fileName, caseInsensitive)) {
                        foundFiles.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private static boolean isMatch(String actualName, String searchName, boolean caseInsensitive) {
        return caseInsensitive ? actualName.equalsIgnoreCase(searchName) : actualName.equals(searchName);
    }

    private static void displayResults(String fileName) {
        if (foundFiles.isEmpty()) {
            System.out.println("File \'" + fileName + "\' not found.");
        } else {
            System.out.println("Found " + foundFiles.size() + " instance(s) of \"" + fileName + "\":");
            for (String path : foundFiles) {
                System.out.println(path);
            }
        }
    }
}
