package UnconstrainedAlgorithms;

import unconstrained_functions.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GWO {
    Solution alfa;
    Solution beta;
    Solution delta;
    Solution bestSolution;
    private Function function;
    ArrayList<Solution> XX=new ArrayList<>();
    ArrayList<Solution> X1=new ArrayList<>();
    ArrayList<Solution> X2=new ArrayList<>();
    ArrayList<Solution> X3=new ArrayList<>();
    Random r;
    private double a[];
    private double A1[];
    private double C1[];
    private double A2[];
    private double C2[];
    private double A3[];
    private double C3[];
    private Solution best;
    public GWO(Function function, Random r)
    {
        this.function=function;

        for(int i=0;i<function.getD();i++){
            XX.add(i,new Solution(function,r));
        }
        for(int i=0;i<function.getD();i++){
            X1.add(i,new Solution(function,r));
        }
        for(int i=0;i<function.getD();i++){
            X2.add(i,new Solution(function,r));
        }
        for(int i=0;i<function.getD();i++){
            X3.add(i,new Solution(function,r));
        }

        a=new double[function.getD()];
        A1=new double[function.getD()];
        C1=new double[function.getD()];
        A2=new double[function.getD()];
        C2=new double[function.getD()];
        A3=new double[function.getD()];
        C3=new double[function.getD()];

    }
   public void init()
    {

        Collections.sort(XX);
        alfa=XX.get(0);
        beta=XX.get(1);
        delta=XX.get(2);

    }


    public Solution solution() {
        //System.out.println("Poceo solution");
        int iter = 1;
        int maxiter=function.getD();
        while (iter < maxiter/10) {
            //System.out.println(alfa[maxiter]);
            int D = function.getD();
            for (int j = 0; j < function.getD(); j++) {
                a[j] = 2.0 - ((double) iter * (2.0 / (double) maxiter));
               // System.out.println(a[j]);
            }

            for (int i = 0; i < D; i++) {
                for (int j = 0; j < D; j++) {
                    double r1 = Math.random();
                    double r2 = Math.random();
                    for (int ii = 0; ii < D; ii++) {
                        A1[ii] = 2.0 * a[ii] * r1 - a[ii];
                    }
                    for (int ii = 0; ii < D; ii++) {
                        C1[ii] = 2.0 * r2;
                    }
                    double X1a[]=new double[function.getD()];
                    X1a[i] = alfa.getX(j) - A1[j] * (Math.abs(C1[j] * alfa.getX(j) - XX.get(i).getX(j)));
                    Solution solution1=new Solution(X1a,function);
                    X1.add(i,solution1);
                    r1 = Math.random();
                    r2 = Math.random();
                    for (int ii = 0; ii < D; ii++) {
                        A2[ii] = 2.0 * a[ii] * r1 - a[ii];
                    }
                    for (int ii = 0; ii < D; ii++) {
                        C2[ii] = 2.0 * r2;
                    }
                    double X2a[]=new double[function.getD()];
                    X2a[i] = beta.getX(j) - A2[j] * (Math.abs(C2[j] * beta.getX(j) - XX.get(i).getX(j)));
                    Solution solution2=new Solution(X2a,function);
                    X2.add(i,solution2);
                    r1 = Math.random();
                    r2 = Math.random();
                    for (int ii = 0; ii < D; ii++) {
                        A3[ii] = 2.0 * a[ii] * r1 - a[ii];
                    }
                    for (int ii = 0; ii < D; ii++) {
                        C3[ii] = 2.0 * r2;
                    }
                    double X3a[]=new double[function.getD()];
                    X3a[i] = delta.getX(j) - A3[j] * (Math.abs(C3[j] * delta.getX(j) - XX.get(i).getX(j)));
                    double XXa[]=new double[function.getD()];
                    XXa[i]=(X1.get(i).getX(j) + X2.get(i).getX(j) + X3.get(i).getX(j)) / 3.0;
                    XX.add(i,new Solution(XXa,function));

                }
            }

            Collections.sort(XX);
            alfa=XX.get(0);
            beta=XX.get(1);
            delta=XX.get(2);
            //System.out.println("Zavrsen solution");
            //System.out.println(iter);
            iter++;
        }
        Collections.sort(XX);
           bestSolution=XX.get(0);
            return bestSolution;
        }
    public void ispis(){
        System.out.println(solution().getObjectiveFunction());
    }
}
