package amata1219.cancel.drop;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CancelDrop extends JavaPlugin implements Listener, CommandExecutor {

    private List<String> list;

    public void onEnable() {
        saveDefaultConfig();
        list = getConfig().getStringList("Worlds");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("cdrop").setExecutor(this);
    }

    public void onDisable() {
        HandlerList.unregisterAll((JavaPlugin) this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        reloadConfig();
        list = getConfig().getStringList("Worlds");
        sender.sendMessage("コンフィグをリロードしました。");
        return true;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (list.contains(e.getPlayer().getWorld().getName())) e.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player) || !list.contains(e.getWhoClicked().getWorld().getName())) return;

        if (e.getClick() == ClickType.DROP || e.getClick() == ClickType.CONTROL_DROP) e.setCancelled(true);
    }

}
