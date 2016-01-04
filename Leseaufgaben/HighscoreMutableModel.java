import java.util.Vector;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataListener;

public class HighscoreMutableModel implements MutableComboBoxModel {
    // Das Attribut eintraege stellt unsere Listelemente dar
    Vector eintraege = new Vector();
    // Index für selektierten Eintrag
    int index = -1;

    /* Hier geben wir zurück, was als selektierter Eintrag
       in der JComboBox angezeigt werden soll */
    public Object getSelectedItem() {
        if (index >= 0) {
            return ((Highscore) eintraege.elementAt(index)).getHighscoreEintrag();
        } else {
            return "";
        }
    }

    // Diese Funktion wird beim Selektieren eines Eintrages aufgerufen
    // Dort ermitteln wir den Index für das ausgewählte Element
    // anItem ist der in der JComoboBox ausgewählte Eintrag
    public void setSelectedItem(Object anItem) {
        for (int i = 0; i < eintraege.size(); i++) {
            if (((Highscore) eintraege.elementAt(i)).
                    getHighscoreEintrag().equals(anItem)) {
                index = i;
                break;
            }
        }
    }

    // Hier liefern wir die Anzahl der Elemente in unserer Liste zurück
    public int getSize() {
        return eintraege.size();
    }

    // Hier wird ein Element an einer bestimmten Stelle zurückgegeben
    public Object getElementAt(int index) {
        return ((Highscore) eintraege.elementAt(index)).getHighscoreEintrag();
    }


    // Hier fügen wir unserer Liste ein Highscore-Objekt hinzu
    // Wir ordnen es so ein, dass es in die Rangfolge passt
    public void addElement(Object obj) {
        if (!eintraege.contains(obj)) {
            int i = 0;

            while (i < eintraege.size() && ((Highscore) eintraege.get(i)).getRang()
                    <= ((Highscore) obj).getRang()) {
                i++;
            }

            eintraege.add(i, obj);
            if (index == -1) index = 0;
        }
    }

    // Hier entfernen wir ein Objekt aus der Liste
    public void removeElement(Object obj) {
        if (eintraege.contains(obj)) {
            eintraege.remove(obj);
        }
    }

    // Hier fügen wir ein Element an einer bestimmten Position ein
    public void insertElementAt(Object obj, int index) {
        eintraege.add(index, obj);
    }

    // Hier entfernen wir ein Element aus der Liste mit dem übergebenem Index
    public void removeElementAt(int index) {
        if (eintraege.size() > index) {
            eintraege.removeElementAt(index);
        }
    }

    // Die beiden folgenden Methoden berücksichtigen wir hier nicht
    public void addListDataListener(ListDataListener l) {
    }

    public void removeListDataListener(ListDataListener l) {
    }
}