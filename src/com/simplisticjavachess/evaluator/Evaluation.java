package com.simplisticjavachess.evaluator;

import com.simplisticjavachess.piece.Color;
import static com.simplisticjavachess.piece.Color.WHITE;
import java.util.Objects;

/**
 *
 * @author Morten Kühnrich
 */
public class Evaluation
{
     /**
     * The un-evaluated evaluation of something  
     */
    static final Evaluation NONE = new Evaluation();
    
    private final Integer value;
    
    private Evaluation()
    {
        this.value = null;
    }
    
    public Evaluation(int value)
    {
        this.value = value;
    }
    
    /**
     * @param color the perspective the comparison is seen from
     * @param other the other evaluation to try to improve with
     * @return this if this is better and else return other
     */
    public Evaluation improveWith(Color color, Evaluation other)
    {
        // Something improves nothing
        if (this.equals(NONE))
        {
            return other;
        }
        
        // Nothing does not improve something
        if (other.equals(NONE))
        {
            return this;
        }
        
        if (color.equals(WHITE))
        {
            return other.value > this.value ? other : this;
        }
        else
        {
            return other.value < this.value ? other : this;
        }
    }

    
    /**
     * @param color the perspective the comparison is seen from
     * @param other the other evaluation to try to improve with
     * @param improver to be invoked, if the other was an improvement
     */
    public void improveWith(Color color, Evaluation other, Improver improver)
    {
        Evaluation result = improveWith(color, other);
        if (!this.equals(result))
        {
            improver.run(result);
        }
    }
    
    @Override
    public String toString()
    {
        return value == null ? "None" : value.toString();
    }
    
    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        
        if (other instanceof Evaluation)
        {
            return Objects.equals(this.value, ((Evaluation) other).value);
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }
}
