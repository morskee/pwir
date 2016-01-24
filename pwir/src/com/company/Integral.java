package com.company;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Ja on 24.01.2016.
 */
public class Integral {

    //to jest metoda monte carlo do liczenia ca�ek. Polega ona na tym �e losujesz liczby z przedzia�u ca�kowania, a nast�pnie liczysz o dodajesz
    //warto�ci funcji i na koniec mno�ysz przez h
    public Double monteCarlo(Double a,Double b,Integer n,Function function){
        Double s = new Double(0.0);
        Double h = (b-a)/n;
        //maszyna losuj�ca z ziarnem jako aktualny czas
        Random random = new Random(System.currentTimeMillis());
        for(int i=0;i<n;++i){
            Double x = (random.nextDouble()*h) + a;
            s+=function.f(x);
        }
        return s*h;
    }

    public Double parallerMonteCarlo(Double a,Double b,Integer n,Function function){
        Double s = new Double(0.0);
        Double h = (b-a)/n;
        Random random = new Random(System.currentTimeMillis());

       //tworzymy list� do wywo�ania w w�tkac rzeczy kt�re b�d� liczy�y warto�ci funkcji
        LinkedList<Callable<Double>> serviceList = new LinkedList<>();
        for(int i=0;i<n;++i){
            serviceList.add(new PooledService(a,b,random,function));
        }
        //lista wykonywanych w�tk�w, kt�re b�d� liczy�y nasze warto�i funkcji w w�tkach.
        //Jak to liczy wida� w klasie PooledService
        ThreadPoolExecutor pool =
                new ThreadPoolExecutor(
                        n,
                        n,
                        1L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>()
                );
        try{
            //lista z obiektami future
            List<Future<Double>> list;
            //wykonanie i przypisanie wyniku dzia�a� naszych w�tk�w
            list = pool.invokeAll(serviceList);
            for(Future<Double> future: list){
                s+=future.get();
            }
            pool.shutdown();
        }catch (Exception e){}
        return s*h;
    }

    //metoda prostok�t�w. Nie musz� t�umaczy� :)
    public Double rechteckRulle(Double a,Double b,Integer n,Function function){
        Double s=0.0;
        Double h = (b-a)/n;
        for(Double a1=a;a1<=b;a1+=h){
            s+=function.f(a1);
        }
        return s*h;
    }

    public Double parallerRechteckRulle(Double a,Double b,Integer n,Function function){
        Sum s=new Sum();
        Double h = (b-a)/n;
        //tworzymy n w�tk�w, kt�re licz� warto�ci funkcji a nast�pnie je dodajemy. Jest to w sumie zadanie z ko�a nr3
        AddFunctionToSum[] addFunctionToSums = new AddFunctionToSum[n];
        Integer i=0;
        for(Double a1=a;a1<=b;a1+=h){
            addFunctionToSums[i] = new AddFunctionToSum(s,a1,function);
            addFunctionToSums[i].start();
            i++;
        }
        for(i=0;i<n;++i){
            try {
                addFunctionToSums[i].join();
            }catch (InterruptedException e){}
        }
        return s.sum/n;
    }

    public Double trapezoidalRulle(Double a,Double b,Integer n,Function function){
        Double s = new Double(0.0);

        Double h = (b-a)/n;
        for(Double a1=a+h;a1<b;a1+=h){
            s+=2.0*function.f(a1);
        }
        s+=function.f(a)+function.f(b);
        s = s*h/2.0;
        return s;
    }

    final static ReentrantLock lock = new ReentrantLock();
    public Double parallerTrapezoidalRulle(Double a,Double b,Integer n,Function function){
        final Sum s = new Sum();
        Double h = (b-a)/n;
        //Ten egzekutor wykonuje nam w�tki.
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(n);

        for(Double a1=a+h;a1<b;a1+=h){
            final Double c = a1;
            //tutaj przypisujemy do egzekutor nowy w�tek. Jest to robione za pomoc� wyra�enia lambda ()->
            //blokujemy dost�p do add, a nast�pnie go odblokowujemy. To niestety nie dzia�a dobrze... Nie wiem czemu : (
            //ale czasami wzystko dobrze policzy : )
            executorService.submit(()->{
               lock.lock();
                try{
                    s.add(2.0*function.f(c));
                }finally {
                    lock.unlock();
                }
            });
        }
        executorService.shutdown();
        s.add(function.f(a)+function.f(b));
        s.sum = s.sum*h/2.0;
        return s.sum;
    }
}
