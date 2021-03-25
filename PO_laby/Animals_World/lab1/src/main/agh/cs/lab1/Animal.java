package agh.cs.lab1;

import Interfaces.IPositionChangeObserver;
import Interfaces.IWorldMap;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Animal extends AbstractWorldMapElement implements Comparable<Animal> {

    private MapDirection orientation;
    private IWorldMap map;
    private final List<IPositionChangeObserver> Observers = new ArrayList();
    int hp;
    private List<Animal> children;
    private  int descendants;
    Genotype Genes;
    private Pair<Animal, Animal> parents;
    private int dayOnTheWorld;
    private Color animalColor = new Color(0,0,0);
    private int death=-1;


    public Animal(IWorldMap map) {
        this.map = map;
        this.addObserver((IPositionChangeObserver) this.map);
        this.position = randomPosition(map.getBoundaries().x, map.getBoundaries().y,0,0);
        this.orientation = randomOrientation();
        this.hp = map.getData().startAnimalEnergy;
        this.children=new ArrayList<>();
        this.descendants=0;
        this.parents = new Pair<>(null, null);
        this.Genes = new Genotype();
        map.place(this);
        this.dayOnTheWorld = 0;

    }

    public Animal (IWorldMap map, Animal mother, Animal father, int hp)
    {
        this.map = map;
        this.addObserver((IPositionChangeObserver) this.map);
        this.position = randomPosition(mother.getPosition().x+1, mother.getPosition().y+1,mother.getPosition().x-1, mother.getPosition().y-1);
        this.orientation = randomOrientation();
        this.hp = hp;
        this.children = new ArrayList<>();
        this.descendants=0;
        this.parents = new Pair<>(mother, father);
        this.Genes = new Genotype(mother, father);
        map.place(this);
        this.dayOnTheWorld = 0;
    }

    private Vector2d randomPosition(int bound1, int bound2, int bound3, int bound4)
    {
        Random r = new Random();
        Vector2d randomPosition;
        do
        {
            randomPosition = new Vector2d(r.nextInt(bound1-bound3)+bound3, r.nextInt(bound2-bound4)+bound4);
        }
        while(randomPosition.x==bound1-1 && randomPosition.y==bound2-1);
        return randomPosition;
    }

    private MapDirection randomOrientation()
    {
        Random r = new Random();
        MapDirection direction =MapDirection.values()[r.nextInt(8)];
        return direction;
    }

    public String toString() {
        switch (orientation) {
            case NORTH:
                return " N ";
            case EAST:
                return " E ";
            case SOUTH:
                return " S ";
            case WEST:
                return " W ";
            case NORTH_EAST:
                return "NE ";
            case SOUTH_EAST:
                return "SE ";
            case SOUTH_WEST:
                return "SW ";
            case NORTH_WEST:
                return "NW ";
            default:
                return null;
        }
    }

    private int selectDirection(Genotype Genes)
    {
        Random r = new Random();
        return  Genes.Genes[r.nextInt(32)];
    }

    public void changeOrientation()
    {
        int angle = selectDirection(this.Genes);
        switch (angle)
        {
            case 1: orientation=orientation.next(); break;
            case 2: orientation=orientation.next().next(); break;
            case 3: orientation=orientation.next().next().next(); break;
            case 4: orientation=orientation.next().next().next().next(); break;
            case 5: orientation=orientation.previous().previous().previous(); break;
            case 6: orientation=orientation.previous().previous(); break;
            case 7: orientation=orientation.previous(); break;
        }
    }

    public void move()
    {
        Vector2d newPosition = position.add(orientation.toUnitVector());

        if(newPosition.x>= map.getBoundaries().x)
            newPosition.x = newPosition.x % map.getBoundaries().x;
        else if(newPosition.x<0)
            newPosition.x = map.getBoundaries().x + newPosition.x;

        if(newPosition.y>= map.getBoundaries().y)
            newPosition.y = abs(newPosition.y) % map.getBoundaries().y;
        else if(newPosition.y<0)
            newPosition.y = map.getBoundaries().y + newPosition.y;

        this.positionChanged(position, newPosition);
        position = newPosition;

    }

    public void eatGrass(int GrassEnergy, int numOfAnimals)
    {
        this.hp+=Math.floor(GrassEnergy/numOfAnimals);
    }

    public void copulate(Animal partner)
    {


        Animal baby= new Animal(this.map,this,partner, this.hp/4+partner.hp/4);
        this.children.add(baby);
        partner.children.add(baby);
        map.subtractCopulationCosts(this.hp/4+partner.hp/4);
        this.hp-=this.hp/4;
        partner.hp-=partner.hp/4;
        map.addChildren(2);

        for (IPositionChangeObserver obs: Observers) {
            obs.hasChildren(this, partner, baby);
        }
    }

    public void addObserver (IPositionChangeObserver observer)
    {
        this.Observers.add(observer);
    }

    public void removeObserver (IPositionChangeObserver observer)
    {
        this.Observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        for(int i=0;i<Observers.size();i++)
        {
            this.Observers.get(i).positionChanged(oldPosition, newPosition, this);
        }
    }

    @Override
    public int compareTo(Animal o) {
        return this.hp-o.hp;
    }

    public int getHp()
    {
        return this.hp;
    }

    public Genotype getGenotype()
    {
        return this.Genes;
    }

    public void setColor(Color color)
    {
        this.animalColor=color;

    }

    public Color getColorOfAnimal()
    {
        double alphaCalculator = (double)this.hp/(double)(map.getData().startAnimalEnergy*4);
        int alpha = (int) (alphaCalculator*255)+50;

        if(alpha>255)
            alpha=255;

        Color color = new Color(animalColor.getRed(), animalColor.getGreen(), animalColor.getBlue(), alpha);
        return color;
    }

    public void actualiseDay()
    {
        this.dayOnTheWorld++;
    }

    public int getNumOfDaysLiving()
    {
        return this.dayOnTheWorld;
    }

    public boolean isLiving()
    {
        if(this.hp>0)
            return true;
        else
        {
            List<IPositionChangeObserver> copyObservers = new ArrayList<>(this.Observers);
            for (IPositionChangeObserver obs: copyObservers) {
                obs.animalIsDead(this);
            }
            if(death<0)
                this.death=map.getStats().getNumberOfDays();
            return false;
        }

    }

    public int getDeath() {
        return death;
    }
}
