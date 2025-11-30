package com.teic.trueris.game;

import com.teic.trueris.Config;
import com.teic.trueris.game.block.BlockData;
import com.teic.trueris.game.block.BlockMover;
import com.teic.trueris.game.block.BlockQueue;
import com.teic.trueris.game.grid.GridData;
import com.teic.trueris.game.grid.GridType;
import com.teic.trueris.game.grid.RowManager;

public class GridManager {
    private final BlockMover blockMover;
    private final RowManager rowManager;
    private final BlockQueue blockQueue;

    private BlockData activeBlock;
    private BlockData ghostBlock;

    public GridManager(Config config, GridData gridData) {
        this.blockMover = new BlockMover(config, gridData);
        this.rowManager = new RowManager(config, gridData);
        this.blockQueue = new BlockQueue();

        generateActiveBlock();
        generateGhostBlock();
    }

    // =====================
    // Movement
    // =====================
    public void moveBlockDown() {
        rowManager.eraseGrid(GridType.ACTIVE);

        if (!blockMover.moveBlockDown(activeBlock)) {
            rowManager.writeGrid(GridType.SOLID, activeBlock);
            rowManager.clearFilledRows();

            generateActiveBlock();
            generateGhostBlock();

            return;
        }

        rowManager.writeGrid(GridType.ACTIVE, activeBlock);
    }

    public void dropBlock() {
        blockMover.dropBlock(activeBlock);

        rowManager.writeGrid(GridType.SOLID, activeBlock);
        rowManager.clearFilledRows();

        generateActiveBlock();
        generateGhostBlock();
    }

    public void moveBlockLeft() {
        if (blockMover.moveBlockLeft(activeBlock)) {
            rowManager.eraseGrid(GridType.ACTIVE);
            rowManager.writeGrid(GridType.ACTIVE, activeBlock);

            generateGhostBlock();
        }
    }

    public void moveBlockRight() {
        if (blockMover.moveBlockRight(activeBlock)) {
            rowManager.eraseGrid(GridType.ACTIVE);
            rowManager.writeGrid(GridType.ACTIVE, activeBlock);

            generateGhostBlock();
        }
    }

    // =====================
    // Rotation
    // =====================
    public void rotateBlockLeft() {
        if (blockMover.rotateBlockLeft(activeBlock)) {
            rowManager.eraseGrid(GridType.ACTIVE);
            rowManager.writeGrid(GridType.ACTIVE, activeBlock);

            generateGhostBlock();
        }
    }

    public void rotateBlockRight() {
        if (blockMover.rotateBlockRight(activeBlock)) {
            rowManager.eraseGrid(GridType.ACTIVE);
            rowManager.writeGrid(GridType.ACTIVE, activeBlock);

            generateGhostBlock();
        }
    }

    // =====================
    // Utilities
    // =====================
    private void generateActiveBlock() {
        activeBlock = new BlockData(blockQueue.getRandomBlock());
        rowManager.eraseGrid(GridType.ACTIVE);
        rowManager.writeGrid(GridType.ACTIVE, activeBlock);
    }

    private void generateGhostBlock() {
        ghostBlock = activeBlock.copyBlockData();

        blockMover.dropBlock(ghostBlock);

        rowManager.eraseGrid(GridType.GHOST);
        rowManager.writeGrid(GridType.GHOST, ghostBlock);
    }
}

