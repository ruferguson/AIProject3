/* Ru Ferguson
 * 5 October 2020
 * 
 * This class is used for the unit test methods to consolidate code more nicely. */

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;

import processing.core.PApplet;

public class UnitTests extends PApplet {
	
	MelodyPlayer player; //play a midi sequence
	MidiFileToNotes midiNotes; // read a midi file
	
	ProbabilityGenerator<Integer> pitchGen, initPitchGen;
	ProbabilityGenerator<Double> rhythmGen, initRhythmGen;
	MarkovGenerator<Integer> markovPitchGen;
	MarkovGenerator<Double> markovRhythmGen;
	OrderMGenerator<Integer> orderMPitchGen;
	OrderMGenerator<Double> orderMRhythmGen;
	
	
	UnitTests() {
		String filePath = getPath("mid/MaryHadALittleLamb.mid");
		midiNotes = new MidiFileToNotes(filePath);
		midiNotes.setWhichLine(0);
		pitchGen = new ProbabilityGenerator<Integer>();
		rhythmGen = new ProbabilityGenerator<Double>();
		markovPitchGen = new MarkovGenerator<Integer>();
		markovRhythmGen = new MarkovGenerator<Double>();
		initPitchGen = new ProbabilityGenerator<Integer>();
		initRhythmGen = new ProbabilityGenerator<Double>();
		orderMPitchGen = new OrderMGenerator<Integer>();
		orderMRhythmGen = new OrderMGenerator<Double>();
	}
	
	void P1UnitTest1() {	// Project 1: Unit Test 1
		trainP1();

		System.out.println("Pitches:\n\n-----Probability Distribution-----\n");
		for (int i = 0; i < pitchGen.getAlphabetSize(); i++) {
			System.out.println("Token: " + pitchGen.getToken(i) + " | Probability: " +
			pitchGen.getProbability(i));
		}
		System.out.println("\n------------\n\nRhythms:\n\n-----Probability Distribution-----\n");
		for (int i = 0; i < rhythmGen.getAlphabetSize(); i++) {
			System.out.println("Token: " + rhythmGen.getToken(i) + " | Probability: " + 
			rhythmGen.getProbability(i));
		}
		System.out.println("\n------------\n");
	}
	
	void P1UnitTest2() {	// Project 1: Unit Test 2
		trainP1();
		
		System.out.println("20 pitches from one melody generated from Mary Had a Little Lamb:");
		System.out.println(pitchGen.generate(20));
		System.out.println("\n20 rhythms from one melody generated from Mary Had a Little Lamb:");
		System.out.println(rhythmGen.generate(20) + "\n------------\n");
	}
	
	void P1UnitTest3() {	// Project 1: Unit Test 3
		ProbabilityGenerator<Integer> melodyPitchGen = new ProbabilityGenerator<Integer>();
		ProbabilityGenerator<Double> melodyRhythmGen = new ProbabilityGenerator<Double>();
		ProbabilityGenerator<Integer> probDistPitchGen = new ProbabilityGenerator<Integer>();
		ProbabilityGenerator<Double> probDistRhythmGen = new ProbabilityGenerator<Double>();
		
		ArrayList<Integer> newSongPitches = new ArrayList<Integer>();
		ArrayList<Double> newSongRhythms = new ArrayList<Double>();
		
		melodyPitchGen.train(midiNotes.getPitchArray());
		melodyRhythmGen.train(midiNotes.getRhythmArray());

		for (int i = 0; i < 9999; i++) {
			newSongPitches = melodyPitchGen.generate(20);
			newSongRhythms = melodyRhythmGen.generate(20);	
			probDistPitchGen.train(newSongPitches);
			probDistRhythmGen.train(newSongRhythms);
		}
		
		System.out.println("Probability of Generated Pitches after 10,000 iterations of 20 note melodies:\n\n-----Probability Distribution-----\n");
		for (int i = 0; i < probDistPitchGen.getAlphabetSize(); i++) {
			System.out.println("Token: " + probDistPitchGen.getToken(i) + " | Probability: " + probDistPitchGen.getProbability(i));
		}
		System.out.println("\n------------\n\nProbability of Generated Rhythms after 10,000 iterations of 20 note melodies:\n\n-----Probability Distribution-----\n");
		for (int i = 0; i < probDistRhythmGen.getAlphabetSize(); i++) {
			System.out.println("Token: " + probDistRhythmGen.getToken(i) + " | Probability: " + probDistRhythmGen.getProbability(i));
		}
		System.out.println("\n------------\n");
	}
	
	void P2UnitTest1() {	// Project 2: Unit Test 1
		trainP2();

		System.out.println("\nPitches:\n\n-----Transition Table-----\n\n   " + markovPitchGen.getAlphabet());
		for (int i = 0; i < markovPitchGen.getAlphabetSize(); i++) {
	        System.out.println(markovPitchGen.getToken(i) + " " + markovPitchGen.getProbabilities(i));
		}
		System.out.println("\n------------\n\nRhythms:\n\n-----Transition Table-----\n\n    " + markovRhythmGen.getAlphabet());
		for (int i = 0; i < markovRhythmGen.getAlphabetSize(); i++) {
	        System.out.println(markovRhythmGen.getToken(i) + " " + markovRhythmGen.getProbabilities(i));
		}
		System.out.println("\n------------\n");		
	}
	
	void P2UnitTest2() {	// Project 2: Unit Test 2
		trainP2();
		
		System.out.println("20 pitches from one melody generated using a Markov Chain from Mary Had a Little Lamb:");
		System.out.println(markovPitchGen.generate(20, initPitchGen.generate(initPitchGen.getProbabilities())));
		System.out.println("\n20 rhythms from one melody generated using a Markov Chain from Mary Had a Little Lamb:");
		System.out.println(markovRhythmGen.generate(20, initRhythmGen.generate(initRhythmGen.getProbabilities())) + "\n------------\n");
	}
	
	void P2UnitTest3() {	// Project 2: Unit Test 3
		// UNIT TEST 3
		trainP2();

		MarkovGenerator<Integer> ttDistPitchGen = new MarkovGenerator<Integer>();
		MarkovGenerator<Double> ttDistRhythmGen = new MarkovGenerator<Double>();
		
		ArrayList<Integer> newSongPitches = new ArrayList<Integer>();
		ArrayList<Double> newSongRhythms = new ArrayList<Double>();	
		
		for (int i = 0; i < 9999; i++) {
			newSongPitches = markovPitchGen.generate(20, initPitchGen.generate(initPitchGen.getProbabilities()));
			newSongRhythms = markovRhythmGen.generate(20, initRhythmGen.generate(initRhythmGen.getProbabilities()));
			ttDistPitchGen.train(newSongPitches);
			ttDistRhythmGen.train(newSongRhythms);
		}
		
		System.out.println("\nProbability of Generated Pitches after 10,000 iterations of 20 note melodies:\n\n-----Transition Table-----\n\n   " + ttDistPitchGen.getAlphabet());
		for (int i = 0; i < ttDistPitchGen.getAlphabetSize(); i++) {
	        System.out.println(ttDistPitchGen.getToken(i) + " " + ttDistPitchGen.getProbabilities(i));
		}
		System.out.println("\n------------\n\nProbability of Generated Rhythms after 10,000 iterations of 20 note melodies:\n\n-----Transition Table-----\n\n    " + ttDistRhythmGen.getAlphabet());
		for (int i = 0; i < ttDistRhythmGen.getAlphabetSize(); i++) {
	        System.out.println(ttDistRhythmGen.getToken(i) + " " + ttDistRhythmGen.getProbabilities(i));
		}
		System.out.println("\n------------\n");	
}
	
	void P3UnitTest1() {	// Project 3: Unit Test 1		
		for (int i = 1; i < 11; i++) {
			orderMPitchGen = new OrderMGenerator<Integer>(i);
			orderMPitchGen.train(midiNotes.getPitchArray());
			System.out.println("Pitches for order " + i + ":\n----Transition Table----");
			for (int k = 0; k < i; k++) {
				System.out.print("    ");
			}
			System.out.println(orderMPitchGen.getAlphabet());
			for (int j = 0; j < orderMPitchGen.getUniqueAlphaSeqSize(); j++) {
				System.out.println(orderMPitchGen.getUniqueAlphaSeq(j) + " " + orderMPitchGen.getProbabilities(j));
			}
			System.out.println("\n------------\n");
		}
		System.out.println("------------------------------------------------------------\n");
		for (int i = 1; i < 11; i++) {
			orderMRhythmGen = new OrderMGenerator<Double>(i);
			orderMRhythmGen.train(midiNotes.getRhythmArray());
			System.out.println("Rhythms for order " + i + ":\n----Transition Table----");
			for (int k = 0; k < i; k++) {
				System.out.print("     ");
			}
			System.out.println(orderMRhythmGen.getAlphabet());
			for (int j = 0; j < orderMRhythmGen.getUniqueAlphaSeqSize(); j++) {
				System.out.println(orderMRhythmGen.getUniqueAlphaSeq(j) + " " + orderMRhythmGen.getProbabilities(j));
			}
			System.out.println("\n------------\n");
		}
	}
	
	void P3UnitTest2() {	// Project 3: Unit Test 2	
		System.out.println("\n------------------------------------------------------------\n");
		for (int i = 1; i < 11; i++) {
			orderMPitchGen = new OrderMGenerator<Integer>(i);
			orderMRhythmGen = new OrderMGenerator<Double>(i);
			orderMPitchGen.train(midiNotes.getPitchArray());
			orderMRhythmGen.train(midiNotes.getRhythmArray());
			System.out.println("Melody from Markov Chain of Order " + i);
			System.out.println("Pitches: " + orderMPitchGen.generate(20, i, orderMPitchGen.getInitSeq(i)));
			System.out.println("Rhythms: " + orderMRhythmGen.generate(20, i, orderMRhythmGen.getInitSeq(i)));
			System.out.println("\n------------------------------------------------------------\n");
		}
	}
	
	void P3UnitTest3() {	// Project 3: Unit Test 3
		System.out.println("\n----WORK IN PROGRESS----\n");
		/* Have a function which generates 10,000 melodies of length 20, then prints out to the console the conditional
		 * probabilities of each pitch and rhythm from that dataset (ie. it should basically print out a transition table
		 * from the data of that 10,000 melodies)  for EACH order 1..10. Provide UI for me to run this test from your
		 * application. Note that the 2 tables (of this unit test and the first test) should look v. similar -- at least
		 * for the sequences that appear in Unit Test 1. This is a test of whether your markov generate() function works.
		 * If the tables are significantly different then the generate() does not work. The one exception of this will be
		 * any sequence that includes the rhythm 4.0. Think about why that would be so (answer is in the lecture sheet).
		 */
		OrderMGenerator<Integer> pitchCondProb = new OrderMGenerator<Integer>();
		OrderMGenerator<Double> rhythmCondProb = new OrderMGenerator<Double>();

		ArrayList<Integer> newSongPitches = new ArrayList<Integer>();
		ArrayList<Double> newSongRhythms = new ArrayList<Double>();	
		
		for (int i = 1; i < 11; i++) {
			orderMPitchGen = new OrderMGenerator<Integer>(i);
			orderMRhythmGen = new OrderMGenerator<Double>(i);
			orderMPitchGen.train(midiNotes.getPitchArray());
			orderMRhythmGen.train(midiNotes.getRhythmArray());
			for (int j = 0; j < 9999; j++) {
				newSongPitches = orderMPitchGen.generate(20, i, orderMPitchGen.getInitSeq(i));
				newSongRhythms = orderMRhythmGen.generate(20, i, orderMRhythmGen.getInitSeq(i));
				pitchCondProb.train(newSongPitches);
				rhythmCondProb.train(newSongRhythms);
			}

			System.out.println("Probability of Generated Pitches after 10,000 iterations of 20 note melodies for order " + i + ":\n----Transition Table----");
			for (int k = 0; k < i; k++) {
				System.out.print("    ");
			}
			System.out.println(pitchCondProb.getAlphabet());
			for (int j = 0; j < pitchCondProb.getUniqueAlphaSeqSize(); j++) {
				System.out.println(pitchCondProb.getUniqueAlphaSeq(j) + " " + pitchCondProb.getProbabilities(j));
			}
			System.out.println("\n------------\n");
			System.out.println("Probability of Generated Rhythms after 10,000 iterations of 20 note melodies for order " + i + ":\n----Transition Table----");
			for (int k = 0; k < i; k++) {
				System.out.print("     ");
			}
			System.out.println(rhythmCondProb.getAlphabet());
			for (int j = 0; j < rhythmCondProb.getUniqueAlphaSeqSize(); j++) {
				System.out.println(rhythmCondProb.getUniqueAlphaSeq(j) + " " + rhythmCondProb.getProbabilities(j));
			}
			System.out.println("\n------------\n");
		}		
	}
	
	void trainP1() {
		pitchGen.train(midiNotes.getPitchArray());
		rhythmGen.train(midiNotes.getRhythmArray());
	}
	
	void trainP2() {
		markovPitchGen.train(midiNotes.getPitchArray());
		markovRhythmGen.train(midiNotes.getRhythmArray());
		
		initPitchGen.train(midiNotes.getPitchArray()); // must train to get initial pitch
		initRhythmGen.train(midiNotes.getRhythmArray()); // must train to get initial rhythm
	}
	
	String getPath(String path) {
		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

}

