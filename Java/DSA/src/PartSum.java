import java.util.Random;

/**
 * Created by oertelt on 10.03.16.
 */
public class PartSum {
    public static void main(String[] args) {
        int size = 1000;
        float[] numbers = new float[size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            numbers[i] = (rand.nextFloat() - 0.5f) * 200.0f;
            //System.out.println(numbers[i]);
        }

        long start = System.currentTimeMillis();
        float greatesSum = greatestPartialSum(numbers);
        System.out.println("The greatest partial sum is " + greatesSum);
        float greatesSum2 = greatestPartialSumOptimized(numbers);
        System.out.println("The greatest partial sum is " + greatesSum2);
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start) * 0.001f + " s");

    }

    public static float greatestPartialSum(float[] array) {
        int curstart = 0;
        int curend = array.length;
        int cursum = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                int tmpsum = 0;
                for (int k = i; k <= j; k++) {
                    tmpsum += array[k];
                }
                if (tmpsum > cursum) {
                    cursum = tmpsum;
                    curstart = i;
                    curend = j;
                }
            }
        }
        return cursum;
    }
        public static float greatestPartialSumOptimized(float[] array) {
        int curstart = 0;
        int curend = array.length;
        int cursum = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
                int tmpsum = 0;
                for (int j = i; j <array.length; j++) {
                    tmpsum += array[j];
                if (tmpsum > cursum) {
                    cursum = tmpsum;
                    curstart = i;
                    curend = j;
                }
            }
        }
        return cursum;
    }
}
