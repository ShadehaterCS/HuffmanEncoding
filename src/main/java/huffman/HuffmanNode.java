package huffman;
import java.io.Serializable;
import java.util.Comparator;

public class HuffmanNode implements Serializable {
    private int frequency;
    private Character character; //if it's a leaf it will have a code corresponding to the char it's holding
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(){}

    public HuffmanNode(int f, Character c, HuffmanNode left, HuffmanNode right){
        frequency = f;
        character = c;
        this.left = left;
        this.right = right;
    }

    public int getFrequency() {
        return frequency;
    }

    /*
    @character will be null if node is not leaf
     */
    public Character getCharacter() {
        return character;
    }

    //inner class to be able to compare 2 nodes for use in the priority queue
    static class HuffmanComparator implements Comparator<HuffmanNode>, Serializable
    {
        @Override
        public int compare(HuffmanNode node1, HuffmanNode node2) {
            return node1.frequency - node2.frequency;
        }
    }
}
