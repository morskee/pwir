package com.company;

import java.util.concurrent.Semaphore;


/**
 * Created by Ja on 24.01.2016.
 */

//To jest cz�� z kolokwium nr 3. Tutaj s� u�yte semaphoty. Co� lepszego od Lock i synhronized

public class AddFunctionToSum extends Thread {
    private Double x;
    private Sum s;
    private Function function;
    AddFunctionToSum(Sum s,Double x,Function function){
        this.s = s;
        this.x = x;
        this.function = function;
    }
    private static Semaphore semaphore = new Semaphore(1);
    @Override
    public void run() {
        add();
    }

    private synchronized void add(){
        //blokujemy innym w�tkom dost�p do zmiennej
        semaphore.acquireUninterruptibly();
        try{
            s.sum += function.f(x);
        }finally {
            //odblokowujemy dost�p do zmiennej
            semaphore.release();
        }
    }

    public Sum getSum(){
        semaphore.acquireUninterruptibly();
        try {
            return s;
        }finally {
            semaphore.release();
        }
    }
}
