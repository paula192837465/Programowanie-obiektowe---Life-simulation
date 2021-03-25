package agh.cs.lab1;

import Data.StartData;
import Data.Statistics;
import Interfaces.IPositionChangeObserver;
import Interfaces.IWorldMap;

import java.util.*;

public class MapWithJungle implements IWorldMap, IPositionChangeObserver {

    protected final HashMap<Vector2d, List<Animal>> animalsMap = new HashMap<>();
    private final int width, height, widthJungle, heightJungle;
    private final ArrayList<Grass> grass = new ArrayList<>();
    private final HashMap<Vector2d, Grass> grassHash = new HashMap();
    private final Vector2d upper;
    private final Vector2d lower;
    protected Statistics stats;
    protected SimulationEngine engine;
    StartData data;
    private Vector2d lowerLeftOfJungle;
    private Vector2d upperRightOfJungle;


    public MapWithJungle(StartData data) {
        this.data = data;
        this.width = data.widthOfMap;
        this.height = data.heightOfMap;
        this.widthJungle = (int) (data.widthOfMap * data.JungleRatio);
        this.heightJungle = (int) (data.heightOfMap * data.JungleRatio);
        upper = new Vector2d(this.width - 1, this.height - 1);
        lower = new Vector2d(0, 0);
        this.lowerLeftOfJungle = new Vector2d((width - widthJungle) / 2, (height - heightJungle) / 2);
        this.upperRightOfJungle = new Vector2d(lowerLeftOfJungle.x + widthJungle - 1, lowerLeftOfJungle.y + heightJungle - 1);
        this.stats = new Statistics(this, widthJungle, heightJungle);
        this.engine = new SimulationEngine(this, stats);

    }

    public boolean canMoveTo(Vector2d position) {
        return position.precedes(upper) && position.follows(lower) && !this.isOccupied(position);
    }

    public void removeAnimal(Animal animal) {
        stats.actualiseNumberOfAnimals(-1);
        animalsMap.get(animal.getPosition()).remove(animal);
        stats.deleteGenotype(animal);

        if (animalsMap.get(animal.getPosition()).size() == 0)
            animalsMap.remove(animal.getPosition());
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animalToMove) {
        if (animalsMap.get(newPosition) != null) {
            int i = 0;
            while (i < animalsMap.get(newPosition).size() && animalsMap.get(newPosition).get(i).hp > animalToMove.hp)
                i++;

            if (i >= animalsMap.get(newPosition).size())
                animalsMap.get(newPosition).add(animalToMove);
            else
                animalsMap.get(newPosition).add(i, animalToMove);
        } else {
            List<Animal> storage = new ArrayList<Animal>();
            storage.add(animalToMove);
            animalsMap.put(newPosition, storage);
        }

        if (engine.getAnimalsPositions().get(newPosition) == null)
        {
            engine.getAnimalsPositions().put(newPosition, 1);
        } else {
            int temp = engine.getAnimalsPositions().get(newPosition) + 1;
            engine.getAnimalsPositions().put(newPosition, temp);
        }

        if (animalsMap.get(oldPosition).size() == 1) {
            animalsMap.remove(oldPosition);
            engine.getAnimalsPositions().remove(oldPosition);
        } else {
            animalsMap.get(oldPosition).remove(animalToMove);
            engine.getAnimalsPositions().put(oldPosition, engine.getAnimalsPositions().get(oldPosition) - 1);
        }
    }

    @Override
    public void hasChildren(Animal mother, Animal father, Animal baby) {
    }

    @Override
    public void animalIsDead(Animal animal) {

    }

    public boolean place(Animal animal) {
        if (!(this.objectAt(animal.getPosition()) == Animal.class)) {
            engine.addToList(animal);

            if (animalsMap.get(animal.getPosition()) != null) {
                int i = 0;
                while (i < animalsMap.get(animal.getPosition()).size() && animalsMap.get(animal.getPosition()).get(i).hp > animal.hp)
                    i++;

                if (i >= animalsMap.get(animal.getPosition()).size())
                    animalsMap.get(animal.getPosition()).add(animal);
                else
                    animalsMap.get(animal.getPosition()).add(i, animal);
            } else {
                List<Animal> storage = new ArrayList<Animal>();
                storage.add(animal);
                animalsMap.put(animal.getPosition(), storage);
            }
            stats.actualiseNumberOfAnimals(1);
            stats.addGenotype(animal);
            stats.actualiseSumOfEnergy(animal.hp);

            return true;
        }
        throw new IllegalArgumentException("You cannot place an animal at position: " + animal.getPosition());
    }

    public boolean isOccupied(Vector2d position) {
        return (this.objectAt(position) != null);
    }

    public Object objectAt(Vector2d position) {
        if (animalsMap.containsKey(position))
            return animalsMap.get(position);
        if (grassHash.containsKey(position))
            return grassHash.get(position);
        return null;
    }

    @Override
    public Vector2d getBoundaries() {
        return new Vector2d(width, height);
    }

    public Vector2d getBoundariesOfJungle() {
        return new Vector2d(widthJungle, heightJungle);
    }

    public Vector2d getLowerLeftOfJungle() {
        return lowerLeftOfJungle;
    }


    public void placeGrassJungle(int grassNumJungle) {
        int i = 0, k = 0;
        while (i != grassNumJungle && k <= widthJungle * heightJungle) {
            Grass cluster = new Grass(choosePosition(grassNumJungle, lowerLeftOfJungle, widthJungle, heightJungle));
            if (this.objectAt(cluster.getPosition()) == null) {
                grass.add(cluster);
                grassHash.put(cluster.getPosition(), cluster);
                stats.actualiseNumberOfGrass(1);
                stats.actualiseNumbGrassJungle(1);
            } else
                i--;
            i++;
            k++;
        }
    }

    public void placeGrassSavanna(int grassNumSawanna) {
        int i = 0, k = 0;

        while (i != grassNumSawanna && k <= height * width) {
            Grass cluster = new Grass(choosePosition(grassNumSawanna, lower, width, height));
            if ((this.objectAt(cluster.getPosition()) == null) && !(cluster.getPosition().precedes(upperRightOfJungle) && (cluster.getPosition().follows(lowerLeftOfJungle)))) {
                grass.add(cluster);
                grassHash.put(cluster.getPosition(), cluster);
                stats.actualiseNumberOfGrass(1);
                stats.actualiseNumOfGrassSawanna(1);
            } else
                i--;
            i++;
            k++;
        }
    }

    private Vector2d choosePosition(int Amount, Vector2d lower, int widthArea, int heightArea) {
        Random generator = new Random();
        return new Vector2d(generator.nextInt(widthArea) + lower.x, generator.nextInt(heightArea) + lower.y);
    }

    public List<Animal> getParents(Vector2d position) //wyciąganie z tablicy njmocniejszych zwierząt
    {
        Random r = new Random();
        List<Animal> result = animalsMap.get(position);
        List<Animal> result2 = new ArrayList<>();
        int k = result.size() - 1, i = -1, j = -1, max1 = 0, max2 = 0;

        if (result.size() <= 1) {
            return null;
        }
        while (k >= 0) {
            if (result.get(k).getHp() >= max1) {
                max1 = result.get(k).getHp();
                i = k;
            } else if (i != -1 && result.get(k).getHp() >= max2) {
                max2 = result.get(k).getHp();
                j = k;
            }
            k -= 1;
        }
        int mother = -1;
        int father = -1;

        if (i <= result.size() - 2 && j == -1) {
            mother = r.nextInt(result.size() - i) + i;

            do {
                father = r.nextInt(result.size() - i) + i;

            } while (father == mother);
        } else if (j != -1) {
            mother = i;
            father = r.nextInt(result.size() - i - j) + j;
        }

        result2.add(result.get(mother));
        result2.add(result.get(father));

        return result2;
    }

    public List<Animal> getStrongest(Vector2d position) {
        List<Animal> result = animalsMap.get(position);
        List<Animal> strongest = new LinkedList<>();
        int max = 0;
        for (int i = result.size() - 1; i >= 0; i--) {
            if (result.get(i).getHp() >= max) {
                strongest.add(result.get(i));
                max = result.get(i).getHp();
            }
        }
        return strongest;
    }

    public boolean checkIfGrass(Vector2d position) {
        if (grassHash.get(position) != null)
            return true;
        else
            return false;
    }

    public void deleteGrass(Vector2d position) {
        grass.remove(grassHash.get(position));
        grassHash.remove(position);

        if (position.follows(lowerLeftOfJungle) && position.precedes(upperRightOfJungle))
            stats.actualiseNumbGrassJungle(-1);
        else
            stats.actualiseNumOfGrassSawanna(-1);
    }

    public List<Grass> getListOfGrass() {
        return grass;
    }

    public List<Animal> getListOfAnimals() {
        return engine.getListOfAnimals();
    }

    public HashMap<Vector2d, List<Animal>> getAnimalsMap() {
        return animalsMap;
    }

    public Animal getStrongestAnimalAt(Vector2d position) {
        return animalsMap.get(position).get(animalsMap.get(position).size() - 1);
    }

    public Statistics getStats() {
        return this.stats;
    }

    public SimulationEngine getEngine() {
        return this.engine;
    }

    public void subtractCopulationCosts(int CopulationEnergyCost) {
        stats.actualiseSumOfEnergy(-CopulationEnergyCost);
    }

    public void addChildren(int howMany) {
        stats.actualiseSumOfChildren(howMany);
    }

    public StartData getData() {
        return data;
    }
}
