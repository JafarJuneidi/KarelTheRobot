public class MutableDirection {
    private Direction direction;

    public MutableDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void turnRight() {
        direction = getDirectionToTheRight();
    }

    public Direction getDirectionToTheRight() {
        int ordinal = direction.ordinal();
        int size = Direction.values().length;
        // used a floorMod so that index -1 circles back to 3, regular Mod doesn't have that behavior with negatives
        int nextOrdinal = (ordinal - 1 + size) % size;
//        int nextOrdinal = Math.floorMod((ordinal - 1), 4);
        return Direction.values()[nextOrdinal];
    }

    public void turnLeft() {
        direction = getDirectionToTheLeft();
    }

    public Direction getDirectionToTheLeft() {
        int ordinal = direction.ordinal();
        int size = Direction.values().length;
        int prevOrdinal = (ordinal + 1) % size;
        return Direction.values()[prevOrdinal];
    }
}
