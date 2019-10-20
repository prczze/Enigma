package enigma;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static enigma.EnigmaException.*;

/**
 * Class that represents a complete enigma machine.
 *
 * @author
 */
public class Machine {

    /**
     * A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     * and 0 <= PAWLS < NUMROTORS pawls. ALLROTORS contains all the
     * available rotors.
     */
    public Machine(Alphabet alpha, int numRotors, int pawls,
                   Rotor[] allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = allRotors;
        _mymachine = new ArrayList<>(numRotors);

        // FIXME - Assign any additional instance variables.
    }

    /**
     * Return the number of rotor slots I have.
     */
    public int numRotors() {

        return _numRotors; // FIXME - How do we access the number of Rotor slots I have?
    }

    /**
     * Return the number pawls (and thus rotating rotors) I have.
     */
    public int numPawls() {

        return _pawls; // FIXME - How do we access the number of pawls I have?
    }

    /**
     * Set my rotor slots to the rotors named ROTORS from my set of
     * available rotors (ROTORS[0] names the reflector).
     * Initially, all rotors are set at their 0 setting.
     */
    public void insertRotors(String[] rotors) {
        for (String rotorName : rotors) {
            for (Rotor rotor : _allRotors) {
                if (rotor.name().toUpperCase().equals(rotorName.toUpperCase())) {
                    _mymachine.add(rotor);
                }
            }

        }

        // FIXME - How do we fill this Machine with Rotors, based on names of available Rotors?
    }

    /**
     * Set my rotors according to SETTING, which must be a string of
     * numRotors()-1 upper-case letters. The first letter refers to the
     * leftmost rotor setting (not counting the reflector).
     */
    public void setRotors(String setting) {
        for (int i = 1; i <= setting.length(); i++) {
            if (i >= _mymachine.size()) {
                break;
            }
            _mymachine.get(i).set(_alphabet.toInt(setting.charAt(i - 1)));
        }
        // FIXME - How do we set the positions of each Rotor in this Machine?
    }

    /**
     * Set the plugboard to PLUGBOARD.
     */
    public void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
        // FIXME - How do we assign our plugboard, based on a given Permutation?
    }

    /**
     * Returns the result of converting the input character C (as an
     * index in the range 0..alphabet size - 1), after first advancing
     * the machine.
     */
    public int convert(int c) {
        // HINT: This one is tough! Consider using a helper method which advances
        //			the appropriate Rotors. Then, send the signal into the
        //			Plugboard, through the Rotors, bouncing off the Reflector,
        //			back through the Rotors, then out of the Plugboard again.
        advance();
        int message = _plugboard.permute(c);
        for (int i = _mymachine.size() - 1; i > 0; i--) {
            message = _mymachine.get(i).convertForward(message);
        }
        message = _mymachine.get(0).convertBackward(message);
        for (int i = 1; i < _mymachine.size(); i++) {
            message = _mymachine.get(i).convertBackward(message);
        }
        message = _plugboard.invert(message);
        return message;

        // FIXME - How do we convert a single character index?
    }

    /**
     * Optional helper method for convert() which rotates the necessary Rotors.
     */
    private void advance() {
        boolean[] willRotate = new boolean[_mymachine.size()];
        for (int i = _mymachine.size() - 1; i >= 1; i--) {
            if (i == _mymachine.size() - 1) {
                willRotate[i] = true;
            }
            if (_mymachine.get(i).rotates() && _mymachine.get(i).atNotch()) {
                willRotate[i] = true;
                willRotate[i - 1] = true;
            }
        }

        for (int i = 0; i < _mymachine.size(); i++) {
            if (willRotate[i]) {
                _mymachine.get(i).advance();
            }
        }

        // FIXME - How do we make sure that only the correct Rotors are advanced?

    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of
     * the rotors accordingly.
     */
    public String convert(String msg) {
        msg = msg.replaceAll("\\s+", "").toUpperCase();
        String current = "";

        for (int i = 0; i < msg.length(); i++) {
            if (_alphabet.contains(msg.toCharArray()[i])) {
                current += _alphabet.toChar(convert(_alphabet.toInt(msg.toCharArray()[i])));
            }

        }
        return current;

        // HINT: Strings are basically just a series of characters

        // FIXME - How do we convert an entire String?
    }

    /**
     * Common alphabet of my rotors.
     */
    private final Alphabet _alphabet;
    private final int _numRotors;
    private final int _pawls;
    private final Rotor[] _allRotors;
    private final ArrayList<Rotor> _mymachine;
    private Permutation _plugboard;


    // FIXME - How do we keep track of my available Rotors/my Rotors/my pawls/my plugboard

    // FIXME: ADDITIONAL FIELDS HERE, IF NEEDED.

    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Machine.java enigma/Rotor.java enigma/FixedRotor.java enigma/Reflector.java enigma/MovingRotor.java enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/Machine
    public static void main(String[] args) {

        CharacterRange upper = new CharacterRange('A', 'Z');
        MovingRotor rotorI = new MovingRotor("I",
                new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", upper),
                "Q");
        MovingRotor rotorII = new MovingRotor("II",
                new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)", upper),
                "E");
        MovingRotor rotorIII = new MovingRotor("III",
                new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)", upper),
                "V");
        MovingRotor rotorIV = new MovingRotor("IV",
                new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)", upper),
                "J");
        MovingRotor rotorV = new MovingRotor("V",
                new Permutation("(AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)", upper),
                "Z");
        FixedRotor rotorBeta = new FixedRotor("Beta",
                new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)", upper));
        FixedRotor rotorGamma = new FixedRotor("Gamma",
                new Permutation("(AFNIRLBSQWVXGUZDKMTPCOYJHE)", upper));
        Reflector rotorB = new Reflector("B",
                new Permutation("(AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", upper));
        Reflector rotorC = new Reflector("C",
                new Permutation("(AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) (PW) (QZ) (SX) (UY)", upper));

        Rotor[] allRotors = new Rotor[9];
        allRotors[0] = rotorI;
        allRotors[1] = rotorII;
        allRotors[2] = rotorIII;
        allRotors[3] = rotorIV;
        allRotors[4] = rotorV;
        allRotors[5] = rotorBeta;
        allRotors[6] = rotorGamma;
        allRotors[7] = rotorB;
        allRotors[8] = rotorC;

        Machine machine2 = new Machine(upper, 3, 1, allRotors);
        machine2.insertRotors(new String[]{"B", "BETA", "I"});
        machine2.setRotors("AA");
        machine2.setPlugboard(new Permutation("(HQ) (EX) (IP) (TR) (BY)", upper));

        System.out.println(machine2.convert(0));


//        Machine machine = new Machine(upper, 5, 3, allRotors);
//        machine.insertRotors(new String[]{"B", "BETA", "III", "IV", "I"});
//        machine.setRotors("AXLE");
//        machine.setPlugboard(new Permutation("(HQ) (EX) (IP) (TR) (BY)", upper));


//        System.out.println(machine.numRotors() == 5);
//        System.out.println(machine.numPawls() == 3);
//        System.out.println(machine.convert(5) == 16);
//        System.out.println(machine.convert(17) == 21);
//        System.out.println(machine.convert("OMHISSHOULDERHIAWATHA").equals("PQSOKOILPUBKJZPISFXDW"));
//        System.out.println(machine.convert("TOOK THE CAMERA OF ROSEWOOD").equals("BHCNSCXNUOAATZXSRCFYDGU"));
//        System.out.println(machine.convert("Made of sliding folding rosewood").equals("FLPNXGXIXTYJUJRCAUGEUNCFMKUF"));
    }
}
