/**
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.piece.Color;
import java.util.Optional;

import static com.simplisticjavachess.piece.Color.WHITE;

public class IntegerEvaluation extends Evaluation
{

    private final Optional<Integer> value;
    
    public IntegerEvaluation()
    {
        this.value = Optional.empty();
    }
    
    public IntegerEvaluation(int value)
    {
        this.value = Optional.of(value);
    }

    public static IntegerEvaluation of(int value)
    {
        return new IntegerEvaluation(value);
    }

    public boolean isAnImprovement(Color color, Evaluation e)
    {
        IntegerEvaluation other = (IntegerEvaluation) e;

        // Something improves nothing
        if (!this.value.isPresent())
        {
            return true;
        }

        // Nothing does not improve something
        if (!other.value.isPresent())
        {
            return false;
        }

        if (color.equals(WHITE))
        {
            return other.value.get() > this.value.get();
        }
        else
        {
            return other.value.get() < this.value.get();
        }
    }

    @Override
    public Evaluation increase() {
        if (this.value.isPresent()) {
            return IntegerEvaluation.of(this.value.get()+1);
        } else {
            return IntegerEvaluation.of(0);
        }
    }

    @Override
    public String toString()
    {
        return value.isPresent() ? value.get().toString() : "None";
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
            return this.value.equals(((IntegerEvaluation) other).value);
        }
        else
        {
            return false;
        }
    }

}
