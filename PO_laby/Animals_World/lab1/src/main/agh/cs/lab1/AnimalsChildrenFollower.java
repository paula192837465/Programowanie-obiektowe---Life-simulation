package agh.cs.lab1;

import Interfaces.IPositionChangeObserver;

public class AnimalsChildrenFollower implements IPositionChangeObserver {


    private int descendants;
    private AnimalObserver mainAnimalObserver;
    
    public AnimalsChildrenFollower(AnimalObserver mainAnimalObserver)
    {
        this.descendants=0;
        this.mainAnimalObserver = mainAnimalObserver;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
    }

    @Override
    public void hasChildren(Animal mother, Animal father, Animal baby) {

        this.descendants++;
        baby.addObserver(this);
        mainAnimalObserver.childHasChildren(baby);
    }

    @Override
    public void animalIsDead(Animal animal) {

    }



}
