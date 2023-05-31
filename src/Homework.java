import stanford.karel.SuperKarel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Homework extends SuperKarel {
    int length;
    int height;
    Pair currentLocation = new Pair(1, 1);
    int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    // Right, Up, Left, Bottom
    int currentDirectionIndex = 0;
    private void moveToUpperRight() {
        while (!facingEast()) {
            turnLeft();
        }

        while(frontIsClear()) {
            move();
        }

        turnLeft();
        while(frontIsClear()) {
            move();
        }
    }

    // Overridden methods
    public void move() {
        if (frontIsBlocked()) return;

        super.move();
        currentLocation.x += directions[currentDirectionIndex][0];
        currentLocation.y += directions[currentDirectionIndex][1];
    }

    public void turnLeft() {
        super.turnLeft();
        currentDirectionIndex = (currentDirectionIndex + 1) % 4;
    }
    public void turnRight() {
        super.turnRight();
        // used a floorMod so that index -1 circles back to 3, regular Mod doesn't have that behavior with negatives
        currentDirectionIndex = Math.floorMod((currentDirectionIndex - 1), 4);
    }
    private Pair LocationOfNextMove() {
        int x = currentLocation.x + directions[currentDirectionIndex][0];
        int y = currentLocation.y + directions[currentDirectionIndex][1];

        return new Pair(x, y);
    }
    private boolean isNextMoveVerticalDifferenceLess(Pair point) {
        int xDiff = point.x - currentLocation.x;
        int xDiffAfterMove = point.x - LocationOfNextMove().x;
        return Math.abs(xDiffAfterMove) < Math.abs(xDiff);
    }
    private boolean isNextMoveHorizontalDifferenceLess(Pair point) {
        int yDiff = point.y - currentLocation.y;
        int yDiffAfterMove = point.y - LocationOfNextMove().y;
        return Math.abs(yDiffAfterMove) < Math.abs(yDiff);
    }
    public void moveToPoint(Pair point) {
        while (!isNextMoveHorizontalDifferenceLess(point) && currentLocation.y != point.y) {
            turnLeft();
        }

        while (currentLocation.y != point.y) {
            move();
        }

        while (!isNextMoveVerticalDifferenceLess(point) && currentLocation.x != point.x) {
            turnLeft();
        }

        while (currentLocation.x != point.x) {
            move();
        }
    }
    private LinkedHashSet<Pair> calculateGoalPoints() {
        LinkedHashSet<Pair> set = new LinkedHashSet<Pair>();

        boolean inorder = true;
        for (int row = height; row > 0; --row) {
            Pair p1 = new Pair(row, (length / 2) + 1);
            Pair p2 = null;
            if (Helper.isEven(length)) {
                p2 = new Pair(row, length / 2);
            }

            if (p2 == null) {
               set.add(p1);
            } else if (inorder) {
                set.add(p1);
                set.add(p2);
            } else {
                set.add(p2);
                set.add(p1);
            }
            inorder = !inorder;
        }

        for (int col = length; col > 0; --col) {
            Pair p1 = new Pair((height / 2) + 1, col);
            Pair p2 = null;
            if (Helper.isEven(height)) {
                p2 = new Pair(height / 2, col);
            }

            if (p2 == null) {
                set.add(p1);
            } else if (inorder) {
                set.add(p1);
                set.add(p2);
            } else {
                set.add(p2);
                set.add(p1);
            }
            inorder = !inorder;
        }

        int squareLength;
        if (height < length) {
            squareLength = height - 2 - 1;
        } else {
            squareLength = length - 2 - 1;
        }
        int leftBorderCol = ((length + 1) / 2) - squareLength / 2;
        int rightBorderCol = (length / 2) + 1 + squareLength / 2;
        int bottomBorderRow = ((height + 1) / 2) - squareLength / 2;
        int topBorderRow = (height / 2) + 1 + squareLength / 2;

        for (int col = leftBorderCol; col <= rightBorderCol; ++col) {
            Pair p = new Pair(bottomBorderRow, col);
            set.add(p);
        }

        for (int row = bottomBorderRow; row <= topBorderRow; ++row) {
            Pair p = new Pair(row, rightBorderCol);
            set.add(p);
        }

        for (int col = rightBorderCol; col >= leftBorderCol; --col) {
            Pair p = new Pair(topBorderRow, col);
            set.add(p);
        }

        for (int row = topBorderRow; row >= bottomBorderRow; --row) {
            Pair p = new Pair(row, leftBorderCol);
            set.add(p);
        }
        return set;
    }
    /* You fill the code here */
    public void run() {
        moveToUpperRight();
        length = currentLocation.y;
        height = currentLocation.x;

        if (length < 5 || height < 5) return;

        LinkedHashSet<Pair> list = calculateGoalPoints();
        setBeepersInBag(list.size());
        for (Pair p: list) {
            moveToPoint(p);
            putBeeper();
        }
    }
}