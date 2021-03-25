package Interfaces;

import agh.cs.lab1.Animal;
import agh.cs.lab1.Vector2d;

public interface IPositionChangeObserver {

    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);

    void hasChildren(Animal mother, Animal father, Animal baby);

    void animalIsDead(Animal animal);
}
