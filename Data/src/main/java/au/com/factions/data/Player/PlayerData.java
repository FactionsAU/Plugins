package au.com.factions.data.Player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.UUID;

public class PlayerData {

    public enum DataType{
        CompletedCrash();
    }

    public int getData(UUID player, DataType d){
        return -1;
    }


//    public static String COMPLETED_CRASH = "CompletedCrash";

//    Objective getCrashObjective(){
//        Objective objective = Bukkit.getScoreboardManager().getMainScoreboard().getObjective(COMPLETED_CRASH);
//        if(objective == null){
//            objective = Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective(COMPLETED_CRASH,"dummy");
//        }
//        return objective;
//    }
//    getValueFor(Objective o, Player p){
//        Score score = o.getScore(p.getUniqueId().toString());
//        score.
//    }
}
