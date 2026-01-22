package hangingMan;

import java.util.ArrayList;
import java.util.Scanner;

public class Logic {
	static String key = "";
	static int length;
	static char userInfo[]; 
	static int fault;
	final static int MAX_FAULT = 7;
	static ArrayList<Character> faultyGuesses = new ArrayList<>();
	
	public static void main(String[] args) {
		System.out.print("enter what the player next to you will guess(case insensitive): ");
		Scanner scanner = new Scanner(System.in);
		key = scanner.nextLine().toLowerCase();
		length = key.length(); // how many letters does this word have?
		// instantiate the array and fill it with hypens 
		userInfo = new char[length];
		fillUserInfoPlaceholders(userInfo);
		String userStringGuess = "";
		char userCharGuess = '\0'; // initially null
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
				+ "--------------------------------Game--------------------------------");
		do {
			// maybe a char array with that length
			// every iteration;
			printInfo(userInfo);
			// - we take a letter guess
			System.out.print("Enter a letter guess if you would like: (* for whole word)\t");
			userCharGuess = Character.toLowerCase(scanner.next().charAt(0)); // only the first char as it feels right
			scanner.nextLine();
			// - we update this array accordingly IF the letter is found
			if(key.contains(userCharGuess + "")) { // string conversion
				userInfo = manageUserInfo(userInfo, userCharGuess);
				if(noHypensRemain(userInfo)) {
					// if the user guessed all the words break it
					// no need for a word guess when there is no _
					printInfo(userInfo);
					System.out.println("You won by guessing all the characters");
					break;
				}
			}
			else if(userCharGuess == '*') {
				System.out.print("Enter your guess: ");
				userStringGuess = scanner.nextLine();
				if(!key.equals(userStringGuess.trim()))
					System.out.println("Faults " + ++fault + "/" + MAX_FAULT);
			}
			else { // guess was faulty and not a menu command(like *)
				if(uniqueFault(userCharGuess)) {
					faultyGuesses.add(userCharGuess);
					System.out.println("Faults " + ++fault + "/" + MAX_FAULT);
				}
			}
			
			if(fault >= MAX_FAULT) {
				System.out.println("You lost! But it is okay...\nThe key was: " + key);
				break;
			}
			// - re-print it on the screen with the updates
			
		} while(!key.equals(userStringGuess.trim())); // while the user hasn't gotten the word yet
		
		if(key.equals(userStringGuess.trim()))
			System.out.println("You won by guessing the whole word");
		
		scanner.close();
	}
	/* returns true if no hypens remain, making the user auto-win*/
	static boolean noHypensRemain(char[] userInfo) {
		for(char c : userInfo) {
			if (c == '_') {
				return false;
			}
		}
		return true;
	}
	
	static boolean uniqueFault(char newFault){
		for(Character c :faultyGuesses) {
			if(c.equals(newFault)) {
				System.out.println("Don't be repeating your mistakes like that!");
				return false;
			}
		}
		return true;
	}
	
	static void fillUserInfoPlaceholders(char[] userInfo) {
		for(int i = 0; i < userInfo.length; i++) {
			if(key.charAt(i) != ' ') {
				userInfo[i] = '_';
			}
			else
				userInfo[i] = ' ';
		}
	}
	
	static char[] manageUserInfo(char[] userInfo, char userChar){
		// compare the char you have taken with the key string
		// replace matches with the character instead
		for(int i = 0; i < key.length(); i++) {
			if(userChar == key.charAt(i)) {
				userInfo[i] = userChar;
			}
		}
		return userInfo;
	}
	
	static void printInfo(char[] userInfo) {
		for(char c: userInfo) {
			System.out.print(c + " ");
		}
		System.out.println("\nFaulty guesses: " + faultyGuesses.toString() + "\n");
	}
}

/* TODO
 * uppercase lowercase insensitivity DONE
 * spaces should not appear as hypen DONE
 * there should be some limiting factor DONE
 * asking for the whole word all the time is no good! DONE
 * dont make the user over-lose rights for a repetition of a faulty guess DONE
 * compare the trimmed input when it is whole-word-guess DONE
 * */
