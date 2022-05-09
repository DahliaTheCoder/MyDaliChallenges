public class BinaryTreeHoffman {

    private BinaryTreeHoffman left, right;    // children; can be null
    Character character;
    int freq;

    /**
     * Constructs leaf node -- left and right are null
     */
    public BinaryTreeHoffman(Character character, int freq) {
        this.character = character;
        this.freq = freq;
    }

    /**
     * Constructs inner node
     */
    public BinaryTreeHoffman(Character character, int freq, BinaryTreeHoffman left, BinaryTreeHoffman right) {
        this.character = character;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }


    /**
     * Returns the character that corresponds to that node
     */
    public Character getCharacter() {
        return character;

    }

    /**
     * Returns the frequency of the character that corresponds to that node
     */
    public Integer getFreq() {
        return freq;

    }

    /**
     * Returns the left node of that root node
     */
    public BinaryTreeHoffman getLeft() {
        return left;
    }

    /**
     * Returns the right node of that root node
     */
    public BinaryTreeHoffman getRight() {
        return right;
    }

    /**
     * Checks if it is a leaf node
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Checks if it has a left child
     */
    public boolean hasLeft() {
        return left != null;
    }

    /**
     * Checks if it has a right child
     */
    public boolean hasRight() {
        return right != null;
    }


    /**
     * Returns a string representation of the tree
     */

    public String toString() {
        return toStringHelper("");
    }

    /**
     * Recursively constructs a String representation of the tree from this node,
     * starting with the given indentation and indenting further going down the tree
     */
    public String toStringHelper(String indent) {
        String res = indent + "Character: " + character + "," + "Frequency: " + freq + "\n";
        if (hasLeft()) res += left.toStringHelper(indent+"  ");
        if (hasRight()) res += right.toStringHelper(indent+"  ");
        return res;
    }

}
