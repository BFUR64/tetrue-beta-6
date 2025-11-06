package com.teic.trueris.game.templates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockTemplate {
    public static final int BLOCK_SET_CAPACITY = 7;
    private static final List<BlockTemplate> BLOCK_SET =
        new ArrayList<>(BLOCK_SET_CAPACITY);
    
    private final int size;
    private final List<CellTemplate> cells;

    static {
        BLOCK_SET.add(new BlockTemplate(
            2, 
            List.of(
                CellTemplate.OCELL, CellTemplate.OCELL, 
                CellTemplate.OCELL, CellTemplate.OCELL
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                CellTemplate.JCELL, CellTemplate.EMPTY, CellTemplate.EMPTY, 
                CellTemplate.JCELL, CellTemplate.JCELL, CellTemplate.JCELL, 
                CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.LCELL, 
                CellTemplate.LCELL, CellTemplate.LCELL, CellTemplate.LCELL, 
                CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                CellTemplate.EMPTY, CellTemplate.SCELL, CellTemplate.SCELL, 
                CellTemplate.SCELL, CellTemplate.SCELL, CellTemplate.EMPTY, 
                CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                CellTemplate.ZCELL, CellTemplate.ZCELL, CellTemplate.EMPTY, 
                CellTemplate.EMPTY, CellTemplate.ZCELL, CellTemplate.ZCELL, 
                CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            3, 
            List.of(
                CellTemplate.EMPTY, CellTemplate.TCELL, CellTemplate.EMPTY, 
                CellTemplate.TCELL, CellTemplate.TCELL, CellTemplate.TCELL, 
                CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY
            )
        ));

        BLOCK_SET.add(new BlockTemplate(
            4, 
            List.of(
                CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY, 
                CellTemplate.ICELL, CellTemplate.ICELL, CellTemplate.ICELL, CellTemplate.ICELL, 
                CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY, 
                CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY, CellTemplate.EMPTY
            )
        ));
    }

    private BlockTemplate(int blockSize, List<CellTemplate> cells) {
        this.size = blockSize;
        this.cells = cells;
    }

    public static List<BlockTemplate> values() {
        return Collections.unmodifiableList(BLOCK_SET);
    }

    public static CellTemplate[][] copyBlock(int index) {
        BlockTemplate block = BLOCK_SET.get(index);
        int blockSize = block.size;

        CellTemplate[][] blockCopy = new CellTemplate[blockSize][blockSize];

        for (int i = 0; i < block.cells.size(); i++) {
            CellTemplate cell = block.cells.get(i);

            blockCopy[i / blockSize][i % blockSize] = (
                cell.isEmpty()
                ? CellTemplate.EMPTY 
                : cell.copy()
            );
        }

        return blockCopy;
    }
}

