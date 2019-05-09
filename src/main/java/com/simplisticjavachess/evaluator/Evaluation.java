/**
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.evaluator;

import com.simplisticjavachess.piece.Color;
import java.util.Objects;

import static com.simplisticjavachess.piece.Color.WHITE;

public class Evaluation
{
     /**
     * The un-evaluated evaluation of something
     */
    public static final Evaluation NONE = new Evaluation(); // TODO: Use Optional instead

    public static final Evaluation EQUAL = new Evaluation(0);
    public static final Evaluation WHITE_IS_MATED = new Evaluation(-2147480000);
    public static final Evaluation BLACK_IS_MATED = new Evaluation(2147480000);

    
    private final Integer value;  // TODO: Use Optional instead
    
    private Evaluation()
    {
        this.value = null;
    }
    
    private Evaluation(int value)
    {
        this.value = value;
    }

    public static Evaluation of(int value)
    {
        return new Evaluation(value);
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

    public boolean isNone()
    {
        return NONE.equals(this);
    }
}
