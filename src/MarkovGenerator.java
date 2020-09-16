/* Ru Ferguson
 * 21 September 2020
 * 
 */

import java.util.ArrayList;

public class MarkovGenerator<T> extends ProbabilityGenerator<T> {
	
	MarkovGenerator() {
		super();
	}
	
	T generate() {
		T newToken = null;
		// do something here
		return newToken;
	}
	
	void train(ArrayList<T> newTokens) {
		
	}
	
	ArrayList<T> generate(int length) {
		ArrayList<T> newSequence = new ArrayList<T>();
//		for (int i = 0; i < length; i++) {
//			newSequence.add(generate());
//	 	}
		return newSequence;
	}
	
	
	ArrayList<T> generate(int length, T initToken) {
		ArrayList<T> newSequence = new ArrayList<T>();
//		for (int i = 0; i < length; i++) {
//			newSequence.add(generate());
//		}
		return newSequence;
	}
}
