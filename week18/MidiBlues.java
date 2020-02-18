import java.util.*;
import javax.sound.midi.*;

public class MidiBlues
{
  public static void main(String[] args)
    throws InvalidMidiDataException,MidiUnavailableException,InterruptedException
  { 
    TrackBuilder trkb = new TrackBuilder(60);

    trkb.addBassline(90);
    trkb.addNote(60,120,"long");
    trkb.addNote(61,120,"long");
    trkb.addNote(62,120,"long");
    trkb.addNote(63,120,"long");
    trkb.addRandomPhrases();
    	
    trkb.play();

  }
}
