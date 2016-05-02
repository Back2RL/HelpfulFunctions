import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int alter;
	private transient int gehalt; // transient: will not be written to file when
									// serialized

	/**
	 * @param name
	 * @param alter
	 * @param gehalt
	 */
	public Person(String name, int alter, int gehalt) {
		this.name = name;
		this.alter = alter;
		this.gehalt = gehalt;
	}

	/**
									 * 
									 */
	public Person() {

	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", alter=" + alter + ", gehalt=" + gehalt + "]";
	}

	public void speichern() {
		try (FileOutputStream fos = new FileOutputStream("Personen.dat");
				ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(this);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Person lesen() {
		Person result = null;
		try (FileInputStream fis = new FileInputStream("Personen.dat");
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			result = (Person) ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result == null)
			return new Person();
		return result;
	}

	public static void main(String[] args) {
		Person Hans = new Person("Hans", 12, 1147);
		System.out.println("Hans : " + Hans);
		Hans.speichern();
		Person peter = Person.lesen();
		System.out.println("peter : " + peter);

	}

}
