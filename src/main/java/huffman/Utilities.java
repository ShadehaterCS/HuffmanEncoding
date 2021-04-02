package huffman;

import java.io.*;
import java.util.HashMap;

public class Utilities {
    private static int[] frequencyTable = new int[128];

    /*
    Loads a file and reads each character.
    Checks if that character is in the first 128 places of the ASCII table.
    If it is then it updates the corresponding (int) cell of the int array
    */
    public static void loadTextFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader in = new BufferedReader(new FileReader(file));
        int character;
        while ((character = in.read()) != -1) {
            if (character <= 127)
                frequencyTable[character] += 1;
        }
        System.out.println("File loaded successfully, frequency table is in memory");
    }
    /*
        This method takes the int array and writes it one line at a time in the specified file
        Goes one line at the time, frequency + a new line
        @includeCharacters is if we also want to have the character, but since we know the array is always the same
            that information is not need. ASCII char 27 is always be at the 27th line when the file is read.
     */
    public static void writeFrequencyTableToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        String str;
        for (int i = 0; i < 128; i++) {
            str = frequencyTable[i] + "\n";
            writer.write(str);
        }
        writer.close();
        System.out.println("Frequency.dat written successfully");
    }

    /*
    Loads the frequencies.dat file and recreates the int array.
    @returns an int[] where each position corresponds to the frequency that ASCII[] character
    */
    public static int[] loadFrequencyFile() throws IOException {
        File temp = new File("frequencies.dat");
        int[] frequencies = new int[128];
        BufferedReader in = new BufferedReader(new FileReader(temp));
        String line;
        for (int i = 0; i < 128; i++) {
            if ((line = in.readLine()) != null)
                frequencies[i] = Integer.parseInt(line);
        }
        return frequencies;
    }

    /*
    @returns a HashMap <Int, Str> where int is a char in ascii form
     */
    public static void buildEncodingTable(HashMap<Integer, String> encodings, File codes) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(codes));
            String line;
            String[] split;
            for (int i = 0; i < 128; i++) {
                if ((line = in.readLine()) != null) {
                    split = line.split(" ");
                    encodings.put(Integer.parseInt(split[0]), split[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Problem with codes file!");
            e.printStackTrace();
        }
    }

}
