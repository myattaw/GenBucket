package com.reliableplugins.genbucket.hook;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.util.Message;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements PluginHook {

    private Economy economy;

    @Override
    public VaultHook setup(GenBucket plugin) {
        RegisteredServiceProvider registration = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (registration == null) {
            return null;
        }
        economy = (Economy) registration.getProvider();
        return this;
    }

    public boolean canAfford(Player player, int cost) {
        if (economy.withdrawPlayer(player, cost).transactionSuccess() && economy.has(player, cost)) {
            return true;
        }
        player.sendMessage(Message.NOT_ENOUGH_MONEY.getMessage());
        return false;
    }


    @Override
    public String[] getPlugins() {
        return new String[]{"Vault"};
    }

    @Override
    public String getName() {
        return "vault";
    }
}
