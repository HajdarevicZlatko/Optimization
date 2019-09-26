package unconstrained_functions;

public class Rosenbrock extends Function {

    private double [] lb;

    private double [] ub;

    private int D;

    public Rosenbrock (int D)
    {
        this.D = D;
        lb = new double [D];
        ub = new double [D];

        for (int i = 0; i < D; i++)
        {
            lb [i] = -30;
            ub [i] = 30;
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
    public double function(double [] sol)
    {
        int j;
        double top=0;
        for(j=0;j<sol.length-1;j++)
        {
            top = top + 100 * Math.pow((sol[j+1] - Math.pow((sol[j]),(double)2)),(double)2) + Math.pow((sol[j]-1),(double)2);
        }
        return top;
    }
}
