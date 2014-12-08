package au.com.factions.data.Spatial;

import au.com.factions.data.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldSaveEvent;

import java.io.File;
import java.util.HashMap;

public class ChunkListener implements Listener{

    private final Plugin plugin;
    private final File storageDirectory;
    private final LoadedChunks loadedChunks ;

    /**
     * Only construct this object after the worlds are loaded
     * **/
    ChunkListener(Plugin p){
        this.plugin = p;
        storageDirectory = new File(p.getDataFolder(),"worlds");
        if (!storageDirectory.isDirectory()){
            storageDirectory.mkdirs();
        }
        loadedChunks = new LoadedChunks(new HashMap<RegionCoord, RegionReference>(), storageDirectory);
        onInit();
    }



    //on save, trigger saving for all known loaded chunks in datastore,
    // this is possibly different to all loaded chunks on server
    // Not sure if this is a good thing, or if the world save should be delegated to the LoadedChunks itself.
    @EventHandler(priority = EventPriority.MONITOR)
    private void onWorldSave(WorldSaveEvent e){
        for(Chunk c:loadedChunks){
            onChunkSave(c);
        }
    }

    //on Init, load all loaded chunks.
    private void onInit(){
        for(World w:Bukkit.getWorlds()){
            for(Chunk c:w.getLoadedChunks()){
                onChunkLoad(c);
            }
        }
    }

    //Does flush mean save, or discard? on disable?
    //  A: Chunk is dirty, but not yet written to disk
    //      Saving, possible to save incorrect data if the chunk is never saved.
    //      Not saving, potential data loss.
    //      Hard to avoid but shouldn't happen unless a read only chunk concept is introduced into API or server crash.
    //  B: Chunk is clean, no worries we are up to date.
    //  C: chunk is dirty, and already unloaded + saved.
    //      Saving, is the correct thing to do.
    //      Not saving, potential DeSync. this should be avoidable
    protected void onDisable(){
        loadedChunks.flush();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onChunkLoad(ChunkLoadEvent e){
        onChunkLoad(e.getChunk());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onChunkUnload(ChunkUnloadEvent e){
        //TODO: add a check to see if chunk was saved if the API ever appears...
        onChunkSave(e.getChunk());
        loadedChunks.remove(e.getChunk());
    }

    private void onChunkLoad(Chunk c){
        loadedChunks.add(c);
    }

    private void onChunkSave(Chunk c) {
        //write file
        loadedChunks.save(c);
    }


}
