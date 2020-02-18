import javax.sound.midi.*;
import java.util.*;

public class TrackBuilder
{
  private int bpm;
  private int beatLength;
  long theTimeMelody;
  long theTimeBass;
  private Track trkBass;
  private Track trkMelody;
  private Sequence seq;
  private Sequencer sequencer;
  ArrayList<ArrayList<Note>> Cphrases = new ArrayList<>();
  ArrayList<ArrayList<Note>> Fphrases = new ArrayList<>();
  ArrayList<ArrayList<Note>> Gphrases = new ArrayList<>();
  
  /******************************************************************/
  public TrackBuilder(int bpmP)
    throws InvalidMidiDataException,MidiUnavailableException
  {
    bpm = bpmP;
    beatLength = 2;
    theTimeMelody = 0;
    theTimeBass = 0;
    seq = new Sequence(Sequence.PPQ,4);
    trkBass = seq.createTrack();
    trkMelody = seq.createTrack();
    this.initialisePhrases();
  }

  /******************************************************************/
  public void addBassline(int midiVelocity)
    throws InvalidMidiDataException,MidiUnavailableException
  {
    int twelveBarBluesBassline[] = {36,39,40,43,36,39,40,43,
				    36,39,40,43,36,39,40,43,
				    41,44,45,48,41,44,45,48,
				    36,39,40,43,36,39,40,43,
				    43,46,47,50,43,46,47,50,
				    36,39,40,43,36,39,40,43};
    for (int pitch : twelveBarBluesBassline)
      {
	ShortMessage onMsg = new ShortMessage();
	onMsg.setMessage(ShortMessage.NOTE_ON, 0, pitch, midiVelocity);
	ShortMessage offMsg = new ShortMessage();
	offMsg.setMessage(ShortMessage.NOTE_OFF, 0, pitch, midiVelocity);
	trkBass.add(new MidiEvent(onMsg,theTimeBass));
	trkBass.add(new MidiEvent(offMsg,theTimeBass+beatLength));
	theTimeBass += beatLength;
      }
  }
  
  /******************************************************************/

    public void addNote(int midiPitch, int midiVelocity, String size)
      throws InvalidMidiDataException,MidiUnavailableException
  {
    ShortMessage onMsg = new ShortMessage();
    onMsg.setMessage(ShortMessage.NOTE_ON, 0, midiPitch, midiVelocity);
    ShortMessage offMsg = new ShortMessage();
    offMsg.setMessage(ShortMessage.NOTE_OFF, 0, midiPitch, midiVelocity);
    if (size.equals("long"))
      {
	trkMelody.add(new MidiEvent(onMsg,theTimeMelody));
	trkMelody.add(new MidiEvent(offMsg,theTimeMelody+beatLength));
	theTimeMelody += beatLength;
      }
    else if (size.equals("short"))
      {
        trkMelody.add(new MidiEvent(onMsg,theTimeMelody/2));
        trkMelody.add(new MidiEvent(offMsg,(theTimeMelody/2)+beatLength));
        theTimeMelody += beatLength;
         //// ADD THREE LINES HERE WHICH ADD A SHORT NOTE (HALF THE
	 //// LENGTH OF THE LONG ONE)
      }
    else
      {
	System.err.println("wrong note length");
	System.exit(1);
      }
  }


  /******************************************************************/

  public void addNote(Note nn)
      throws InvalidMidiDataException,MidiUnavailableException
  {
    this.addNote(nn.pitch, nn.velocity, nn.length);
  }

  
  /******************************************************************/

  public void addRandomPhrases()
    throws InvalidMidiDataException,MidiUnavailableException
  {
    for (int i=0;i<4;i++)
      {
	ArrayList<Note> phrase =
	  Cphrases.get(new Random().nextInt(Cphrases.size()));
	for (Note n: phrase) { this.addNote(n); }
      }
    for (int i=0;i<2;i++)
      {
	ArrayList<Note> phrase =
	  Fphrases.get(new Random().nextInt(Fphrases.size()));
	for (Note n: phrase) { this.addNote(n); }
      }
    for (int i=0;i<2;i++)
      {
	ArrayList<Note> phrase =
	  Cphrases.get(new Random().nextInt(Cphrases.size()));
	for (Note n: phrase) { this.addNote(n); }
      }
    for (int i=0;i<2;i++)
      {
	ArrayList<Note> phrase =
	  Gphrases.get(new Random().nextInt(Gphrases.size()));
	for (Note n: phrase) { this.addNote(n); }
      }
    for (int i=0;i<2;i++)
      {
	ArrayList<Note> phrase =
	  Cphrases.get(new Random().nextInt(Cphrases.size()));
	for (Note n: phrase) { this.addNote(n); }
      }
  }


  /******************************************************************/
  public void play()
    throws InvalidMidiDataException,MidiUnavailableException,InterruptedException
  {
    sequencer = MidiSystem.getSequencer();
    sequencer.setTempoInBPM​(bpm);
    sequencer.open();
    sequencer.setSequence(seq);
   
    sequencer.start();
    Thread.sleep(sequencer.getMicrosecondLength());
    System.out.println(sequencer.getMicrosecondLength()*100);
    sequencer.stop();
    //// ADD A LINE HERE TO PAUSE THE PROGRAM WHILST IT PLAYS THE TRACK
    //// ADD A LINE HERE TO STOP THE SEQUENCER
  }

  /******************************************************************/
  private void initialisePhrases()
  {
    Cphrases = new ArrayList<>();
    Fphrases = new ArrayList<>();
    Gphrases = new ArrayList<>();

    ArrayList<Note> cc0 = new ArrayList<>();
    cc0.add(new Note(0,0,"short"));
    cc0.add(new Note(60,120,"short"));
    cc0.add(new Note(60,120,"long"));
    cc0.add(new Note(0,0,"short"));
    cc0.add(new Note(67,120,"short"));
    cc0.add(new Note(60,120,"long"));
    Cphrases.add(cc0);
    ArrayList<Note> cc1 = new ArrayList<>();
    cc1.add(new Note(0,0,"short"));
    cc1.add(new Note(60,120,"short"));
    cc1.add(new Note(64,120,"short"));
    cc1.add(new Note(67,120,"long"));
    cc1.add(new Note(64,120,"short"));
    cc1.add(new Note(60,120,"long"));
    Cphrases.add(cc1);

    ArrayList<Note> ff0 = new ArrayList<>();
    ff0.add(new Note(0,0,"short"));
    ff0.add(new Note(72,120,"short"));
    ff0.add(new Note(71,120,"short"));
    ff0.add(new Note(70,120,"short"));
    ff0.add(new Note(0,0,"short"));
    ff0.add(new Note(64,120,"long"));
    ff0.add(new Note(65,0,"short"));
    ff0.add(new Note(67,0,"short"));
    Fphrases.add(ff0);
    
    ArrayList<Note> ff1 = new ArrayList<>();
    ff1.add(new Note(65,120,"short"));
    ff1.add(new Note(66,120,"short"));
    ff1.add(new Note(67,120,"short"));
    ff1.add(new Note(68,120,"short"));
    ff1.add(new Note(69,120,"short"));
    ff1.add(new Note(70,120,"short"));
    ff1.add(new Note(71,120,"short"));
    ff1.add(new Note(0,0,"short"));
    Fphrases.add(ff1);
    
    ArrayList<Note> gg0 = new ArrayList<>();
    gg0.add(new Note(0,0,"short"));
    gg0.add(new Note(74,120,"short"));
    gg0.add(new Note(76,120,"short"));
    gg0.add(new Note(0,0,"short"));
    gg0.add(new Note(78,120,"short"));
    gg0.add(new Note(77,120,"short"));
    gg0.add(new Note(76,0,"short"));
    gg0.add(new Note(75,0,"short"));
    Gphrases.add(gg0);

    ArrayList<Note> gg1 = new ArrayList<>();
    gg1.add(new Note(84,85,"short"));
    gg1.add(new Note(83,90,"short"));
    gg1.add(new Note(82,95,"short"));
    gg1.add(new Note(81,100,"short"));
    gg1.add(new Note(80,105,"short"));
    gg1.add(new Note(79,110,"short"));
    gg1.add(new Note(78,115,"short"));
    gg1.add(new Note(79,120,"short"));
    Gphrases.add(gg1);
  }

  /******************************************************************/
  class Note
  {
    public int pitch;
    public int velocity;
    public String length;

    public Note(int pitchP, int velocityP, String lengthP)
    {
      pitch = pitchP; velocity = velocityP; length = lengthP;
    }
  }
}

  
