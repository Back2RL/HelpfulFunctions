package Tutorial_Swing_Komponenten;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

public class ComponentDemo extends JFrame {
    public ComponentDemo() {
        super("ComponentDemo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bauePanels();
        baueNameGUI();
        baueVisitenKarteGUI();



        pack();
    }

    private JPanel jpnlName;
    private JPanel jpnlWeitere;
    private JPanel jpnlFoto;
    private JPanel jpnlVisit;

    private JTextField jtfName;
    private JTextField jtfRufname;
    private JTextField jtfVorname;

    private JTextPane jtpVisitenKarte;

    private void bauePanels() {
        jpnlName = new JPanel();
        jpnlName.setLayout(new GridBagLayout());
        jpnlName.setBorder(BorderFactory.createTitledBorder("Name"));

        jpnlWeitere = new JPanel();
        jpnlWeitere.setLayout(new GridBagLayout());
        jpnlWeitere.setBorder(BorderFactory.createTitledBorder("Weitere Angaben"));

        jpnlFoto = new JPanel();
        jpnlFoto.setLayout(new BorderLayout());
        jpnlFoto.setBorder(BorderFactory.createTitledBorder("Foto"));

        jpnlVisit = new JPanel();
        jpnlVisit.setLayout(new BorderLayout());
        jpnlVisit.setBorder(BorderFactory.createTitledBorder("Visitenkarte"));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(jpnlName);
        getContentPane().add(jpnlWeitere);
        getContentPane().add(jpnlFoto);
        getContentPane().add(jpnlVisit);
    }

    private void baueNameGUI(){
        jtfName = new JTextField(10);
        jtfRufname = new JTextField(10);
        jtfVorname = new JTextField(20);

        Insets lblInsets = new Insets(2,5,2,2);
        Insets nullInsets = new Insets(0,0,0,0);

        addComponent(0,0,1,1,0.0,0.0,jpnlName,new JLabel("Name:"),lblInsets);
        addComponent(1,0,1,1,1.0,0.0,jpnlName,jtfName,nullInsets);
        addComponent(2,0,1,1,0.0,0.0,jpnlName,new JLabel("Rufname:"),lblInsets);
        addComponent(3,0,1,1,1.0,0.0,jpnlName,jtfRufname,nullInsets);
        addComponent(0,1,1,1,0.0,0.0,jpnlName,new JLabel("Vornamen:"),lblInsets);
        addComponent(1,1,3,1,1.0,0.0,jpnlName,jtfVorname, nullInsets);

        // fette Schrift des Rufnamens
        Font ftBold = jtfRufname.getFont().deriveFont(Font.BOLD);
        jtfRufname.setFont(ftBold);

        DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                visitenKarteBauen();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                visitenKarteBauen();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };

        jtfRufname.getDocument().addDocumentListener(docListener);
        jtfVorname.getDocument().addDocumentListener(docListener);
        jtfName.getDocument().addDocumentListener(docListener);
    }

    private void baueVisitenKarteGUI(){
        jtpVisitenKarte =  new JTextPane();
        jtpVisitenKarte.setPreferredSize(new Dimension(400,200));
        jpnlVisit.add(new JScrollPane(jtpVisitenKarte));

        HTMLEditorKit htmlEditorKit =  new HTMLEditorKit();
        htmlEditorKit.setStyleSheet(new StyleSheet());
        jtpVisitenKarte.setEditorKit(htmlEditorKit);
        HTMLDocument htmlDocument = (HTMLDocument)htmlEditorKit.createDefaultDocument();
        jtpVisitenKarte.setDocument(htmlDocument);

        jtpVisitenKarte.setEditable(false);
    }

    private void visitenKarteBauen() {
        // ---- Baue einen String mit HTML-Content
        // -- Aufbau: Tabelle mit einer Zeile und zwei Spalten
        // -- Erste Spalte: Text, zweite Spalte: Foto
        StringBuilder sb = new StringBuilder();
        sb.append("<body style=\"font-family:sans-serif\">");
        sb.append(" <table width=\"100%\"><tr><td valign=\"top\">");

        // -- Anrede
        // kommt später
        // -- Name
        sb.append("<span style=\"font-size:larger\">").
                append("<span style=\"font-weight:bold\">").
                append(jtfRufname.getText()).
                append("</span>").
                append(" ").append(jtfVorname.getText()).
                append(" ").append(jtfName.getText()).
                append("</span>").
                append(" <br/>");
        // -- Geburtsdatum
        // kommt später
        // -- Beruf
        // kommt später
        sb.append(" </td><td align=\"right\">");

        // -- Foto
        // kommt später
        sb.append("</td></tr></table>");
        sb.append("</body>");
        try {
            HTMLDocument htmlDocument = (HTMLDocument) jtpVisitenKarte.getDocument();
            Element htmlElement = htmlDocument.getRootElements()[0];
            Element bodyElement = htmlElement.getElement(0);
            htmlDocument.setOuterHTML(bodyElement, sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Fehler beim Bauen der Visitenkarte: " + e.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }





    /**
     * Hilfsroutine beim Hinzufügen einer Komponente zu einem
     * Container im GridBagLayout.
     * Die Parameter sind Constraints beim Hinzufügen.
     *
     * @param x       x-Position
     * @param y       y-Position
     * @param width   Breite in Zellen
     * @param height  Höhe in Zellen
     * @param weightx Gewicht
     * @param weighty Gewicht
     * @param cont    Container
     * @param comp    Hinzuzufügende Komponente
     * @param insets  Abstände rund um die Komponente
     */
    private static void addComponent(final int x, final int y, final int width, final int height, final double weightx, final double weighty, Container cont, Component comp, Insets insets) {
GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.insets = insets;
        cont.add(comp,gbc);
    }



    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new ComponentDemo();
                f.setVisible(true);
            }
        });
    }
}
