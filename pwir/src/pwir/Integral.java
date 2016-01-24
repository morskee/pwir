package pwir;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Ja on 24.01.2016.
 */
public class Integral {
    public Double monteCarlo(Double a,Double b,Integer n,Function function){
        Double s = new Double(0.0);
        Double h = new Double(b-a);
        Random random = new Random(System.currentTimeMillis());

        for(int i=0;i<n;++i){
            Double x = (random.nextDouble()*h) + a;
            s+=function.f(x);
        }
        return s/n;
    }

    public Double parallelMonteCarlo(Double a,Double b,Integer n,Function function){
        Double s = new Double(0.0);
        Double h = new Double(b-a);
        Random random = new Random(System.currentTimeMillis());
        LinkedList<Callable<Double>> serviceList = new LinkedList<>();

        for(int i=0;i<n;++i){
            serviceList.add(new PooledService(a,b,random,function));
        }
        ThreadPoolExecutor pool =
                new ThreadPoolExecutor(
                        n,
                        n,
                        1L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>()
                );
        try{
            List<Future<Double>> list;
            list = pool.invokeAll(serviceList);
            for(Future<Double> future: list){
                s+=future.get();
            }
            pool.shutdown();
        }catch (Exception e){}
        return s/n;
    }
}
