package minesweeper.model;

import java.util.Objects;

/**
 * A simple pair type implementation
 *
 * Used for wrapping X, Y coordinate values for example
 */
public class Pair<T> {
    public T first;
    public T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.first);
        hash = 17 * hash + Objects.hashCode(this.second);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<?> other = (Pair<?>) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.second, other.second)) {
            return false;
        }
        return true;
    }

    
}
