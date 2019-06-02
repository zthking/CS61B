package es.datastructur.synthesizer;

import java.util.Arrays;

public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /** Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /** Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        if (frequency <= 0) {
            throw new IllegalArgumentException();
        }
        buffer = new ArrayRingBuffer<>((int) Math.round(SR / frequency));
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(0d);
        }
    }

    /** Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
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
            if (Arrays.asList(randomNumbers).contains(number)) {
                i -= 1;
            } else {
                randomNumbers[i] = number;
                buffer.dequeue();
                buffer.enqueue(number);
            }
        }
    }
    /**
     * Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double dequeuedItem = buffer.dequeue();
        double newFirstItem = this.sample();
        buffer.enqueue(DECAY * 0.5 * (dequeuedItem + newFirstItem));
    }

    /** Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
