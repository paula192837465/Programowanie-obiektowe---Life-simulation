package agh.cs.lab1;
import Data.StartData;
import Visualization.Menu;

import java.io.FileNotFoundException;

import static java.lang.System.exit;
import static java.lang.System.out;

public class World {

    public static void main(String[] args) throws FileNotFoundException {

        StartData data = StartData.loadData();
        try
        {
            data.checkData();
            Menu menu= new Menu();
            menu.startSimulation(data);

        }
        catch (IllegalArgumentException exception)
        {
            out.print(exception);
            exit(-1);
        }
    }


}
