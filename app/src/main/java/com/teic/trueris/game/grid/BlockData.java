package com.teic.trueris.game.grid;

import com.teic.trueris.game.templates.CellTemplate;

public class BlockData {
    private CellTemplate[][] block;
    private int blockRow;
    private int blockCol;

    // Let BlockData handle BlockCollision. GridManager does 
    // not need to know the specific implementations of how 
    // BlockData validates if it is valid or not. Only 
    // BlockData is supposed to know that!!! 

    // moveDown, etc. stays as it is. Smart enough to revert 
    // if the position is invalid and passes a bool. The 
    // difference is a new method/s, either named lockBlock 
    // or setBlock, or removeBlock, or something similar to 
    // indicate: Yes. Commit the write. I allow you to write 
    // to grid. Not sure if it is a good idea or if I should 
    // let GridManager do that instead. But it makes things, 
    // uh, simpler, *maybe* 


    public boolean moveDown() {

    }

}

