package UnconstrainedAlgorithms;

import unconstrained_functions.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ABC {
    Random r;
   public int brojNadjenih=0;
    private ArrayList<Solution> population;
    Function function;
    double lambda;
    Solution bestSolution;
    private double[] globalParams;
    int N;
            //Konstruktor
    public ABC(int N, double lambda, Random r, Function function) {
        this.lambda = lambda;
        this.N=N;
        this.function = function;
        this.r = r;
        this.population = new ArrayList<>();
        this.globalParams = new double[function.getD()];
        initPopulation();
        //Pretpostavljam da je najbolje resenje prvo resenje;
        bestSolution=population.get(0);
        //employedBees();
        //onLockers();
    }
    //Kreiranje populacije pcela
    public void initPopulation(){
        for (int i = 0; i < N; i++) {
            //Popunjavam listu sa resenjima
            population.add(new Solution(function, r));
        }
        //Sortiram listu
        Collections.sort(population);
        globalParams = population.get(0).getX();
        //
        //System.out.println("Ispis sotriranog fitnesa pri inicijalizaciji roja pcela");
        //int brojac=0;
        //for (Solution s: population
          //   ) {
            //brojac++;
            //Ovde sam proveravao da li je lista sortirana kako treba od najboljeg fitnesa ka najgorem
         //   System.out.println("Fitnes broj: "+brojac+" = "+s.getFitness());
           // System.out.println("----------------------------------");
       // }
       // System.out.println("Ispis sotriranog fitnesa pri inicijalizaciji roja pcela zavrsen");
    }


    //Kreiranje novog resenja na osnovu starog resenja
    private Solution generateNewSolutionLocalSearch(Solution solution, Solution solutionJ) {

        if (solution == null) {
            System.out.println("Solution  in local search is null");
        }
        double[] x = solution.getX();
        double[] y=solutionJ.getX();
        //Pravim niz koji ima duzinu istu kao i resenje
        double[] xNew = new double[x.length];
        //System.out.println("Nalazenje boljeg resenja u lokalu");
        //System.out.println("--------------------------------------");
        for (int i = 0; i < x.length; i++) {
           //Pravim random broj
            int brojK=r.nextInt(x.length);
            xNew[i]=x[i]+(r.nextDouble()*r.nextInt(2)-1)*(x[i]-y[i]);
        }
        Solution newSolution = new Solution(xNew, function);
       // System.out.println("------Kraj kreiranja novog resenja-----------");
        return newSolution;
    }
    //Funkcija zaposlenih pcela
    public void employedBees() {
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).numberOfIteration > 10) {
                population.add(i, new Solution(function, r));
            }
            sortPopulation();
        }
                for (int i = 0; i < N; i++) {
                    int broj = r.nextInt(N);
                    Solution newSolution = generateNewSolutionLocalSearch(population.get(i), population.get(broj));
                    //Ukoliko su ista resenja nastavljam dalje
                    if (i == broj) {
                        continue;
                    }
                    if (newSolution.getFitness() > population.get(i).getFitness()) {
                        //Ukoliko je fitnes bolji menjam resenje
                        population.set(i, newSolution);
                        brojNadjenih++;
                    } else {
                        //Ukoliko nije inkrementujem broj iteracija koja vodi racuna da nakon odredjenog broja promeni resenje
                        //i potrezi resenje globalno
                        population.get(i).addNumberOfIteration();
                    }

                    sortPopulation();
                }

            }


    public void onLockers(){
        sortPopulation();
        double sumOfFitness=sumOfFitness();
        if(onLockerCalculateBestFoodSource(bestSolution, sumOfFitness)  < onLockerCalculateBestFoodSource(population.get(0),sumOfFitness)){
            bestSolution=population.get(0);
            System.out.println("Promenio je najbolje resenje");
        }
        for (int i = 0; i < N; i++) {
         //   int broj = r.nextInt(N);
            Solution newSolution = generateNewSolutionLocalSearch(bestSolution, population.get(i));
            if (onLockerCalculateBestFoodSource(newSolution, sumOfFitness)  > onLockerCalculateBestFoodSource(bestSolution,sumOfFitness)) {
                bestSolution=newSolution;
                brojNadjenih++;
                System.out.println("Nasao je bolje resenje onLocker");
            }



        }


        //System.out.println("Zavrsion onLocker");
    }
    private double onLockerCalculateBestFoodSource(Solution solutionBee, double sumOfFitness){

        return solutionBee.getFitness()/sumOfFitness;
    }
    private double sumOfFitness(){
        double sumfitnesForOnLockers=0;
        for (
                Solution solution:population
        ) {
            sumfitnesForOnLockers+=solution.getFitness();
        }
        return sumfitnesForOnLockers;

    }
    public void print(){
        System.out.println(bestSolution.getObjectiveFunction());
    }

    public void sortPopulation(){
        Collections.sort(population);
    }
    public void printGlobalBest() {
        sortPopulation();
        System.out.println(population.get(0).getObjectiveFunction());
    }

    public void generateNewSolutions() {

        sortPopulation();
        globalParams = population.get(0).getX();

        for (int i = N/2; i < N; i++) {
            Solution solution = new Solution(function, r);
            long[] maskOperator = new long[solution.getX().length];
            double[] newSolutionX = solution.getX();
            for (int j = 0; j < maskOperator.length; j++) {
                maskOperator[j] = Math.round(r.nextDouble());
            }
            for (int j = 0; j < maskOperator.length; j++) {
                if (maskOperator[j] == 1) {
                    newSolutionX[j] = globalParams[j];
                }
            }
            solution.setX(newSolutionX);
            solution.calculateObjectiveFunction();
            population.add(i, solution);
        }
    }

    private int[] findClosest(int index) {

        double distance = 0;
        double minDistance = Integer.MAX_VALUE;
        double minDistance1 = Integer.MAX_VALUE;
        int minPosition = 0;
        int minPosition1 = 0;
        int position = 0;


        for (int i = 0; i < 50; i++) {

            if (i == index) {
                distance = Integer.MAX_VALUE;
            }
            distance = calculateDistance(index, i);
            if (distance < minDistance) {
                minDistance = distance;
                minPosition = i;
            } else if (distance < minDistance1) {
                minDistance1 = distance;
                minPosition1 = i;
            }
        }
        int[] positionArray = {minPosition, minPosition1};
        return positionArray;
    }

// method to calculate distance between solutions (Eq. 2 in the paper)

    private double calculateDistance(int firstIndex, int secondIndex) {

        double[] firstSolution = population.get(firstIndex).getX();
        double[] secondSolution = population.get(secondIndex).getX();

        double distance = 0;
        for (int i = 0; i < firstSolution.length; i++) {
            distance += Math.pow((firstSolution[i] - secondSolution[i]), 2);
        }

        return Math.sqrt(distance);
    }


    public void test() {
//        ListIterator<Solution> iterator=population.listIterator();
//        while (iterator.hasNext()){
//            Solution solution=iterator.next();
//            System.out.println(solution.getObjectiveFunction());
//        }

        for (int i = 0; i < N; i++) {
            System.out.println(i + ":" + population.get(i).getObjectiveFunction());
        }
    }

}