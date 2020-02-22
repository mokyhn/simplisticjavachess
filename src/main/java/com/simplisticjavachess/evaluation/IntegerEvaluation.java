/**
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.piece.Color;
import java.util.Objects;

import static com.simplisticjavachess.piece.Color.WHITE;

public class IntegerEvaluation implements Evaluation
{

    private final Integer value;  // TODO: Use Optional instead
    
    public IntegerEvaluation()
    {
        this.value = null;
    }
    
    public IntegerEvaluation(int value)
    {
        this.value = value;
    }

    public static IntegerEvaluation of(int value)
    {
        return new IntegerEvaluation(value);
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
     * @param e the candidate that may improve this
     * @return true if the other improves this
     */
    public boolean isAnImprovement(Color color, Evaluation e)
    {
        IntegerEvaluation other = (IntegerEvaluation) e;

        // Something improves nothing
        if (this.equals(EvaluationConstantsFactoryImpl.instance().getNone()))
        {
            return true;
        }

        // Nothing does not improve something
        if (other.equals(EvaluationConstantsFactoryImpl.instance().getNone()))
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
        
        if (other instanceof IntegerEvaluation)
        {
            return Objects.equals(this.value, ((IntegerEvaluation) other).value);
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
