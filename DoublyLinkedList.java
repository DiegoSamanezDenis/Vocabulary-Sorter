import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DoublyLinkedList {

    private DNode head;
    private DNode tail;
    private int size;
    
    // Default constructor initializes an empty doubly linked list
    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }
    // Method to add a new vocab at the beginning of the linked list
    public void addAtHead(Vocab val) {
        DNode temp = head;
        head = new DNode(val, null, head); 
        // If the list was empty, set both head and tail to the new node
        if (tail == null) {
            tail = head;
        } else {
            temp.prev = head; // Update the previous reference of the old head node
        }
        size++;
    }
    // Method to add a new vocab at the end of the linked list
    public void addAtTail(Vocab val) {
        DNode temp = tail;
        tail = new DNode(val, tail, null);
        if (head == null) {
            head = tail;
        } else {
            temp.next = tail; // Update the next reference of the old tail node
        }
        size++;
    }
    // Method to add a new vocab after a specific position in the linked list
    public void addAfter(int where, Vocab newVal) {
    	DNode dummy = head;
    	for (int i = 0; i < where - 1; i++) {
    		dummy = dummy.next;
    	}
        if (dummy != null) {
            if (dummy.next == null) {
                addAtTail(newVal);
            } else {
                // Create a new node and adjust references to insert after the specified position
                DNode n = new DNode(newVal, null, null);
                n.next = dummy.next;
                n.prev = dummy;
                dummy.next.prev = n;
                dummy.next = n;
                size++;
            }
        }
    }
    // Method to remove a vocab at a specific position in the linked list
    public void removeTopic(int what) {
    	DNode dummy = head;
    	for (int i = 0; i < what - 2; i++) {
    		dummy = dummy.next;
    	}
    	DNode temp = dummy;
    	dummy.next = dummy.next.next;
    	dummy = dummy.next;
    	dummy.prev = temp;
    	size--;
    }
    // Method to remove the first vocab from the linked list
    public void removeHead() {
        if (head == tail) {
            head = null;
            tail = null;
            size--;
            return;
        } else {
            head = head.next;
            head.prev = null;
            size--;
            return ;
        }
    }
    // Method to remove the last vocab from the linked list
    public Vocab removeTail() {
        if (head == tail) {
            DNode temp = head;
            head = null;
            tail = null;
            size--;
            return temp.vocab;
        } else {
            DNode temp = tail;
            tail = tail.prev;
            tail.next = null;
            size--;
            return temp.vocab;
        }
    }  
    // Method to get the vocab at a specific position in the linked list
    public Vocab getTheTopic(int answer) {
    	DNode dummy = head;
    	for (int i = 0; i < answer - 1; i++) {
    		dummy = dummy.next;
    	}
    	return dummy.vocab;
    }
    // Method to display all vocabs from head to tail in the linked list
    public void displayFromHeadToTail() {
        if (head == null) {
            System.out.println("There are no topics yet.");
        } else {
            DNode position = head;
            int count = 0;
            while (position != null) {
                System.out.println(++count + " " + position.vocab.getName());
                position = position.next;
            }
        }
    }
    // Method to search for a specific word in all vocabs in the linked list
    public void searchForTopic(String target) {
    	String toPrint = "";
    	DNode temp = head;
    	while (temp != null) {
    		if (temp.vocab.getWords().searchForWord(target))
    			toPrint += temp.vocab.getName() + " ";
    		temp = temp.next;
    	}
    	if (toPrint.equals("")) {
    		System.out.println("The word '" + target + "' doesn't belong to any topics in the system.");
    	} else {
    		System.out.println("The word '" + target + "' belongs to topic: " + toPrint);
    	}
    }
    // Method to get all words starting with a specific character from all vocabs in the linked list
    public ArrayList<String> getAllWordsStarting(char target){
    	ArrayList<String> compilation = new ArrayList<>();
    	DNode temp = head;
    	while (temp != null) {
    		ArrayList<String> each = temp.vocab.getWords().searchStartWord(target);
    		compilation.addAll(each); 
    		temp = temp.next;
    	}
    	// Bubble sort the ArrayList
    	for (int i = 0; i < compilation.size(); i++) {
    		for (int j = i + 1; j < compilation.size(); j++) {
    			if (compilation.get(i).compareTo(compilation.get(j)) > 0) { 
    				String temporaryString = compilation.get(i);
    				compilation.set(i, compilation.get(j));
    				compilation.set(j, temporaryString);
    			}
    		}
    	}	
    	return compilation;
    }   
    // Method to get the size of the linked list
    public int getSize() {
    	return size;
    }
    // Method to write all vocabs and their words to a file
    public void writeToFile(String fileName) {
    	PrintWriter so = null; 
		try {
			so = new PrintWriter(new FileOutputStream(fileName)); 
			DNode dummy = head;
			while (dummy != null) {
				so.println(); 
				so.println("#" + dummy.vocab.getName());
				ArrayList<String> allTheWords = dummy.vocab.getWords().allTheWords();
				for (int i = 0; i < allTheWords.size(); i++) {
					so.println(allTheWords.get(i));
				}
				dummy = dummy.next;
			}
			System.out.println("All the vocabs have been written to file: " + fileName);
		} catch (FileNotFoundException fnf) {
			System.out.println("The file was not found.");
			System.exit(0); 
		} finally {
			if (so != null)
				so.close();
		}
    }
    // Inner class to represent a node in the doubly linked list
    private class DNode {
        
        private Vocab vocab;
        private DNode next;
        private DNode prev;

        // Constructor to initialize a node with a vocab, and references to the previous and next nodes
        public DNode(Vocab vocab, DNode prev, DNode next) {
            this.vocab = vocab;
            this.prev = prev;
            this.next = next;
        }
    }
}
