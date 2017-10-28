import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Main {

	public static void main(String[] args) {
		createXML_withDOM4J();
	}

	static void createXML_withDOM4J() {
		try {
			org.dom4j.Document document = DocumentHelper.createDocument();
			org.dom4j.Element root = document.addElement("cars");
			org.dom4j.Element supercarElement = root.addElement("supercars").addAttribute("company", "Ferrai");

			supercarElement.addElement("carname").addAttribute("type", "Ferrari 101").addText("Ferrari 101");

			supercarElement.addElement("carname").addAttribute("type", "sports").addText("Ferrari 202");

			// Pretty print the document to System.out
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer;
			writer = new XMLWriter(System.out, format);
			writer.write(document);
			File out = new File("dom4j_cars.xml");
			writer = new XMLWriter(new BufferedOutputStream(new FileOutputStream(out)), format);
			writer.write(document);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
