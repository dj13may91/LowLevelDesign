package com.game.of.snake;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Cell {
    @Getter @Setter private int x, y;
    @Setter @Getter private CellType cellType = CellType.SPACE;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ":" + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
