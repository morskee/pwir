package pwir;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by Ja on 24.01.2016.
 */
public class PooledService implements Callable<Double> {

    private Double a,b;
    Random random;
    Function function;
    public PooledService(Double a, Double b,Random random, Function function){
        this.a = a;
        this.b = b;
        this.random = random;
        this.function = function;
    }
    @Override
    public Double call() throws Exception {
        Double x = (random.nextDouble()*(b-a)) + a;
        x = function.f(x);
        return x;
    }
}
