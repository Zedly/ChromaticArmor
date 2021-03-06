package zedly.chromaticarmor;

import java.util.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import static org.bukkit.Material.*;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandProcessor {

    public static String getConfig(Player player, String[] args) {
        String str = null;
        switch (args[0].toLowerCase()) {
            case "custom":
                if (args.length == 11) {
                    str = (ChatColor.GRAY + args[1] + ":" + args[2] + ":" + args[3] + ":" + args[4] + ":" + args[5] + ":" + args[6] + ":" + args[7] + ":" + args[8] + ":" + args[9] + ":" + args[10]);
                } else {
                    player.sendMessage(Storage.logo + " <Red Frequency[-180..180]> <Green Frequency [-180..180]> <Blue Frequency [-180..180]> <Red Delay [-180..180]> <Green Delay [-180..180]> <Blue Delay [-180..180]> <Brightness [0..127> <Offset [0..]> <Speed [1..]> <Gamma [-1.0..]>");
                    return str;
                }
                break;
            case "grayscale":
                if (args.length == 4) {
                    str = (ChatColor.GRAY + "7:7:7:0:0:0:" + args[1] + ":" + args[2] + ":" + args[3] + ":1");
                } else {
                    player.sendMessage(Storage.logo + " <Brigtness [0..127]> <Offset [0..]> <Speed [1..]>");
                    return str;
                }
                break;
            case "normal":
                if (args.length == 4) {
                    str = (ChatColor.GRAY + "7:7:7:0:120:240:" + args[1] + ":" + args[2] + ":" + args[3] + ":1");
                } else {
                    player.sendMessage(Storage.logo + " <Brigtness [0..127]> <Offset [0..]> <Speed [1..]>");
                    return str;
                }
                break;
            case "pastel":
                if (args.length == 3) {
                    str = (ChatColor.GRAY + "7:7:7:0:120:240:35:" + args[1] + ":" + args[2] + ":1");
                } else {
                    player.sendMessage(Storage.logo + " <Offset [0..]> <Speed [1..]>");
                    return str;
                }
                break;
            case "bright":
                if (args.length == 3) {
                    str = (ChatColor.GRAY + "7:7:7:0:120:240:127:" + args[1] + ":" + args[2] + ":.001");
                } else {
                    player.sendMessage(Storage.logo + " <Offset [0..]> <Speed [1..]>");
                    return str;
                }
                break;
            case "color":
                if (args.length == 4) {
                    int offset = Integer.parseInt(args[2]);
                    int speed = Integer.parseInt(args[3]);
                    if (ArrayUtils.contains(Storage.FW_COLOR_FRIENDLY_NAMES, args[1].toLowerCase())) {
                        args[0] = args[1];
                        str = (ChatColor.GRAY + "" + speed + Storage.CHROMA_PREDEFINED_CONFIGS[ArrayUtils.indexOf(Storage.FW_COLOR_FRIENDLY_NAMES, args[1].toLowerCase())] + offset + ":" + offset + ":" + offset);
                    }
                } else {
                    player.sendMessage(Storage.logo + " <Color> <Offset [0..]> <Speed [1..]>");
                    player.sendMessage(ChatColor.YELLOW + Storage.colorString);
                }
                break;
        }
        return str;
    }

    private static void trl(Player player, String[] args) {
        String[] trlList = new String[]{"custom", "mineral", "color", "flower"};
        ArrayList<String> trails = new ArrayList<>();
        ArrayList<String> notTrails = new ArrayList<>();
        List<String> lore = player.getInventory().getItemInMainHand().getItemMeta().getLore();
        HashSet<String> oldItems = new HashSet<>();
        HashSet<String> newItems = new HashSet<>();
        if (lore != null) {
            for (String s : lore) {
                if (ArrayUtils.contains(trlList, ChatColor.stripColor(s.toLowerCase().replace(" trail", "")))) {
                    trails.add(ChatColor.stripColor(s.toLowerCase().replace(" trail", "")));
                } else {
                    Material m = Material.matchMaterial(ChatColor.stripColor(s.toUpperCase().replace(" ", "_")));//
                    if (m != null && m != Material.AIR) {  // !ArrayUtils.contains(Storage.badIds, m.getId())
                        oldItems.add(m.toString());
                    } else {
                        notTrails.add(s);
                    }
                }
            }
        }
        for (String s : args) {
            Material m = Material.matchMaterial(s.toUpperCase().replace(" ", "_"));
            if (m != null && m != AIR) { // && !ArrayUtils.contains(Storage.badIds, m.getId()
                newItems.add(m.toString());
            }
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (ArrayUtils.contains(trlList, args[1].toLowerCase()) && !trails.contains(args[1].toLowerCase())) {
                trails.add(args[1].toLowerCase());
            }
            oldItems.addAll(newItems);
        } else if (args[0].equalsIgnoreCase("del")) {
            if (ArrayUtils.contains(trlList, args[1].toLowerCase())) {
                trails.remove(args[1].toLowerCase());
            }
            if (args.length == 2 && args[1].equalsIgnoreCase("custom")) {
                oldItems.clear();
            } else {
                oldItems.removeAll(newItems);
            }
        }
        if (oldItems.isEmpty()) {
            trails.remove("custom");
        } else if (!trails.contains("custom")) {
            trails.add("custom");
        }
        List<String> newLore = new ArrayList<>();
        newLore.addAll(notTrails);
        for (String trl : trails) {
            newLore.add(ChatColor.GOLD + WordUtils.capitalize(trl) + " Trail");
        }
        for (String s : oldItems) {
            newLore.add(ChatColor.GREEN + WordUtils.capitalize(s.toLowerCase().replace("_", " ")));
        }
        player.sendMessage(Storage.logo + " Trail effect changed.");
        ItemStack is = player.getInventory().getItemInMainHand();
        ItemMeta meta = is.getItemMeta();
        meta.setLore(newLore);
        is.setItemMeta(meta);
        player.getInventory().setItemInMainHand(is);
    }

    public static void run(CommandSender sender, Command command, String commandlabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Storage.logo + " Chromatic Armor commands only make sense ingame!");
            return;
        }
        Player player = (Player) sender;
        switch (commandlabel.toLowerCase()) {
            case "chromo": {
                if (ArrayUtils.contains(Storage.leather, player.getInventory().getItemInMainHand().getType())) {
                    if (args.length == 0) {
                        sender.sendMessage(Storage.logo + " Chromatic Armor Types: \nCustom\nGrayscale\nNormal\nPastel\nBright\nColor");
                        return;
                    }
                    ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                    List<String> lore = new ArrayList<>();
                    boolean b = false;
                    for (int i = 1; i < args.length; i++) {
                        try {
                            Double.parseDouble(args[i]);
                        } catch (NumberFormatException e) {
                            if (!ArrayUtils.contains(Storage.FW_COLOR_FRIENDLY_NAMES, args[i].toLowerCase())) {
                                b = true;
                            }
                        }
                    }
                    if (b) {
                        player.sendMessage(Storage.logo + " One of those values is not a number!");
                        return;
                    }
                    String config = getConfig(player, args);
                    if (config == null) {
                        sender.sendMessage(Storage.logo + " Chromatic Armor Types: \nCustom\nGrayscale\nNormal\nPastel\nBright\nColor");
                    } else {
                        lore.add(ChatColor.GREEN + "Chromatic Armor: " + ChatColor.GOLD + WordUtils.capitalize(args[0].replace("_", " ")));
                        lore.add(config);
                        if (player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
                            List<String> oldLore = player.getInventory().getItemInMainHand().getItemMeta().getLore();
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
                        player.getInventory().getItemInMainHand().setItemMeta(meta);
                        player.updateInventory();
                        player.sendMessage(Storage.logo + " Chromatic Armor created!");
                    }
                } else {
                    player.sendMessage(Storage.logo + " You need to be holding a leather armor piece!");
                }
                break;
            }
            case "trl": {
                Material mat = player.getInventory().getItemInMainHand().getType();
                if (args.length == 0 || "help".equalsIgnoreCase(args[0])) {
                    player.sendMessage(Storage.logo + " For Normal Trails: /trl add Flower/Color/Mineral");
                    player.sendMessage(Storage.logo + " For Custom Trails: /trl add <Item ID:Data> ...");
                    break;
                }
                if (mat == DIAMOND_CHESTPLATE || mat == IRON_CHESTPLATE || mat == CHAINMAIL_CHESTPLATE
                        || mat == GOLDEN_CHESTPLATE || mat == LEATHER_CHESTPLATE) {
                    if ("add".equalsIgnoreCase(args[0]) || "del".equalsIgnoreCase(args[0]) && args.length > 1) {
                        trl(player, args);
                    } else if ("list".equalsIgnoreCase(args[0])) {
                        player.sendMessage(Storage.logo + " Trails:\nFlower\nColor\nMineral\nCustom");
                    }
                } else {
                    player.sendMessage(Storage.logo + " You need to be holding a chestplate!");
                }
                break;
            }
            default: {
                sender.sendMessage(Storage.logo + " Unknown command. Try /py help");
            }
            break;
        }
    }
}
