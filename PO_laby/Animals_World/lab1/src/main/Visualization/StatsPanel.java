package Visualization;

import Interfaces.IWorldMap;
import Data.Statistics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StatsPanel extends JPanel implements KeyListener {

    private IWorldMap map;
    private MapVisualization simulation;
    private Statistics stats;
    public boolean pause;


    public StatsPanel(IWorldMap map, MapVisualization visualization)
    {
        this.map=map;
        this.simulation=visualization;
        this.stats = map.getStats();
        this.pause=false;
      }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setSize((int) (simulation.frame.getWidth() * 0.4), simulation.frame.getHeight() - 38);
        this.setLocation(0, 0);

        g.drawString("Total days: " + stats.getNumberOfDays(), 10,  20);
        g.drawString("Total number of grass: " + stats.getNumerOfGrass(), 10,  40);
        g.drawString("Total number of animals on map: " + stats.getNumerOfAnimals(), 10,  60);
        g.drawString("Average energy level: " + stats.getAvgOfEnergy(), 10,  80);
        g.drawString("Average number of children: " + stats.getAvgOfChildren(), 10,  100);
        g.drawString("Average length of life: " + stats.getAvgOfLiving(), 10,  120);
        g.drawString("Domain genotype: ", 10,  140);
        g.drawString(""+ stats.getDominant(), 10,  160);
        g.drawString("Number Od Animals With Dominant Genotype: " + stats.getNumberOfAnimalsWithDominantGenotype(), 10,  180);

    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S)
            this.pause=!pause;
        if(!pause);
        {
            simulation.renderPanel.paintDominantGenotype(false);
            simulation.timer.start();
        }


        if (e.getKeyCode() == KeyEvent.VK_G && pause)
            simulation.renderPanel.paintDominantGenotype(true);

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
