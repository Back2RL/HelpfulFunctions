import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SubMap {

	public static void main(String[] args) {
		Map<String, String> map1 = new HashMap<>();
		map1.put("Meier", "0511-673904");
		map1.put("Müller", "030-8497489");
		Map<String, String> map2 = new HashMap<>();
		map2.put("Meier", "0511-673904");
		map2.put("Schulz", "0511-673904");
		map2.put("Müller", "030-8497489");
		System.out.println(subMap(map1, map2));
		System.out.println(map1);
		System.out.println(map2);
	}

	public static boolean subMap(Map<String, String> map1, Map<String, String> map2) {
		Set<Entry<String, String>> temp = new HashSet<>(map2.entrySet());
		temp.retainAll(map1.entrySet());
		return map1.entrySet().equals(temp);
	}

}
