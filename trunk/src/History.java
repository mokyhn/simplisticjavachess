
import java.util.Stack;

/**
 *
 * @author mku
 */
public class History implements Cloneable {
    private Stack<State> history; // A stack of previous game states

    public History() {
        history =  new Stack<State>();
    }
    
    public void add(State s) {
     history.add(s);
    }
    
    @Override
    public History clone() {
        History theClone = new History();
        
        for (int i = 0; i < history.size(); i ++) {
            (theClone.history).push((history.get(i)).clone());
        }

        return theClone;
    }

}
