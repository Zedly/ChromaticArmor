package zedly.chromaticarmor;

import java.util.*;
import org.bukkit.*;
import static org.bukkit.Material.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes {
    public static void chromo() {
        for (Material m : new Material[]{LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS}) {
            ItemStack armor = new ItemStack(m);
            ItemMeta meta = armor.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add(0, ChatColor.GREEN + "Chromatic Armor: " + ChatColor.GOLD + "Not Configured");
            lore.add(1, ChatColor.GRAY + "Not Configured");
            meta.setLore(lore);
            armor.setItemMeta(meta);
            ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(ChromaticArmor.instance, "chromatic_" + m.toString().toLowerCase()), new ItemStack(armor));
            recipe.addIngredient(m).addIngredient(NETHER_STAR);
            Bukkit.getServer().addRecipe(recipe);
        }
    }
}
