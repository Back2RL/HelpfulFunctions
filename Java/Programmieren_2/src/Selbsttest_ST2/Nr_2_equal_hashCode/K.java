package Selbsttest_ST2.Nr_2_equal_hashCode;


import java.util.Objects;

public class K {

    private String s;
    private char c;

    public K(final String s, final char c) {
        this.s = s;
        this.c = c;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) return false;
        if( obj instanceof K){
            K k = (K) obj;
            return s.equals(k.s) && c == k.c;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(s,c);
    }
}
