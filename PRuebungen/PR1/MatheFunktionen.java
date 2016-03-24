public class MatheFunktionen {
    public static final int MAX_VALUE = 3;
    
    public static void main(String[] args) {
        System.out.println("Hello world!");
		for (int i = 1; i<=5; i++) {
			System.out.print(21-i*4+" ");
		}
		System.out.println();
		System.out.println(vectorLen3d(20,10,0));
		pqFormel(2,4,-8);
		drawBox(5, 10);
		drawBox(10, 5);
		drawBox(30,1);
		System.out.println(steigung(1,2,7,10));
		System.out.println(steigung(1,2,3,4)+steigung(4,5,6,9));
    }
	
    //berechnet den Betrag eines 3-dimensionalen Vektors
	public static double vectorLen3d(double x, double y, double z) {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	//berechnet wenn möglich die 2 Lösungen der PQ-Formel
	public static boolean  pqFormel(double factor, double p, double q) {
	    p /=factor;
	    q /= factor;
	    
	    double wurzel = 0.25*p*p - q;
	    if(wurzel < 0.0) {
	        System.out.println("Es gibt keine nicht-imaginäre Lösung!");
	        return false;
	    }
	    else {
	        double x1 = -0.5*p + Math.sqrt(wurzel);
	        double x2 = -0.5*p - Math.sqrt(wurzel);
	        System.out.println("x1 = "+x1+" x2 = "+x2);
	    }
	    return true;
	    
	}
	
	// zeichnet eine Box der die Größe übergeben werden muss
	public static void drawBox(int sizeX, int sizeY) {
	    for ( int i = 1; i <= sizeY; i++) {
	        for ( int j = 1;  j <= sizeX; j++) {
	            if( j == 1 || j == sizeX) {
	                System.out.print("*");
	            }
	            else {
	                if( i == 1 || i == sizeY) {
	                    System.out.print("*");
	                }
	                else {
	                    System.out.print(" ");
	                }
	            }
	        }
	        System.out.println();
	    }
	    
	}
	
	public static double steigung(double x1, double x2, double y1, double y2) {
		return (y2-y1) / (x2-x1);
	}
}