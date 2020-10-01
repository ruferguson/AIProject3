/* Ru Ferguson
 * 28 September 2020
 * 
 * This class inherits from the superclass, MarkovGenerator from Project 2 */

import java.util.ArrayList;
import java.util.Arrays;


public class OrderMGenerator<T> extends MarkovGenerator<T> {
	
	int orderM;  // the order of the Markov Chain – set this in your constructor.
	int rowIndex;
	ArrayList<ArrayList<T>> uniqueAlphabetSequences;  // array of the unique sequences of size N found in your input.
	ArrayList<T> curSequence; // the current sequence – will have to be a container
	
	// inherits from MarkovGenerator
	OrderMGenerator() {
		super();
		orderM = 2;
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
		
	void train(ArrayList<T> newTokens) {
		//	input – an array (probably ArrayList called newTokens) of your input tokens	
		// Rows are uniqueAlphabetSequences
		// Columns are alphabet 
		/*
		// from old generators
		alphabet – array of unique symbols/tokens in the input.
		transitionTable – a 2D array representing your transition tables – so, probably an array of arrays
		
		Algorithm Overview – Big Picture
		
		Find and put the unique symbols into a container (eg. ArrayList alphabet).
		Find and put the unique symbol sequences (ArrayLists) into a container (eg. ArrayList uniqueAlphabetSequences)
		Expand the transition table vertically (add a row, eg. add an ArrayList) for every sequence we find.
		Expand the transition table horizontally (add a column, eg. add an element to an ArrayList) for every new token we find.
		Update the counts in the table.
		rowIndex will be referencing the sequences, thus the row or ArrayList in the transition table.
		rowIndex then, will be an index that comes from the uniqueAlphabetSequences.
		In the Markov chain of order 1, we called our rowIndex lastIndex. This serves the same purpose, but the re-naming helps to clarify exactly what it is referencing in this context.
		
		tokenIndex will be referencing the unique tokens (or alphabet) or elements in each ArrayList of the transition table.
		tokenIndex will be an index coming from the alphabet
		
		it will be the next index, so represented by i+1 instead of i in the example procedure below. */		
		for (int i = orderM - 1; i < newTokens.size() - 1; i++) {
			int fromIndex = i - (orderM - 1);
			int toIndex = fromIndex + orderM;

			curSequence = new ArrayList<T>(newTokens.subList(fromIndex, toIndex)); // Create the current sequence (eg. curSequence) of size orderM from the input
			System.out.println("curSequence is: " + curSequence);
			
			//	2. Find curSequence in uniqueAlphabetSequences
			int index = uniqueAlphabetSequences.indexOf(curSequence);
			//System.out.println("index is: " + index);
			
			if (index == -1) {
				rowIndex = uniqueAlphabetSequences.size();	// 1. set rowIndex to the size of uniqueAlphabetSequences
				
				uniqueAlphabetSequences.add(curSequence);	// 2. add the curSequence to uniqueAlphabetSequences

        		ArrayList<Integer> newRow = new ArrayList<Integer>(); // 3. add a new row to the transition table the size of the alphabet
        		transitionTable.add(newRow); // Then add to your transition table (the array of arrays) (expanding vertically)	
			}

			// 3.	Find the current next token (tokenIndex)
			int tokenIndex = alphabet.indexOf(newTokens.get(i + 1)); // tokenIndex = the next index of the token in the alphabet (i+1)
			System.out.println("newToken is: " + newTokens.get(i + 1) + " token index is: " + tokenIndex);
			
        	if (tokenIndex == -1) {  // if tokenIndex is not found in the alphabet
        		tokenIndex = alphabet.size();	// 1. tokenIndex = size of the alphabet 
        		alphabet.add(newTokens.get(i + 1));	// 2. add the token to the alphabet
            	for (int j = 0; j < transitionTable.size(); j++) {
                	ArrayList newColumn = transitionTable.get(j);
                	while (newColumn.size() < alphabet.size()) {	// 3. expand transitionTable horizontally
    	        		newColumn.add(0); // for each array (row) in the transition table add 0 (expand horizontally)
               		}
    	        }
				alphabet_counts.add(0);
        	}
    		System.out.println("alphabet: " + alphabet);
			
    		System.out.println("ttsize: " + transitionTable.size());
			// 	4.	Update the counts – since we started after the beginning, rowIndex will not be -1
        	for (int j = 0; j < transitionTable.size(); j++) {
            	ArrayList row = transitionTable.get(rowIndex); // 	a.	Get the row using rowIndex
	        	for (int k = 0; k < row.size(); k++) {
	        		if (k == tokenIndex) {  // Use the tokenIndex to index the correct column (value of the row you accessed)
        				int currentValue = (int) row.get(k);
        				row.set(k, currentValue + 1); // Add 1 to that value.
                		//System.out.println("alphabet counts size: " + alphabet_counts.size());
        				//int tempCount = alphabet_counts.get(k) + 1; // update alphabet counts
    					//alphabet_counts.set(k, tempCount);
        			}
        		}
    		}
		
		}
	}
	
	T generate(ArrayList initSeq) { // WORK IN PROGRESS
		/* {	
			curSeqIndex  = find the index of initSeq in uniqueAlphabetSequence 
			if(initSeq is not found)
			{
				1. generate from a trained markov chain 1
				//note – there are other solutions, you could generate from prob. dist. instead.
				//you could rollback your generation one character & generate again

				//whatever you choose will affect your transition tables in Unit 3 – note that only the sequences 
				// found in training will have the reported probabilities for each row. Eg. you will see "discrepancies"
				//in the rhythms, as the symbol 4.0 comes after things but nothing comes after it.
			}
			else
			{
				1. find the row in the transition table using curSeqIndex  
				2. generate from that row using your Probability Generator 
				//note: remember to handle 0% probability across all tokens
			}

		ArrayList generate(ArrayList initSeq, int numTokensToGen)
		{
			1.	create an ArrayList of T - outputMelody
			2.	for 1 to numTokensToGen do 
			{
				1.	call your single generate using your initSeq
				2.	remove the first token you added from your initSeq
				3.	add the generated token to your initSeq
				4.	add the generated token to outputMelody
				5.	remove the first token off the top of the initSeq
			} */
		return newToken;
	}

}
