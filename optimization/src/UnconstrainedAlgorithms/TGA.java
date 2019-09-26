package UnconstrainedAlgorithms;

import unconstrained_functions.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TGA {

    //algorithm parameters

    // total number of solutions
    private int N;

    // N1 best solution for the local search
    private int N1;

    private int N2;

    private int N3;

    private int N4;

    private int L = 2;

    private double lambda;

    private double[] globalParams;

    private int bestPosition;

    private double bestFitness;


    // local search parameter, Eq. (1) in the paper
    private double teta;

    private ArrayList<Solution> population;

    Random r;

    Function function;

    public TGA(int N, int N1, int N2, int N4, double lambda, double teta, Random r, Function function) {

        this.N = N;
        this.N1 = N1;
        this.N2 = N2;
        this.N3 = N - (N1 + N2);
        this.N4 = N4;
        this.lambda = lambda;
        this.teta = teta;
        this.function = function;
        this.r = r;
        this.population = new ArrayList<>();
        this.globalParams = new double[function.getD()];


    }

    public void initPopulation() {

        for (int i = 0; i < N; i++) {
            population.add(new Solution(function, r));
        }


        Collections.sort(population);
        bestPosition = 0;
        globalParams = population.get(0).getX();

    }

    public void sortPopulation() {
        Collections.sort(population);
    }


    // local search for N1 better folustions using formula (1) in the paper


    public void localSearch() {

        sortPopulation();


        for (int i = 0; i < N1; i++) {

            Solution newSolution = generateNewSolutionLocalSearch(population.get(i));
            if (newSolution.getFitness() > population.get(i).getFitness()) {
               // System.out.println("Nasao je bolje resenje");
                population.set(i, newSolution);

            }
        }

    }


    // local search for N1 better solutions using formula (1) in the paper, generate one new solutions - axillary method

    private Solution generateNewSolutionLocalSearch(Solution solution) {

        if (solution == null) {
            System.out.println("Solution in local search is null");
        }

        double[] x = solution.getX();

        // backup arrays for holding modified values
        double[] x1 = new double[x.length];
        double[] x2 = new double[x.length];
        double[] xNew = new double[x.length];


        for (int i = 0; i < x.length; i++) {
            x1[i] = x[i] / teta;
            x2[i] = x[i] * r.nextDouble();
            xNew[i] = x1[i] + x2[i];
        }

        Solution newSolution = new Solution(xNew, function);
        return newSolution;
    }


    // method that performs search towards the best solution - phase 2 of the TGA
    public void searchTowardsTheBest() {

        double alpha = r.nextDouble(); // search parameter

        for (int i = N1; i < (N1+N2); i++) {
            int[] minPositionArray = findClosest(i);
            int firstClosest = minPositionArray[0];
            int secondClosest = minPositionArray[1];

            // first close array
            double[] X1 = population.get(firstClosest).getX();
            for (int j = 0; j < X1.length; j++) {
                X1[j] = X1[j] * lambda;
            }

            // second close array
            double[] X2 = population.get(secondClosest).getX();
            for (int j = 0; j < X2.length; j++) {
                X2[j] = X2[j] * (1 - lambda);
            }

            double[] X1X2 = new double[X2.length];
            for (int j = 0; j < X1.length; j++) {
                X1X2[j] = X1[j] + X2[j];
            }

            double[] arrayAlpha = new double[X2.length];
            alpha = r.nextDouble();
            for (int j = 0; j < X1.length; j++) {
                arrayAlpha[j] = X1X2[j] * alpha;
            }

            double[] newSolution = new double[X1.length];
            double[] oldSolution = population.get(i).getX();


            for (int j = 0; j < X1.length; j++) {

                newSolution[j] = oldSolution[j] + arrayAlpha[j];
            }

            Solution solution = new Solution(newSolution, function);
            if (solution.getFitness() > population.get(i).getFitness()) {
                population.set(i, solution);
            }


        }

    }


    // method that removes worst N3 solutions from the population

    public void replaceWorstSolutions() {

        for (int i = (N1 + N2); i < N; i++) {
            population.set(i, new Solution(function, r));
        }
    }


    // method that generates N4 new solutions and apply the mask opertor (last phase of the algorithm)

    public void generateNewSolutions() {

        sortPopulation();
        // parameters of the best solution in the population
        globalParams = population.get(0).getX();

        for (int i = 0; i < (N + N4); i++) {

            //generate random new solution
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


    // find the most similar solution in the population, with the lowest distance (auxilliary method for phase 2)

    private int[] findClosest(int index) {

        double distance = 0;
        double minDistance = Integer.MAX_VALUE;
        double minDistance1 = Integer.MAX_VALUE;
        int minPosition = 0;
        int minPosition1 = 0;
        int position = 0;


        for (int i = 0; i < (N1 + N2); i++) {

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

    public void printGlobalBest() {
        System.out.println(population.get(0).getObjectiveFunction());
    }


}
