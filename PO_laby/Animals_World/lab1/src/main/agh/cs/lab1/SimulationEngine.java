package agh.cs.lab1;

import Data.StartData;
import Data.Statistics;
import Interfaces.IWorldMap;

import java.io.IOException;
import java.util.*;


public class SimulationEngine {
    private HashMap<Vector2d, Integer> animalsPositions= new HashMap<>();
    private List<Animal> animals = new ArrayList<>();
    private IWorldMap map;
    private Statistics stats;
    private StartData data;

    public SimulationEngine(IWorldMap map, Statistics stats)
    {
        this.map=map;
        this.data = map.getData();
        this.stats=stats;
    }

    public void pickRandomParents()
    {
        List<Vector2d> listOfVectors = new ArrayList<>(animalsPositions.keySet());

        for (Vector2d vector : listOfVectors)
        {
            List<Animal> potentialParents = map.getParents(vector);
            if(potentialParents!=null)
                if(potentialParents.get(0).getHp()>=data.startAnimalEnergy/2 && potentialParents.get(1).getHp()>=data.startAnimalEnergy/2)
                    potentialParents.get(0).copulate(potentialParents.get(1));
        }
    }

    public void eat()
    {
        for (Vector2d vector: animalsPositions.keySet())
        {
            int numOfTheStrongest = map.getStrongest(vector).size();
            for (Animal animal: map.getStrongest(vector))
            {
                if(map.checkIfGrass(vector))
                {
                    animal.eatGrass(data.grassProfit ,numOfTheStrongest);
                    stats.actualiseSumOfEnergy(data.grassProfit /numOfTheStrongest*numOfTheStrongest);
                    map.deleteGrass(vector);
                    stats.actualiseNumberOfGrass(-1);
                }
            }
        }
    }

    public void addToList(Animal animal)
    {
        animals.add(animal);

        if(animalsPositions.get(animal.getPosition())==null)
        {
            animalsPositions.put(animal.getPosition(), 1 );
        }
        else
        {
            int numOfAnimalsOnPosition =  animalsPositions.get(animal.getPosition());
            animalsPositions.put(animal.getPosition(),numOfAnimalsOnPosition+1);
        }
    }


    public void deleteDead()
    {
        for (int j = 0; j < animals.size(); j++)
        {

            if(!animals.get(j).isLiving())
            {
                map.removeAnimal(animals.get(j));

                if(animalsPositions.get(animals.get(j).getPosition())==1)
                    animalsPositions.remove(animals.get(j).getPosition());
                else
                    animalsPositions.put(animals.get(j).getPosition(), animalsPositions.get(animals.get(j).getPosition())-1);

                stats.actualiseOfAvgLivingData(animals.get(j).getNumOfDaysLiving());
                animals.remove(j);
            }
        }
    }

    public void makeMove()
    {
       for (int j = 0; j < animals.size(); j++)
            {
                animals.get(j).changeOrientation();
                animals.get(j).move();
                animals.get(j).actualiseDay();
            }
    }

    public void decreaseHP()
    {
        for (int k = 0; k < animals.size(); k++)
        {
            animals.get(k).hp-=data.dayEnergyCost;
        }
    }

    public void spawnGrass()
    {
        map.placeGrassSavanna(Math.min(data.grassSpawnedPerDay/2,stats.getfreeplacesSawanna()));
        map.placeGrassJungle(Math.min(data.grassSpawnedPerDay/2, stats.getFreePlacesJungle()));
    }

    public List<Animal> getListOfAnimals()
    {
        return animals;
    }

    public void run() throws IOException {
        //definicja dnia
        stats.getAvgOfEnergy();
        stats.getAvgOfChildren();
        stats.getAvgOfLiving();
        this.deleteDead();
        this.makeMove();
        this.eat();
        this.pickRandomParents();
        stats.actualiseDays();
        this.spawnGrass();
        stats.getDominant();
        this.decreaseHP();

        //odejmujemy w statystykach tyle punktów życia ile wszytskie zwierzęta traca każdego dnia
        stats.actualiseSumOfEnergy(-(animals.size()* data.dayEnergyCost));
    }

    public HashMap<Vector2d, Integer> getAnimalsPositions() {
        return animalsPositions;
    }
}
