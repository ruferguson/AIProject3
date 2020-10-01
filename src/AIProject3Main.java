/* Ru Ferguson
 * 21 September 2020
 * 
 */

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
	ProbabilityGenerator<Integer> pitchGenerator;
	ProbabilityGenerator<Double> rhythmGenerator;
	
	OrderMGenerator<Integer> orderMPitchGenerator;

	public static void main(String[] args) {
		PApplet.main("AIProject3Main"); 
	}

	//setting the window size to 300x300
	public void settings() {
		size(350, 350);
	}

	//doing all the setup stuff
	public void setup() {		
		// create my generators for pitch and rhythm
		pitchGenerator = new ProbabilityGenerator<Integer>();
		rhythmGenerator = new ProbabilityGenerator<Double>();
		
		orderMPitchGenerator = new OrderMGenerator<Integer>();
		
		// returns a url
		//String filePath = getPath("mid/Super_Mario_Bros_Theme.mid"); // use this for probabilistic generation
		// playMidiFile(filePath);
		String filePath = getPath("mid/MaryHadALittleLamb.mid"); // use this for probabilistic generation


		midiNotes = new MidiFileToNotes(filePath); //creates a new MidiFileToNotes -- reminder -- ALL objects in Java must 
												   //be created with "new". Note how every object is a pointer or reference. Every. single. one.

	    // which line to read in --> this object only reads one line (or ie, voice or ie, one instrument)'s worth of data from the file
		midiNotes.setWhichLine(0);

		player = new MelodyPlayer(this, 100.0f);
		
		player.setup();

		// play the midi notes as they are in the file
		player.setMelody(midiNotes.getPitchArray());
		player.setRhythm(midiNotes.getRhythmArray());
		
		System.out.println("pitch array input: " + midiNotes.getPitchArray());
		orderMPitchGenerator.train(midiNotes.getPitchArray());
		
        System.out.println("	 " + orderMPitchGenerator.getAlphabet());
		for (int i = 0; i < orderMPitchGenerator.getUniqueAlphaSeqSize(); i++) {
	        System.out.println(orderMPitchGenerator.getUniqueAlphaSeq(i) + " " + orderMPitchGenerator.getTransTable(i));
		}

		// orderMPitchGenerator.printTransTable();
	}

	public void draw() {
	    player.play(); //play each note in the sequence -- the player will determine whether is time for a note onset

	    background(250);
	    // display instructions to the user
		textAlign(CENTER);
		textSize(20);
		fill(100, 0, 255);
		text("Welcome to the\nProbabalistic Melody Generator", width/2, height*1/10);
		textSize(16);
		fill(115, 0, 255);
		text("Press p to play 35 generated notes\nfrom Super_Mario_Bros_Theme.mid", width/2, height*3/10);
		fill(130, 0, 255);
		text("Press the spacebar to restart the melody", width/2, height*5/10);
		fill(145, 0, 255);
		text("Press s to stop playing", width/2, height*6/10);
		fill(160, 0, 255);
		text("Press 1 for Unit Test 1", width/2, height*7/10);
		fill(175, 0, 255);
		text("Press 2 for Unit Test 2", width/2, height*8/10); 
		fill(190, 0, 255);
		text("Press 3 for Unit Test 3", width/2, height*9/10);
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

	//this starts & restarts the melody.
	public void keyPressed() {
		MidiFileToNotes midiNotesMary; // read a midi file
		// returns a url
		String filePath = getPath("mid/Super_Mario_Bros_Theme.mid");
		// playMidiFile(filePath);

		midiNotesMary = new MidiFileToNotes(filePath);
		//creates a new MidiFileToNotes -- reminder -- ALL objects in Java must 
		//be created with "new". Note how every object is a pointer or reference. Every. single. one.

		// which line to read in --> this object only reads one line (or ie, voice or ie, one instrument)'s worth of data from the file
		midiNotesMary.setWhichLine(0);

		if (key == ' ') {
			player.reset();
			println("Melody started!");
			
		} else if (key == 'p') {
			filePath = getPath("mid/Super_Mario_Bros_Theme.mid");
			midiNotes = new MidiFileToNotes(filePath);
			midiNotes.setWhichLine(0);
			
			pitchGenerator = new ProbabilityGenerator<Integer>();
			rhythmGenerator = new ProbabilityGenerator<Double>();
			
			// call the train function for both pitches and rhythms
			pitchGenerator.train(midiNotes.getPitchArray());
			rhythmGenerator.train(midiNotes.getRhythmArray());
			
			player = new MelodyPlayer(this, 100.0f);
			player.setup();
			
			// generate 20 new notes using the probabalistic generator
			player.setMelody(pitchGenerator.generate(35)); 
			player.setRhythm(rhythmGenerator.generate(35));
			
		} else if (key == '1') {
			// UNIT TEST 1
			filePath = getPath("mid/MaryHadALittleLamb.mid");
			midiNotes = new MidiFileToNotes(filePath);
			midiNotes.setWhichLine(0);

			pitchGenerator = new ProbabilityGenerator<Integer>();
			rhythmGenerator = new ProbabilityGenerator<Double>();
			
			pitchGenerator.train(midiNotes.getPitchArray());
			rhythmGenerator.train(midiNotes.getRhythmArray());
			
			System.out.println("Pitches:\n\n-----Probability Distribution-----\n");
			for (int i = 0; i < pitchGenerator.getAlphabetSize(); i++) {
				System.out.println("Token: " + pitchGenerator.getToken(i) + " | Probability: " +
				pitchGenerator.getProbability(i));
			}
			System.out.println("\n------------\n\nRhythms:\n\n-----Probability Distribution-----\n");
			for (int i = 0; i < rhythmGenerator.getAlphabetSize(); i++) {
				System.out.println("Token: " + rhythmGenerator.getToken(i) + " | Probability: " + 
				rhythmGenerator.getProbability(i));
			}
			System.out.println("\n------------\n");
		} else if (key == '2') {
			// UNIT TEST 2
			filePath = getPath("mid/MaryHadALittleLamb.mid");
			midiNotes = new MidiFileToNotes(filePath);
			midiNotes.setWhichLine(0);

			pitchGenerator = new ProbabilityGenerator<Integer>();
			rhythmGenerator = new ProbabilityGenerator<Double>();
			
			pitchGenerator.train(midiNotes.getPitchArray());
			rhythmGenerator.train(midiNotes.getRhythmArray());
			
			System.out.println("20 pitches from one melody generated from Mary Had a Little Lamb:");
			System.out.println(pitchGenerator.generate(20));
			System.out.println("\n20 rhythms from one melody generated from Mary Had a Little Lamb:");
			System.out.println(rhythmGenerator.generate(20) + "\n------------\n");
			
		} else if (key == '3') {
			// UNIT TEST 3
			filePath = getPath("mid/MaryHadALittleLamb.mid");
			midiNotes = new MidiFileToNotes(filePath);
			midiNotes.setWhichLine(0);
			
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
		} else if (key == 's') {		
			player.hasMelody = false; // stops the player
		}
	}
}
