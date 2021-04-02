package huffman;

import java.math.MathContext;
import java.util.HashMap;
import java.util.Random;

public class smth {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1,0);
        map.put(2,0);
        map.put(3,0);
        map.put(4,0);
        map.put(5,0);
        map.put(6,0);

        Random random = new Random();
        for (int i =0; i< 10000;i++){
            int rand = random.nextInt(6)+1;
            map.put(rand,map.get(rand)+1);
        }
        map.forEach((k,v)-> System.out.println(k + " " + v));
        var ref = new Object() {
            double mean = 0;
        };
        map.forEach((k,v) -> ref.mean +=v);

        final double mean = Math.round(ref.mean / map.keySet().size());
        System.out.println(mean);

        ref.mean = 0;
        map.forEach((k,v) -> ref.mean += Math.pow(v - mean,2));

        double lmean = ref.mean / 10000;
        System.out.println("Standard deviation: "+ lmean);
    }
}
