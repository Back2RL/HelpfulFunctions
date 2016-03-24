public class Math {
    public static void main(String[] args) {
        System.out.println("Hello world!");
		for (int i = 1; i<=5; i++) {
			System.out.print(21-i*4+" ");
		}
		System.out.println();
		System.out.println(vectorLen(20,10,0));
		pqFormel(2,4,8);
    }
	
	public static double vectorLen(double x, double y, double z) {
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
}