package au.com.factions.data.Spatial;

import au.com.factions.data.datainterface.Identity;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

/**
 * Created by Ryan on 6/12/2014.
 */
public class RegionCoord extends PairI implements Identity{

    private final UUID world;

    RegionCoord(World world, int x, int z) {
        super(x, z);
        this.world = world.getUID();
    }

    RegionCoord(Location l){
        this(l.getChunk());
    }

    RegionCoord(Chunk c){
        this(c.getWorld(), floor32(c.getX()), floor32(c.getZ()));
    }

    private static int floor32(int i){
        return (int) Math.floor(i/32.0);
    }

    public UUID getWorld() {
        return world;
    }

    public int getX() {
        return super.x;
    }

    public int getZ() {
        return super.z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionCoord that = (RegionCoord) o;

        if (x != that.x) return false;
        if (z != that.z) return false;
        if (!world.equals(that.world)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = world.hashCode();
        result = 31 * result + x;
        result = 31 * result + z;
        return result;
    }
}
