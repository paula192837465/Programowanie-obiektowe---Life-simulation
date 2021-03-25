package Visualization;

import agh.cs.lab1.Animal;
import agh.cs.lab1.AnimalObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimalFollowerFrame extends JFrame {

    AnimalPanel panel;
    Animal animal;
    AnimalObserver animalObserver;
    SelectNumberFrame selectFrame;

    public AnimalFollowerFrame(Animal animal, AnimalObserver animalObserver)
    {

        setSize(510, 200);
        setVisible(true);
        this.animal=animal;
        this.animalObserver=animalObserver;
        panel= new AnimalPanel(animal, animalObserver);
        selectFrame = new SelectNumberFrame(this);
        this.add(panel);


    }

    public void sendDaysOfFollowing (int days)
    {
        animalObserver.setDaysOfFollowing(days);
    }

}
