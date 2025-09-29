package com.teic.trueris.game.templates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockTemplate {
    private static final int BLOCK_SET_CAPACITY = 7;
    private static final List<BlockTemplate> BLOCK_SET =
        new ArrayList<>(BLOCK_SET_CAPACITY);
    
    private final int size;
    private final List<Cell> cells;

    static {
        BLOCK_SET.add(new BlockTemplate(
            2, 
            List.of(
                Cell.OCELL, Cell.OCELL, 
                Cell.OCELL, Cell.OCELL
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                Cell.JCELL, Cell.EMPTY, Cell.EMPTY, 
                Cell.JCELL, Cell.JCELL, Cell.JCELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                Cell.EMPTY, Cell.EMPTY, Cell.LCELL, 
                Cell.LCELL, Cell.LCELL, Cell.LCELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                Cell.EMPTY, Cell.SCELL, Cell.SCELL, 
                Cell.SCELL, Cell.SCELL, Cell.EMPTY, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                Cell.ZCELL, Cell.ZCELL, Cell.EMPTY, 
                Cell.EMPTY, Cell.ZCELL, Cell.ZCELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                Cell.EMPTY, Cell.TCELL, Cell.EMPTY, 
                Cell.TCELL, Cell.TCELL, Cell.TCELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            4, 
            List.of(
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, 
                Cell.ICELL, Cell.ICELL, Cell.ICELL, Cell.ICELL, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, 
                Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY
            )
        ));
    }

    private BlockTemplate(int blockSize, List<Cell> cells) {
        this.size = blockSize;
        this.cells = cells;
    }

    public static List<BlockTemplate> values() {
        return Collections.unmodifiableList(BLOCK_SET);
    }

    public static Cell[][] copyBlock(int index) {
        BlockTemplate block = BLOCK_SET.get(index);
        int blockSize = block.size;

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

