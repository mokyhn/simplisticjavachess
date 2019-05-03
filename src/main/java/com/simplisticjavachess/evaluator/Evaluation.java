/**
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.evaluator;

import com.simplisticjavachess.piece.Color;
import static com.simplisticjavachess.piece.Color.WHITE;
import java.util.Objects;

public class Evaluation
{
     /**
     * The un-evaluated evaluation of something  
     */
    public static final Evaluation NONE = new Evaluation();
    
    public static final Evaluation EQUAL = new Evaluation(0);
    public static final Evaluation WHITE_IS_MATED = new Evaluation(-2147480000);
    public static final Evaluation BLACK_IS_MATED = new Evaluation(2147480000);

    
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
        return this.isAnImprovement(color, other) ? other : this;
    }


    /**
     * @param color the perspective the comparison is seen from
     * @param other the candidate that may improve this
     * @return true if the other improves this
     */
    public boolean isAnImprovement(Color color, Evaluation other)
    {
        // Something improves nothing
        if (this.equals(NONE))
        {
            return true;
        }
        
        // Nothing does not improve something
        if (other.equals(NONE))
        {
            return false;
        }
        
        if (color.equals(WHITE))
        {
            return other.value > this.value;
        }
        else
        {
            return other.value < this.value;
        }
    }
 
    /**
     * @param color the perspective the comparison is seen from
     * @param other the candidate that may improve this
     * @return true if the other improves this or this equals other
     */
    public boolean isAnImprovementOrEqual(Color color, Evaluation other)
    {
        return this.equals(other) || isAnImprovement(color, other);
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
