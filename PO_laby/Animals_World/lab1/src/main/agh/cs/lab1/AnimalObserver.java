package agh.cs.lab1;

import Interfaces.IPositionChangeObserver;
import Visualization.RenderPanel;

import java.util.LinkedList;
import java.util.List;

public class AnimalObserver implements IPositionChangeObserver {

    private int children;
    private int descendants;
    private int daysOfFollowing;
    private int startDay;
    private Animal animal;
    AnimalsChildrenFollower follower;
    List<Animal> ChildrenList = new LinkedList<>();
    RenderPanel renderPanel;


    public AnimalObserver(Animal animal, RenderPanel renderPanel)
    {
        this.children=0;
        this.descendants=0;
        this.startDay=0;
        this.animal= animal;
        this.animal.addObserver(this);
        this.follower = new AnimalsChildrenFollower(this);
        this.renderPanel = renderPanel;
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
    }

    @Override
    public void hasChildren(Animal mother, Animal father, Animal baby) {
        this.children++;
        this.descendants++;
        baby.addObserver(follower);
        ChildrenList.add(baby);
    }

    @Override
    public void animalIsDead(Animal animal) {
        animal.removeObserver(this);
        this.endOfFollowing();
    }


    public void childHasChildren(Animal animal)
    {
        this.descendants++;
        ChildrenList.add(animal);
    }

    public void actualiseDayOfFollowing()
    {
        this.startDay++;

        if(this.startDay>daysOfFollowing)
        {
            animal.removeObserver(this);
            this.endOfFollowing();
        }
    }

    public void endOfFollowing()
    {
        for (Animal animal: ChildrenList) {
                animal.removeObserver(follower);
        }
        renderPanel.deleteFrameOfObserver(this);
    }

    public int getChildren() {
        return children;
    }

    public Animal getAnimal() {
        return animal;
    }

    public int getDescendants() {
        return descendants;
    }

    public void setDaysOfFollowing(int daysOfFollowing) {
        this.daysOfFollowing = daysOfFollowing;
    }
}
