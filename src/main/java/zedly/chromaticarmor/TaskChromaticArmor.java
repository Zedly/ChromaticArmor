/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zedly.chromaticarmor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.Material.LEATHER_BOOTS;
import static org.bukkit.Material.LEATHER_CHESTPLATE;
import static org.bukkit.Material.LEATHER_HELMET;
import static org.bukkit.Material.LEATHER_LEGGINGS;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 *
 * @author Dennis
 */
public class TaskChromaticArmor implements Runnable {

    private static final HighFrequencyRunnableCache CACHE = new HighFrequencyRunnableCache(TaskChromaticArmor::feedCache, 20);
    private static final HashMap<Integer, Integer> chromaticColorProgress = new HashMap<>();
    private static final ArrayList<Function<Player, ItemStack>> ARMOR_SLOT_GETTERS = new ArrayList<>();
    private static final ArrayList<BiConsumer<Player, ItemStack>> ARMOR_SLOT_SETTERS = new ArrayList<>();
    private static final Material[] ARMOR_TYPE_IDS = {Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS};

    private static void feedCache(Player player, Consumer<Supplier<Boolean>> consoomer) {
        if (!player.isOnline()) {
            return;
        }
        for (int itemIt = 0; itemIt < 4; itemIt++) {
            ItemStack stk = ARMOR_SLOT_GETTERS.get(itemIt).apply(player);
            if (stk != null && stk.getType() == ARMOR_TYPE_IDS[itemIt] && ChromaticArmorDescriptor.forItem(stk) != null) {
                int chromaSlot = itemIt;
                consoomer.accept(() -> {
                    return tickChroma(player, chromaSlot);
                });
            }
        }
    }

    private static boolean tickChroma(Player player, int chromaSlot) {
        if (!player.isOnline()) {
            return false;
        }
        ItemStack chromaItem = ARMOR_SLOT_GETTERS.get(chromaSlot).apply(player);
        if (chromaItem == null || chromaItem.getType() != ARMOR_TYPE_IDS[chromaSlot]) {
            return false;
        }

        ChromaticArmorDescriptor desc = ChromaticArmorDescriptor.forItem(chromaItem);

        if (desc == null) {
            return false;
        }

        int entityId = player.getEntityId();
        int counter = 0;
        if (chromaticColorProgress.containsKey(entityId)) {
            counter = chromaticColorProgress.get(entityId);
        }
        chromaticColorProgress.put(entityId, counter + 1);

        Color color;
        if (Storage.vanishedPlayers.contains(player)) {
            color = desc.calculateVanished(counter);
        } else {
            color = desc.calculate(counter);
            if (Storage.rnd.nextBoolean() && (player.isFlying() || player.isSprinting())) {
                Location loc = player.getLocation().clone();
                loc.subtract(player.getLocation().getDirection());
                float heightAboveFeetPos = 0;
                switch (chromaItem.getType()) {
                    case LEATHER_HELMET:
                        heightAboveFeetPos = 1.75f;
                        break;
                    case LEATHER_CHESTPLATE:
                        heightAboveFeetPos = 1.25f;
                        break;
                    case LEATHER_LEGGINGS:
                        heightAboveFeetPos = .83f;
                        break;
                    case LEATHER_BOOTS:
                        heightAboveFeetPos = .38f;
                        break;
                }
                loc.setY(loc.getY() + heightAboveFeetPos + (Storage.rnd.nextFloat() / 4) * (Storage.rnd.nextInt(2) * 2 - 1));
                loc.setX(loc.getX() + (Storage.rnd.nextFloat() / 4) * (Storage.rnd.nextInt(2) * 2 - 1));
                loc.setZ(loc.getZ() + (Storage.rnd.nextFloat() / 4) * (Storage.rnd.nextInt(2) * 2 - 1));
                Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 32, 0, 0, 0, 0, dustOptions, true);
            }
        }

        LeatherArmorMeta meta = (LeatherArmorMeta) chromaItem.getItemMeta();
        meta.setUnbreakable(true);
        meta.setColor(color);
        chromaItem.setItemMeta(meta);
        ARMOR_SLOT_SETTERS.get(chromaSlot).accept(player, chromaItem);
        return true;
    }

    public void run() {
        CACHE.run();
    }

    static {
        ARMOR_SLOT_GETTERS.add((p) -> {
            return p.getInventory().getHelmet();
        });
        ARMOR_SLOT_GETTERS.add((p) -> {
            return p.getInventory().getChestplate();
        });
        ARMOR_SLOT_GETTERS.add((p) -> {
            return p.getInventory().getLeggings();
        });
        ARMOR_SLOT_GETTERS.add((p) -> {
            return p.getInventory().getBoots();
        });

        ARMOR_SLOT_SETTERS.add((p, is) -> {
            p.getInventory().setHelmet(is);
        });
        ARMOR_SLOT_SETTERS.add((p, is) -> {
            p.getInventory().setChestplate(is);
        });
        ARMOR_SLOT_SETTERS.add((p, is) -> {
            p.getInventory().setLeggings(is);
        });
        ARMOR_SLOT_SETTERS.add((p, is) -> {
            p.getInventory().setBoots(is);
        });
    }
}
