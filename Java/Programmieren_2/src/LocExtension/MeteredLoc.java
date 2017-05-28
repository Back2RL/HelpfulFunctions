package LocExtension;

import Loc.Loc;

public class MeteredLoc extends Loc {
	private double totalDistance;

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	@Override
	public void setLocation(int x, int y) {
		totalDistance += distance(new Loc(x, y));
		super.setLocation(x, y);
	}

	public MeteredLoc(int x, int y) {
		super(x, y);
		this.totalDistance = 0;
	}
}
