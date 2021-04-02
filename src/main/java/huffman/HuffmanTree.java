package huffman;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.PriorityQueue;

public class HuffmanTree implements Serializable {
    private PriorityQueue<HuffmanNode> queue;
    public HuffmanNode root;

    public HuffmanTree(){
        queue = new PriorityQueue<>(128, new HuffmanNode.HuffmanComparator());
    }
    /*
    The tree is nodes connected together
    To achieve the connection we use @java's PriorityQueue
    The HuffmanNode class has its own comparator
    The first 2 nodes are pulled from the queue and become linked to a new node
    The new node has a null @Character and its frequency is the other two nodes' combined
    The new node is put back into the list and it iterates again until there is only one node left
    The last node is assigned to @this object's root node and is removed from the queue
    */
    public boolean createTree(){
        int[] frequencies;
        try{
            frequencies = Utilities.loadFrequencyFile();
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }

        for (int i=0;i<frequencies.length;i++){
            int f = frequencies[i];
            if (f > 0) //no need to add a character that never appears
                queue.add(new HuffmanNode(f, (char) i, null, null));
        }
        if (queue.size() == frequencies.length)
            System.out.println("Queue created successfully");

        while(queue.size() > 1){
            HuffmanNode h1 = queue.poll();
            HuffmanNode h2 = queue.poll();
            queue.add(new HuffmanNode(h1.getFrequency() + h2.getFrequency(), null, h1, h2));
        }
        root = queue.poll(); //Now the queue is empty :)

        System.out.println("Huffman Tree structure created successfully");
        return true;
    }
    /*
Saves a HuffmanTree object to a file called "Tree.dat"
 */
    public boolean saveTree() {
        try {
            FileOutputStream fos = new FileOutputStream("Tree.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Tree object saved successfully");
        return true;
    }
}
