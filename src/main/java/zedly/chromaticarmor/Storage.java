package zedly.chromaticarmor;

import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import static org.bukkit.Material.*;
import org.bukkit.inventory.ItemStack;

public class Storage {

    //Variables
    public static final Random rnd = new Random();
    public static String colorString;

    //Pre-defined Variables
    public static final String logo = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "Pyro" + ChatColor.DARK_RED + "]" + ChatColor.YELLOW + "";
    public static Material[] leather = new Material[]{LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_LEGGINGS};

    public static Integer[] badIds = new Integer[]{0, 8, 9, 10, 11, 26, 34, 36, 43, 51, 55, 59, 63, 64, 68, 71, 74, 75, 83, 90, 92, 93, 94, 104, 105, 115, 117,
        118, 119, 124, 125, 127, 132, 140, 141, 142, 144, 149, 150, 176, 177, 178, 181, 193, 194, 195, 196, 197};

    // TODO: replace this with an array of tuples
    public static final Material[] FW_COLOR_ICON_MATS = {RED_DYE, ORANGE_DYE, YELLOW_DYE, LIME_DYE,
        GREEN_DYE, LIGHT_BLUE_DYE, BLUE_DYE, LIGHT_GRAY_DYE, PINK_DYE, MAGENTA_DYE,
        PURPLE_DYE, CYAN_DYE, GRAY_DYE, BROWN_DYE, WHITE_DYE, BLACK_DYE};
    
    public static final String[] FW_COLOR_FRIENDLY_NAMES = {
        "red", "orange", "yellow", "light_green",
        "dark_green", "light_blue", "dark_blue", "light_gray",
        "pink", "magenta", "purple", "aqua",
        "dark_gray", "brown", "white", "black"};
 
    public static final int[][] FW_COLOR_RGB_RICH = {
        {255, 0, 0}, {255, 127, 0}, {255, 255, 0}, {0, 255, 0},
        {0, 127, 0}, {0, 255, 255}, {0, 0, 255}, {127, 127, 127},
        {255, 0, 127}, {255, 0, 255}, {0, 127, 127}, {0, 127, 127},
        {63, 63, 63}, {127, 63, 0}, {255, 255, 255}, {0, 0, 0}};
    
    public static final int[][] FW_COLOR_RGB_MOJANG = {
        {179, 49, 44}, {235, 136, 68}, {222, 207, 42}, {65, 205, 52},
        {59, 81, 26}, {102, 137, 211}, {37, 49, 146}, {171, 171, 171},
        {216, 129, 152}, {195, 84, 205}, {123, 47, 190}, {40, 118, 151},
        {67, 67, 67}, {81, 48, 26}, {240, 240, 240}, {30, 27, 27}};

    //Collections
    public static final HashMap<String, Boolean> recipes = new HashMap<>();
    public static final HashSet<Player> vanishedPlayers = new HashSet<>();
}
