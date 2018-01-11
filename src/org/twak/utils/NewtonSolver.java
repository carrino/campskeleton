package org.twak.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author twak
 */
public abstract class NewtonSolver {

    double t, threshold;
    int handbrake = 1000;

    public NewtonSolver ( double threshold ) {
        this.threshold = threshold;
    }

    public Double go(double startValue)
    {
        t = startValue;

        int i = 0;
        double current;
        
        while (Math.abs ( current = step()) > threshold && i++ < handbrake);

        if (i >= handbrake)
            return null;

        return t;
    }

    public double step() {
        double res = eval(t);
        t -= res / evalD(t);
        return res;
    }

    public abstract double eval (double t);
    public abstract double evalD (double t);


//    public static List<Double> parallel(List<? extends NewtonSolver> solvers, double threshold)
//    {
//        int handbrake = 1000;
//        double minVal = Double.MAX_VALUE;
//        int i = 0;
//        NewtonSolver winner = solvers.get(0);
//
//        while (minVal > threshold && i++ < handbrake)
//        {
//            for (NewtonSolver s : solvers)
//            {
//                double val = Math.abs(s.step());
//                if (val < minVal)
//                {
//                    winner = s;
//                     minVal = val;
//                }
//            }
//        }
//
//        if (i >= handbrake)
//            return null;
//
//        return winner.t;
//    }
}
