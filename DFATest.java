/* DFATest.java */
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Tests for the DFA class using JUnit.
 * @author  Dr. Jody Paul
 * @version CSTheory (4)
 */
public class DFATest {
    /**
     * Constructs and tests a string validator for
     * regular expression a* over the alphabet {a, b}.
     */
    @Test
    public void astarDFATest() {
        Set<String> alphabet = new HashSet<String>();
        alphabet.add("a");
        alphabet.add("b");
        State q0 = new State("q0", "a*");
        State q1 = new State("q1", "TRAP");
        Set<State> states = new HashSet<State>();
        states.add(q0);
        states.add(q1);
        State initial = q0;
        Set<State> accept = new HashSet<State>();
        accept.add(q0);
        Map<State, Map<String, State>> transitions
                = new HashMap<State, Map<String, State>>();
        Map<String, State> trans = new HashMap<String, State>();
        trans.put("a", q0);
        trans.put("b", q1);
        transitions.put(q0, trans);
        trans = new HashMap<String, State>();
        trans.put("a", q1);
        trans.put("b", q1);
        transitions.put(q1, trans);
        DFA dfa = new DFA(states, alphabet, transitions, initial, accept);
        assertTrue(dfa.accepts(""));
        assertTrue(dfa.accepts(null));
    }

    /**
     * Constructs and tests a string validator for
     * regular expression a*b.
     * Includes tests of state activity counter.
     */
    @Test
    public void astar_bDFATest() {
        Set<String> alphabet = new HashSet<String>();
        alphabet.add("a");
        alphabet.add("b");
        State q0 = new State("q0", "a*");
        State q1 = new State("q1", "a*b");
        State q2 = new State("q2", "TRAP");
        Set<State> states = new HashSet<State>();
        states.add(q0);
        states.add(q1);
        states.add(q2);
        State initial = q0;
        Set<State> accept = new HashSet<State>();
        accept.add(q1);
        Map<State, Map<String, State>> transitions
                = new HashMap<State, Map<String, State>>();
        Map<String, State> trans = new HashMap<String, State>();
        trans.put("a", q0);
        trans.put("b", q1);
        transitions.put(q0, trans);
        trans = new HashMap<String, State>();
        trans.put("a", q2);
        trans.put("b", q2);
        transitions.put(q1, trans);
        trans = new HashMap<String, State>();
        trans.put("a", q2);
        trans.put("b", q2);
        transitions.put(q2, trans);
        DFA dfa = new DFA(states, alphabet, transitions, initial, accept);
        assertTrue(dfa.accepts("b"));
        assertEquals(1L, q0.counter());
        assertEquals(1L, q1.counter());
        assertEquals(0L, q2.counter());
        assertTrue(dfa.accepts("aaaaab"));
        assertEquals(6L, q0.counter());
        assertEquals(1L, q1.counter());
        assertEquals(0L, q2.counter());
        assertFalse(dfa.accepts("ababab"));
        assertEquals(2L, q0.counter());
        assertEquals(1L, q1.counter());
        assertEquals(4L, q2.counter());
        assertFalse(dfa.accepts("aaaaabbbbb"));
        assertEquals(6L, q0.counter());
        assertEquals(1L, q1.counter());
        assertEquals(4L, q2.counter());
        assertFalse(dfa.accepts("bb"));
        assertEquals(1L, q0.counter());
        assertEquals(1L, q1.counter());
        assertEquals(1L, q2.counter());
    }

    /**
     * Construct and test a string validator for
     * regular expression a*c(ab)*cc*.
     *
     */
    @Test
    public void astar_c_abstar_c_cstarDFATest() {
        Set<String> alphabet = new HashSet<String>();
        alphabet.add("a");
        alphabet.add("b");
        alphabet.add("c");
        State q0 = new State("q0", null);
        State q1 = new State("q1", null);
        State q2 = new State("q2", null);
        State q3 = new State("q3", null);
        State q4 = new State("q4", "TRAP");
        Set<State> states = new HashSet<State>();
        states.add(q0);
        states.add(q1);
        states.add(q2);
        states.add(q3);
        states.add(q4);
        State initial = q0;
        Set<State> accept = new HashSet<State>();
        accept.add(q2);
        Map<State, Map<String, State>> transitions
                = new HashMap<State, Map<String, State>>();
        Map<String, State> trans = new HashMap<String, State>();
        trans.put("a", q0);
        trans.put("b", q4);
        trans.put("c", q1);
        transitions.put(q0, trans);
        trans = new HashMap<String, State>();
        trans.put("a", q3);
        trans.put("b", q4);
        trans.put("c", q2);
        transitions.put(q1, trans);
        trans = new HashMap<String, State>();
        trans.put("a", q4);
        trans.put("b", q4);
        trans.put("c", q2);
        transitions.put(q2, trans);
        trans = new HashMap<String, State>();
        trans.put("a", q4);
        trans.put("b", q1);
        trans.put("c", q4);
        transitions.put(q3, trans);
        trans = new HashMap<String, State>();
        trans.put("a", q4);
        trans.put("b", q4);
        trans.put("c", q4);
        transitions.put(q4, trans);
        DFA regx = new DFA(states, alphabet, transitions, initial, accept);
        assertTrue(regx.accepts("cc"));
        assertTrue(regx.accepts("acabc"));
        assertTrue(regx.accepts("accc"));
        assertTrue(regx.accepts("aaaccc"));
        assertTrue(regx.accepts("cabababcc"));
        assertFalse(regx.accepts("accabc"));
        assertFalse(regx.accepts("aaccabc"));
    }

    /**
     * Construct and test a simple assignment statement checker.
     * All variables are a single lower-case letter.
     * Expression operators are +, -, *, and /.
     * Assignment operator is =.
     * Blanks are not in the alphabet.
     * The assignment statement must end with ; (a semi-colon).
     */
    @Test
    public void assignmentStatementDFATest() {
        Set<String> variables = new HashSet<String>();
        for (char c = 'a'; c <= 'z'; c++) {
            variables.add(String.valueOf(c));
        }
        Set<String> operators = new HashSet<String>();
        operators.add("+");
        operators.add("-");
        operators.add("*");
        operators.add("/");
        String assignment = "=";
        String terminator = ";";
        Set<String> alphabet = new HashSet<String>();
        alphabet.addAll(variables);
        alphabet.addAll(operators);
        alphabet.add(assignment);
        alphabet.add(terminator);

        State q0 = new State("q0", "S");
        State q1 = new State("q1", "A");
        State q2 = new State("q2", "B");
        State q3 = new State("q3", "E");
        State q4 = new State("q4", "");
        Set<State> states = new HashSet<State>();
        states.add(q0);
        states.add(q1);
        states.add(q2);
        states.add(q3);
        states.add(q4);
        State initial = q0;
        Set<State> accept = new HashSet<State>();
        accept.add(q4);
        Map<State, Map<String, State>> transitions
                = new HashMap<State, Map<String, State>>();
        Map<String, State> trans = new HashMap<String, State>();
        for (String v : variables) {
            trans.put(v, q1);
        }
        transitions.put(q0, trans);
        trans = new HashMap<String, State>();
        trans.put(assignment, q2);
        transitions.put(q1, trans);
        trans = new HashMap<String, State>();
        for (String v : variables) {
            trans.put(v, q3);
        }
        transitions.put(q2, trans);
        trans = new HashMap<String, State>();
        for (String v : operators) {
            trans.put(v, q2);
        }
        trans.put(terminator, q4);
        transitions.put(q3, trans);
        DFA asgn = new DFA(states, alphabet, transitions, initial, accept);
        assertTrue(asgn.accepts("x=y;"));
        assertTrue(asgn.accepts("z=x+y;"));
        assertTrue(asgn.accepts("y=x*z+x*y;"));
        assertFalse(asgn.accepts("x"));
        assertFalse(asgn.accepts(";"));
        assertFalse(asgn.accepts("y=x*z+x*y"));
    }

    /**
     * Determines if java.util.regex identifies the entire
     * input string as matching the given regular expression.
     * @param regexp the regular expression
     * @param input the input string
     * @return true if the entire input string is matched by the pattern;
     *         false otherwise
     */
    public static boolean regexMatch(final String regexp, final String input) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(input);
        boolean found = false;
        while (matcher.find()) {
            if (input.equals(matcher.group())
                && 0 == matcher.start()
                && input.length() == matcher.end()) {
                    found = true;
            }
        }
        return found;
    }

    /**
     * Tests using java.util.regex for comparison.
     * The RE <code>[a-z][0-9]+</code> represents
     * identifiers that start with a single
     * lower-case letter which is then followed by
     * one or more numerals (e.g., a0 and d4298 but
     * not ab1 or 0a).
     */
    @Test
    public void utilregexTests() {
        Set<String> alphabet = new HashSet<String>();
        Set<String> letters = new HashSet<String>();
        Set<String> numerals = new HashSet<String>();
        for (char c = 'a'; c <= 'z'; c++) {
            letters.add(String.valueOf(c));
        }
        for (char c = '0'; c <= '9'; c++) {
            numerals.add(String.valueOf(c));
        }
        alphabet.addAll(letters);
        alphabet.addAll(numerals);

        State q0 = new State("q0", null);
        State q1 = new State("q1", null);
        State q2 = new State("q2", null);
        State q3 = new State("q3", "TRAP");
        Set<State> states = new HashSet<State>();
        states.add(q0);
        states.add(q1);
        states.add(q2);
        states.add(q3);
        State initial = q0;
        Set<State> accept = new HashSet<State>();
        accept.add(q2);
        Map<State, Map<String, State>> transitions
                = new HashMap<State, Map<String, State>>();
        Map<String, State> trans = new HashMap<String, State>();
        for (String x : letters) {
            trans.put(x, q1);
        }
        for (String n : numerals) {
            trans.put(n, q3);
        }
        transitions.put(q0, trans);
        trans = new HashMap<String, State>();
        for (String x : letters) {
            trans.put(x, q3);
        }
        for (String n : numerals) {
            trans.put(n, q2);
        }
        transitions.put(q1, trans);
        trans = new HashMap<String, State>();
        for (String x : letters) {
            trans.put(x, q3);
        }
        for (String n : numerals) {
            trans.put(n, q2);
        }
        transitions.put(q2, trans);
        trans = new HashMap<String, State>();
        for (String x : letters) {
            trans.put(x, q3);
        }
        for (String n : numerals) {
            trans.put(n, q3);
        }
        transitions.put(q3, trans);

        DFA ident = new DFA(states, alphabet, transitions, initial, accept);

        String regexp = "[a-z][0-9]+";
        String input = "a42";
        assertEquals(regexMatch(regexp, input), ident.accepts(input));
        input = "a918273645";
        assertEquals(regexMatch(regexp, input), ident.accepts(input));
        input = "zz1";
        assertEquals(regexMatch(regexp, input), ident.accepts(input));
        input = "m0";
        assertEquals(regexMatch(regexp, input), ident.accepts(input));
        input = "7";
        assertEquals(regexMatch(regexp, input), ident.accepts(input));
    }
}