package huffman;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        //---------PART 1---------//
        System.out.println("Part 1 : Calculate frequencies");
        Utilities.loadTextFile(args[0]);
        Utilities.writeFrequencyTableToFile("frequencies.dat");

        //---------PART 2---------//
        HuffmanTree tree = new HuffmanTree();
        if(tree.createTree())
            tree.saveTree();

        //---------PART 3---------//
        Pathfinder pathfinder = new Pathfinder();
        pathfinder.createPaths();
        pathfinder.savePathsToFile();

        //---------PART 4---------//
        System.out.println("Part 4 : Encoding Text");
        Encoder encoder = new Encoder();
        File input = new File(args[0]);
        File output = new File("encodedText.bin");
        File codes = new File("codes.dat");

        Utilities.buildEncodingTable(encoder.encodings, codes);
        encoder.encode(input, output);

        //---------PART 5---------//
        System.out.println("Part 5 : Decoder");
        input = new File("encodedText.bin");
        output = new File("decodedText.txt");

        Decoder decoder = new Decoder();
        decoder.loadHuffManTree();
        decoder.decode("encodedText.bin", output);
    }
}
