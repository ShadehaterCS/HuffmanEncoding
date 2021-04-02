package huffman;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;

public class Encoder {
    public HashMap<Integer, String> encodings;

    public Encoder() {
        encodings = new HashMap<>();
    }

    /*@design choice
    Since it is not guaranteed the last byte will be filled, we use the first byte to write an int
    that tells how many bits should not be considered at the last byte.
    We will know when to stop by only counting 8-remainingBits at the last byte of the byteArray when we load
    the file.
    ---------
    Using the BitSet class to represent the bits to write
    Reads the input .txt line by line and breaks that line into a charArray
    Pulls the encoded path from the encodings{HashMap} and converts that into a charArray so it can treat it as bits
    Goes over every char {'0' or '1'} and uses BitSet.set() to assign 1 to that bit. Does not care for the bits in
        between as they default to 0.
    ---------
    @explanation for the BitSet
    The BitSet creates chunks of 8 bits that will then be turned into bytes
    A byte can be written through a BitSet but the BitSet writes it right to left while we want to write it left to right
        which makes sense, as the 8th bit is actually the first from our point of human view.
    Thus in order to make it work without filling our string with 0s to achieve a length of multiples of 8 and then
        having to reverse the string we do the following:
    @technique
    We use 2 ints, {totalBits} & {currentBits}.
    While the totalBits is being incremented, currentBits is decremented.
    Every time totalBits % 8 is equal to 0 it means we have completed a byte and we set currentBits at totalBits+7
    +7 because BitSet is zero-based like arrays ([0])
    This means that currentBits acts as our relative positioning to set the bits from left to right as we're reading
        the path String.
    totalBits is finally used to know how many bits are leftover to complete the byte.
    We write that to the first byte and then write the whole BitSet as a byte array.
    */
    public void encode(File input, File output) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(input));
            OutputStream outputStream = new FileOutputStream(output);

            BitSet bitset = new BitSet();
            int totalBits = 0;
            int currentBits = 7;
            String encodedString;

            int character;
            while ((character = in.read()) != -1) {
                encodedString = encodings.get(character);
                if (encodedString == null){ //if it encounters a character beyond the 127 ASCII mark
                    continue;
                }

                for (char x : encodedString.toCharArray()) {
                    if (x == '1')
                        bitset.set(currentBits);
                    currentBits--;
                    totalBits++;
                    if (totalBits % 8 == 0) {
                        currentBits = totalBits + 7;
                    }
                }
            }
            byte remainingBits;
            /*System.out.println("\nTotal bits written: "+totalBits
                    +"\nCurrent bit counter: "+currentBits
                    +"\nBitset Size: "+bitset.size()); */

            if (totalBits % 8 != 0)
                remainingBits = (byte) (8 - totalBits % 8);
            else
                remainingBits = 0;

            outputStream.write(remainingBits);
            outputStream.write(bitset.toByteArray());
            if (bitset.size() < totalBits) //this is in the codes end up in 0s. The BitSet can't account for that so we add an empty byte
                outputStream.write(0);
            outputStream.close();
            System.out.println("Writing binary file finished successfully");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
