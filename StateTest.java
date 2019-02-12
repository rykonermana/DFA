/* StateTest.java */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
/**
 * Basic tests for class State using JUnit.
 * @author  Dr. Jody Paul
 * @version CSTheory (1)
 */
public class StateTest {

    /** Construction and accessor tests. */
    @Test
    public void constructAndAccessTests() {
        State state1 = new State("q1", null);
        assertEquals("q1", state1.name());
        assertNull(state1.label());
        State state2 = new State("q2", "S");
        assertEquals("q2", state2.name());
        assertEquals("S", state2.label());
        State state3 = new State(null, null);
        assertNull(state3.label());
        assertNull(state3.name());
    }

    /** Number of activity counter increments for testing. */
    private static final int NUM_INCR = 42;
    /** Activity counter tests. */
    @Test
    public void counterTests() {
        State state0 = new State("q0", "TEST");
        assertEquals(0, state0.counter());
        state0.increment();
        assertEquals(1, state0.counter());
        assertEquals(1, state0.counter());
        for (int i = 2; i < NUM_INCR; i++) {
            state0.increment();
            assertEquals(i, state0.counter());
        }
        state0.reset();
        assertEquals(0, state0.counter());
    }
}