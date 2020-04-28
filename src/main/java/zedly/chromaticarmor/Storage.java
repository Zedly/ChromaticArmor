package zedly.chromaticarmor;

import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import static org.bukkit.Material.*;

public class Storage {

    //Variables
    public static final Random rnd = new Random();
    public static String colorString;

    //Pre-defined Variables
    public static final String logo = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "Pyro" + ChatColor.DARK_RED + "]" + ChatColor.YELLOW + "";
    public static Material[] leather = new Material[]{LEATHER_BOOTS, LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_LEGGINGS};

    public static Integer[] badIds = new Integer[]{0, 8, 9, 10, 11, 26, 34, 36, 43, 51, 55, 59, 63, 64, 68, 71, 74, 75, 83, 90, 92, 93, 94, 104, 105, 115, 117,
        118, 119, 124, 125, 127, 132, 140, 141, 142, 144, 149, 150, 176, 177, 178, 181, 193, 194, 195, 196, 197};

    public static final String[] CHROMA_PREDEFINED_CONFIGS = new String[]{":2:3:180:0.05:0.05:62:0.05:0.95:", ":0:3:7:0:0.1:30:1:0.9:", ":0:3:10:0:0.15:300:1:0.85:",
        ":5:3:10:0.3:0.15:200:0.7:0.85:", ":0:3:7:0:0.15:62:1:0.85:", ":0:3:25:0.1:0.15:110:0.9:0.85:", ":0:3:10:0:0.1:327:1:0.9:",
        ":0:3:15:0:0.1:25:0:0.45:", ":0:3:15:0:0.15:25:0:0.75:", ":0:3:15:0.05:0.05:180:0.95:0.75:", ":0:3:10:0:0.20:280:1:0.80:",
        ":0:3:15:0.05:0.25:240:0.95:0.75:", ":0:3:10:0.05:0.1:25:0.55:0.45:", ":0:3:15:0.05:0.1:96:0.93:0.4:", ":0:3:15:0:0.2:0:1:0.8:",
        ":0:3:180:0.05:0.05:180:0.15:0.15:"};

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
