package Loc;

import java.util.Comparator;

public class LocLexiComparator implements Comparator<Loc> {

	@Override
	public int compare(Loc o1, Loc o2) {
		// TODO Auto-generated method stub
		if (o1.getX() < o2.getX())
			return -1;
		if (o1.getX() > o2.getX())
			return +1;
		return Integer.compare(o1.getY(), o2.getY());
	}

}
