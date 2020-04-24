/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.chromaticarmor;

import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 *
 * @author wicden
 */
public class ChromaticArmorDescriptor {

    private String chromaType;
    private double[] params;

    public static ChromaticArmorDescriptor forItem(ItemStack stk) {
        if (!stk.hasItemMeta()) {
            return null;
        }
        LeatherArmorMeta meta = (LeatherArmorMeta) stk.getItemMeta();
        if (meta.hasLore()) {
            List<String> lore = stk.getItemMeta().getLore();
            for (int lineIt = 0; lineIt < lore.size() - 1; lineIt++) {
                if (lore.get(lineIt).contains(ChatColor.GREEN + "Chromatic Armor") && !lore.get(lineIt).contains("Not Configured")) {
                    try {
                        //TODO: Cache this - DONE
                        ChromaticArmorDescriptor desc = new ChromaticArmorDescriptor();
                        desc.chromaType = ChatColor.stripColor(lore.get(lineIt).toLowerCase().split(": ")[1].replace(" ", "_"));
                        desc.params = Utilities.parseParameters(ChatColor.stripColor(lore.get(lineIt + 1)));
                        return desc;
                    } catch (Exception e) {
                        lore.set(0, ChatColor.GREEN + "Chromatic Armor: " + ChatColor.GOLD + "Not Configured");
                        lore.set(1, ChatColor.GRAY + "Not Configured");
                        meta.setLore(lore);
                        meta.setLore(lore);
                        stk.setItemMeta(meta);
                    }
                }
            }
        }
        return null;
    }

    private ChromaticArmorDescriptor() {
    }

    public Color calculate(int counter) {
        int[] color;
        if (ArrayUtils.contains(Storage.FW_COLOR_FRIENDLY_NAMES, chromaType)) {
            color = Utilities.getThemedColor(params, counter);
        } else {
            color = Utilities.getColor(params, counter);
        }

        return Color.fromRGB(Utilities.clamp(color[0]), Utilities.clamp(color[1]), Utilities.clamp(color[2]));
    }

    public Color calculateVanished(int counter) {
        int[] color;
        int i = Storage.rnd.nextInt(30);
        if (Storage.rnd.nextInt(50) == 10) {
            color = new int[]{255, 255, 255};
        } else {
            color = new int[]{Storage.rnd.nextInt(20) + 75 + i, Storage.rnd.nextInt(20) + 75 + i, Storage.rnd.nextInt(20) + 75 + i};
        }
        return Color.fromRGB(Utilities.clamp(color[0]), Utilities.clamp(color[1]), Utilities.clamp(color[2]));
    }

}
