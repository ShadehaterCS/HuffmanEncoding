package huffman;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Decoder {
    public HashMap<Character, String> encodings;
    public HuffmanTree tree;

    public Decoder() {
        encodings = new HashMap<>();
    }

    private int remainingBits;
    private String binaryInput;

    /*
    This method loads the whole file in a byte array
    It goes over every byte and uses Integer.toBinaryString to produce a long 01 representation.
    Cuts it to the last 8 digits which are all we care about and then iterates over its characters.
    @StringBuilder {decodedText} is this long string of 01s that are to be parsed through the function
        @getOutput
    */
    public void decode(String input, File output) {
        try {
            String binaryString;
            Path path = Paths.get(input);
            byte[] encodedBinaryInput = Files.readAllBytes(path);

            System.out.println("Encoded file size: " + encodedBinaryInput.length + " bytes");
            remainingBits = encodedBinaryInput[0];

            StringBuilder decodedText = new StringBuilder();
            for (int i = 1; i < encodedBinaryInput.length - 1; i++) {
                binaryString =
                        String.format("%8s", Integer.toBinaryString(encodedBinaryInput[i] & 0xFF))
                                .replace(' ', '0');
                decodedText.append(binaryString);
            }
            //Read last byte except for the {remainingBits} bits
            binaryString = String.format("%8s", Integer.toBinaryString(encodedBinaryInput[encodedBinaryInput.length - 1] & 0xFF))
                    .replace(' ', '0');
            char[] bits = binaryString.toCharArray();
            for (int rem = 0; rem < 8 - remainingBits; rem++) {
                decodedText.append(bits[rem]);
            }

            binaryInput = decodedText.toString();
            getOutput(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    @outputFile is the file name at which the decoded text will be written
    Iterates over the whole length of the binary string that has been created in {decode()}
    At every iteration it checks if the current node is a leaf, if so it resets to the root
    and goes again until we reach the end of the string.
    The algorithm is complete and should never finish the string without reaching the final leaf node.
    At the end it converts and writes the StringBuilder to a string at the output file.
     */
    public void getOutput(File outputFile) {
        StringBuilder output = new StringBuilder();
        HuffmanNode n = tree.root;
        for (int i = 0; i < binaryInput.length() + 1; i++) {
            if (n.getCharacter() != null) {
                output.append((char) n.getCharacter());
                n = tree.root;
                if (i == binaryInput.length())
                    break;
            }
            if (binaryInput.charAt(i) == '0')
                n = n.left;
            else if (binaryInput.charAt(i) == '1')
                n = n.right;
        }
        try {
            FileWriter writer = new FileWriter(outputFile);
            writer.write(output.toString());
            writer.close();
            System.out.println("\nOutput written successfully at: " + outputFile);
        } catch (IOException e) {
            System.out.println("Failed to write output file");
            e.printStackTrace();
        }
    }

    public void loadHuffManTree() {
        try {
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream("Tree.dat"));
            tree = (HuffmanTree) oin.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
