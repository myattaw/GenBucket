package com.reliableplugins.genbucket.hook.buildcheck;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.reliableplugins.genbucket.hook.BuildCheckHook;
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


}
