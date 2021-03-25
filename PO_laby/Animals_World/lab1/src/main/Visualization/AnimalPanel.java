package Visualization;

import agh.cs.lab1.Animal;
import agh.cs.lab1.AnimalObserver;

import javax.swing.*;
import java.awt.*;

public class AnimalPanel extends JPanel {

    Animal animal;
    AnimalObserver animalObserver;

    public AnimalPanel(Animal animal, AnimalObserver observer)
    {
        this.animal=animal;
        this.animalObserver=observer;
    }
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawString("Genotype: " + animal.getGenotype(), 10,  20);
        g.drawString("Liczba Dzieci: " + animalObserver.getChildren(), 10,  40);
        g.drawString("Liczba potomków: " + animalObserver.getDescendants(), 10,  60);
        if(animal.isLiving())
            g.drawString("Energia: " + animal.getHp(), 10,  80);
        else
            g.drawString("Zmarl dnia: " + animal.getDeath(), 10,  80);

    }
}
