package Data;

import agh.cs.lab1.Animal;
import agh.cs.lab1.Genotype;
import Interfaces.IWorldMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Statistics {

    private int numForStatistics = 50;

    private int days;
    private int animalsOnMap;
    private IWorldMap map;
    private int grassOnMap;
    private int numOfGrassJungle;
    private int numOfGrassSawanna;
    private int jungleWidth;
    private int jungleHeight;
    private int sumOfEnergy;
    private int sumOfChildren;
    private int deadAnimals;
    private long sumOfLivingDays;

    private double AvgSumOfAnimals = 0;
    private double AvgSumOfGrass = 0;
    private double AvgEnergy = 0;
    private double AvgLengthOfLife = 0;
    private double AvgChildren = 0;
    private Genotype dominateGenotypeEver;


    private HashMap<Genotype, List<Animal>> domainGenotype;
    private HashMap<Genotype, Integer> domainGenotypeEver;


    public Statistics(IWorldMap map, int width, int height) {
        this.days = 1;
        this.animalsOnMap = 0;
        this.map = map;
        this.grassOnMap = 0;
        this.numOfGrassJungle = 0;
        this.numOfGrassSawanna = 0;
        this.jungleWidth = width;
        this.jungleHeight = height;
        this.domainGenotype = new HashMap<>();
        this.sumOfEnergy = 0;
        this.sumOfChildren = 0;
        this.deadAnimals = 0;
        this.sumOfLivingDays = 0;
        this.domainGenotypeEver = new HashMap<>();

    }

    public int getNumberOfDays() {
        return this.days;
    }

    public void actualiseDays() throws IOException {
        days++;
        if (days < numForStatistics) {
            AvgSumOfAnimals += (double) this.getNumerOfAnimals() / (double) numForStatistics;
            AvgSumOfGrass += (double) this.getNumerOfGrass() / (double) numForStatistics;
            AvgEnergy += this.getAvgOfEnergy() / (double) numForStatistics;
            AvgLengthOfLife += (double) this.getAvgOfLiving() / (double) numForStatistics;
            AvgChildren += (double) this.getAvgOfChildren() / (double) numForStatistics;
        } else if (days == numForStatistics) {
            this.sendToFile();
        }

    }

    public int getNumerOfAnimals() {
        return this.animalsOnMap;
    }

    public void actualiseNumberOfAnimals(int howMany) {
        animalsOnMap += howMany;
    }

    public int getNumerOfGrass() {
        return this.grassOnMap;
    }

    public void actualiseNumberOfGrass(int howMany) {
        grassOnMap += howMany;
    }


    public void actualiseNumbGrassJungle(int howMany) {
        this.numOfGrassJungle += howMany;
    }

    public void actualiseNumOfGrassSawanna(int howMany) {
        this.numOfGrassSawanna += howMany;
    }

    public int getfreeplacesSawanna() {
        return map.getBoundaries().x * map.getBoundaries().y - (jungleWidth * jungleHeight) - numOfGrassSawanna;
    }

    public int getFreePlacesJungle() {
        return jungleWidth * jungleHeight - numOfGrassJungle;
    }

    public void addGenotype(Animal animal) {
        if (!domainGenotype.containsKey(animal.getGenotype())) {
            List<Animal> animalsList = new LinkedList<>();
            animalsList.add(animal);
            this.domainGenotype.put(animal.getGenotype(), animalsList);
        } else {
            this.domainGenotype.get(animal.getGenotype()).add(animal);
        }

        if (days < numForStatistics) {
            if (domainGenotypeEver.get(animal.getGenotype()) == null)
                this.domainGenotypeEver.put(animal.getGenotype(), 1);
            else
                this.domainGenotypeEver.put(animal.getGenotype(), domainGenotypeEver.get(animal.getGenotype()) + 1);
        }
    }

    public void deleteGenotype(Animal animal) {
        this.domainGenotype.get(animal.getGenotype()).remove(animal);
        if (this.domainGenotype.get(animal.getGenotype()).size() == 0)
            this.domainGenotype.remove(animal.getGenotype());
    }

    public Genotype getDominant() {
        int max = 0;
        Genotype domain = null;
        for (Genotype i : domainGenotype.keySet()) {
            if (domainGenotype.get(i).size() > max) {
                max = domainGenotype.get(i).size();
                domain = i;
            }
        }
        return domain;
    }

    public int getNumberOfAnimalsWithDominantGenotype() {
        if(domainGenotype.isEmpty())
            return 0;
        return domainGenotype.get(this.getDominant()).size();
    }


    public void actualiseSumOfEnergy(int hp) {
        this.sumOfEnergy += hp;
    }

    public double getAvgOfEnergy() {
        if (animalsOnMap > 0)
            return (double) sumOfEnergy / animalsOnMap;
        else
            return 0;
    }

    public void actualiseSumOfChildren(int num) {
        this.sumOfChildren += num;
    }

    public int getAvgOfChildren() {
        if (animalsOnMap > 0)
            return sumOfChildren / animalsOnMap;
        else
            return 0;
    }

    public void actualiseOfAvgLivingData(int numOfDaysLiving) {
        this.deadAnimals++;
        this.sumOfLivingDays += numOfDaysLiving;
    }

    public long getAvgOfLiving() {
        if (deadAnimals > 0)
            return sumOfLivingDays / deadAnimals;
        else
            return 0;
    }

    public HashMap<Genotype, List<Animal>> getDomainGenotype() {
        return domainGenotype;
    }

    public Genotype getDominantGenotypeEver() {
        int max = 0;
        Genotype maxGenes = null;

        for (Genotype G : domainGenotypeEver.keySet()) {
            if (domainGenotypeEver.get(G) > max) {
                max = domainGenotypeEver.get(G);
                maxGenes = G;
            }
        }
        return maxGenes;
    }

    public void setNumForStatistics(int numForStatistics) {
        this.numForStatistics = numForStatistics;
    }

    public void sendToFile() throws IOException {

        List<String> statisticsData = new ArrayList<>();
        statisticsData.add("Average statistics of " + numForStatistics + " days");
        statisticsData.add("Average number of animals: " + AvgSumOfAnimals);
        statisticsData.add("Average number of grasses: " + AvgSumOfGrass);
        statisticsData.add("Average length of life of dead animals: " + AvgLengthOfLife);
        statisticsData.add("Average energy level: " + AvgEnergy);
        statisticsData.add("The most popular genome (according to a number of animals which had it): " + getDominantGenotypeEver());

        Files.write(Paths.get("statisticsOfWorld.txt"), statisticsData, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

    }
}




