package Selbsttest_ST1.Nr_7_Zugriffs_Modifizierer;

public class S {
    static int getTotalInstanceCount(){
        return A.getInstanceCount() + B.getInstanceCount();
    }
}
