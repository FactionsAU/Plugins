package au.com.factions.spawn;

import au.com.factions.data.Player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

final class SpawnWorldListener implements Listener {

    final UUID spawnWorld;
    final int yMin;
    final Logger log;
    final PlayerData data;

    SpawnWorldListener(Config config, Logger log, PlayerData data) {
        Optional<UUID> temp = config.getSpawnWorld();
        if(temp.isPresent()) spawnWorld = temp.get();
        else{
            throw new IllegalArgumentException();
        }
        this.yMin = config.getYMin();
        this.log = log;
        this.data = data;
    }

    /**
     * Detect when the players are falling out of the space station.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public final void onFall(PlayerMoveEvent e){
        UUID UID = e.getTo().getWorld().getUID();
        if(!UID.equals(spawnWorld)) return;
        if(e.getTo().getY() > yMin) return;
        //TODO: call the callback.
        log.log(Level.FINE, "Fall Detected");
    }

    /**
     * Prevent damage to players in the spawn world, in case they fall out of the world.
     **/
    @EventHandler()
    public final void onDamage(EntityDamageEvent e) {
        //Only affect players being damaged.
        if(e.getEntityType() != EntityType.PLAYER) {
            return;
        }
        if(e.getEntity() instanceof Player) {
            //if Player isn't in the spawn do not cancel damage.
            if (!((Player) e.getEntity()).getWorld().getUID().equals(spawnWorld)) {
                return;
            }
        }
        //Cancel damage of players falling out of the world.
        if(e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            e.setCancelled(true);
            //TODO: teleport the player back to spawn.
        }


        if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
           //If player is allowed to fly, cancel the fall damage.
            if(((Player) e).getAllowFlight()) {
               e.setCancelled(true);
           }
        }
    }

    @EventHandler()
    public final void onSpawn(PlayerSpawnLocationEvent e){
        Location loc = e.getSpawnLocation();
        Player p = e.getPlayer();
        if(p.hasPlayedBefore()) {
            if(data.getData(p.getUniqueId(), PlayerData.DataType.CompletedCrash) == 1){
                return;
            } else{
                e.setSpawnLocation(Bukkit.getWorld(spawnWorld).getSpawnLocation());
            }
        }

    }

    /**
     * Whether the SpawnMoveListener is valid with a given configuration.
     * **/
    public static boolean canListen(Config config) {
        return config.getSpawnWorld().isPresent() && config.getLandWorld().isPresent();
    }
}
