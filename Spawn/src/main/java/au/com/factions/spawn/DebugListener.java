package au.com.factions.spawn;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

/**
 * Created by Ryan on 3/12/2014.
 */
public class DebugListener implements Listener {
    @EventHandler
    public void on(PlayerChangedWorldEvent e){
        on(e, e.getPlayer());
    }
    @EventHandler
    public void on(PlayerJoinEvent e){
        on(e, e.getPlayer());
    }
    @EventHandler
    public void on(PlayerRespawnEvent e){
        on(e,e.getPlayer());
    }
    @EventHandler
    public void on(PlayerLoginEvent e){
        on(e,e.getPlayer());
    }
    @EventHandler
    public void on(PlayerSpawnLocationEvent e){
        on(e,e.getPlayer());
    }
    @EventHandler
    public void on(PlayerToggleFlightEvent e){
        on(e, e.getPlayer());
    }

    private void on(Event e, Player p){
        Bukkit.getLogger().info(e.getEventName());
        p.setAllowFlight(false);
        p.setFlying(true);
    }
}
