/**
 * @author Morten Kühnrich
 */
package com.simplisticjavachess.evaluation;

import com.simplisticjavachess.piece.Color;

import java.util.Optional;

import static com.simplisticjavachess.piece.Color.WHITE;

public class IntegerEvaluation extends Evaluation {

    private final Optional<Integer> value;

    public IntegerEvaluation() {
        this.value = Optional.empty();
    }

    public IntegerEvaluation(int value) {
        this.value = Optional.of(value);
    }

    public static IntegerEvaluation of(int value) {
        return new IntegerEvaluation(value);
    }

    @Override
    public boolean isSomething() {
        return value.isPresent();
    }

    public boolean isWorseThan(Color color, Evaluation e) {
        IntegerEvaluation other = (IntegerEvaluation) e;

        // Nothing does not improve something
        if (!other.value.isPresent()) {
            return false;
        }

        // Something improves nothing
        if (!this.value.isPresent() && other.value.isPresent()) {
            return true;
        }

        if (color.equals(WHITE)) {
            return other.value.get() > this.value.get();
        } else {
            return other.value.get() < this.value.get();
        }
    }

    @Override
    public String toString() {
        return value.isPresent() ? value.get().toString() : "None";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof IntegerEvaluation) {
            return this.value.equals(((IntegerEvaluation) other).value);
        } else {
            return false;
        }
    }

}
