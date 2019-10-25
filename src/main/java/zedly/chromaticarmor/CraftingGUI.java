package zedly.chromaticarmor;

import java.util.*;
import org.bukkit.*;
import static org.bukkit.Material.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class CraftingGUI {

    private static final Material[] accepted = new Material[]{GUNPOWDER, SUGAR, REDSTONE, GLOWSTONE_DUST, RED_DYE, ORANGE_DYE, YELLOW_DYE, LIME_DYE,
        GREEN_DYE, LIGHT_BLUE_DYE, BLUE_DYE, LIGHT_GRAY_DYE, PINK_DYE, MAGENTA_DYE,
        PURPLE_DYE, CYAN_DYE, GRAY_DYE, BROWN_DYE, WHITE_DYE, BLACK_DYE, PRISMARINE_SHARD, FLINT, QUARTZ, WATER_BUCKET, SPONGE};

    // Chomatic Armor Crafting *DISABLED*
    /*
    public static void craftChromo(final InventoryView view, final Set<Integer> slots, final Player p,
            final boolean canCraft) {
        if (!Storage.recipes.get("Chromatic Armor")) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(ChromaticArmor.instance, new Runnable() {
            public void run() {
                int offset = 0;
                int speed = 0;
                String type = "not configured";
                int color = 0;
                boolean newArmor = false;
                boolean hasArmor = false;
                ItemStack armor = new ItemStack(AIR);
                int sponge = 1;
                int spongeAmount = 0;
                int bucket = 2;
                boolean washed = false;

                ArrayList<Material> mats = new ArrayList<>();
                String[] args = new String[]{};
                for (int i = 1; i < 10; i++) {
                    if (!view.getItem(i).getType().equals(AIR)) {
                        if (view.getItem(i).getItemMeta().hasLore()) {
                            if (ArrayUtils.contains(Storage.leather, view.getItem(i).getType())) {
                                if (view.getItem(i).getItemMeta().getLore().get(0).contains(ChatColor.GREEN + "Chromatic Armor")) {
                                    if (view.getItem(i).getItemMeta().getLore().size() > 1) {
                                        if (hasArmor == true) {
                                            return;
                                        }
                                        hasArmor = true;
                                        args = ChatColor.stripColor(view.getItem(i).getItemMeta().getLore().get(1)).split(":");
                                        type = ChatColor.stripColor(view.getItem(i).getItemMeta().getLore().get(0)).split(": ")[1].toLowerCase();
                                        armor = view.getItem(i);
                                        if (view.getItem(i).getItemMeta().getLore().get(0).contains("Not Configured")) {
                                            newArmor = true;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (ArrayUtils.contains(accepted, view.getItem(i).getType())) {
                                if (view.getItem(i).getType().equals(WATER_BUCKET)) {
                                    bucket = i;
                                } else if (view.getItem(i).getType().equals(SPONGE)) {
                                    sponge = i;
                                    spongeAmount = view.getItem(i).getAmount();
                                }
                                mats.add(view.getItem(i).getType());
                            } else {
                                return;
                            }
                        }
                    }
                }
                if (hasArmor && newArmor) {
                    speed += 10;
                    if (mats.size() == 4) {
                        int notInk = 0;
                        boolean sameI = true;
                        for (int i = 0; i < 4; i++) {
                            if (!(mats.get(i).equals(mats.get(0)) && Utilities.isMaterialDye(mats.get(i)))) {
                                sameI = false;
                            }
                            if (!Utilities.isMaterialDye(mats.get(i))) {
                                notInk++;
                            }
                        }
                        if (sameI) {
                            type = "color";
                            color = ArrayUtils.indexOf(Storage.FW_COLOR_ICON_MATS, mats.get(0));
                        } else {
                            if (notInk == 1) {
                                if (mats.contains(PRISMARINE_SHARD)) {
                                    mats.remove(mats.lastIndexOf(PRISMARINE_SHARD));
                                    if (mats.contains(RED_DYE) && mats.contains(GREEN_DYE) && mats.contains(BLUE_DYE)) {
                                        type = "normal";
                                    }
                                } else if (mats.contains(GLOWSTONE_DUST)) {
                                    mats.remove(mats.lastIndexOf(GLOWSTONE_DUST));
                                    if (mats.contains(RED_DYE) && mats.contains(GREEN_DYE) && mats.contains(BLUE_DYE)) {
                                        type = "bright";
                                    }
                                } else if (mats.contains(QUARTZ)) {
                                    mats.remove(mats.lastIndexOf(QUARTZ));
                                    if (mats.contains(RED_DYE) && mats.contains(GREEN_DYE) && mats.contains(BLUE_DYE)) {
                                        type = "pastel";
                                    }
                                } else if (mats.contains(FLINT)) {
                                    mats.remove(mats.lastIndexOf(FLINT));
                                    if (mats.contains(WHITE_DYE) && mats.contains(LIGHT_GRAY_DYE) && mats.contains(GRAY_DYE)) {
                                        type = "grayscale";
                                    }
                                }
                            }
                        }
                    }
                } else if (hasArmor) {
                    if (mats.size() == 2 && mats.contains(WATER_BUCKET) && mats.contains(SPONGE)) {
                        washed = true;
                        type = "not configured";
                    } else {
                        if (!type.equals("not configured")) {
                            try {
                                if (ArrayUtils.contains(Storage.FW_COLOR_FRIENDLY_NAMES, type.replace(" ", "_"))) {
                                    color = ArrayUtils.indexOf(Storage.FW_COLOR_FRIENDLY_NAMES, type.replace(" ", "_"));
                                    speed = Integer.parseInt(args[0]);
                                    offset = Integer.parseInt(args[9]);
                                    type = "color";
                                } else {
                                    speed = Integer.parseInt(args[8]);
                                    offset = Integer.parseInt(args[7]);
                                }
                            } catch (NumberFormatException e) {
                            }
                            for (Material mat : mats) {
                                switch (mat) {
                                    case GUNPOWDER:
                                        speed--;
                                        break;
                                    case SUGAR:
                                        speed++;
                                        break;
                                    case REDSTONE:
                                        offset -= 5;
                                        break;
                                    case GLOWSTONE_DUST:
                                        offset += 5;
                                        break;
                                }
                            }
                        }
                    }
                }
                if (hasArmor) {
                    String config;
                    if (!type.equals("not configured")) {
                        switch (type) {
                            case "color":
                                args = new String[]{type, Storage.FW_COLOR_FRIENDLY_NAMES[color], offset + "", speed + ""};
                                type = Storage.FW_COLOR_FRIENDLY_NAMES[color];
                                break;
                            case "normal":
                            case "grayscale":
                                args = new String[]{type, 127 + "", offset + "", speed + ""};
                                break;
                            default:
                                args = new String[]{type, offset + "", speed + ""};
                                break;
                        }
                        config = CommandProcessor.getConfig(p, args);
                    } else {
                        config = ChatColor.GRAY + "Not Configured";
                    }
                    ItemStack finalArmor = new ItemStack(armor.getType());
                    List<String> lore = new ArrayList<>();
                    ItemMeta meta = finalArmor.getItemMeta();
                    lore.add(ChatColor.GREEN + "Chromatic Armor: " + ChatColor.GOLD + WordUtils.capitalize(type.replace("_", " ")));
                    lore.add(config);
                    if (armor.getItemMeta().hasLore()) {
                        List<String> oldLore = armor.getItemMeta().getLore();
                        if (oldLore.get(0).contains(ChatColor.GREEN + "Chromatic Armor")) {
                            if (oldLore.size() > 2) {
                                for (int i = 2; i < oldLore.size(); i++) {
                                    lore.add(oldLore.get(i));
                                }
                            }
                        } else {
                            lore.addAll(oldLore);
                        }
                    }
                    meta.setLore(lore);
                    finalArmor.setItemMeta(meta);

                    view.setItem(0, finalArmor);
                    if (slots.size() == 1 && slots.contains(0) && canCraft) {
                        for (int i1 = 1; i1 < 10; i1++) {
                            ItemStack stk = view.getItem(i1);
                            stk.setAmount(stk.getAmount() - 1);
                            if (stk.getAmount() < 1) {
                                stk = null;
                            }
                            view.setItem(i1, stk);
                        }
                        if (washed) {
                            view.setItem(sponge, new ItemStack(SPONGE, 1));
                            view.setItem(bucket, new ItemStack(BUCKET));
                        }
                    }
                }
                p.updateInventory();
            }
        }, 0);
    }
    */
    // Sponge loss // Full Inventory Loss

    public static void remove(InventoryView view, List<ItemStack> realInv, int amount) {
        for (int i = 0; i < 9; i++) {
            ItemStack stk = realInv.get(i).clone();
            stk.setAmount(realInv.get(i).getAmount() - amount);
            if (stk.getAmount() < 1) {
                stk = new ItemStack(AIR, 0);
            }
            view.setItem(i + 1, stk);
            realInv.set(i, stk);
        }
    }

    public static void removeItem(Player player, ItemStack stk, int amount) {
        Inventory inv = player.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null && inv.getItem(i).getType() == stk.getType()) {
                if (inv.getItem(i).getItemMeta().equals(stk.getItemMeta())) {
                    if (inv.getItem(i).getAmount() > amount) {
                        int res = inv.getItem(i).getAmount() - amount;
                        ItemStack rest = inv.getItem(i);
                        rest.setAmount(res);
                        inv.setItem(i, rest);
                        return;
                    } else {
                        amount -= inv.getItem(i).getAmount();
                        inv.setItem(i, null);
                    }
                }
            }
        }
    }

}
