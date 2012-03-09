
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
     history.push(s);
    }
    
    public State pop() {
     return history.pop();
    }
    
    public State peek() {
     return history.peek();
    }
    
    public int size() {
     return history.size();
    }
    
    public State get(int index) {
     return history.get(index);
    }
    
    public boolean isEmpty() {
     return history.isEmpty();
    }
    
    public String toString() {
     String r      = "";
     String prefix = "";
     Move   m;
     
     for(int i = 0; i < history.size(); i++) {
       m = history.get(i).move;
         if (m.whoMoves == Piece.WHITE) prefix = (i+2)/2 + ".";         
         //if (m.whoMoves == Piece.BLACK) prefix = (i+2)/2 + "....";
         r = r + prefix + m.toString() + " ";
         prefix = "";
     }
        
     return r;
     
     
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
