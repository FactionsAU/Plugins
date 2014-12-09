package au.com.factions.data.Spatial;

import au.com.factions.data.datainterface.Identity;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class ChunkCoord extends PairI implements Identity{
    private final UUID world;

    ChunkCoord(World w, int x, int z) {
        super(x, z);
        this.world = w.getUID();
    }

    ChunkCoord(Location l){
        this(l.getChunk());
    }

    ChunkCoord(Chunk c){
        this(c.getWorld(),c.getX(),c.getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChunkCoord that = (ChunkCoord) o;

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
