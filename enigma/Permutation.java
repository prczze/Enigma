package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author
 */
public class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    public Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;

        // FIXME - Assign any additional instance variables.
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    public int size() {

        return _alphabet.size(); // FIXME - How do we ask the alphabet for its size?
    }

    /** Return the index result of applying this permutation to the character
     *  at index P in ALPHABET. */
    public int permute(int p) {
        return alphabet().toInt(permute(alphabet().toChar(wrap(p))));

    	// NOTE: it might be beneficial to have one permute() method always call the other
        // FIXME - How do we use our instance variables to get the index that P permutes to?
    }

    /** Return the index result of applying the inverse of this permutation
     *  to the character at index C in ALPHABET. */
    public int invert(int c) {
        return alphabet().toInt(invert(alphabet().toChar(wrap(c))));



    	// NOTE: it might be beneficial to have one invert() method always call the other
        // FIXME - How do we use our instance variables to get the index that C inverts to?
    }

    /** Return the character result of applying this permutation to the index
     * of character P in ALPHABET. */
    public char permute(char p) {
    	// NOTE: it might be beneficial to have one permute() method always call the other
        if (_cycles.length() == 0) {
            return p;
        }
        char first = _cycles.charAt(1);
        for (int i = 0; i < _cycles.length(); i++) {
            if (_cycles.charAt(i) == '(') {
                first = _cycles.charAt(i + 1);
            }
            if (_cycles.charAt(i) == p){
                if (_cycles.charAt(i+1) == ')') {
                    return first;
                }
                else {
                    return _cycles.charAt(i+1);
                }
            }
        }
        // FIXME - How do we use our instance variables to get the character that P permutes to?
        return p;
    }

    /** Return the character result of applying the inverse of this permutation
	 * to the index of character P in ALPHABET. */
    public char invert(char c) {
        if (_cycles.length() == 0) {
            return c;
        }
        char first = _cycles.charAt(_cycles.length() - 2);
        for (int i = _cycles.length() - 1; i >= 1; i--) {
            if (_cycles.charAt(i) == ')') {
                first = _cycles.charAt(i - 1);
            }
            if (_cycles.charAt(i) == c){
                if (_cycles.charAt(i-1) == '(') {
                    return first;
                }
                else {
                    return _cycles.charAt(i-1);
                }
            }
        }
    	// NOTE: it might be beneficial to have one invert() method always call the other
        // FIXME - How do we use our instance variables to get the character that C inverts to?
        return c;
    }

    /** Return the alphabet used to initialize this Permutation. */
    public Alphabet alphabet() {

        return _alphabet;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    private String _cycles;


    // FIXME - How do we store which letter permutes/inverts to which?

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED

    // Some starter code for unit tests. Feel free to change these up!
    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/Permutation
    public static void main(String[] args) {
        Permutation perm = new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", new CharacterRange('A', 'Z'));
        System.out.println(perm.size() == 26);
        System.out.println(perm.permute('A') == 'B');
        System.out.println(perm.invert('B') == 'A');
        System.out.println(perm.permute(0) == 1);
        System.out.println(perm.invert(1) == 0);
    }
}
