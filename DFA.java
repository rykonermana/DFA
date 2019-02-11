/*DFA.java*/
/**
 * Definite Finite Automate
 * Attributes of the DFA include states
 * (immutable set of states present in the DFA),
 * alphabet (immutable set of symbols present in the DFA),
 * transition (immutable map that is used to traverse states),
 * start (immutable starting state for the DFA),
 * and accept (immutable set of states that is used to indicate a 
 * string accepted by the DFA).
 * @author Daniel Reuter
 * @version Theory of Comp (1)
 */

import java.util.Map;
import java.util.Set;

public class DFA{
	/* The set of states to be working with*/
	public Set<State> states;
	/* The set of symbols to be used in the DFA*/
	public Set<String> alphabet;
	/* The map of transitions to go from one state to another*/
	public Map<State,Map<String,State>> transition;
	/* The start state for this DFA*/
	public State start;
	/* The set of accept states for the */
	public Set<State> accept;

	/*
	 * Fully parameterized constructor for DFA objects.
	 * @param states - the set of states of this DFA
	 * @param alphabet - the alphabet of this DFA
	 * @param transition - the transition function of this DFA
	 * @param start - the start state of this DFA
	 * @param accept - the set of accept states of this DFA
	 */
	public DFA(Set<State> states, Set<String> alphabet, Map<State,Map<String,State>> transition, State start, Set<State> accept){
		this.states=states;
		this.alphabet=alphabet;
		this.transition=transition;
		this.start=start;
		this.accept=accept;
	}
	
	
	public boolean accepts(String input) {
		/*
		 * Initializes activity counter to zero for each state
		 */
		for(State temp : states) {
			temp.reset();
		}
		String read = input;
		State current = start;

		/*
		 * Traverse through the states based on the input
		 */
		while(read != null && read.length()>0) {
			/* traverses through the state given the current string */
			current = nextState(current,read);
			/* reduces the read string by one, the escape clause*/
			read = read.substring(1);
		}
		/* has gone through all the string to obtain the final state
		 * will return false if current is not an accept state
		 * returns true if current is an accept state
		 */
		for(State temp : accept) {
			if(current == temp) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Retrieves the set of the accept states
	 * @return accept states
	 */
	public Set<State> acceptStates(){
		return accept;
	}
	
	/*
	 * Retrieves the alphabet of this DFA.
	 * @return the alphabet
	 */
	public Set<String> alphabet(){
		return alphabet;
	}
	
	/*
	 * Retrieves the initial state of this DFA, if any.
	 * @return the initial state; null if none
	 */
	public State initialState() {
		return start;
	}
	
	/*
	 * Lookup transition for specified state and input.
	 * @param source - the source state
	 * @param input - the input symbol
	 * @return the destination state; null if none
	 */
	public State nextState (State source, String input) {
		Map<String,State> traversal = transition.get(source);
		State current = traversal.get(input.substring(0, 1));
		return current;
	}
	public Set<State> states(){
		return states;
	}
	public Map<State,Map<String,State>> transitionFunction(){
		return transition;
	}
}