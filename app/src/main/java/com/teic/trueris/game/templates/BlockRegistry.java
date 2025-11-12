package com.teic.trueris.game.templates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockRegistry {
    private static final List<BlockTemplate> BLOCK_SET = new ArrayList<>();

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

    public static List<BlockTemplate> values() {
        return Collections.unmodifiableList(BLOCK_SET);
    }

    public static int size() {
        return BLOCK_SET.size();
    }

    public static class BlockTemplate {
        private final int size;
        private final List<CellTemplate> cells;

        protected BlockTemplate(int size, List<CellTemplate> cells) {
            this.size = size;
            this.cells = cells;
        }
        
        public CellTemplate[][] copyBlock() {
            CellTemplate[][] blockCopy = new CellTemplate[size][size];

            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    int idx = row * size + col;
                    CellTemplate cell = cells.get(idx);
                    blockCopy[row][col] = cell.isEmpty() ? CellTemplate.EMPTY : cell.copy();
                }
            }

            return blockCopy;
        }
    }
}

