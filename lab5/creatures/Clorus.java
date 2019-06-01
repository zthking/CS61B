package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Color color() {
        return color(34, 0, 231);
    }

    @Override
    public void attack(Creature c) {
        energy = energy + c.energy();
    }

    @Override
    public void move() {
        energy = energy - 0.03;
    }

    @Override
    public void stay() {
        energy = energy - 0.01;
    }

    public Clorus replicate() {
        energy = energy / 2;
        return new Clorus(energy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        boolean anyPlip = false;
        boolean noEmptySpace = true;

        for (Map.Entry<Direction, Occupant> pair : neighbors.entrySet()) {
            boolean isEmpty = pair.getValue().name().equals("empty");
            if (isEmpty) {
                emptyNeighbors.add(pair.getKey());
            }
            if (noEmptySpace) {
                noEmptySpace = !isEmpty;
            }
            if (!anyPlip) {
                anyPlip = pair.getValue().name().equals("plip");
            }
        }

        if (noEmptySpace) {
            return new Action(Action.ActionType.STAY);
        } else if (anyPlip) {
            if (neighbors.get(Direction.TOP).name().equals("plip")) {
                return new Action(Action.ActionType.ATTACK, Direction.TOP);
            } else if (neighbors.get(Direction.BOTTOM).name().equals("plip")) {
                return new Action(Action.ActionType.ATTACK, Direction.BOTTOM);
            } else if (neighbors.get(Direction.LEFT).name().equals("plip")) {
                return new Action(Action.ActionType.ATTACK, Direction.LEFT);
            } else if (neighbors.get(Direction.RIGHT).name().equals("plip")) {
                return new Action(Action.ActionType.ATTACK, Direction.RIGHT);
            } else {
                return new Action(Action.ActionType.STAY);
            }
        } else if (this.energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }


}
