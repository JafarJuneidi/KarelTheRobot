import java.util.Objects;

public class Pair {
    int x;
    int y;
    Pair (int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return x == pair.x && y == pair.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    public int distanceBetweenPairs(Pair p1) {
        int xDiff = p1.x - this.x;
        int yDiff = p1.y - this.y;

        return Math.abs(xDiff) + Math.abs(yDiff);
    }

    public void updatePairBasedOnDirection(Direction direction) {
        this.x += direction.x;
        this.y += direction.y;
    }

    public Pair getPairAfterMove(Direction direction) {
        return new Pair(this.x + direction.x, this.y + direction.y);
//        this.x += direction.x;
//        this.y += direction.y;
    }
}
