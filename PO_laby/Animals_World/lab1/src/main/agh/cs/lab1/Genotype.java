package agh.cs.lab1;

import java.util.*;

public class Genotype {

    int[] Genes;


    public Genotype()
    {
        this.Genes=generateGenes();
    }

    public Genotype(Animal mother, Animal father)
    {
        this.Genes=inheritFromParents(mother, father);
    }

    public int[] generateGenes()
    {
        Random r = new Random();
        int[] arr = new int[32];
        for(int i =0;i<32;i++)
            arr[i]=r.nextInt(8);


        arr=checkCorrectness(arr);
        Arrays.sort(arr);

        return arr;
    }

    private int[] checkCorrectness(int[] arr)
    {
        int[] typeOfGenes = new int[8];
        for(int i=0;i<32;i++)
        {
            typeOfGenes[arr[i]]++;
        }
        int maximum=0;

        for(int i=1;i<8;i++)
        {
            if(typeOfGenes[i]>typeOfGenes[maximum])
                maximum=i;
        }

        for(int i=0;i<8;i++)
        {
            if(typeOfGenes[i]==0)
            {
                typeOfGenes[i]++;
                typeOfGenes[maximum]--;
            }
        }
        int j=0;
        for(int i=0;i<8;i++)
        {
            while(typeOfGenes[i]>0)
            {
                arr[j]=i;
                j++;
                typeOfGenes[i]--;
            }
        }
        return arr;
    }

    @Override
    public String toString() {
        return  Arrays.toString(Genes);

    }

    private void prescribe (List<Integer> result, int min, int max, Animal animal)
    {
        for(int i=min;i<max;i++)
        {
            result.add(animal.getGenotype().Genes[i]);
        }
    }

    public int[] inheritFromParents(Animal mother, Animal father)
    {
        Random r = new Random();

        int breakpoint1 = r.nextInt(32);
        int breakpoint2 = r.nextInt(32);
        List<Integer> result = new ArrayList<>();

        prescribe(result,0,Math.min(breakpoint1, breakpoint2),father);
        prescribe(result,Math.min(breakpoint1, breakpoint2),Math.max(breakpoint1, breakpoint2),mother);
        prescribe(result,Math.max(breakpoint1, breakpoint2),32, father);

        int[] res = new int[32];
        for(int i =0;i<32;i++)
        {
            res[i]=result.get(i);
        }

        res=checkCorrectness(res);
        Arrays.sort(res);

        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genotype genotype = (Genotype) o;
        return Arrays.equals(Genes, genotype.Genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(Genes);
    }
}
