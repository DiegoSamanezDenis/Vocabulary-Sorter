public class Vocab {
	// Attributes to store the name of the vocabulary and its associated words
	private String name;
	private SinglyLinkedList words;
	
	// Default constructor initializes variables to null
	public Vocab() {
		name = null;
		words = null;
	}
	// Constructor with name parameter initializes the name and creates a new linked list for words
	public Vocab(String name) {
		this.name = name;
		this.words = new SinglyLinkedList();
	}	
	//Getter methods for name and words
	public String getName() {
		return name;
	}	
	public SinglyLinkedList getWords() {
		return words;
	}
	// Method to add a new word to the vocabulary
	public void addWord(String word) {
		words.addWord(word);
	}
	// Method to check if a new word can be added to the vocabulary
	public void canAddWord(String newWord) {
		words.canAddWord(newWord);
	}
	// Method to check if a word can be deleted from the vocabulary
	public void canDeleteWord(String target) {
		words.deleteWord(target);
	}
	// Method to check if a word can be changed in the vocabulary
	public boolean canChange(String target) {
		return words.canChangeWord(target);
	}
	// Method to change a word in the vocabulary
	public void changeWord(String target, String newWord) {
		words.changeWord(target, newWord);
	}
}