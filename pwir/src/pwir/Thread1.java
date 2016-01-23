package pwir;

public class Thread1 extends Thread implements Runnable{
	
	// METODA PROSTOKĄTÓW
	
	public double f(double x) {
		return x*x+x+2;
	}
	
	double Pole(int a, int b, int n) {
	  double x = (b-a)/(double)n; //pierwszy bok - każdy prostokąt ma taki sam
	  double S = 0.0; //zmienna będzie przechowywać sumę pól trapezów
	  double srodek = a+(b-a)/(2.0*n); //środek pierwszego boku
	 
	  for(int i=0;i<n;i++) {
		  S+=f(srodek); //obliczenie wysokości prostokąta
	      srodek+=x; //przejście do następnego środka    
	  	  }
	  return S*x;
	  }
	
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		int a = 0, b = 0, n = 0;
		System.out.println(Pole(a, b, n));        

	}

}
