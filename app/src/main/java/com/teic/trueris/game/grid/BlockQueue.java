package com.teic.trueris.game.grid;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.teic.trueris.game.templates.BlockRegistry;
import com.teic.trueris.game.templates.CellTemplate;

public class BlockQueue {
    private static final int MIN_BLOCK_QUEUE_SIZE = 1;
    
    private List<BlockRegistry.BlockTemplate> blockQueue;

    public BlockQueue() {
        blockQueue = new LinkedList<>();
    }

    public CellTemplate[][] getRandomBlock() {
        if (blockQueue.size() < MIN_BLOCK_QUEUE_SIZE) {
            addtoBlockQueue(createRandomizedBag());
        }

        CellTemplate[][] cells = blockQueue.getFirst().copyBlock();
        blockQueue.removeFirst();

        return cells;

    }

    public List<BlockRegistry.BlockTemplate> viewBlockQueue() {
        return Collections.unmodifiableList(blockQueue);
    }

    private void addtoBlockQueue(List<BlockRegistry.BlockTemplate> blocks) {
        blockQueue.addAll(blocks);
    }

    private List<BlockRegistry.BlockTemplate> createRandomizedBag() {
        List<BlockRegistry.BlockTemplate> sevenBag = new LinkedList<>(BlockRegistry.values());
        Collections.shuffle(sevenBag);

        return sevenBag;
    }
}

