package unconstrained_functions;

public class Sphere extends Function {


    private double [] lb;

    private double [] ub;

    private int D;

    public Sphere (int D)
    {
        this.D = D;
        lb = new double [D];
        ub = new double [D];

        for (int i = 0; i < D; i++)
        {
            lb [i] = -100;
            ub [i] = 100;
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
            top=top+sol[j]*sol[j];
        }
        return top;
    }



}
