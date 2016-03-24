import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Verdoppeln {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>(Arrays.asList(new String[] { "1", "2", "3" }));
		verdoppeln(list);
		System.out.println(list);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public void verdoppeln(List list) {
		for (int i = 0; i < list.size(); i += 2) {
			list.add(i, list.get(i));
		}
	}
}
