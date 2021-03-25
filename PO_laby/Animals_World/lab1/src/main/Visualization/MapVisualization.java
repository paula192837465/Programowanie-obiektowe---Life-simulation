package Visualization;

import Data.StartData;
import Data.Statistics;
import Interfaces.IWorldMap;
import agh.cs.lab1.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MapVisualization implements ActionListener {

    public final int delay;
    public IWorldMap map;
    public SimulationEngine engine;
    public int startNumOfAnimals;
    public int grassSpawnedInEachDay;
    public Statistics stats;

    public JFrame frame;
    public RenderPanel renderPanel;
    public StatsPanel statsPanel;
    public Timer timer;
    StartData data;


    public MapVisualization(IWorldMap map, int delay, int startNumOfAnimals, int grassSpawnedInEachDay) {
        this.map = map;
        this.data = map.getData();
        this.delay = delay;
        this.stats= map.getStats();
        this.engine= map.getEngine();
        this.startNumOfAnimals = startNumOfAnimals;
        this.grassSpawnedInEachDay = grassSpawnedInEachDay;

    timer = new Timer(delay, this);

    frame = new JFrame("Evolution Simulator");
        frame.setSize(1300, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    renderPanel = new RenderPanel(map, this);
        renderPanel.setSize(new Dimension(1, 1));


        statsPanel = new StatsPanel(map,this);
        statsPanel.setSize(10,10);

        frame.add(renderPanel);
        frame.add(statsPanel);
        frame.addKeyListener(statsPanel);


}

    public void startSimulation() {

        for (int i = 0; i < data.numOfAnimals; i++) {
            new Animal(map);
        }
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if( !statsPanel.pause && engine.getListOfAnimals().size()>0)
        {
            System.out.println(statsPanel.pause);
            try {
                engine.run();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            statsPanel.repaint();
            renderPanel.repaint();
            renderPanel.actualiseObserversDays();
        }
        else
            timer.stop();
    }

}

