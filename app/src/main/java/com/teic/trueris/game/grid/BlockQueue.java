package com.teic.trueris.game.grid;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.teic.trueris.game.templates.BlockRegistry;
import com.teic.trueris.game.templates.CellTemplate;

public class BlockQueue {
    private static final int BLOCK_QUEUE_THRESHHOLD = 1;
    
    private List<BlockRegistry.BlockTemplate> blockQueue;

    public BlockQueue() {
        blockQueue = new LinkedList<>();
    }

    public CellTemplate[][] getRandomBlock() {
        if (blockQueue.size() < BLOCK_QUEUE_THRESHHOLD) {
            addtoQueue(generateSevenBag());
        }
        
        CellTemplate[][] cells = blockQueue.getFirst().copyBlock();
        blockQueue.removeFirst();

        return cells;

    }

    public List<BlockRegistry.BlockTemplate> viewBlockQueue() {
        return Collections.unmodifiableList(blockQueue);
    }

    private void addtoQueue(List<BlockRegistry.BlockTemplate> blocks) {
        blockQueue.addAll(blocks);
    }

    private List<BlockRegistry.BlockTemplate> generateSevenBag() {
        List<BlockRegistry.BlockTemplate> sevenBag = new LinkedList<>(BlockRegistry.values());
        Collections.shuffle(sevenBag);

        return sevenBag;
    }
}

