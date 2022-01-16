package assignment2;

import java.util.Random;

public class Deck {
 public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
 public static Random gen = new Random();
 

 public int numOfCards; // contains the total number of cards in the deck
 public Card head; // contains a pointer to the card on the top of the deck

 
 
 /* 
  * TODO: Initializes a Deck object using the inputs provided
  */
 public Deck(int numOfCardsPerSuit, int numOfSuits) {
	 
	 if (numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13) {
		 throw new IllegalArgumentException("The first input must be between 1 and 13, inclusive.");
	 }
	 
	 if (numOfSuits < 1 || numOfSuits > suitsInOrder.length) {
		 throw new IllegalArgumentException("The second input must be between 1 and the size of the class field suitsInOrder.");
	 }
	 

	 Card tmp = new PlayingCard("clubs",1);
	 Joker red = new Joker("red");
	 Joker black = new Joker("black");
	 
	 
	 if (numOfCardsPerSuit == 1 && numOfSuits ==1) {
		 this.head = tmp;
		 tmp.next = red;
		 red.prev = tmp;
	 }
	 else {
	 this.head = tmp;
	 for (int i = 1; i<=numOfSuits;i++) {
		 for (int j = 1;j<=numOfCardsPerSuit;j++) {
			 PlayingCard c = new PlayingCard(suitsInOrder[i-1],j);
			 
			 
			if (i==numOfSuits && j==numOfCardsPerSuit) {
				 tmp.next = c;
				 c.prev = tmp;
				 c.next =red;
				 red.prev = c;
				 
			 }
			else if (i==1 && j ==1) {
				continue;
			}
			 else {
				 tmp.next = c;
				 c.prev = tmp;
				 tmp = c;				 
			 } 
		 }
	 }}
	 
	 red.next = black;
	 black.prev = red;
	 this.head.prev = black;
	 black.next = this.head;
	 
	 this.numOfCards =  numOfCardsPerSuit*numOfSuits+2;
	 
 }
 

 /* 
  * TODO: Implements a copy constructor for Deck using Card.getCopy().
  * This method runs in O(n), where n is the number of cards in d.
  */
 public Deck(Deck d) {

	 Card tmp = d.head;
	 if (d.numOfCards == 1) {
		 this.head = tmp.getCopy();
	 }
	 
	 else if (d.numOfCards == 0) {
		 this.head = null;
	 }
	 else {
		 
		 
		 this.head = tmp.getCopy();
		 this.head.next = tmp.next.getCopy();
		 
		 this.head.next.prev = this.head;
		 Card c = this.head.next;
		 tmp = tmp.next;
		 
	 for (int i = 2; i<=d.numOfCards;i++) {

		 if (i == d.numOfCards) {
			 c.next = this.head;
			 this.head.prev = c;
		 }
		 else {
			c.next = tmp.next.getCopy();
			c.next.prev = c;
			c = c.next;
			tmp = tmp.next;
			
		 }	
		 
	 }}
	 this.numOfCards = d.numOfCards;
	 
 }

 /*
  * For testing purposes we need a default constructor.
  */
 public Deck() {}

 /* 
  * TODO: Adds the specified card at the bottom of the deck. This 
  * method runs in $O(1)$. 
  */
 public void addCard(Card c) {
	 if (this.numOfCards == 0) {
		 this.head = c;
		 this.head.next = this.head;
		 this.head.prev = this.head;
	 }
	 else if (this.numOfCards == 1) {
		 this.head.next = c;
		 this.head.prev = c;
		 c.next = this.head;
		 c.prev = this.head;
	 }
	 
	 else { 
		 Card lastCard = this.head.prev;
		 lastCard.next = c;
		 c.prev = lastCard;
		 c.next = this.head;
		 this.head.prev = c;


	 }
	 this.numOfCards++;
 }

 /*
  * TODO: Shuffles the deck using the algorithm described in the pdf. 
  * This method runs in O(n) and uses O(n) space, where n is the total 
  * number of cards in the deck.
  */
 public void shuffle() {
	 
	 
	 if (this.numOfCards==0) {
		 this.head = null;
	 }
	 
	 
	 else {
	 Deck.Card [] cards = new Card[this.numOfCards];
	 
	 
	 Deck.Card tmp = this.head;
	 
	 for (int i = 0; i<this.numOfCards;i++) {
		 if (i == 0) {
			 cards[i] = tmp;
 
		 }
		 else {
			 cards[i] = tmp.next;
			 tmp = tmp.next;
		 }
	 }

	 for (int j = this.numOfCards-1;j>=1;j--) {
		 
		 int p = gen.nextInt(j+1);
		 Deck.Card tmp1 = cards[p];
		 cards[p] = cards[j];
		 cards[j] = tmp1;
		 
		 
	 }
	 
	 
	 Deck.Card tmp2 = cards[0];
	 for (int m = 0;m<this.numOfCards;m++) {
		 
		 if (m==0) {
			 this.head = tmp2;
		 }
		 
		 
		 else {
		 
			 tmp2.next = cards[m];
			 tmp2.next.prev = tmp2;
			 
			 tmp2 = tmp2.next;
		 }
	 }
	 this.head.prev = tmp2;
	 tmp2.next = this.head;
	 } 
 }

 /*
  * TODO: Returns a reference to the joker with the specified color in 
  * the deck. This method runs in O(n), where n is the total number of 
  * cards in the deck. 
  */
 public Joker locateJoker(String color) {
	 
	 if (color == "red" || color == "black") {
  Joker j = new Joker(color);
  String s = j.toString();
  
  Card tmp = this.head;
  
  for (int i = 0; i<this.numOfCards;i++) {
	  if (tmp.toString().equals(s)) {
		  return (Joker) tmp;
	  }
	  else {
		  tmp = tmp.next;
	  }
	  
  }}
  return null;
 }

 /*
  * TODO: Moved the specified Card, p positions down the deck. You can 
  * assume that the input Card does belong to the deck (hence the deck is
  * not empty). This method runs in O(p).
  */
 public void moveCard(Card c, int p) {
		 
	 if (p == 0) {
		 
	 }
	 else {
	 Card head = this.head;
	 
		 c.next.prev = c.prev;
		 c.prev.next = c.next;
		 
		 Card tmp = c;
		 
		 for (int i = 0;i<p;i++) {
			 tmp = tmp.next;
		 }
		 		 
		 tmp.next.prev = c;
		 c.next = tmp.next;
		 c.prev = tmp;
		 tmp.next = c;
		 
		 
		 if ( this.head != head ) {
		 this.head = head;
		 }
		 
	 }

 }

 /*
  * TODO: Performs a triple cut on the deck using the two input cards. You 
  * can assume that the input cards belong to the deck and the first one is 
  * nearest to the top of the deck. This method runs in O(1)
  */
 public void tripleCut(Card firstCard, Card secondCard) {
	 
	 Card afterSecond = secondCard.next;
	 Card tail = this.head.prev;
	 Card head = this.head;
	 Card beforeFirst = firstCard.prev;

	 if (afterSecond != head && beforeFirst != tail) {
	 secondCard.next = head;
	 head.prev = secondCard;
	 beforeFirst.next = afterSecond;
	 afterSecond.prev = beforeFirst; 
	 tail.next = firstCard;
	 firstCard.prev = tail;
	 
	 
	 this.head = afterSecond;
	 this.head.prev = beforeFirst;
	 this.head.prev.next = this.head;
	 }
	 
	 else if (this.head == firstCard && this.head.prev == secondCard) {
		 
	 }
	 
	 else if(this.head == firstCard && this.head.prev != secondCard) {
		 this.head = afterSecond;
	 }
	 
	 else { //this.head != firstCard && this.head.prev == secondCard
		 this.head = firstCard;
	 }	 
 }

 /*
  * TODO: Performs a count cut on the deck. Note that if the value of the 
  * bottom card is equal to a multiple of the number of cards in the deck, 
  * then the method should not do anything. This method runs in O(n).
  */
 public void countCut() {
	 
	 int number = this.head.prev.getValue()%this.numOfCards;
	 
	 if (number == 0 || number == this.numOfCards-1) {
		 
	 }
	 else {
		 Card tail = this.head.prev;
		 Card beforeTail = this.head.prev.prev;
		 Card head = this.head;
		 Card target = this.head;
		 
		 for (int i = 0;i<number-1;i++ ) {
			 target = target.next;
		 }
		 
		 tail.next = target.next;
		 target.next.prev = tail;
		 
		 this.head = target.next;
		 
		 target.next = tail;
		 tail.prev = target;
		 beforeTail.next = head;
		 head.prev = beforeTail;
		 

	 } 
 }

 /*
  * TODO: Returns the card that can be found by looking at the value of the 
  * card on the top of the deck, and counting down that many cards. If the 
  * card found is a Joker, then the method returns null, otherwise it returns
  * the Card found. This method runs in O(n).
  */
 public Card lookUpCard() {

	 int value = this.head.getValue()%this.numOfCards;
	 
	 
	 Card tmp = this.head;
	 
	 
	 for (int i = 0; i<=value;i++) {
		 if (i == value) {
			 if (tmp instanceof Joker) {
				 return null;
			 }
			 else {
				 return tmp;
			 }
		 }
		 
		 else {
			 tmp = tmp.next;
		 }
	 }
	 
  return null;
 }

 /*
  * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
  * using this deck. This method runs in O(n).
  */
 public int generateNextKeystreamValue() {
 
	 moveCard(this.locateJoker("red"),1);
	 moveCard(this.locateJoker("black"),2);
 
	 Card tmp = this.head;
	 	 
	 Card firstJoker = this.head;
		 
	 for (int i = 0;i<this.numOfCards;i++) {
		 if (tmp instanceof Joker) {
			 firstJoker = tmp;
			 break;
		 }
		 tmp = tmp.next;
	 }
	
	 
	 if (((Joker) firstJoker).getColor() == "red") {
		 Joker secondJoker = locateJoker("black");
		 tripleCut(firstJoker, secondJoker);
	 }
	 
	 if (((Joker) firstJoker).getColor() == "black") {
		 Joker secondJoker = locateJoker("red");
		 tripleCut(firstJoker, secondJoker);
	 }

	 countCut(); 
	 
	 if (lookUpCard() == null) {
		 return generateNextKeystreamValue();
	 }
	 
	 return lookUpCard().getValue();

 }


 public abstract class Card { 
  public Card next;
  public Card prev;

  public abstract Card getCopy();
  public abstract int getValue();

 }

 public class PlayingCard extends Card {
  public String suit;
  public int rank;
  

  public PlayingCard(String s, int r) {
   this.suit = s.toLowerCase();
   this.rank = r;
  }

  public String toString() {
   String info = "";
   if (this.rank == 1) {
    //info += "Ace";
    info += "A";
   } else if (this.rank > 10) {
    String[] cards = {"Jack", "Queen", "King"};
    //info += cards[this.rank - 11];
    info += cards[this.rank - 11].charAt(0);
   } else {
    info += this.rank;
   }
   //info += " of " + this.suit;
   info = (info + this.suit.charAt(0)).toUpperCase();
   return info;
  }

  public PlayingCard getCopy() {
   return new PlayingCard(this.suit, this.rank);   
  }

  public int getValue() {
   int i;
   for (i = 0; i < suitsInOrder.length; i++) {
    if (this.suit.equals(suitsInOrder[i]))
     break;
   }

   return this.rank + 13*i;
  }

 }

 public class Joker extends Card{
  public String redOrBlack;

  public Joker(String c) {
   if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
    throw new IllegalArgumentException("Jokers can only be red or black"); 

   this.redOrBlack = c.toLowerCase();
  }

  public String toString() {
   //return this.redOrBlack + " Joker";
   return (this.redOrBlack.charAt(0) + "J").toUpperCase();
  }

  public Joker getCopy() {
   return new Joker(this.redOrBlack);
  }

  public int getValue() {
   return numOfCards - 1;
  }

  public String getColor() {
   return this.redOrBlack;
  }
 }

}
