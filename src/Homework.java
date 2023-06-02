import stanford.karel.SuperKarel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Homework extends SuperKarel {
    int length;
    int height;
    Pair currentLocation;
    MutableDirection direction;
    int numberOfMoves;
    public void init() {
        currentLocation = new Pair(1, 1);
        direction = new MutableDirection(Direction.RIGHT);
        numberOfMoves = 0;
    }
    public void move() {
        if (frontIsBlocked()) return;

        super.move();
        currentLocation.updatePairBasedOnDirection(direction.getDirection());
        numberOfMoves++;
    }
    public void turnLeft() {
        super.turnLeft();
        direction.turnLeft();
    }
    public void turnRight() {
        super.turnRight();
        direction.turnRight();
    }
    private void moveToUpperRight() {
        while(frontIsClear()) {
            move();
        }

        turnLeft();
        while(frontIsClear()) {
            move();
        }
    }
    public void moveToPoint(Pair point, boolean addPointWhileMoving) {
        while (!currentLocation.equals(point)) {
            int distance = currentLocation.distanceBetweenPairs(point);
            Pair nextMoveLocation = currentLocation.getPairAfterMove(direction.getDirection());
            if (nextMoveLocation.distanceBetweenPairs(point) >= distance) {
                Direction directionToTheLeft = direction.getDirectionToTheLeft();
                Direction directionToTheRight = direction.getDirectionToTheRight();

                Pair leftTurnMoveLocation = currentLocation.getPairAfterMove(directionToTheLeft);
                Pair rightTurnMoveLocation = currentLocation.getPairAfterMove(directionToTheRight);

                if (leftTurnMoveLocation.distanceBetweenPairs(point) < distance) {
                    turnLeft();
                } else if (rightTurnMoveLocation.distanceBetweenPairs(point) < distance) {
                    turnRight();
                } else {
                    turnLeft();
                    turnLeft();
                }
            }
            move();
            if (addPointWhileMoving && !beepersPresent()) putBeeper();
        }
    }
    private ArrayList<Pair> calculateCrossPoints() {
        LinkedHashSet<Pair> set = new LinkedHashSet<Pair>();

        for (int row = height; row > 0; --row) {
            Pair p = new Pair(row, (length + 1) / 2);
            set.add(p);
        }

        if (Helper.isEven(length)) {
            for (int row = 1; row <= height; ++row) {
                Pair p = new Pair(row, (length / 2) + 1);
                set.add(p);
            }
        }

        if (Helper.isEven(height)) {
            for (int col = length; col > 0; --col) {
                Pair p = new Pair((height + 1) / 2, col);
                set.add(p);
            }
        }

        for (int col = 1; col <= length; ++col) {
            Pair p = new Pair((height / 2) + 1, col);
            set.add(p);
        }

        return new ArrayList<Pair>(set);
    }
    public ArrayList<Pair> calculateSquarePoints() {
        ArrayList<Pair> list = new ArrayList<Pair>();

        int squareLength;
        if (height < length) {
            squareLength = height - 3;
        } else {
            squareLength = length - 3;
        }
        int leftBorderCol = ((length + 1) / 2) - squareLength / 2;
        int rightBorderCol = (length / 2) + 1 + squareLength / 2;
        int bottomBorderRow = ((height + 1) / 2) - squareLength / 2;
        int topBorderRow = (height / 2) + 1 + squareLength / 2;

        Pair topLeft = new Pair(topBorderRow, leftBorderCol);
        Pair topRight = new Pair(topBorderRow, rightBorderCol);
        Pair bottomRight = new Pair(bottomBorderRow, rightBorderCol);
        Pair bottomLeft = new Pair(bottomBorderRow, leftBorderCol);
        list.add(topRight);
        list.add(bottomRight);
        list.add(bottomLeft);
        list.add(topLeft);
        // because I need to circle back to this point
        list.add(topRight);

        return list;
    }
    /* You fill the code here */
    public void run() {
        init();
        moveToUpperRight();
        length = currentLocation.y;
        height = currentLocation.x;

        if (length < 5 || height < 5) return;

        setBeepersInBag(10000);
        ArrayList<Pair> crossPoints = calculateCrossPoints();
        for (Pair p: crossPoints) {
            moveToPoint(p, false);
            putBeeper();
        }

        ArrayList<Pair> squarePoints = calculateSquarePoints();
        moveToPoint(squarePoints.get(0), false);
        for (int i = 1; i < squarePoints.size(); ++i) {
            moveToPoint(squarePoints.get(i), true);
        }
        System.out.println("Number of moves: " + numberOfMoves);
    }
}