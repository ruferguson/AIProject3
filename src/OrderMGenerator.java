/* Ru Ferguson
 * 5 October 2020
 * 
 * This class inherits from the superclass, MarkovGenerator from Project 2 and takes the use of Markov
 * Chains to another level. Now the probabilities that a note will occur takes into account the preceding
 * sequence of order M to predict the next most likely "nice-sounding" note. The train() method formulates
 * the probabilities and the next most probable note will be output and stored using the using the
 * generate() method.*/

import java.util.ArrayList;
import java.util.Arrays;


public class OrderMGenerator<T> extends MarkovGenerator<T> {
	
	int orderM;  // the order of the Markov Chain – set this in your constructor.
	int rowIndex;
	int currentValue;
	ArrayList<ArrayList<T>> uniqueAlphabetSequences;  // array of the unique sequences of size N found in your input.
	ArrayList<T> curSequence; // the current sequence – will have to be a container
	MarkovGenerator<T> initTokenGen = new MarkovGenerator<T>();
	ArrayList initSeq = new ArrayList();

	
	// inherits from MarkovGenerator
	OrderMGenerator() {
		super();
		orderM = 2;
		uniqueAlphabetSequences = new ArrayList<ArrayList<T>>();	
	}
	
	OrderMGenerator(int order) {
		super();
		orderM = order;
		uniqueAlphabetSequences = new ArrayList<ArrayList<T>>();	
	}
	
	// returns the an ArrayList of row i from the uniqueAlphabetSequences
	public ArrayList<T> getUniqueAlphaSeq(int i) {
		return uniqueAlphabetSequences.get(i);
	}
	
	// returns the size of unique alphabet sequence
	public int getUniqueAlphaSeqSize() {
		return uniqueAlphabetSequences.size();
	}
	
	public ArrayList getInitSeq(int i) {
		if (i == 1) {
			T initToken = (T) generate();
			initSeq.add(initToken);
		} else {
			for (int j = 0; j < i; j++) {
				T initToken = initTokenGen.generate();
				initSeq.add(initToken);
			}
		}
		
		// System.out.println("get Init seq returns: " + initSeq);
		return initSeq;
	}
		
	void train(ArrayList<T> inputTokens) {
		for (int i = orderM - 1; i < inputTokens.size() - 1; i++) {
			int fromIndex = i - (orderM - 1);
			int toIndex = fromIndex + orderM;
			curSequence = new ArrayList<T>(inputTokens.subList(fromIndex, toIndex));	// create the current sequence (eg. curSequence) of size orderM from the input
			int rowIndex = uniqueAlphabetSequences.indexOf(curSequence);	// find curSequence in uniqueAlphabetSequences			
			if (rowIndex == -1) {
				rowIndex = uniqueAlphabetSequences.size();	// set rowIndex to the size of uniqueAlphabetSequences
				uniqueAlphabetSequences.add(curSequence);	// add the curSequence to uniqueAlphabetSequences
        		ArrayList<Integer> newRow = new ArrayList<Integer>();	// add a new row to the transition table the size of the alphabet
        		transitionTable.add(newRow);	// add to your transition table (the array of arrays) (expanding vertically)	
        		for (int j = 0; j < transitionTable.size(); j++) {	// for each row
                	ArrayList row = transitionTable.get(j);
                	while (row.size() < alphabet.size()) {	// expand transitionTable horizontally
    	        		row.add(0);	// for each array (row) in the transition table add 0 (expand horizontally)
               		}
    	        }
			}

			// Find the current next token (tokenIndex)
			int tokenIndex = alphabet.indexOf(inputTokens.get(i + 1));	// tokenIndex = the next index of the token in the alphabet (i+1)
        	if (tokenIndex == -1) {	// if tokenIndex is not found in the alphabet
        		tokenIndex = alphabet.size();	// tokenIndex = size of the alphabet 
        		alphabet.add(inputTokens.get(i + 1));	// add the token to the alphabet
            	for (int j = 0; j < transitionTable.size(); j++) {
                	ArrayList row = transitionTable.get(j);
                	while (row.size() < alphabet.size()) {	// expand transitionTable horizontally
    	        		row.add(0); // for each array (row) in the transition table add 0 (expand horizontally)
               		}
    	        }
            	alphabet_counts.add(0);
        	}
			
			// 	update the counts – since we started after the beginning, rowIndex will not be -1
        	ArrayList row = transitionTable.get(rowIndex);	// get the row using rowIndex
        	for (int k = 0; k < row.size(); k++) {
        		if (k == tokenIndex) {  // use the tokenIndex to index the correct column (value of the row you accessed)
    				row.set(k, (int) row.get(k) + 1); // add 1 to that value
    				int tempCount = alphabet_counts.get(k) + 1; // update alphabet counts
					alphabet_counts.set(k, tempCount);
    			}
    		}		
		}
		
		initTokenGen.train(inputTokens);;
	}
	
	T generate(ArrayList initSeq) { // WORK IN PROGRESS
		int curSeqIndex  = uniqueAlphabetSequences.indexOf(initSeq);	// find the index of initSeq in uniqueAlphabetSequence 
		// T tokenRow = null;

		// System.out.println("unique alphabet sequences are: " + uniqueAlphabetSequences);
		// System.out.println("current sequence index is: " + curSeqIndex);

		if(curSeqIndex == -1) {	// initSeq is not found 
			newToken = initTokenGen.generate();	// generate from a trained markov chain 1
			// System.out.println("new token is: " + newToken);
		} else {
			// System.out.println("token row is: " + tokenRow);
			//System.out.println("ttrow is: " + getProbabilities(curSeqIndex));	// 1. find the row in the transition table using curSeqIndex  
			if (getRowTotal(curSeqIndex) == 0) { // if there is 0% chance use random probability from original probability generator
				newToken = generate(getProbabilities());
			} else {
				newToken = generate(getProbabilities(curSeqIndex)); // else use the probability distribution from the transition table
			}
			// 2. generate from that row using your Probability Generator 
			// note: remember to handle 0% probability across all tokens
		}
		
		// System.out.println("new Token is: " + newToken);

		return newToken;
	}

	ArrayList<T> generate(int length, int order, ArrayList<T> initSeq) {
		// System.out.println("init sequence is: " + initSeq);

		T newToken = null;
		ArrayList<T> outputMelody = new ArrayList<T>();	// create an ArrayList of T - outputMelody
		
		for (int i = 0; i < length; i++) {
			newToken = generate(initSeq);	// 1.	call your single generate using your initSeq

			initSeq.remove(0);	// 2.	remove the first token you added from your initSeq
			initSeq.add(newToken);	// 3.	add the generated token to your initSeq
			// System.out.println("init sequence is: " + initSeq);
			outputMelody.add(newToken);	// 4.	add the generated token to outputMelody
			// initSeq.remove(initSeq.size() - 1);	// 5.	remove the first token off the top of the initSeq
		}
		
		return outputMelody;
	}
	//question: what is the behavior of the initSeq in this algorithm?
	//answer: its first-in, first-out (FIFO) – so it is like a queue
	//note: another route is to just use sublists from your outputMelody as your initSeqs 
	
	// this calls the above with a random initToken using the probability generator from Project 1
}
