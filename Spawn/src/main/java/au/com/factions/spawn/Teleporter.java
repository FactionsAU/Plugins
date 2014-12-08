package au.com.factions.spawn;

import java.util.UUID;
import java.util.logging.Logger;

public class Teleporter {
    final UUID landWorld;
    final Logger log;

    public Teleporter(UUID landWorld, Logger log) {
        this.landWorld = landWorld;
        this.log = log;
    }
}
