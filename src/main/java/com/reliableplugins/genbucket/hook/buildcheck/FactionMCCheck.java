package com.reliableplugins.genbucket.hook.buildcheck;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.BuildCheckHook;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author : Michael
 * @since : 11/1/2020, Sunday
 **/
public class FactionMCCheck extends BuildCheckHook {

    @Override
    public boolean canBuild(Player player, Location location) {
        MPlayer fPlayer = MPlayer.get(player);

        return BoardColl.get().getFactionAt(PS.valueOf(player.getLocation())).equals(fPlayer.getFaction());
    }

    @Override
    public boolean cannotBuildInChunk(Player player, Chunk chunk, Location location) {
        Faction otherFaction = BoardColl.get().getFactionAt(PS.valueOf(chunk));
        MPlayer me = MPlayer.get(player);
        Faction myFaction = me.getFaction();


        if(myFaction.getId().equals(otherFaction.getId())){
            //check to see if the faction genning is the same faction the player is in
            if(!GenBucket.getInstance().getConfig().getBoolean("allow-wilderness-gen")){
                //check to see if the faction is genning in wilderness
                if(otherFaction.getName().equals(GenBucket.getInstance().getConfig().getString("default-faction-name"))){
                    return false;
                }
            }

            return true;
        }else{
            //check to see if the faction is genning in wilderness
            if(!GenBucket.getInstance().getConfig().getBoolean("allow-wilderness-gen")){
                return !otherFaction.getName().equals(GenBucket.getInstance().getConfig().getString("default-faction-name"));
            }
        }

        return false;
    }
}
