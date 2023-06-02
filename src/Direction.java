public enum Direction {
    RIGHT(0, 1),
    UP(1, 0),
    LEFT(0, -1),
    BOTTOM(-1, 0);
    public final int x;
    public final int y;
    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }
}