package au.com.factions.data.Spatial;

import au.com.factions.data.datainterface.FileReference;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import org.bukkit.Chunk;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * Holds a reference to a file, responsible for persisting the data in the chunk references,
 * and tracking whether writing is needed.
 */
public class RegionReference implements Iterable<Chunk>,FileReference {

    private final RegionCoord coord;
    private final Map<ChunkCoord,ChunkReference> chunks;
    public final File region;
    private boolean dirty = false;

    RegionReference(RegionCoord coord, Map<ChunkCoord, ChunkReference> set, File f){
        this.coord = coord;
        region = f;
        chunks = set;
    }

    public int getCount() {
        return chunks.size();
    }

    boolean isDirty(){
        return dirty;
    }

    void setDirty(){
        dirty = true;
    }

    public void add(Chunk c) {
        ChunkCoord cc = new ChunkCoord(c);
        if(chunks.containsKey(cc)) {
            throw new IllegalArgumentException("Chunk already loaded");
        }
        chunks.put(cc, new ChunkReference(c, false));
    }

    public void remove(Chunk c) {
        ChunkCoord cc = new ChunkCoord(c);
        if(!chunks.containsKey(cc)){
            throw new IllegalArgumentException("Chunk was not loaded");
        }
        chunks.remove(cc);
    }

    public boolean noReferences() {
        return getCount()==0;
    }

    @Override
    public Iterator<Chunk> iterator() {
        return Iterators.transform(chunks.values().iterator(), ref->ref.getChunk());
    }
}
//TODO
//read file
//File file = new File(storageDirectory,filenameFrom(c));
//System.out.println(filenameFrom(c) + " " + file.exists());
/*
    void save(Chunk c) {
        File file = new File(storageDirectory,filenameFrom(c));
        try
        {
            if (!file.exists())
                new FileOutputStream(file).close();
            file.setLastModified(System.currentTimeMillis());
        }
        catch (IOException e)
        {
        }
    }
 */