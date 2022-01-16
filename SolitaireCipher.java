package assignment2;

public class SolitaireCipher {
	public Deck key;
	
	
	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}
	
	/* 
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {
		
		int [] array = new int[size];
		

		for (int i = 0;i<size;i++) {

			array[i] = this.key.generateNextKeystreamValue();
		}
		
		return array;
	}
		
	/* 
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {
		
		String s = "";
		
		for (int i = 0; i < msg.length();i++) {
			char letter = msg.charAt(i);
			if ((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z')) {
				s += letter;
			}
			else {
				continue;
			}
			
		}
		
		s = s.toUpperCase();
		
		int [] keystream = this.getKeystream(s.length());
		String newString = "";
		
		for (int j = 0;j<s.length();j++) {
			int shift = keystream[j]%26;
			
			newString += (char) (((s.charAt(j)-65)+shift)%26+65);
		}
		
		return newString;
	}
	
	/* 
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	public String decode(String msg) {
		
		int [] keystream = this.getKeystream(msg.length());
		String s = "";
		
		for (int i = 0; i<msg.length();i++) {
			int shift = keystream[i]%26;
			
			
			int a = msg.charAt(i)-65-shift;
			
			if (a<0) {
				a += 26;
				s += (char) (a+65);
			}
			else {
			
			s += (char) (a+65);
		}
		}
		
		return s;
	}
	
}
