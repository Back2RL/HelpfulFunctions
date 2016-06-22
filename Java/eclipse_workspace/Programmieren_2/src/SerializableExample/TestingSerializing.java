package SerializableExample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestingSerializing {



    public static void main(String[] args) {
//        Person leo = new Person("Leo", 99, 20);
//        Person hans = new Person("hans", 59, 220);

//        Person.speichern();
//        Person.laden();

        Personen menge = new Personen();
        menge.save();
        Personen geladen =  Personen.laden();
        System.out.println(geladen);
    }
}
