package Visualization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectNumberFrame extends JFrame implements ActionListener {

    JPanel numberPanel = new JPanel();
    JButton button = new JButton("Go");
    JLabel numOfDays;
    JTextField enterNum;
    AnimalFollowerFrame animalFrame;

    public SelectNumberFrame(AnimalFollowerFrame animalFrame)
    {
        this.animalFrame=animalFrame;
        setSize(200, 100);
        setLocationRelativeTo(null);
        setVisible(true);
        numOfDays = new JLabel("Enter number of days: ");
        enterNum = new JTextField(10);

        numberPanel.add(numOfDays);
        numberPanel.add(enterNum);
        button.addActionListener(this);
        numberPanel.add(button);
        this.add(numberPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        if(!enterNum.getText().equals(""))
            animalFrame.sendDaysOfFollowing(Integer.parseInt(enterNum.getText()));
        else
            animalFrame.sendDaysOfFollowing(Integer.MAX_VALUE);

    }
}
