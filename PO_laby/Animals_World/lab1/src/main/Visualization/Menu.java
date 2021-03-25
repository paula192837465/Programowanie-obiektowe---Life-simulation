package Visualization;

import Data.StartData;

import javax.swing.*;

public class Menu {
    public JFrame menuFrame;

    public Menu() {

        menuFrame = new JFrame("Evolution Simulator (Settings)");
        menuFrame.setSize(500, 150);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);

    }
    public void startSimulation(StartData data){
        menuFrame.add(new SettingPanel(data));
        menuFrame.setVisible(true);
    }
}
