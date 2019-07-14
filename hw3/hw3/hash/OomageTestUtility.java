package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] numInBucket = new int[M];
        for (Oomage s : oomages) {
            int bucketNumber = (s.hashCode() & 0x7FFFFFFF) % M;
            numInBucket[bucketNumber] += 1;
        }

        for (int n : numInBucket) {
            if (n < (oomages.size() / 50) || n > (oomages.size() / 2.5)) {
                return false;
            }
        }

        return true;
    }
}
