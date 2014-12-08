package au.com.factions.data.Player;

import au.com.factions.data.Plugin;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldSaveEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PlayerCache implements Listener{

    private final Plugin plugin;
    private final File storageDirectory;

    PlayerCache(Plugin p){
        this.plugin = p;
        storageDirectory = new File(p.getDataFolder(),"players");
        if (!storageDirectory.isDirectory()){
            storageDirectory.mkdirs();
        }
    }

    private Set<Player> loadedPlayer = new HashSet();

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerJoin(PlayerJoinEvent e){
        loadedPlayer.add(e.getPlayer());
        //read file
        File file = new File(storageDirectory,filenameFrom(e.getPlayer()));
        System.out.println(filenameFrom(e.getPlayer()) + " " + file.exists());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerLeave(PlayerQuitEvent e){
        onPlayerSave(e.getPlayer());
        loadedPlayer.remove(e.getPlayer());
    }

    private void onPlayerSave(Player p) {
        //write file
        File file = new File(storageDirectory,filenameFrom(p));
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

    private String filenameFrom(Player c){
        return c.getUniqueId().toString()+".json";
    }
}
