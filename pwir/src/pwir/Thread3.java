package pwir;

public class Thread3 extends Thread implements Runnable{

	// METODA SIMPSONA
	
	float func(float x) {
		return x*x+3;
		}
	
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		float xp = 0, xk = 0, dx, calka, s, x;
		int i, n = 0;
		
		dx = (xk - xp) / (float)n;
		 
		calka = 0;
		s = 0;
		for (i=1; i < n; i++) {
			x = xp + i*dx;
			s += func(x - dx / 2);
			calka += func(x);
			}
		s += func(xk - dx / 2);
		calka = (dx/6) * (func(xp) + func(xk) + 2*calka + 4*s);
		
		System.out.println(calka);        

	}

}
