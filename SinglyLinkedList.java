import java.util.ArrayList;
public class SinglyLinkedList {

	private SNode head;
	private SNode tail;
	private int size;
	
	// Constructor to initialize an empty linked list
	public SinglyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	// Method to add a word to the end of the linked list
	public void addWord(String value) {
		SNode temp = tail;
		tail = new SNode(value, null);
		if (head == null) {
			head = tail;
		}else {
			temp.next = tail;
		}
		size++;
	}
	// Method to check if a word can be added to the linked list (avoid duplicates)
	public void canAddWord(String value) {

		if (head == null) {
			head = new SNode(value, null);
			tail = head;
			size++;
		}else {
			SNode dummy = head;
			boolean canAdd = true;
			while (dummy != null) {
				if (dummy.ue.equalsIgnoreCase(value)) {
					canAdd = false;
					break;
				}
				dummy = dummy.next;
			}
			if (canAdd) {
				SNode temp = head;
				head = new SNode(value, null);
				head.next = temp;
				size++;
			}else {
				System.out.println("The word '" + value + "' is already listed");
			}
		}
	}
	// Method to delete a word from the linked list
	public void deleteWord(String word) {
		SNode dummy = head;
		boolean canDelete = false;
		int where = 0;
		while (dummy != null) {
			if (dummy.ue.equals(word)) {
				canDelete = true;
				break;
			}
			where++;
			dummy = dummy.next;
		}	
		if (canDelete) {
			if (size == 1) {
				head = null;
				tail = null;
				size = 0;
			}else {
				SNode temp = head;
				for (int i = 0; i < where - 1; i++) {
					temp = temp.next;
				}
				temp.next = temp.next.next;
				size--;
			}
		}else {
			System.out.println("The word "+word+" does not exist.");
		}
	}
	// Method to check if a word can be changed in the linked list
	public boolean canChangeWord(String word) {
		SNode dummy = head;
		boolean canChange = false;
		while (dummy != null) {
			if (dummy.ue.equals(word)) {
				canChange = true;
				break;
			}
			dummy = dummy.next;
		}
		return canChange;
	}
	// Method to change a word in the linked list
	public void changeWord(String word, String newWord) {
		SNode dummy = head;
		while (dummy != null) {
			if (dummy.ue.equals(word)) {
				break;
			}
			dummy = dummy.next;
		}
		dummy.ue = newWord;
	}
	// Method to get the size of the linked list
	public int getSize() {
		return size;
	}
	// Method to display all words in the linked list
	public void showWords() {
		SNode dummy = head;
		for (int i = 1; i <= size; i++) {
			if (i % 4 == 1 && i != 1)
				System.out.println();
			System.out.printf("%-25s",i + ": " + dummy.ue);
			dummy = dummy.next;
		}
	}
	// Method to search for a specific word in the linked list
	public boolean searchForWord(String target) {
		
		SNode dummy = head;
		while (dummy != null) {
			if (dummy.ue.equalsIgnoreCase(target))
				return true;
			
			dummy = dummy.next;
		}
		return false;
	}
	// Method to search for words starting with a specific character in the linked list
	public ArrayList<String> searchStartWord(char target){
		ArrayList<String> each = new ArrayList<String>();
		SNode temp = head;
		
		while (temp != null) {
			if (temp.ue.charAt(0) == target) {
				each.add(temp.ue);
			}
			temp = temp.next;
		}
		return each;
	}
	// Method to get all words in the linked list
	public ArrayList<String> allTheWords(){
		ArrayList<String> all = new ArrayList<>();
		SNode dummy = head;
		while (dummy != null) {
			all.add(dummy.ue);
			dummy = dummy.next;
		}
		return all;
	}
	// Inner class to represent a node in the linked list
	private class SNode{
		private String ue;
		private SNode next;
	    // Constructor to initialize a node with a ue and reference to the next node
		public SNode(String value, SNode next) {
			this.ue = value;
			this.next = next;
		}	
	}
}