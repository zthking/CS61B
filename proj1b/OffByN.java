public class OffByN implements CharacterComparator{

    int offValue;
    public OffByN(int N) {
        offValue = N;
    }

    @Override
    public boolean equalChars (char c1, char c2) {
        int diff = c2 - c1;
        return diff == offValue;
    }
}
