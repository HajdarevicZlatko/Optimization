package UnconstrainedAlgorithms;

import unconstrained_functions.Function;

import java.util.Random;

public class Solution implements  Comparable<Solution> {


    // array for one solutions (function parameters)

    private double[] x;

    // value of objective function
    private double objectiveFunction;

    // value of fitness
    private double fitness;

    private Function function;

    // pseudo-random number
    Random r;
    //Broj iteracija gde se nije naslo bolje resenje
    int numberOfIteration=0;





    public Solution(Function function, Random r){

        this.function=function;
        x=new double[function.getD()];
        this.r=r;
        init();
    }

    public Solution(double[] x, Function function){
        this.x=x;
        this.function=function;
        calculateObjectiveFunction();
        calculateFitness();
        numberOfIteration=0;

    }
    // getters and setters

    public double[] getX(){
        return this.x;
    }

    public double getX(int position){
        return this.x[position];
    }

    public void setX(double[] x){
        this.x=x;
    }



    public void calculateObjectiveFunction(){

        this.objectiveFunction=function.function(x);
        this.fitness=1/(1+Math.abs(objectiveFunction));

    }

    public void calculateFitness(){
        this.fitness=1/(1+Math.abs(objectiveFunction));
    }

    public double getObjectiveFunction(){
        return this.objectiveFunction;
    }

    public double getFitness(){
        return this.fitness;
    }


    // initialization

    private void init() {

        for (int i = 0; i < x.length; i++) {
            x[i] = r.nextDouble() * (function.getUb()[i] - function.getLb()[i]) + function.getLb()[i];

        }

        calculateObjectiveFunction();
        calculateFitness();
    }

    // compare against fitness value
    @Override
    public int compareTo(Solution s) {
        if(this.fitness>s.fitness){
            return -1;
        }
        else if(this.fitness<s.fitness){
            return 1;
        }
        else{
            return 0;
        }
    }
    public void addNumberOfIteration(){
        numberOfIteration++;
    }
}
