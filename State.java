/* State.java */
/**
 * State for a DFA.
 * Attributes of a state include its Name
 * (an immutable unique identifier within the FA),
 * its Label (an immutable comment regarding the
 * intent of the state),
 * and its Activity Counter (an integer valued
 * variable typically used for collecting
 * statistics).
 * @author Dr. Jody Paul
 * @version CSTheory (1)
 */
public class State {
    /** The name of this state. */
    private String stateName;
    /** The label of this state. */
    private String stateLabel;
    /** Activity counter for this state. */
    private long counter = 0L;

    /**
     * Constructs a state with the specified name and label.
     * The activity counter is set to zero.
     * @param name the name for this state
     * @param label the label for this state
     */
    public State(final String name, final String label) {
        this.stateName = name;
        this.stateLabel = label;
        this.counter = 0L;
    }

    /**
     * Retrieves the name of this state.
     * @return the name of this state
     */
    public String name() {
        return this.stateName;
    }

    /**
     * Retrieves the label of this state.
     * @return the label of this state
     */
    public String label() {
        return this.stateLabel;
    }

    /**
     * Retrieves the activity counter of this state.
     * @return the current counter value
     */
    public long counter() {
        return this.counter;
    }

    /**
     * Increments the activity counter of this state.
     */
    public void increment() {
        this.counter++;
    }

    /**
     * Resets the activity counter of this state to 0.
     */
    public void reset() {
        this.counter = 0L;
    }
}