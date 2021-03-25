package Visualization;


import Interfaces.IWorldMap;
import agh.cs.lab1.MapWithJungle;
import Data.StartData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends JPanel implements ActionListener {


    private JTextField statisticsDays;
    private JLabel statsToTxt;
    private JButton startButton;
    private StartData startData;

    public SettingPanel(StartData data) {

        setSize(200, 200);

        this.startData = data;

        startButton = new JButton("Start Simulation");
        startButton.addActionListener(this);

        statsToTxt = new JLabel("Enter the number of days to get Statistics later:           ");

        statisticsDays = new JTextField();
        statisticsDays.setVisible(true);
        statisticsDays.setColumns(10);


        statsToTxt.setLabelFor(statisticsDays);
        JPanel panel = new JPanel();
        panel.add(statsToTxt);
        panel.add(statisticsDays);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);

        add(panel);
        add(buttonPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //jeśli nie podamy wartości to wpisuje do pliku tekstowego statystyki z 50 dni
        int numForStats=50;
        if(!statisticsDays.getText().equals(""))
            numForStats= Integer.parseInt(statisticsDays.getText());

        IWorldMap map = new MapWithJungle(startData);

        map.getStats().setNumForStatistics(numForStats);
        MapVisualization simulation = new MapVisualization(
                map, startData.delay,
                startData.numOfAnimals,
                startData.grassSpawnedPerDay);

        simulation.startSimulation();

    }
}
