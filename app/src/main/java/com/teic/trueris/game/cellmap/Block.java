package com.teic.trueris.game.cellmap;

import java.util.LinkedList;
import java.util.List;

public class Block {
    private static final List<Block> BLOCK_GROUP =
        new LinkedList<>();
    
    private final int blockLength;
    private final List<Cell> block;

    static {
        BLOCK_GROUP.add(new Block(
                    2, 
                    List.of(
                        Cell.OCELL, Cell.OCELL, 
                        Cell.OCELL, Cell.OCELL
                        )));
    }

    private Block(int blockSize, List<Cell> block) {
        this.blockLength = blockSize;
        this.block = block;
    }
}
