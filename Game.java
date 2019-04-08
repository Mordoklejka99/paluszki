import java.util.Random;
import java.util.Scanner;

public class Game {

	private int nOfFingers;
	private int nOfPlayers;
	private int turn;
	private Player[] players = new Player[6];
	private boolean[] playing = new boolean[6];
	
	public Game(int fingers, int players) {
	
		nOfFingers = fingers;
		nOfPlayers = players;
	}
	
	private class Hand {
		
		private int fingers;
		
		public Hand() {
		
			fingers = nOfFingers;
		}
		public void add(int val) {
		
			fingers = (fingers + val) % 5;
		}
		public boolean check() {
		
			return (fingers != 0);
		}
		public int checkValue() {
		
			return fingers;
		}
	}
	
	private class Player {
		
		private Hand leftHand;
		private Hand rightHand;
		
		public Player() {
			
			leftHand = new Hand();
			rightHand = new Hand();
		}
		public void addToLeft(int val) {
		
			leftHand.add(val);
		}
		public void addToRight(int val) {
		
			rightHand.add(val);
		}
		public int checkLeftValue() {
		
			return leftHand.checkValue();
		}
		public int checkRightValue() {
		
			return rightHand.checkValue();
		}
		public boolean splitPossible() {
			
			if((checkLeftValue() == 0 && checkRightValue() != 0 && checkRightValue() % 2 == 0) || (checkRightValue() == 0 && checkLeftValue() != 0 && checkLeftValue() % 2 == 0))
				return true;
			else
				return false;
		}
		public void split() {
			
			if(checkLeftValue() == 0 && checkRightValue() != 0 && checkRightValue() % 2 == 0) {
				addToLeft(checkRightValue() / 2);
				addToRight(-checkLeftValue());
			}
			else {
				addToRight(checkLeftValue() / 2);
				addToLeft(-checkRightValue());
			}
		}
		public boolean check() {
		
			return (leftHand.check() || rightHand.check());
		}
		public void checkState() {
			
			System.out.println("\tLeft Hand: " + leftHand.checkValue());
			System.out.println("\tRight Hand: " + rightHand.checkValue());
		}
	}
	
	private void checkState() {
	
		for(int i = 0; i < nOfPlayers; i++)
			if(playing[i]) {
				System.out.println("Player " + (i + 1) + ":");
				players[i].checkState();
			}
	}
	private boolean keepPlaying() {
	
		int result = 0;
		for(int i = 0; i < nOfPlayers; i++)
			if(playing[i])
				result++;
		return result > 1;
	}
	private boolean checkPlayer(int idx) {
		
		if(!players[idx].check())
			playing[idx] = false;
		return playing[idx];
	}
	private int nextPlaying(int idx) {
		
		int result = (idx + 1) % nOfPlayers;
		while(!playing[result])
			result = (result + 1) % nOfPlayers;
		return result;
	}
	private void nextTurn() {
	
		turn = nextPlaying(turn);
	}

	private void main() {
		
		Scanner in = new Scanner(System.in);
		int action1, action2;
		do {
			int tmp = nextPlaying(turn) % nOfPlayers;
			do {
				do {
					boolean split;
					boolean validAction;
					do {
						System.out.println("\nPlayer " + (turn + 1) + "'s turn\n");
						checkState();
						validAction = true;
						System.out.println("\nWhat to do?");
						if(players[turn].checkLeftValue() > 0)
							System.out.println("\t1 - Use left hand");
						if(players[turn].checkRightValue() > 0)
							System.out.println("\t2 - Use right hand");
						split = players[turn].splitPossible();
						if(split)
							System.out.println("\t3 - Split");
						action1 = in.nextInt();
						if((action1 != 1 && action1 != 2 && action1 != 3) || (action1 == 1 && players[turn].checkLeftValue() == 0) || (action1 == 2 && players[turn].checkRightValue() == 0) || (action1 == 3 && !split)) {
							System.out.println("\nInvalid action!");
							validAction = false;
						}
						else if(action1 == 3 && split)
							players[turn].split();
					}
					while(!validAction);
				}
				while(action1 == 3);
				boolean validAction;
				do {
					validAction = true;
					System.out.println("\nOn which hand?");
					if(players[tmp].checkLeftValue() > 0)
						System.out.println("\t1 - On left hand");
					if(players[tmp].checkRightValue() > 0)
						System.out.println("\t2 - On right hand");
					System.out.println("\t0 - Change your hand");
					action2 = in.nextInt();
					if((action2 != 0 && action2 != 1 && action2 != 2) || (action2 == 1 && players[tmp].checkLeftValue() == 0) || (action2 == 2 && players[tmp].checkRightValue() == 0)) {
						System.out.println("\nInvalid action!");
						validAction = false;
					}
				}
				while(!validAction);
			}
			while(action2 == 0);
			switch(action1) {
			case 1:
				switch(action2) {
				case 1:
					players[tmp].addToLeft(players[turn].checkLeftValue());
					break;
				case 2:
					players[tmp].addToRight(players[turn].checkLeftValue());
					break;
				}
				break;
			case 2:
				switch(action2) {
				case 1:
					players[tmp].addToLeft(players[turn].checkRightValue());
					break;
				case 2:
					players[tmp].addToRight(players[turn].checkRightValue());
					break;
				}
				break;
			}
			if(!checkPlayer(tmp))
				System.out.println("Player " + (tmp + 1) + " loses!");
			nextTurn();
		}
		while(keepPlaying());
		System.out.println("Player " + (turn + 1) + " wins!");
	}
	
	public void run() {
		
		Random generator = new Random();
		turn = generator.nextInt(nOfPlayers);
		for(int i = 0; i < nOfPlayers; i++) {
			playing[i] = true;
			players[i] = new Player();
		}
		main();
	}
}
