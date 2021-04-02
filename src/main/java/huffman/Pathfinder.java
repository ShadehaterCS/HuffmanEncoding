package huffman;

import java.io.*;
import java.util.HashMap;
import java.util.Stack;

public class Pathfinder {

    private HuffmanNode root;

    private HashMap<Integer, String> paths;
    private Stack<Character> currentPath;

    public HashMap<Integer, String> getPaths() {
        return paths;
    }
    public HuffmanNode getRoot() {
        return root;
    }

    public Pathfinder(){
        try{
            FileInputStream in = new FileInputStream("Tree.dat");
            ObjectInputStream oin = new ObjectInputStream(in);
            HuffmanTree tree = (HuffmanTree) oin.readObject();
            root = tree.root;
            paths = new HashMap<>();
            currentPath = new Stack<>();
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /*
   This method works in conjunction with @function::traverse and will go through the whole tree. When a leaf node is
   encountered it saves a tuple(k,v) into the HashMap where k is the character and v is a string of 01s where
   0 means go left and 1 means go right

   To find the paths we use a Stack and the function goes deeper recursively. When we hit a leaf node we save it
   and go back up.
   Going back up is achieved by having a Stack.pop() after every traversal thus it's guaranteed that the stuck will
   be empty and we'll end up back at the root node and we can go again.
   */
    public void createPaths(){
        traverse(root);
        System.out.println("Paths generated successfully");
    }

    private void traverse(HuffmanNode node){
        if (node.getCharacter() == null){
            if (node.left != null) {
                currentPath.add('0');
                traverse(node.left);
                currentPath.pop();
            }
            if (node.right != null) {
                currentPath.add('1');
                traverse(node.right);
                currentPath.pop();
            }
        }
        else{ //This means we are at a leaf node. Add the path to the hashmap and go back
            StringBuilder fullpath = new StringBuilder();
            for (Character c : currentPath)
                fullpath.append(c);

            paths.put((int)node.getCharacter(), fullpath.toString());
        }
    }

    /*
    This method takes a string @path like "0101" and uses each character
    in the string to traverse the tree.
    The result is guaranteed because the paths are unique and absolute.
     */
    private Character reachNode(String path){
        HuffmanNode n = root;
        for (char c : path.toCharArray()){
            if (c == '0')
                n = n.left;
            else
                n = n.right;
        }
        return n.getCharacter();
    }

    public void printPaths(){
        System.out.println("-------GENERATED PATHS-------");
        for (var a : paths.keySet()) {
            System.out.println("Character: '" + a + "' " + "path: " + paths.get(a));
        }
    }

    //TODO update documentation
    /*
   Saves all the paths in the hashmap
   It's important to also save the character because not all 128 chars we started with at the int[] array are
   guaranteed to be found in the given text, thus we cannot use the positional data from the lines themselves to know
   which character is supposed to be which 01 representation.

   @WrittenValues are Strings in the form of {Character}+{path}
   ex.: a0101010
        d001
    */
    public void savePathsToFile() {
        File file = new File("codes.dat");
        try {
            FileWriter writer = new FileWriter(file);
            String str;
            for (Integer a : paths.keySet()) {
                str = a + " " + paths.get(a) + "\n";
                writer.write(str);
            }
            writer.close();
            System.out.println("Codes.dat saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
