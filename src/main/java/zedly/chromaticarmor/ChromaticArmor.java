/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.chromaticarmor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Dennis
 */
public class ChromaticArmor extends JavaPlugin {

    public static ChromaticArmor instance;

    @Override
    public void onEnable() {
        ChromaticArmor.instance = this;

        getServer().getPluginManager().registerEvents(new Watcher(), this);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TaskChromaticArmor(), 0, 1);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TaskItemTrails(), 0, 20);
        
        String col = ChatColor.GOLD + "Colors: " + ChatColor.YELLOW + "";
        for (int i = 0; i < Storage.FW_COLOR_FRIENDLY_NAMES.length; i++) {
            col += Storage.FW_COLOR_FRIENDLY_NAMES[i];
            if (i != Storage.FW_COLOR_FRIENDLY_NAMES.length - 1) {
                col += ChatColor.GOLD + ", " + ChatColor.YELLOW + "";
            }
        }
        Storage.colorString = col;
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandlabel, String[] args) {
        CommandProcessor.run(sender, command, commandlabel, args);
        return true;
    }
}
