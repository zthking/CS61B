/**
 * Implement equalChars in CharacterComparator.
 * Return true if two characters are different by input value N.
 */
public class OffByN implements CharacterComparator {

    private int offValue;

    public OffByN(int N) {
        offValue = N;
    }

    @Override
    public boolean equalChars(char c1, char c2) {
        int diff = c2 - c1;
        return Math.abs(diff) == offValue;
    }
}
