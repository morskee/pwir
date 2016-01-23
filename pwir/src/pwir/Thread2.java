package pwir;

public class Thread2 extends Thread implements Runnable{

	// METODA TRAPEZÓW
	
	double f(double x) {
	  return x*x+x+2;
	}
	 
	double Pole(int a, int b, int n) {
		double h = (b-a)/(double)n; //wysokosć trapezów
		double S = 0.0; //zmienna będzie przechowywać sumę pól trapezów
		double podstawa_a = f(a), podstawa_b;
	 
		for(int i=1;i<=n;i++){
			podstawa_b = f(a+h*i);
			S += (podstawa_a+podstawa_b);
			podstawa_a = podstawa_b;
			}
		return S*0.5*h;
		}
	
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		int a = 0, b = 0, n = 0;
		System.out.println(Pole(a, b, n));        

	}

}
