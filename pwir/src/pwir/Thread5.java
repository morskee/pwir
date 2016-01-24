package pwir;

public class Thread5 extends Thread {

	// METODA MONE CARLO
	
	private static double func(double x) {
		return x*x+3;
	}
	
	private static double funcIn(double x, double y) {
		if (( y > 0) && (y <= func(x)))
				return 1;
		else if (( y > 0) && (y <= func(x)))
			return -1;
	return 0;
	}
	 
	//random number from a to b
	private static double randomPoint(double a, double b) {
	return  a + Math.random() * (b-a);
	}  
	
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		double xp = 0, xk = 0, yp, yk, calka = 0;
		int n = 0;
		int pointsIn = 0;
		
		yp = 0;
		yk = Math.ceil(Math.max(func(xp), func(xk)));
		 
		for (int i = 0; i < n; i++) {
		pointsIn += funcIn(randomPoint(xp, xk), randomPoint(yp, yk));
		}
		
		System.out.println(calka);        

	}

}
