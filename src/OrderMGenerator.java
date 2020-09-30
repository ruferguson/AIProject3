/* Ru Ferguson
 * 28 September 2020
 * 
 * This class inherits from the superclass, MarkovGenerator from Project 2 */

import java.util.ArrayList;
import java.util.Arrays;

public class OrderMGenerator<T> extends MarkovGenerator<T> {
	
	int orderM;
	
	// inherits from MarkovGenerator
	OrderMGenerator() {
		super();
		orderM = 2;
	}
	
	void train() {
		/*
		
		// from old generators
		input – an array (probably ArrayList called newTokens) of your input tokens
		alphabet – array of unique symbols/tokens in the input.
		transitionTable – a 2D array representing your transition tables – so, probably an array of arrays
		
		// data new to this generator:
		uniqueAlphabetSequences – array of the unique sequences of size N found in your input.
		orderM – the order of the Markov Chain – set this in your constructor.
		curSequence – the current sequence – will have to be a container
		
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
		
		it will be the next index, so represented by i+1 instead of i in the example procedure below.

		for i = orderM -1 to (i < size of the input - 1) do
		{
					
			1.	Create the current sequence (eg. curSequence) of size orderM from the input
			Remember to start the index into the input at 0 (with this algorithm) 
				a.	add the previous tokens to a container (eg ArrayList). 
				b.	You may do this in a for-loop or use .subList()
					i.	https://beginnersbook.com/2013/12/how-to-get-sublist-of-an-arraylist-with-example/
						
			2.	Find  curSequence in uniqueAlphabetSequences
			if curSequence is not found
			{
				1. set rowIndex to the size of uniqueAlphabetSequences
						
				2. add the curSequence to uniqueAlphabetSequences

				3. add a new row to the transition table the size of the alphabet
			}

			3.	Find the current next token (tokenIndex)
			{
				tokenIndex = the next index of the token in the alphabet (i+1)
					
				if tokenIndex is not found in the alphabet
				{
					1. tokenIndex = size of the alphabet 
					2. add the token to the alphabet
					3. expand transitionTable horizontally
				}
			}
					
			4.	Update the counts – since we started after the beginning, rowIndex will not be -1
				a.	Get the row using rowIndex
				b.	Get the column using tokenIndex
				c.	Add one to that value retrieved from the transition table
		} */
		
	}
	
	T generate(ArrayList initSeq) {
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
