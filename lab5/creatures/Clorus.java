package creatures;

import huglife.*;

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

    private Deque<Direction> typeNeighbors(String type, Map<Direction, Occupant> neighbors) {
        Deque<Direction> myNeighbors = new ArrayDeque<>();
        for (Map.Entry<Direction, Occupant> pair : neighbors.entrySet()) {
            if (pair.getValue().name().equals(type)) {
                myNeighbors.add(pair.getKey());
            }
        }
        return myNeighbors;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = typeNeighbors("empty", neighbors);
        Deque<Direction> plipNeighbors = typeNeighbors("plip", neighbors);

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plipNeighbors.size() != 0) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        } else if (this.energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }
}
