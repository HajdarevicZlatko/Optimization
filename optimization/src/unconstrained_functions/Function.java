package unconstrained_functions;

public abstract class Function {


    // calculate function
    public abstract double function (double [] niz);

    // array of lb for all parameters
    public abstract double [] getLb ();

    // array of ub for all parameters
    public abstract double [] getUb ();

    // getter for function dimensions
    public abstract int getD ();
}
