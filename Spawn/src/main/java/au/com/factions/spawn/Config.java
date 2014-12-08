package au.com.factions.spawn;

import org.bukkit.World;

import java.util.Optional;
import java.util.UUID;

public class Config {

    private final Plugin plugin;
    private Optional<UUID> spawnWorld;
    private Optional<UUID> landWorld;
    private int yMin = -1;

    public Config(Plugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        load();

    }

    void load() {
        yMin = loadInteger("YMin", 50);
        spawnWorld = loadWorldUUID("SpawnWorld", "Spawn");
        landWorld = loadWorldUUID("LandWorld", "world");

    }

    void setUUID(String node, UUID uid){
        this.plugin.getConfig().set("node", uid.toString());
        load();
    }

    void refresh(){
        plugin.reloadConfig();
    }

    void save(){
        this.plugin.saveConfig();
    }

    private Optional<UUID> loadWorldUUID(String node, String def) {
        Optional<UUID> uid = loadUUID(node);
        if(uid.isPresent()){
            return uid;
        }else{
            plugin.log.warning(node + " UUID not set in config.");
            World defaultedSpawn = plugin.getServer().getWorld(def);
            if(defaultedSpawn == null)
                plugin.log.warning("World "+def+" not found no sane default.");
            else
                return Optional.of(defaultedSpawn.getUID());
        }
        return Optional.empty();
    }

    int loadInteger(String node,int def){
        int out = -1;
        out = plugin.getConfig().getInt(node,def);
        plugin.log.config("Config["+node+"] : "+out);
        return out;
    }

    Optional<UUID> loadUUID(String node){
        Object o = plugin.getConfig().get(node);
        if(o!=null){
            plugin.log.config("Config["+node+"] : "+o.toString());
            try{
                return Optional.of(UUID.fromString(o.toString()));
            }catch(Exception e){
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public Optional<UUID> getSpawnWorld() {
        return spawnWorld;
    }

    public int getYMin() {
        return yMin;
    }

    public Optional<UUID> getLandWorld() {
        return landWorld;
    }
}
