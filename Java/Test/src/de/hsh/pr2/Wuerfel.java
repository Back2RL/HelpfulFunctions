package de.hsh.pr2;

import java.util.Random;

public class Wuerfel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sample();
	}

	public static int sample() {
		Random rand = new Random();
		for (int i = 0; i < 60; i++) {
			int wurf = rand.nextInt(6) + 1;
			System.out.println(wurf);
		}
		return 0;
	}

}
