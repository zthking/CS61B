public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char c1, char c2) {
        int diff = c2 - c1;
        return Math.abs(diff) == 1;
    }
}
