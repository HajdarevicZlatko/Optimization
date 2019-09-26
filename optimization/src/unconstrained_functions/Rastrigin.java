package unconstrained_functions;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class Rastrigin extends Function
{
    private double [] lb;

    private double [] ub;

    private int D;

    public Rastrigin (int D)
    {
        this.D = D;
        lb = new double [D];
        ub = new double [D];

        for (int i = 0; i < D; i++)
        {
            lb [i] = -5.12;
            ub [i] = 5.12;
        }
    }

    @Override
    public int getD()
    {
        return D;
    }

    @Override
    public double[] getLb()
    {
        return lb;
    }

    @Override
    public double[] getUb()
    {
        return ub;
    }
    public double function (double [] sol)
    {
        int j;
        double top=0;

        for(j=0;j<sol.length;j++)
        {
            top=top+(Math.pow(sol[j],(double)2)-10*Math.cos(2*Math.PI*sol[j])+10);
        }
        return top;
    }

}
