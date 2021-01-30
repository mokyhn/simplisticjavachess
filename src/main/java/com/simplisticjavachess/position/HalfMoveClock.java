package com.simplisticjavachess.position;

/**
 * Encapsulates dealing with half move counters
 */
public final class HalfMoveClock {
    private final int clock;

    public HalfMoveClock() {
        this.clock = 0;
    }

    private HalfMoveClock(int halfMoveClock) {
        if (halfMoveClock >= 0) {
            this.clock = halfMoveClock;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static HalfMoveClock fromString(String s) {
        try {
            int clock = Integer.parseInt(s);
            return new HalfMoveClock(clock);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse " + s + " which were not an integer");
        }
    }

    public HalfMoveClock tick() {
        return new HalfMoveClock(this.clock + 1);
    }

    public int getClock() {
        return clock;
    }

    public HalfMoveClock reset() {
        return new HalfMoveClock();
    }

    public boolean isFifty() {
        return this.clock == 50;
    }

    @Override
	public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof HalfMoveClock) {
            HalfMoveClock other = (HalfMoveClock) object;
            return this.clock == other.clock;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(clock);
    }

    @Override
    public int hashCode() {
        return this.clock;
    }

}
