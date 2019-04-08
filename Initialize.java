package pl.replayGames.paluszki;

import java.util.Scanner;

public class Initialize {

	public static void main(String[] args) {
	
		Scanner in = new Scanner(System.in);
		int players, fingers;
		do {
			System.out.println("How many players? (from 2 to 6)");
			players = in.nextInt();
		}
		while(!(players>1 && players<7));
		do {
			System.out.println("How many fingers at the beggining? (from 1 to 4)");
			fingers = in.nextInt();
		}
		while(!(fingers>0 && fingers<5));
		Game gra = new Game(fingers, players);
		gra.run();
		//Thread th = new Thread(gra);
		//th.start();
	}
}