package main;

import UnconstrainedAlgorithms.ABC;
import UnconstrainedAlgorithms.GWO;
import UnconstrainedAlgorithms.TGA;
import unconstrained_functions.Function;
import unconstrained_functions.Rastrigin;
import unconstrained_functions.Rosenbrock;
import unconstrained_functions.Sphere;

import java.util.Random;

public class Main {



    public static void main(String[] args) {

        Function function=new Sphere(100);
        Function function1=new Rosenbrock(100);
        Function function2=new Rastrigin(100);
        Random r=new Random();
        TGA tga=new TGA(100,20,50,20,0.5,0.5,r,function);

        tga.initPopulation();

for(int i=0;i<100;i++) {
    tga.localSearch();
    tga.searchTowardsTheBest();
    tga.replaceWorstSolutions();
    tga.generateNewSolutions();
    tga.sortPopulation();

}
        System.out.println("Zavrsen TGA");

   tga.printGlobalBest();
    //   tga.test();
        /*ABC abc=new ABC(100, 0.5, r, function);
        for(int i=0; i<1000; i++){
            abc.employedBees();
            //abc.searchTowardsTheBest();
            //abc.replaceWorstSolutions();
            abc.generateNewSolutions();
            abc.sortPopulation();
            abc.printGlobalBest();
            abc.onLockers();
            abc.print();
        }
        System.out.println("Nasao je ukupno boljih resenja u local-Searchu: "+abc.brojNadjenih);
        abc.print();
    */

        GWO gwo=new GWO(function,r);
        gwo.init();
        gwo.ispis();

    }


}
