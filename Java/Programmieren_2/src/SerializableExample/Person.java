package SerializableExample;

import java.io.*;

public class Person implements Serializable {

    private String name;
    private int age;
    private transient int weight; // not being serialized

    public Person(String name, int age, int weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public void save() {
        try (FileOutputStream fileOutStream = new FileOutputStream(name + ".dat");
             ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream)) {

            objectOutStream.writeObject(this);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(name + " serialized");
    }

    public static void speichern() {
        Person gerd = new Person("Gerd", 23, 47000);
        Person maria = new Person("Maria", 65, 77000);
        System.out.println(gerd);
        System.out.println(maria);
        try {             // OutputStream für Datei erzeugen
            FileOutputStream fos = new FileOutputStream("personen.dat");
            // ObjectOutputstream, der Objekte serialisiert, erzeugen
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //Serialisieren der Objekte
            oos.writeObject(gerd);
            oos.writeObject(maria);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Serialisierung abgeschlossen");
    }

    public static void laden() {
        Person gerd = null, maria = null;
        try (// InputStream für Serialisierungs-Datei erzeugen
             FileInputStream fis = new FileInputStream("personen.dat");
             // ObjectInputstream, der Objekte deserialisiert, erzeugen
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Objekte müssen in selber Reihenfolge deserialisiert werden
            gerd = (Person) ois.readObject();
            maria = (Person) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(gerd);
        System.out.println(maria);
        System.out.println("Deserialisierung abgeschlossen");
    }


    public boolean writeToFile(ObjectOutputStream oos) {
        if (oos == null) {
            return false;
        }
        try {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                '}';
    }
}
