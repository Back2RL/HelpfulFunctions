/**
*	Mi 21. Okt 13:17:04 CEST 2015
*
*	PR1, WS2015/16
*
*	Leonard Oertelt
*	Matrikelnummer 1276156
*	leonard.oertelt@stud.hs-hannover.de
* 
*	Julian Opitz
* 	Matrikelnummer 1302082
* 	julian.opitz@stud.hs-hannover.de
*
*	-----------------------------------------
*	Bitshift operators:
*   a=1,b=0
*   ~a -> 0 (dual)
*   a | b -> 1 (dual)
*   a & b -> 0 (dual)
*   a ^ b -> 1  (only when a is 1 and b is 0 or when b is 1 and a is 0)
*   3 << 2 -> 1100
*   1 << 5 -> 100000
*   100000 >> 3 -> 100
*   101000 >>> 3 -> 001000 >> 3 -> 1
*/
public class BitShift {
    
    public static final int BLOND= 0; 
    public static final int ROT= 1; 
    public static final int SCHWARZ= 2; 
    public static final int BRUENETT= 3;
    public static final int STUDENT= 1<<6;  // 001000000 
    public static final int LEDIG= 1<<7;    // 010000000 
    public static final int WEIBLICH= 1<<8; // 100000000 
    
	public static void main(String[] args) {
	    
	    int person = encode(49,SCHWARZ, STUDENT | LEDIG);	    
	    System.out.println(person);
	    decode(person);
	    
	    
	    
	    person = encode(49,SCHWARZ, STUDENT | LEDIG | WEIBLICH);	    
	    System.out.println(person);
	    decode(person);
	    //show bits from right to left
	    for (int i = 0; i<9; i++) {
	        System.out.println(getBit(person,i));
	    }
	    writeBinary(person);
	    
	}
	
	public static int encode(int schuhgroesse, int haarfarbe, int status) {
	    // z.B.: schuhgroesse = 49 -> 49-36 = 13 -> 1101 (dual)
	    int merkmale = schuhgroesse-36; 
	    // z.B.: 0001101 | 1110000 -> 1111101
	    merkmale = merkmale | (haarfarbe << 4);  // rechts 4 0en anhängen (dual)
	    // -> 1111101 | LEDIG -> 001111101 | 010000000 -> 011111101
	    merkmale = merkmale | status; 
	    // 011111101
	    return merkmale; 
	} 	
	
	public static void decode(int merkmale) { 
        int mask= 15; // 000001111 
        int schuhgroesse= merkmale & mask; // z.B.: 011111101 -> 1101
        schuhgroesse += 36; // z.B.: 1101 = 13 -> 13 + 36 = 49
        
        // Die Bits 4 und 5 enthalten die Haarfarbe: 
        mask= 3 << 4;  // = 000110000 
        int haarfarbe= merkmale & mask; 
        haarfarbe >>= 4; // vier 0en rechts abstreichen 
        
        boolean istStudent= (merkmale & STUDENT) != 0; 
        boolean istLedig= (merkmale & LEDIG) != 0; 
        boolean istWeiblich= (merkmale & WEIBLICH) != 0; 
        
        System.out.println("Schuhgröße " + schuhgroesse); 
        
        String f; 
        switch (haarfarbe) { 
        case BLOND:    f= "blond"; break; 
        case ROT:      f= "rot"; break; 
        case SCHWARZ:  f= "schwarz"; break; 
            default:       f= "brünett"; break; 
        } 
        System.out.println(f); 
        System.out.println("Student: " + istStudent); 
        System.out.println("ledig: " + istLedig); 
        System.out.println("weiblich: " + istWeiblich); 
    } 
    
    public static boolean getBit(int zahl, int pos) { 
        int mask = 1 << pos;
        zahl &= mask;
        zahl >>= pos;
        if(zahl == 1) return true;
        return false;
    } 
    
    public static void writeBinary(int zahl) { 
        for (int i = 31; i>=0; i--) {
	        System.out.print(getBinaryAtPos(zahl,i));
	    }
	    System.out.println();
    } 
    
    public static int getBinaryAtPos(int zahl, int pos) { 
        int mask = 1 << pos;
        zahl &= mask;
        zahl >>= pos;
        return zahl;
    } 
    
    
	
}
