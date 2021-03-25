package Interfaces;

import Data.StartData;
import Data.Statistics;
import agh.cs.lab1.Animal;
import agh.cs.lab1.Grass;
import agh.cs.lab1.SimulationEngine;
import agh.cs.lab1.Vector2d;

import java.util.HashMap;
import java.util.List;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    default boolean canMoveTo(Vector2d position) {
        return false;
    }

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */
    boolean place(Animal animal);

    /**
     * Move the animal on the map according to the provided move directions. Every
     * n-th direction should be sent to the n-th animal on the map.
     *
     //* @param repeats
     *            Array of move directions.
     */
    //void run(int repeats) throws InterruptedException;
    //void  eatGrass(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2d position);

    Vector2d getBoundaries();
    Vector2d getBoundariesOfJungle();
    void removeAnimal(Animal animal);
    //void Copulate(Animal animal);
    void placeGrassSavanna(int numer);
    void placeGrassJungle(int numer);

    List<Animal> getStrongest(Vector2d element);
    boolean checkIfGrass(Vector2d position);
    void deleteGrass(Vector2d position);
    List<Grass> getListOfGrass();
    Statistics getStats();
    SimulationEngine getEngine();
    Vector2d getLowerLeftOfJungle();
    void subtractCopulationCosts(int CopulationEnergyCost);
    void addChildren(int howMany);
    HashMap<Vector2d, List<Animal>> getAnimalsMap();
    Animal getStrongestAnimalAt(Vector2d position);
    StartData getData();

    List<Animal> getParents(Vector2d vector);


}
