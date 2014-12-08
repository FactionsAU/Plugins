package au.com.factions.spawn;

import au.com.factions.data.Player.PlayerData;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Plugin extends JavaPlugin{

    Logger log;
    PluginDescriptionFile pdf;
    Config config;
    private SpawnWorldListener spawnWorldListener;
    PlayerData data;

    @Override
    public void onEnable() {
        super.onEnable();
        log = this.getLogger();
        pdf = this.getDescription();
        data = this.getServer().getServicesManager().load(PlayerData.class);
        config = new Config(this);
        this.getLogger().log(Level.INFO, pdf.getFullName() + " : Enabled");

        if(SpawnWorldListener.canListen(config)){
            spawnWorldListener = new SpawnWorldListener(config, log, data);
            //this.getServer().getPluginManager().registerEvents(spawnWorldListener, this);
        }
        this.getServer().getPluginManager().registerEvents(new DebugListener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.getLogger().log(Level.INFO, pdf.getFullName() + " : Disabled");
        log = null;
        pdf = null;
        config = null;
        data = null;
        PlayerMoveEvent.getHandlerList().unregister(spawnWorldListener);
        EntityDamageEvent.getHandlerList().unregister(spawnWorldListener);
        spawnWorldListener = null;
    }

}
