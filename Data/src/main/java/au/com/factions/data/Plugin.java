package au.com.factions.data;

import au.com.factions.data.Spatial.ChunkListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Ryan on 2/12/2014.
 */

public class Plugin extends JavaPlugin{
    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ChunkListener cl = new ChunkListener(this);
        Bukkit.getPluginManager().registerEvents(cl,this);
    }
}
