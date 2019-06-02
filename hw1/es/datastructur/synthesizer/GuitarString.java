package es.datastructur.synthesizer;

import java.util.Arrays;

//Note: This file will not compile until you complete task 1 (BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your buffer should be initially filled with zeros.
        if (frequency <= 0) {
            throw new IllegalArgumentException();
        }
        buffer = new ArrayRingBuffer<>((int) Math.round(SR/frequency));
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(0d);
        }
    }
/*
    private double[] nonDuplicatedRandom(int size) {
        double[] numbers = new double[size];
        if (size == 0) {
            return null;
        }
        numbers[0] = Math.random() - 0.5;
        for (int i = 1; i < size; i++) {
            numbers[i] = Math.random() - 0.5;
            for (int j = 0; j < i; j++) {
                if (numbers[j] == numbers[i]) {
                    i -= 1;
                }
            }
        }
        return numbers;
    }
*/
    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other.
        //double[] randomNumbers = nonDuplicatedRandom(buffer.capacity());

        if (buffer.capacity() == 0) {
            return;
        }
        double[] randomNumbers = new double[buffer.capacity()];
        double number = Math.random() - 0.5;
        randomNumbers[0] = number;
        buffer.dequeue();
        buffer.enqueue(number);

        for (int i = 1; i < buffer.capacity(); i++) {
            number = Math.random() - 0.5;
            randomNumbers[i] = number;
            buffer.dequeue();
            buffer.enqueue(number);
            if (Arrays.asList(randomNumbers).contains(number)) {
                i -= 1;
            }
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        double dequeuedItem = buffer.dequeue();
        double newFirstItem = this.sample();
        buffer.enqueue(DECAY * 0.5 * (dequeuedItem + newFirstItem));
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
    // TODO: Remove all comments that say TODO when you're done.
