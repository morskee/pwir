package pwir;

public class Main {
    public static void main( String[] args ) {
    	    	
        Thread1 t1 = new Thread1();
        (new Thread(t1)).start();
        
        Thread2 t2 = new Thread2();
        (new Thread(t2)).start();
        
        Thread3 t3 = new Thread3();
        (new Thread(t3)).start();
        
        Thread4 t4 = new Thread4();
        (new Thread(t4)).start();
        
        Thread5 t5 = new Thread5();
        (new Thread(t5)).start();    

    }
    
}
