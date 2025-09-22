package com.teic.trueris.game.cellmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Block {
    private static final List<Block> BLOCKS =
        new ArrayList<>();
    
    private final int blockSize;
    private final List<Cell> cells;

    static {
        BLOCKS.add(new Block(
            2, 
            List.of(
                Cell.OCELL, Cell.OCELL, 
                Cell.OCELL, Cell.OCELL
            )
        ));

        BLOCKS.add(new Block(
            3, 
            List.of(
                Cell.JCELL, Cell.EMPTY, Cell.EMPTY, 
                Cell.JCELL, Cell.JCELL, Cell.JCELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCKS.add(new Block(
            3, 
            List.of(
                Cell.EMPTY, Cell.EMPTY, Cell.LCELL, 
                Cell.LCELL, Cell.LCELL, Cell.LCELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCKS.add(new Block(
            3, 
            List.of(
                Cell.EMPTY, Cell.SCELL, Cell.SCELL, 
                Cell.SCELL, Cell.SCELL, Cell.EMPTY, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCKS.add(new Block(
            3, 
            List.of(
                Cell.ZCELL, Cell.ZCELL, Cell.EMPTY, 
                Cell.EMPTY, Cell.ZCELL, Cell.ZCELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCKS.add(new Block(
            3, 
            List.of(
                Cell.EMPTY, Cell.TCELL, Cell.EMPTY, 
                Cell.TCELL, Cell.TCELL, Cell.TCELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCKS.add(new Block(
            4, 
            List.of(
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, 
                Cell.ICELL, Cell.ICELL, Cell.ICELL, Cell.ICELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));
    }

    private Block(int blockSize, List<Cell> cells) {
        this.blockSize = blockSize;
        this.cells = cells;
    }

    public static List<Block> values() {
        return Collections.unmodifiableList(BLOCKS);
    }

    public static Cell[][] copyBlock(int index) {
        Block block = BLOCKS.get(index);
        int blockSize = block.blockSize;

        Cell[][] blockCopy = new Cell[blockSize][blockSize];

        for (int i = 0; i < block.cells.size(); i++) {
            Cell cell = block.cells.get(i);

            blockCopy[i / blockSize][i % blockSize] = (
                cell.isEmpty()
                ? Cell.EMPTY 
                : cell.copy()
            );
        }

        return blockCopy;
    }
}

