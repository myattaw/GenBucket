package com.reliableplugins.genbucket.hook.buildcheck;



import com.massivecraft.factions.*;
import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.BuildCheckHook;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FactionUUIDCheck extends BuildCheckHook {

    @Override
    public boolean canBuild(Player player, Location location) {
        return !FactionsBlockListener.playerCanBuildDestroyBlock(player, location, "build", true);
    }


    @Override
    public boolean chunkCheck(Player player, Chunk chunk, Location location) {

        FLocation loc = new FLocation(location);
        Faction otherFaction = Board.getInstance().getFactionAt(loc);
        FPlayer me = FPlayers.getInstance().getById(player.getUniqueId().toString());
        Faction myFaction = me.getFaction();





//check to see if the faction genning is the same faction the player is in
        if(myFaction.getId().equals(otherFaction.getId())){
            if(!GenBucket.getInstance().getConfig().getBoolean("allow-wilderness-gen")){
                if(myFaction.isWilderness()){
                    return false;
                }
            }

            return true;
        }else{
            //check to see if the faction is genning in wilderness
            if(!GenBucket.getInstance().getConfig().getBoolean("allow-wilderness-gen")){

                return !otherFaction.isWilderness();
            }
        }


        return false;
    }
}
