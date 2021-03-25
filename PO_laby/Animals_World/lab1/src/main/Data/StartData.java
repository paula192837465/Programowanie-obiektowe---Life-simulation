package Data;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class StartData {

    public int numOfAnimals;
    public int heightOfMap;
    public int widthOfMap;
    public double JungleRatio;
    public int startAnimalEnergy;
    public int grassProfit;
    public int delay;
    public int grassSpawnedPerDay;
    public int dayEnergyCost;

    static public StartData loadData() throws FileNotFoundException {
        Gson gson = new Gson();

        return gson.fromJson(new FileReader("src/main/Data/startData.json"), StartData.class);
    }

    public void checkData()
    {
        if(numOfAnimals<=0)
            throw new IllegalArgumentException("Podaj dodatnią liczbę zwierząt");
        if(heightOfMap<=0)
            throw new IllegalArgumentException("Podaj dodatnią wysokość mapy");
        if(widthOfMap<=0)
            throw new IllegalArgumentException("Podaj dodatnią szerokość mapy");
        if(JungleRatio<=0 || JungleRatio>=1)
            throw new IllegalArgumentException("Współczynnik jungli musi zawierać sie w przedziale (0,1)");
        if(startAnimalEnergy<=0)
            throw new IllegalArgumentException("Podaj dodatnią wartość dla energii zwierząt na poczatku symulacji");
        if(grassProfit<=0)
            throw new IllegalArgumentException("Podaj dodatnią wartość mocy trawy");
        if(delay<=0)
            throw new IllegalArgumentException("Podaj dodatnią wartość opóźnienia w renderowaniu mapy");
        if(grassSpawnedPerDay<=0)
            throw new IllegalArgumentException("Podaj dodatnią wartość trawy na dzień");
        if(dayEnergyCost<=0)
            throw new IllegalArgumentException("Podaj dodatnią wartość utraty energii przez zwierzęta na dzień");
    }
}
