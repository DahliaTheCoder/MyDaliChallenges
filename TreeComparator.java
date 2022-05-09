import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


/**
 * Compares the frequency of two different character nodes
 */

public class TreeComparator implements Comparator<BinaryTreeHoffman> {


    @Override
    public int compare(BinaryTreeHoffman s1, BinaryTreeHoffman s2) {
        if(s1.getFreq()>s2.getFreq()){
            return 1;
        } else if(s1.getFreq()<s2.getFreq()) {
            return -1;
        } else if(s1.getFreq()==s2.getFreq() || s1.getCharacter()<s2.getCharacter()) {
            return 1;
        } else if(s1.getFreq()==s2.getFreq() || s1.getCharacter()>s2.getCharacter()) {
            return -1;
        } else {
            return 0;
        }

    }

}