package au.com.factions.data.Spatial;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import org.bukkit.Chunk;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

/**
 * Collection of regions/chunks responsible for the management of RegionReferences & storage location.
 */
public class RegionCache implements Iterable<Chunk>{

    private final Map<RegionCoord, RegionReference> regions;
    private final File storageDirectory;

    /**
     * Responsible for the management of region files
     * @param regions
     * @param storageDirectory
     */
    RegionCache(Map<RegionCoord, RegionReference> regions, File storageDirectory) {
        this.regions = regions;
        this.storageDirectory = storageDirectory;
    }

    /**
     * Adds a chunk to a RegionReference, loading the regionfile if necessary.
     * @param c
     */
    void add(Chunk c){
        RegionCoord rc = new RegionCoord(c);
        RegionReference r = regions.get(rc);
        if(r == null){
            r = new RegionReference(rc, new HashMap<ChunkCoord,ChunkReference>(), fileFor(rc));
            regions.put(rc, r);
        }
        r.add(c);

    }

    /**
     * Removes a chunk from a RegionReference, unloading the regionfile if necessary.
     * @param c
     */
    void remove(Chunk c){
        RegionCoord coord = new RegionCoord(c);
        RegionReference r = regions.get(coord);
        if(r != null){
            r.remove(c);
            if(r.noReferences()){
                regions.remove(coord);
            }
        } else {
            //There shouldn't ever be a region that was loaded that wasn't in our map...
            throw new AssertionError("Attempted to remove an already unloaded chunk");
        }
    }

    void save(Chunk c){
        //TODO: Do we save on all chunks that need updating? or only when the world save event throws?
        //Do we add saved files to a queue? then save on flush?
    }

    void flush() {
        //TODO: flush? what does flush even mean? what does save mean?
        //I suspect we should use save to mark regions?chunks? as dirty, and use flush to make changes permanent.
    }

    private File fileFor(RegionCoord r){
        return new File(storageDirectory,filenameFrom(r));
    }

    private String filenameFrom(RegionCoord r){
        return String.join("_",r.getWorld().toString(),"x"+r.getX(),"z"+r.getZ()) + ".json";
    }

    @Override
    public Iterator<Chunk> iterator() {
        final List<Iterator<Chunk>> its = new LinkedList<Iterator<Chunk>>();
        for(RegionReference ref:regions.values()){
            its.add(ref.iterator());
        }
        return Iterators.concat(its.iterator());
    }
}
