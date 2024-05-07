// -----------------------------------------------------
// Assignment 3 - COMP 249
// Written by: Diego Samanez Denis 40286385
// Due date : April 15, 2024
// -----------------------------------------------------
// -----------------------------------------------------
//This program enables users to manage topics and their corresponding vocabulary. 
//It facilitates the storage of topics alongside their associated words. Additionally, 
//users can read data from a file, adhering to a specific structure (using '#' to denote topic names), 
//to populate the program with topics and vocabulary. Furthermore, the program offers functionality to 
//create and write topics and their vocabulary to a file. Users can interactively add, modify, and delete 
//topics and vocabulary entries within the program's interface.
// -----------------------------------------------------
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class represents a driver program for managing vocabulary topics and their associated words.
 * Users can browse, insert, remove, modify, search, load from file, and save to file topics and words.
 */
public class Driver {
    // Scanner object for user input
    static Scanner key = new Scanner(System.in);
    // Doubly linked list to store topics
    static DoublyLinkedList topics = new DoublyLinkedList();

    /**
     * The main method runs a loop to display a menu of options and execute user-selected actions.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        String choice = "";
        System.out.println("Welcome to the Vocabulary Control Center!");
        do {
            // Display main menu
            System.out.print("-----------------------------\n  Vocabulary Control Center\n"
                    + "-----------------------------\n"
                    + "1  Browse a topic\n"
                    + "2  Insert a topic before another one\n"
                    + "3  Insert a topic after another one\n"
                    + "4  Remove a topic\n"
                    + "5  Modify a topic\n"
                    + "6  Search topics for a word\n"
                    + "7  Load from a file\n"
                    + "8  Show all words starting by a given letter\n"
                    + "9  Save to file\n"
                    + "0  Exit\n"
                    + "-----------------------------\nEnter Your Choice: ");

            choice = key.next();
            key.nextLine(); // Clear the buffer

            // Perform action based on user choice
            switch (choice) {
                case "1":
                    browseTopics();
                    break;
                case "2":
                    insertTopicBefore();
                    break;
                case "3":
                    insertTopicAfter();
                    break;
                case "4":
                    removeTopic();
                    break;
                case "5":
                    modifyTopic();
                    break;
                case "6":
                    searchTopicsForWord();
                    break;
                case "7":
                    loadFromFile();
                    break;
                case "8":
                    showAllWordWithSameBeginning();
                    break;
                case "9":
                    saveInFile();
                    break;
                case "0":
                	System.out.println("Thank you for using Vocabulary Control Center. Goodbye!");
                    key.close();
                    break;
                default:
                    System.out.println("Please select a valid option.");
            }

        } while (!choice.equals("0"));
    }

    /**
     * Allows the user to choose a topic and display all words associated with that topic.
     */
    static void browseTopics() {
        // Check if topics exist
        if (topics.getSize() == 0) {
            System.out.println("There are no topics yet.");
        } else {
            int answer = chooseTopic();
            // Display words associated with the selected topic
            while (answer != 0) {
                Vocab target = topics.getTheTopic(answer);
                System.out.println("Topic: " + target.getName());
                target.getWords().showWords();
                System.out.println();
                answer = chooseTopic();
            }
        }
    }

    /**
     * Allows the user to insert a new topic before another existing topic.
     */
    static void insertTopicBefore() {
        // Check if topics exist
        if (topics.getSize() == 0) {
            System.out.println("There are no topics yet.");
        } else {
            int answer = chooseTopic();
            // Insert new topic before the selected topic
            if (answer != 0) {
                System.out.println("Enter the topic name: ");
                Vocab newTopic = new Vocab(key.nextLine());
                if (answer == 1) {
                    topics.addAtHead(newTopic); // Insert at head
                } else {
                    topics.addAfter(answer - 1, newTopic); // Insert before the selected topic
                }
                // Add words to the new topic
                System.out.println("Enter a word (press Enter to quit): ");
                boolean words = true;
                do {
                    String word = key.nextLine();
                    if (word.equals("-")) {
                        words = false;
                    } else if (!word.trim().equals("")) {
                        newTopic.addWord(word); // Add word to the topic
                    }
                } while (words);
            }
        }
    }

    /**
     * Allows the user to insert a new topic after another existing topic.
     */
    static void insertTopicAfter() {
        // Check if topics exist
        if (topics.getSize() == 0) {
            System.out.println("There are no topics yet.");
        } else {
            int answer = chooseTopic();
            // Insert new topic after the selected topic
            if (answer != 0) {
                System.out.println("Enter the topic name: ");
                Vocab newTopic = new Vocab(key.nextLine());
                topics.addAfter(answer, newTopic); // Insert after the selected topic
                // Add words to the new topic
                System.out.println("Enter a word (press Enter to quit): ");
                boolean words = true;
                do {
                    String word = key.nextLine();
                    if (word.equals("-")) {
                        words = false;
                    } else {
                        newTopic.addWord(word); // Add word to the topic
                    }
                } while (words);
            }
        }
    }

    /**
     * Allows the user to remove a topic.
     */
    static void removeTopic() {
        // Check if topics exist
        if (topics.getSize() == 0) {
            System.out.println("There are no topics yet.");
        } else {
            int answer = chooseTopic();
            // Remove the selected topic
            if (answer != 0) {
                if (topics.getSize() == 1 && answer == 1) {
                    topics = new DoublyLinkedList(); // Reset the topics list if there's only one topic
                } else if (answer == 1) {
                    topics.removeHead(); // Remove the head topic
                } else if (answer == topics.getSize()) {
                    topics.removeTail(); // Remove the tail topic
                } else {
                    topics.removeTopic(answer); // Remove the topic at the specified position
                }
            }
        }
    }

    /**
     * Allows the user to modify a topic by adding, removing, or changing words.
     */
    static void modifyTopic() {
        // Check if topics exist
        if (topics.getSize() == 0) {
            System.out.println("There are no topics yet.");
        } else {
            int answer = chooseTopic();
            Vocab topicToChange = topics.getTheTopic(answer);
            // Prompt user for modification type
            System.out.print("-----------------------------\n\tModify Topics Menu\n-----------------------------\n"
                    + "a Add a word\n"
                    + "r Remove a word\n"
                    + "c Change a word\n"
                    + "0 Exit\n"
                    + "-----------------------------\nEnter Your Choice: ");
            String choice = key.nextLine();
            // Perform modification based on user choice
            while (!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("r")
                    && !choice.equalsIgnoreCase("c") && !choice.equalsIgnoreCase("0")) {
                System.out.print("Please enter a valid choice: ");
                choice = key.nextLine();
            }
            if (choice.equalsIgnoreCase("a")) {
                // Add word to the topic
                System.out.println("Enter a word (press Enter to quit): ");
                String addition = key.nextLine();
                if (!addition.trim().equals("")) {
                    topicToChange.canAddWord(addition); // Check and add word to the topic
                }
            } else if (choice.equalsIgnoreCase("r")) {
                // Remove word from the topic
                if (topicToChange.getWords().getSize() == 0) {
                    System.out.println("There are no words to delete yet.");
                } else {
                    System.out.println("Enter a word to remove:");
                    String remove = key.nextLine();
                    if (!remove.trim().equals("")) {
                        topicToChange.canDeleteWord(remove); // Check and remove word from the topic
                    } else {
                        System.out.println("No word entered.");
                    }
                }
            } else if (choice.equalsIgnoreCase("c")) {
                // Change word in the topic
                if (topicToChange.getWords().getSize() == 0) {
                    System.out.println("There are no words to change yet.");
                } else {
                    System.out.println("Enter the word to change:");
                    String change = key.nextLine();
                    if (!change.trim().equals("")) {
                        if (topicToChange.canChange(change)) {
                            System.out.println("Enter the modified version of the word:");
                            String modified = key.nextLine();
                            if (!modified.trim().equals("")) {
                                topicToChange.changeWord(change, modified); // Change word in the topic
                            } else {
                                System.out.println("No modification entered, the word remains unchanged.");
                            }
                        } else {
                            System.out.println("Word not found.");
                        }
                    } else {
                        System.out.println("No word entered.");
                    }
                }
            }
        }
    }

    /**
     * Allows the user to search for topics containing a specific word.
     */
    static void searchTopicsForWord() {
        // Check if topics exist
        if (topics.getSize() == 0) {
            System.out.println("There are no topics yet.");
        } else {
            System.out.println("Enter the word to search for:");
            String target = key.nextLine();
            topics.searchForTopic(target); // Search for word in topics
        }
    }

    /**
     * Loads topics and words from a file into the program.
     */
    static void loadFromFile() {
        System.out.println("Enter the name of the input file:");
        String fileName = key.next();
        key.nextLine();
        try (Scanner fileSelector = new Scanner(new FileReader(fileName))) {
            Vocab currentVocab = new Vocab();
            while (fileSelector.hasNextLine()) {
                String info = fileSelector.nextLine();
                if (!info.equals("")) {
                    if (info.charAt(0) == '#') {
                        currentVocab = new Vocab(info.replaceAll("#", ""));
                        topics.addAtTail(currentVocab); // Add new topic
                    } else {
                        currentVocab.addWord(info); // Add word to current topic
                    }
                }
            }
            System.out.println("Loading complete.");
        } catch (FileNotFoundException fnf) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Displays all words starting with a given letter from all topics.
     */
    static void showAllWordWithSameBeginning() {
        // Check if topics exist
        if (topics.getSize() == 0) {
            System.out.println("There are no topics yet.");
        } else {
            System.out.println("Enter the letter to search for:");
            String line = key.nextLine();
            while (line.trim().equals("") || line.trim().length() != 1) {
                System.out.println("Please enter just one character.");
                line = key.nextLine();
            }
            char target = line.charAt(0);
            ArrayList<String> words = topics.getAllWordsStarting(target);
            // Display words starting with the specified letter
            for (int i = 1; i < words.size(); i++) {
                if (i % 4 == 1 && i != 1) {
                    System.out.println();
                }
                System.out.printf("%-30s", i + ": " + words.get(i - 1));
            }
            System.out.println();
        }
    }

    /**
     * Saves all topics and words to a file specified by the user.
     */
    static void saveInFile() {
        System.out.println("Enter the name of the output file:");
        String fileName = key.next();
        key.nextLine();
        topics.writeToFile(fileName); // Write topics and words to file
    }

    /**
     * Displays a menu of topics and allows the user to choose one.
     *
     * @return The user's chosen topic
     */
    static int chooseTopic() {
        System.out.print("-----------------------------\n\tPick a topic\n-----------------------------\n");
        topics.displayFromHeadToTail(); // Display available topics
        System.out.print("0 Exit\n-----------------------------\nEnter Your Choice: ");

        boolean valid = false;
        int answer = 0;
        // Validate user input
        do {
            try {
                answer = Integer.parseInt(key.next());
                key.nextLine(); // Clear buffer
                if (answer < 0 || answer > topics.getSize()) {
                    throw new NumberFormatException();
                } else {
                    valid = true;
                }
            } catch (NumberFormatException nf) {
                System.out.print("Please Enter a Valid Choice: ");
            }
        } while (!valid);
        return answer;
    }
}
