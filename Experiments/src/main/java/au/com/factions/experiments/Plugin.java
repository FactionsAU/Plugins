package au.com.factions.experiments;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Ryan on 2/12/2014.
 */

public class Plugin extends JavaPlugin{
    @Override
    public void onEnable() {
        // This will throw a NullPointerException if you don't have the command defined in your plugin.yml file!
        this.getCommand("packet").setExecutor(new MyPluginCommandExecutor(this));
    }
}
