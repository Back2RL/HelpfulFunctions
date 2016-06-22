package SerializableExample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo2400 on 22.06.2016.
 */
public class Personen implements Serializable {
    private List<Person> personen;

    public Personen() {
        this.personen = new ArrayList<>();
        personen.add(new Person("XXXXXXXX",12,122222));
        personen.add(new Person("YYYYY",13242,232));
        personen.add(new Person("Y567tddYY",1256742,22));
    }

    public void save(){
        try {             // OutputStream für Datei erzeugen
            FileOutputStream fos = new FileOutputStream("personen.dat");
            // ObjectOutputstream, der Objekte serialisiert, erzeugen
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //Serialisieren der Objekte
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Personen laden() {
        Personen personen = null;
        try (// InputStream für Serialisierungs-Datei erzeugen
             FileInputStream fis = new FileInputStream("personen.dat");
             // ObjectInputstream, der Objekte deserialisiert, erzeugen
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Objekte müssen in selber Reihenfolge deserialisiert werden
            personen = (Personen) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Deserialisierung abgeschlossen");
        return personen;
    }

    @Override
    public String toString() {
        return "Personen{" +
                "personen=" + personen.toString() +
                '}';
    }
}