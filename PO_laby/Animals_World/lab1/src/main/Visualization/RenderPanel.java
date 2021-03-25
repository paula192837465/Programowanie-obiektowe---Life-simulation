package Visualization;

import Data.Statistics;
import Interfaces.IWorldMap;
import agh.cs.lab1.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RenderPanel extends JPanel implements MouseListener {
    public IWorldMap map;
    public MapVisualization simulation;
    private int widthScale;
    private int heightScale;
    private List<AnimalObserver> listOfMainAnimalObserver;
    private AnimalFollowerFrame followFrame;
    private int daysOfFollowing=0;
    private Statistics stats;
    private HashMap<AnimalObserver, AnimalPanel> observerMap = new HashMap<>();

    public RenderPanel(IWorldMap map, MapVisualization simulation) {
        this.map = map;
        this.simulation = simulation;
        addMouseListener(this);
        this.listOfMainAnimalObserver= new LinkedList<>();
        this.stats=map.getStats();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize((int) (simulation.frame.getWidth() * 0.6), simulation.frame.getHeight() - 38);
        this.setLocation((int) (0.4 * simulation.frame.getWidth()), 0);
        int width = this.getWidth();
        int height = this.getHeight();
        widthScale = Math.round(width / map.getBoundaries().x);
        heightScale = height / map.getBoundaries().y;

        //draw Savanna
        g.setColor(new Color(232, 183, 104));
        g.fillRect(0, 0, width, height);

        //draw Jungle
        g.setColor(new Color(17, 78, 6)); //to pozmieniać
        g.fillRect(map.getLowerLeftOfJungle().x * widthScale,
                map.getLowerLeftOfJungle().y * heightScale,
                map.getBoundariesOfJungle().x * widthScale,
                map.getBoundariesOfJungle().y * heightScale);

        //draw Grass
        for (Grass grass : map.getListOfGrass()) {
            g.setColor(grass.toColor());
            int y = grass.getPosition().y * heightScale;
            int x = grass.getPosition().x * widthScale;
            g.fillOval(x, y, widthScale, heightScale);
        }
        //draw Animals
        for (Vector2d vector : map.getAnimalsMap().keySet()) {
            Animal a = map.getStrongestAnimalAt(vector);
            g.setColor(a.getColorOfAnimal());
            int y = a.getPosition().y * heightScale;
            int x = a.getPosition().x * widthScale;
            g.fillOval(x, y, widthScale, heightScale);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Vector2d position = new Vector2d(e.getX()/widthScale, e.getY()/heightScale);
        System.out.println(position.toString());
        if(map.objectAt(position)!=null)
        {
            if( map.objectAt(position).getClass()!=Grass.class)
            {
                Animal a = map.getStrongestAnimalAt(position);
                a.setColor(new Color(0xC11616));

                AnimalObserver mainAnimalObserver = new AnimalObserver(a, this);
                listOfMainAnimalObserver.add(mainAnimalObserver);
                a.addObserver(mainAnimalObserver);
                followFrame = new AnimalFollowerFrame(a, mainAnimalObserver);
                followFrame.panel.repaint();

                observerMap.put(mainAnimalObserver, followFrame.panel);
            }
        }
        this.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void actualiseObserversDays()
    {
        for (AnimalObserver mainAnimalObserver: listOfMainAnimalObserver) {
            mainAnimalObserver.actualiseDayOfFollowing();
        }


        for(AnimalPanel animalPanel : observerMap.values()){
            animalPanel.repaint();
        }
    }

    public void deleteFrameOfObserver(AnimalObserver animalObserver){
        animalObserver.getAnimal().setColor(new Color(0));
        observerMap.remove(animalObserver);

    }

    public void paintDominantGenotype(boolean pause)
    {

        Genotype dominant = stats.getDominant();
        List<Animal> animalsWithDominantGenotype= new LinkedList<>(stats.getDomainGenotype().get(dominant));
        for (Animal animal : animalsWithDominantGenotype)
        {
            if(pause)
                animal.setColor(new Color(0x3210B1));
            else
                animal.setColor(new Color(0));
        }

        this.repaint();
    }


}
