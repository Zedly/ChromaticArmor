package zedly.chromaticarmor;

import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import static org.bukkit.event.inventory.InventoryType.SlotType.RESULT;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

public class Watcher implements Listener {

    @EventHandler // Vanish activated
    public boolean onCommand(PlayerCommandPreprocessEvent evt) {
        if (evt.getMessage().equals("/vanish") && evt.getPlayer().hasPermission("vanish.vanish")) {
            if (Storage.vanishedPlayers.contains(evt.getPlayer())) {
                Storage.vanishedPlayers.remove(evt.getPlayer());
            } else {
                Storage.vanishedPlayers.add(evt.getPlayer());
            }
        }
        return true;
    }

    @EventHandler // Player teleports, linked to tpboom
    public boolean onTeleport(PlayerTeleportEvent evt) {
        if (evt.getTo().getWorld() == evt.getFrom().getWorld() && evt.getTo().distance(evt.getFrom()) < 30) {
            return true;
        }
        if (Storage.vanishedPlayers.contains(evt.getPlayer())) {
            return true;
        }
        Location loc = evt.getFrom().clone();
        Utilities.explodeFromArmor(evt.getPlayer().getInventory().getBoots(), loc.add(new Vector(0, 0.38, 0)).clone());
        Utilities.explodeFromArmor(evt.getPlayer().getInventory().getLeggings(), loc.add(new Vector(0, 0.45, 0)).clone());
        Utilities.explodeFromArmor(evt.getPlayer().getInventory().getChestplate(), loc.add(new Vector(0, 0.42, 0)).clone());
        Utilities.explodeFromArmor(evt.getPlayer().getInventory().getHelmet(), loc.add(new Vector(0, 0.5, 0)).clone());
        return true;
    }

    @EventHandler // Stops Easter eggs from being pickef up
    public boolean onItemPickup(InventoryPickupItemEvent evt) {
        Item item = evt.getItem();
        if (TaskItemTrails.trailItems.contains(evt.getItem())) {
            TaskItemTrails.trailItems.remove(evt.getItem());
            evt.getItem().remove();
            evt.setCancelled(true);
        } else if (item.getItemStack().hasItemMeta() && item.getItemStack().getItemMeta().hasDisplayName()
                && item.getItemStack().getItemMeta().getDisplayName().equals(ChatColor.MAGIC + "Transient")) {
            // Kill items left after a crash
            evt.setCancelled(true);
            item.remove();
        }
        return true;
    }

    @EventHandler // Links to lore crafting (craftArrow/craftChromo)
    public void onCraft(InventoryDragEvent evt) {
        if (!evt.getInventory().getType().equals(InventoryType.WORKBENCH) || evt.isCancelled()) {
            return;
        }
        //CraftingGUI.craftChromo(evt.getView(), evt.getInventorySlots(), (Player) evt.getWhoClicked(), false);
    }

    @EventHandler // Links to lore crafting (craftArrow/craftChromo)
    public void onCraft(final InventoryClickEvent evt) {
        if ((evt.getInventory().getType() != InventoryType.WORKBENCH && evt.getInventory().getType() != InventoryType.CRAFTING) || evt.isCancelled()) {
            return;
        }
        boolean canCraft = false;
        if (evt.getSlotType().equals(RESULT)) {
            canCraft = true;
        }
        //Set<Integer> slot = new HashSet<>();
        //slot.add(evt.getSlot());

        //CraftingGUI.craftChromo(evt.getView(), slot, (Player) evt.getWhoClicked(), canCraft);
    }
}
