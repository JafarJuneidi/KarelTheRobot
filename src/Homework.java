import stanford.karel.SuperKarel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
//        System.out.println("x: " + currentLocation.x + ", y: " + currentLocation.y);
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
    private ArrayList<Pair> calculateGoalPoints() {
        HashSet<Pair> set = new HashSet<Pair>();
        ArrayList<Pair> list = new ArrayList<Pair>();

        for (int row = height; row > 0; --row) {
            Pair p = new Pair(row, (length / 2) + 1);
            if (set.contains(p)) continue;
            set.add(p);
            list.add(p);
            if (Helper.isEven(length)) {
                p = new Pair(row, length / 2);
                if (set.contains(p)) break;
                list.add(p);
                set.add(p);
            }
        }

        for (int col = length; col > 0; --col) {
            Pair p = new Pair((height / 2) + 1, col);
            if (set.contains(p)) continue;
            set.add(p);
            list.add(p);
            if (Helper.isEven(height)) {
                p = new Pair(height / 2, col);
                if (set.contains(p)) break;
                list.add(p);
                set.add(p);
            }
        }

//        for (int col = 1; col < length - 1; ++col) {
//            set.add(new Pair(height - 2, col));
//            set.add(new Pair(1, col));
//        }
//
//        for (int row = 1; row < height - 1; ++row) {
//            set.add(new Pair(row, length - 2));
//            set.add(new Pair(row, 1));
//        }

        return list;
    }
    /* You fill the code here */
    public void run() {
        moveToUpperRight();
        length = currentLocation.y;
        height = currentLocation.x;

        if (length < 5 || height < 5) {
            return;
        }


        ArrayList<Pair> list = calculateGoalPoints();
        setBeepersInBag(list.size());
        System.out.println(list.size());
        for (Pair p: list) {
            System.out.println("x: " + p.x + ", y: " + p.y);
            moveToPoint(p);
            putBeeper();
        }
    }
}