package blackjack;

import java.util.ArrayList;
import java.util.Random;

import javax.net.ssl.SSLEngineResult.HandshakeStatus;

import deckOfCards.*;

/* This class contains a few instance methods and a few private static methods which can be used to instantiate a
 * BlackjackModel which contains arrayLists of dealCards and playerCards and a Deck object.
 */
public class BlackjackModel {

	private ArrayList<Card> dealerCards; // ArrayList of Cards used to store the dealer's cards
	private ArrayList<Card> playerCards; // ArrayList of Cards used to store the player's cards
	private Deck deck; // Deck variable representing the Deck of cards for the game

	// Instance method which returns a copy of the ArrayList of dealer's cards
	public ArrayList<Card> getDealerCards() {
		return new ArrayList<>(dealerCards);
	}

	// Instance method which returns a copy of the ArrayList of player's cards
	public ArrayList<Card> getPlayerCards() {
		return new ArrayList<>(playerCards);
	}

	/* Instance method which takes in an ArrayList of Cards as an argument and assigns the current object's dealerCards
	 * to a copy of that 
	 */
	public void setDealerCards(ArrayList<Card> cards) {
		dealerCards = new ArrayList<>(cards);
	}

	/* Instance method which takes in an ArrayList of Cards as an argument and assigns the current object's playerCards
	 * to a copy of that 
	 */
	public void setPlayerCards(ArrayList<Card> cards) {
		playerCards = new ArrayList<>(cards);
	}

	/* Instance method which takes in a number generated by the Random module as an argument. It instantiates the
	 * current object's deck and then shuffles it using the shuffle method from the Deck class by passing the parameter
	 * random along with it.
	 */
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}

	/* Instance method which instantiates the current object's dealerCards. It then deals two cards from the deck
	 * and adds them to the list of dealer's cards.
	 */
	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			dealerCards.add(deck.dealOneCard());
		}
	}

	/* Instance method which instantiates the current object's playerCards. It then deals two cards from the deck
	 * and adds them to the list of player's cards.
	 */
	public void initialPlayerCards() {
		playerCards = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			playerCards.add(deck.dealOneCard());
		}
	}

	/* Instance method which deals one card from the deck and adds it to the list of player's cards. This method
	 * assumes that the initialPlayerCards method has been called already.
	 */
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}

	/* Instance method which deals one card from the deck and adds it to the list of dealer's cards. This method
	 * assumes that the initialDealerCards method has been called already.
	 */
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}

	/* Static method which takes in an ArrayList of cards as an argument. It evaluates the passed argument and returns
	 * an ArrayList of integers representing the possible values of the hand. When the hand has an Ace in it, the 
	 * method returns an ArrayList of size 2 as long as the second hand value is under 21. In the case where the only
	 * possible hand value is over 21, it returns an ArrayList containing that value. The size of the returned 
	 * ArrayList is always either 1 or 2 and the integers will always be sorted in ascending order.
	 */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand) {
		Integer sum1 = 0;
		Integer sum2 = 0;
		ArrayList<Integer> returnArray = new ArrayList<>();

		for (Card card : hand) {
			sum1 += card.getRank().getValue();
		} 
		returnArray.add(sum1);

		if (!hasAce(hand)) { // checks for Ace in hand
			return returnArray;
		} else { // Ace is present in hand
			sum2 = sum1 + 10; // Considering one Ace's value as 11
			if (sum2 > 21) {
				return returnArray;
			} else { // if second sum is under 32
				returnArray.add(sum2);
				return returnArray;
			}
		}		
	}
	
	/* Static method which takes in an ArrayList of cards as an argument. It checks for the presence of Aces in the 
	 * passed argument. It returns true if an Ace is present and false otherwise.
	 */
	private static boolean hasAce(ArrayList<Card> hand) {
		boolean handHasAce = false;
		for (Card card : hand) {
			if (card.getRank() == Rank.ACE) {
				handHasAce = true;
			}
		}
		return handHasAce;
	}

	/* Static method which takes in an ArrayList of cards as an argument. It evaluates the passed argument and returns
	 * one of the 4 HandAssesment constants. If the hand is null or contains lesser than 2 cards, it returns 
	 * INSUFFICIENT_CARDS. In the case where the hand is a Natural Blackjack, it returns NATURAL_BLACKJACK. If the 
	 * hand's only possible value is over 21 it returns BUST and if none of the above conditions line up, then it
	 * returns NORMAL.
	 */
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		if (hand == null || hand.size() < 2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		} else if ((hand.get(1).getRank().getValue() == 10 || hand.get(0).getRank().getValue() == 10)
				&& hasAce(hand) && hand.size() == 2) { // if hand has one Ace and one card of value 10
			return HandAssessment.NATURAL_BLACKJACK;
		} else if (possibleHandValues(hand).get(0).compareTo(21) > 0) {
			return HandAssessment.BUST;
		} else {
			return HandAssessment.NORMAL;
		}
	}

	/* Instance method which evaluates playerCards and dealerCards and determines the outcome of the game. It returns
	 * one of the four GameResult constants. The method assumes that the game has ended and that the player and the
	 * dealer each have at least 2 cards. If the player has a Natural Blackjack, it returns NATURAL_BLACKJACK. If both
	 * the dealer and the player have a Natural Blackjack, it returns PUSH. If the dealer is busted, it returns 
	 * PLAYER_WON and if the player is busted, it returns PLAYER_LOST. In the case where none of them have busted, 
	 * it returns PUSH when their hands have the same value, PLAYER_LOST if dealer's hand's value is higher and 
	 * PLAYER_WON if player's hand's value is higher.
	 */
	public GameResult gameAssessment() {
		int lengthDealer = possibleHandValues(dealerCards).size();
		int lengthPlayer = possibleHandValues(playerCards).size();

		if (assessHand(playerCards) == HandAssessment.NATURAL_BLACKJACK &&
				assessHand(dealerCards) != HandAssessment.NATURAL_BLACKJACK) {
			return GameResult.NATURAL_BLACKJACK;
		} else if (assessHand(playerCards) == HandAssessment.NATURAL_BLACKJACK &&
				assessHand(dealerCards) == HandAssessment.NATURAL_BLACKJACK){
			return GameResult.PUSH;
		} else if (assessHand(playerCards) == HandAssessment.BUST) {
			return GameResult.PLAYER_LOST;
		} else if (assessHand(dealerCards) == HandAssessment.BUST) {
			return GameResult.PLAYER_WON;
		} else if (assessHand(playerCards) != HandAssessment.NATURAL_BLACKJACK &&
				possibleHandValues(playerCards).get(lengthPlayer - 1) == 21 &&
				assessHand(dealerCards) == HandAssessment.NATURAL_BLACKJACK) {
			return GameResult.PUSH;
		} else if (possibleHandValues(playerCards).get(lengthPlayer - 1) > /* length - 1 gives max value of 
 																		possibleHandValues arrayList as it is sorted */
					possibleHandValues(dealerCards).get(lengthDealer - 1)) { 
			return GameResult.PLAYER_WON;
		} else if (possibleHandValues(dealerCards).get(lengthDealer - 1) > 
					possibleHandValues(playerCards).get(lengthPlayer - 1)) {
			return GameResult.PLAYER_LOST;
		} else { // If hands of both the player and the dealer have equal values
			return GameResult.PUSH;
		}  

	}

	/* Instance method which evaluates the current object's dealerCards to check whether or not the dealer should 
	 * take another card in his/her turn. returns true if the dealer's hand is 16 or less, or if the hand's value
	 * could be either 7 or 17. Otherwise, it returns false.
	 */
	public boolean dealerShouldTakeCard() {
		int length = possibleHandValues(dealerCards).size();
		if (possibleHandValues(dealerCards).get(length - 1) >= 18) {
			return false;
		} else if (possibleHandValues(dealerCards).contains(17) && 
				!possibleHandValues(dealerCards).contains(7)) {
			return false;
		} 
		return true; // if the dealer's hand <= 16 OR hand's value is both 7 & 17
	}
}
