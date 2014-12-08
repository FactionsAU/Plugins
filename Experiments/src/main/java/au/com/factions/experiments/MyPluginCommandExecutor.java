package au.com.factions.experiments;


import net.minecraft.server.v1_8_R1.EnumDifficulty;
import net.minecraft.server.v1_8_R1.EnumGamemode;
import net.minecraft.server.v1_8_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R1.WorldType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MyPluginCommandExecutor implements CommandExecutor {
    private final Plugin plugin;

    public MyPluginCommandExecutor(Plugin plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // implementation exactly as before...
        //float f = Float.parseFloat(args[1]);
        //int i = Integer.parseInt(args[0]);
        CraftPlayer player = ((CraftPlayer) sender);
        CraftWorld world = (CraftWorld) player.getWorld();
        Location location = player.getLocation();

        PacketPlayOutRespawn packet = new PacketPlayOutRespawn(World.Environment.valueOf(args[0]).getId(), EnumDifficulty.getById(world.getDifficulty().getValue()), WorldType.NORMAL, EnumGamemode.getById(player.getGameMode().getValue()));

        player.getHandle().playerConnection.sendPacket(packet);

        int viewDistance = Bukkit.getServer().getViewDistance();

        int xMin = location.getChunk().getX() - viewDistance;
        int xMax = location.getChunk().getX() + viewDistance;
        int zMin = location.getChunk().getZ() - viewDistance;
        int zMax = location.getChunk().getZ() + viewDistance;

        for (int x = xMin; x < xMax; ++x){
            for (int z = zMin; z < zMax; ++z){
                world.refreshChunk(x, z);
            }
        }

        player.updateInventory();

        return true;
    }
}