/**
 *	Sa 02. Jan 15:28:17 CEST 2016
 *
 *	PR1, WS2015/16
 *
 *	Leonard Oertelt
 *	Matrikelnummer 1276156
 *	leonard.oertelt@stud.hs-hannover.de
 * 
 *	Julian Opitz
 * 	Matrikelnummer 1302082
 * 	julian.opitz@stud.hs-hannover.de
 *
 *	-----------------------------------------
 *	Programmbeschreibung
 */

import java.util.HashMap;

public class SubMap {
    public static void main(String[] args) {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("Meier", "0511-673904");
        map1.put("Müller", "030-8497489");
        //map1.put("Müller", "030-1111111");

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("Meier", "0511-673904");
        map2.put("Schulz", "0511-673904");
        map2.put("Müller", "030-8497489");

        System.out.println(subMap(map1, map2));
    }

    public static boolean subMap(HashMap<String, String> map1, HashMap<String, String> map2) {
        for (String key : map1.keySet()) {
            if (map2.containsKey(key) && map2.get(key).equals(map1.get(key))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
