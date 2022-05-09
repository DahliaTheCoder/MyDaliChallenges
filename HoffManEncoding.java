import java.io.*;
import java.util.*;


public class HoffManEncoding {

    HashMap<Character, Integer> charaFreq;      // Instantiates a HashMap to store each character as key and its frequency in the file as value

    public HoffManEncoding(HashMap<Character, Integer> charaFreq) {

        this.charaFreq = charaFreq;

    }

    /**
     * createFreqTable method creates the frequency table that has each character and its frequency
     *
     * @param filename
     * @return the created HashMap, charFreq
     * @throws Exception
     */
    public static HashMap<Character, Integer> createFreqTable(String filename) throws Exception {


        BufferedReader in = new BufferedReader(new FileReader(filename));

        // Declares new Map, each entry in the Map has an integer value that will hold the frequencies of each character in the file
        HashMap<Character, Integer> charFreq = new HashMap<Character, Integer>();

        int singleChar = in.read();

        while ((singleChar) != -1) {        // Checks if the loop has reached the end of the file

            Character character = (char) singleChar;         // Casts the integer singleChar to be a character
            //            character = character.toLowerCase(character);

            // Checks to see if we have seen this character before, update wordFreq appropriately
            if (charFreq.get(character) != null) {

                charFreq.put(character, charFreq.get(character) + 1);          // Updates the frequency of the character by one

            } else {
                // Adds the new word with its frequency updated to one
                charFreq.put(character, 1);
            }
            singleChar = in.read();             // Continue updating in the loop
        }
    return charFreq;            // Returns the filled tree map

    }

    /**
     * charPriorityQueue method builds the priority queue and uses the created HashMap to fill the PriorityQueue with all
     * the character trees created for each set of characters
     *
     * @param characterFreq
     * @return charPriorityQueue
     */
    public static PriorityQueue<BinaryTreeHoffman> charPriorityQueue(HashMap<Character, Integer> characterFreq) throws Exception {

        TreeComparator treeComp = new TreeComparator();

        PriorityQueue<BinaryTreeHoffman> charPriorityQueue = new PriorityQueue<BinaryTreeHoffman>(characterFreq.size(), new TreeComparator());

        //if (charPriorityQueue == null ) throws Exception ("Empty File!"){
            if (characterFreq.size() > 0) {
                for (Character caractere : characterFreq.keySet()) {     //

                    BinaryTreeHoffman characterTree = new BinaryTreeHoffman(caractere, characterFreq.get(caractere));        // Creates a binaryTree named xterTree
                    charPriorityQueue.add(characterTree);            // Adds the xterTree to the priorityQueue
                }
            }
        //}

        return charPriorityQueue;           // Returns the priority queue

    }

    /** treeCreation method creates the tree with the lowest frequency characters being deepest in the tree and the
     * highest frequency characters be near the top of the tree.
     * @param charPriorityQueue
     * @return a BinaryTreeHoffman tree
     */

    public static BinaryTreeHoffman treeCreation(PriorityQueue<BinaryTreeHoffman> charPriorityQueue) {

        if(charPriorityQueue.size() == 1) {     // In case the tree contains only one tree
            BinaryTreeHoffman T = charPriorityQueue.remove();      // Extracts the only tree available
            BinaryTreeHoffman r = new BinaryTreeHoffman(T.getCharacter(), T.getFreq() + T.getFreq(), T, T);      // Creates a new tree with T set as both the right and left children.

            return r;           // Adds the tree T to the priority queue.
        }

        while (charPriorityQueue.size() > 1) {      // Checks if the size of the priorityqueue is greater than 1

            BinaryTreeHoffman T1 = charPriorityQueue.remove();      // Extracts the first tree with the lowest frequency
            BinaryTreeHoffman T2 = charPriorityQueue.remove();      // Extracts the second tree with the lowest frequency

            BinaryTreeHoffman r = new BinaryTreeHoffman(null, T1.getFreq() + T2.getFreq(), T1, T2);      // Creates a new root node that has T1 and T2 as right and left nodes respectively.

            charPriorityQueue.add(r);           // Adds the tree T to the priority queue.
        }

        return charPriorityQueue.remove();
    }


    /**
     * codeRetrieval method fills the HashMap that contains the name of the character and its respective string codeword
     *
     * @param finalTree
     * @return the completed HashMap, pathTree
     */
    public static HashMap<Character, String> codeRetrieval(BinaryTreeHoffman finalTree) throws Exception {


        HashMap<Character, String> pathMap = new HashMap<Character, String>();          // Creates a new HashMap to store each character as key and its accurate codeword as the value

        if (finalTree != null) {        // Checks if the passed-in tree is empty

            codeRetrievalHelperMethod(finalTree, pathMap, "");      // Calls the helper method to handle updating the HashMap, pathMap, with each character and its accurate pathSoFar codeword.

        }

        return pathMap;     // Returns the pathMap

    }

    /**
     * This is a helper method for codeRetrieval method that is in charge of completing the pathSoFar string code word
     * for each passed in character.
     *
     * @param finalTree
     * @param pathMap
     * @param pathSoFar
     *
     */

    public static void codeRetrievalHelperMethod(BinaryTreeHoffman finalTree, HashMap<Character, String> pathMap, String pathSoFar) {


        if (finalTree.isLeaf()) {         // Checks if the finalTree is the leaf;
            pathMap.put(finalTree.getCharacter(), pathSoFar);              // Add the character and its string code word to the HashMap
        } else {
            codeRetrievalHelperMethod(finalTree.getLeft(), pathMap, pathSoFar + "0");        // Recurse through the left part of the tree
            codeRetrievalHelperMethod(finalTree.getRight(), pathMap, pathSoFar + "1");       // Recurse through the right part of the tree
        }

    }

    /**
     * Compression Method: Takes in a text file, among other parameters, reads each character, traverses the pathTree
     * to find the code for that character and records it as bits to an output compressed file.
     * @param fileName
     * @param compressedFileName
     * @param pathMap
     */

    public static void compressionMethod(String fileName, String compressedFileName, HashMap<Character, String> pathMap) throws IOException {

        BufferedReader input = new BufferedReader(new FileReader(fileName));
        BufferedBitWriter output = new BufferedBitWriter(compressedFileName);

        try {
            int chara = input.read();
            while (chara != -1) {             // Checks if the loop has reached the end of the file

                char c = (char) (chara);       // Casts the integer to a character
                String codeWord = pathMap.get(c);    // Stores the codeword of character c in the parameter, codeWord.

                for (int i = 0; i < codeWord.length(); i++) {

                    output.writeBit(codeWord.charAt(i) == '1');             // Writes the '1' and '0's bits to the output file. If the character is equal to 1, write '1', if it's not, write '0'

                }
                chara = input.read();       // moves on to another character
            }
        } finally {
            input.close();          // Closes the file we are reading from
            output.close();         // Closes the file we are writing to
        }
    }

    /**
     * deCompressionMethod takes in the compressedTest.txt, among other parameters, reads bit by bit from the
     * compressedTest.txt and traverses the pathTree accordingly, and then returns the character stored in the last leaf.
     * @param fileName
     * @param decompressedFileName
     * @param pathMap
     * @param pathTree
     * @throws IOException
     */

    public static void deCompressionMethod(String fileName, String decompressedFileName, HashMap<Character, String> pathMap, BinaryTreeHoffman pathTree) throws IOException {

        if(pathTree == null){       // Checks if the pathTree is empty
            return;
        }

        BufferedBitReader input = new BufferedBitReader(fileName);
        BufferedWriter output = new BufferedWriter(new FileWriter(decompressedFileName));

        BinaryTreeHoffman helperpathTree = pathTree;

        try {
            while (input.hasNext()) {             // Checks if the loop has reached the end of the file
                Boolean chara = input.readBit();        // Stores the value of the character value in the parameter chara. It's declared as a boolean because it is reading bits '1' and '0' from the file

                if (chara) {    // If chara is true, that is, if the value read from the file is 1
                    helperpathTree = helperpathTree.getRight();      // Traverse to the right side of the tree
                } else {        // If chara is false, that is if the value read from the file is 0
                    helperpathTree = helperpathTree.getLeft();      // Traverse to the left side of the tree
                }

                if (helperpathTree.isLeaf()) {    // If the tree is a leaf
                    output.write(helperpathTree.getCharacter());          // Writes the character in that leaf to the ouput file
                    helperpathTree = pathTree;  // Reassign it so that it starts from the root for the next character

                }

            }

        } finally {
            input.close();          // Closes the file we are reading from
            output.close();         // Closes the file we are writing to
        }
    }
}



