package au.com.factions.data.Spatial;

import org.bukkit.Chunk;

/**
 * Container class for a chunk to get around the fact that we can not hold custom data inside a chunk.
 * TODO: May possibly be the container for chunk data at some point, or should regions own the data since it's per region?
 */
public class ChunkReference {

    private final Chunk chunk;
    //Whether the data associated with this chunk has been modified, without being persisted to disk
    private boolean isDirty;


    ChunkReference(Chunk chunk, boolean isDirty) {
        this.chunk = chunk;
        this.isDirty = isDirty;
    }

    void makeDirty(){
        isDirty = true;
    }

    boolean isDirty() {
        return isDirty;
    }

    public Chunk getChunk() {
        return chunk;
    }
}
