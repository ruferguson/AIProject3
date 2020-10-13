/* Ru Ferguson
 * 5 October 2020
 * 
 * This program will play the Super Mario Bros Theme using the Kontakt MIDI Player just as it did in Project 1
 * and will store sequences of order M, the notes that follow each unique sequence, the occurrences of each note,
 * and the probabilities of each note occurring in an ArrayList of ArrayLists using the OrderMGenerator class.
 * Generate function is a work in progress.
 * Project 1 and 2 methods and unit tests are still available in this patch. */

import processing.core.*;

import java.util.*; 

//importing the JMusic stuff
import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.midi.*;

import java.io.UnsupportedEncodingException;
import java.net.*;

//import javax.sound.midi.*;

//make sure this class name matches your file name, if not fix.
public class AIProject3Main extends PApplet {

	MelodyPlayer player; //play a midi sequence
	MidiFileToNotes midiNotes; //read a midi file
	UnitTests unitTest = new UnitTests(); // create unit tests
	
	ProbabilityGenerator<Integer> pitchGenerator;
	ProbabilityGenerator<Double> rhythmGenerator;
	MarkovGenerator<Integer> markovPitchGenerator;
	MarkovGenerator<Double> markovRhythmGenerator;
	OrderMGenerator<Integer> orderMPitchGenerator;
	OrderMGenerator<Double> orderMRhythmGenerator;
	
	String filePath;

	public static void main(String[] args) {
		PApplet.main("AIProject3Main"); 
	}

	//setting the window size to 300x300
	public void settings() {
		size(375, 500);
	}

	//doing all the setup stuff
	public void setup() {		
		// create my generators for pitch and rhythm
		pitchGenerator = new ProbabilityGenerator<Integer>();
		rhythmGenerator = new ProbabilityGenerator<Double>();
				
		// returns a url
		filePath = getPath("mid/Super_Mario_Bros_Theme.mid"); // use this for probabilistic generation

		midiNotes = new MidiFileToNotes(filePath); //creates a new MidiFileToNotes -- reminder -- ALL objects in Java must 
												   //be created with "new". Note how every object is a pointer or reference. Every. single. one.

	    // which line to read in --> this object only reads one line (or ie, voice or ie, one instrument)'s worth of data from the file
		midiNotes.setWhichLine(0);

		player = new MelodyPlayer(this, 100.0f);
		
		player.setup();

		// play the midi notes as they are in the file
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
	}

	public void draw() {
	    player.play();		//play each note in the sequence -- the player will determine whether is time for a note onset
	    background(250);
	    showInstructions();
	}

	//this finds the absolute path of a file
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

	// this function is not currently called. you may call this from setup() if you want to test
	// this just plays the midi file -- all of it via your software synth. You will not use this
	// function in upcoming projects but it could be a good debug tool.
	void playMidiFile(String filename) {
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}

	
	// this starts & restarts the melody and runs unit tests
	public void keyPressed() {
		if (key == ' ') {
			player.reset();
			player.setMelody(midiNotes.getPitchArray());
			player.setRhythm(midiNotes.getRhythmArray());
			player.hasMelody = true; // starts the player
			println("Melody started!");
		} else if (key == 'p') {
			P3GenerateNotes(); 
			System.out.println("Generating notes . . . enjoy!");
		} else if (key == '1') {
			unitTest.P1UnitTest1();
		} else if (key == '2') {
			unitTest.P1UnitTest2();
		} else if (key == '3') {
			unitTest.P1UnitTest3();
		} else if (key == 'q') {
			unitTest.P2UnitTest1();
		} else if (key == 'w') {
			unitTest.P2UnitTest2();
		} else if (key == 'e') {
			unitTest.P2UnitTest3();	
		} else if (key == 'a') {
			unitTest.P3UnitTest1();
		} else if (key == 's') {
			unitTest.P3UnitTest2();
		} else if (key == 'd') {
			unitTest.P3UnitTest3();	
		} else if (key == 'o') {		
			player.hasMelody = false; // stops the player
		} 
	}
	
	
	// display instructions to the user
	public void showInstructions() {
		textAlign(CENTER);
		textSize(25);
		fill(255, 75, 75);
		text("Welcome to the", width/2, height*2/28);
		text("Markov Melody Generator", width/2, height*4/28);
		textSize(16);
		fill(240, 75, 90);
		text("Press p to play 35 generated notes", width/2, height*6/28);
		text("from Super_Mario_Bros_Theme.mid", width/2, height*7/28);
		fill(225, 75, 105);
		text("Press the spacebar to restart original melody", width/2, height*11/28);
		text("Press o to stop playing", width/2, height*12/28);
		fill(210, 75, 120);
		text("Press 1 for Project 1: Unit Test 1", width/2, height*14/28);
		text("Press 2 for Project 1: Unit Test 2", width/2, height*15/28); 
		text("Press 3 for Project 1: Unit Test 3", width/2, height*16/28);
		fill(195, 75, 135);
		text("Press q for Project 2: Unit Test 1", width/2, height*18/28);
		text("Press w for Project 2: Unit Test 2", width/2, height*19/28);
		text("Press e for Project 2: Unit Test 3", width/2, height*20/28);
		fill(180, 75, 150);
		text("Press a for Project 3: Unit Test 1", width/2, height*22/28);
		text("Press s for Project 3: Unit Test 2", width/2, height*23/28);
		text("Press d for Project 3: Unit Test 3", width/2, height*24/28);
		fill(240, 75, 90);
		textSize(10);
		text("(now generated by the Order M Generator)", width/2, height*8/28);
	}
	
	
	// generate a melody using the Probability Generator from Project 1
	public void P1GenerateNotes() {
		filePath = getPath("mid/Super_Mario_Bros_Theme.mid");
		midiNotes = new MidiFileToNotes(filePath);
		midiNotes.setWhichLine(0);
		
		pitchGenerator = new ProbabilityGenerator<Integer>();
		rhythmGenerator = new ProbabilityGenerator<Double>();
		
		// call the train function for both pitches and rhythms
		pitchGenerator.train(midiNotes.getPitchArray());
		rhythmGenerator.train(midiNotes.getRhythmArray());
		
		// generate 20 new notes using the probabalistic generator
		player.setMelody(pitchGenerator.generate(35)); 
		player.setRhythm(rhythmGenerator.generate(35));
	}
	
	// generate a melody using the Markov Generator from Project 2
	public void P2GenerateNotes() {
		filePath = getPath("mid/Super_Mario_Bros_Theme.mid");
		midiNotes = new MidiFileToNotes(filePath);
		midiNotes.setWhichLine(0);
		
		markovPitchGenerator = new MarkovGenerator<Integer>();
		markovRhythmGenerator = new MarkovGenerator<Double>();
		pitchGenerator = new ProbabilityGenerator<Integer>();
		rhythmGenerator = new ProbabilityGenerator<Double>();
		
		// call the train function for both pitches and rhythms and to generate an initial token
		markovPitchGenerator.train(midiNotes.getPitchArray());
		markovRhythmGenerator.train(midiNotes.getRhythmArray());
		pitchGenerator.train(midiNotes.getPitchArray()); 
		rhythmGenerator.train(midiNotes.getRhythmArray()); 
				
		// generate 35 new notes using the probabalistic generator
		player.setMelody(markovPitchGenerator.generate(35, pitchGenerator.generate(pitchGenerator.getProbabilities()))); 
		player.setRhythm(markovRhythmGenerator.generate(35, rhythmGenerator.generate(rhythmGenerator.getProbabilities())));	
	}
		
	// generate a melody using the Order M Generator from Project 3
	public void P3GenerateNotes() {
		filePath = getPath("mid/Super_Mario_Bros_Theme.mid");
		midiNotes = new MidiFileToNotes(filePath);
		midiNotes.setWhichLine(0);
		
		orderMPitchGenerator = new OrderMGenerator<Integer>(1);
		orderMRhythmGenerator = new OrderMGenerator<Double>(1);
		orderMPitchGenerator.train(midiNotes.getPitchArray());
		orderMRhythmGenerator.train(midiNotes.getRhythmArray());
				
		player.setMelody(orderMPitchGenerator.generate(35, orderMPitchGenerator.getInitSeq())); 
		player.setRhythm(orderMRhythmGenerator.generate(35, orderMRhythmGenerator.getInitSeq()));	
	}
}
