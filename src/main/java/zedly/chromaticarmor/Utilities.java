package zedly.chromaticarmor;

import java.lang.reflect.Field;
import java.util.*;
import net.minecraft.server.v1_14_R1.DataWatcher;
import net.minecraft.server.v1_14_R1.DataWatcherObject;
import net.minecraft.server.v1_14_R1.DataWatcherRegistry;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityMetadata;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

public class Utilities {

    private static boolean nmsDetected = false;

    // Creates firework on teleport with Chromatic Armor
    public static void explodeFromArmor(ItemStack is, Location loc) {
        if (is != null && ArrayUtils.contains(Storage.leather, is.getType()) && is.getItemMeta().hasLore()) {
            if (is.getItemMeta().hasLore() && is.getItemMeta().getLore().get(0).startsWith(ChatColor.GREEN + "Chromatic Armor")) {
                LeatherArmorMeta lm = (LeatherArmorMeta) is.getItemMeta();
                Color rgb = lm.getColor();
                try {
                    FireworkEffectPlayer.playFirework(loc, FireworkEffect.Type.BALL, rgb, false, true);
                } catch (Exception ex) {
                }
            }
        }
    }

    public static int[] getColor(double[] params, int counter) {
        if (params.length != 10) {
            throw new IllegalArgumentException();
        }
        counter += params[7];
        double redFreq = (params[0] / 180.0 * (Math.PI)) * (params[8] / 100.0);
        double greenFreq = (params[1] / 180.0 * (Math.PI)) * (params[8] / 100.0);
        double blueFreq = (params[2] / 180.0 * (Math.PI)) * (params[8] / 100.0);
        int red = (int) ((Math.pow(Math.sin(redFreq * counter + (params[3] * Math.PI / 180.0)), params[9]) * params[6]) + 255 - params[6]);
        int green = (int) ((Math.pow(Math.sin(greenFreq * counter + (params[4] * Math.PI / 180.0)), params[9]) * params[6]) + 255 - params[6]);
        int blue = (int) ((Math.pow(Math.sin(blueFreq * counter + (params[5] * Math.PI / 180.0)), params[9]) * params[6]) + 255 - params[6]);
        return new int[]{red, green, blue};
    }

    public static int[] getThemedColor(double[] params, int counter) throws IllegalArgumentException {
        if (params.length != 12) {
            throw new IllegalArgumentException();
        }
        double h = params[6] + params[3] * Math.sin(params[0] * ((counter + params[9]) * Math.PI / 180.0));
        double s = params[7] + params[4] * Math.sin(params[1] * ((counter + params[10]) * Math.PI / 180.0));
        double v = params[8] + params[5] * Math.sin(params[2] * ((counter + params[11]) * Math.PI / 180.0));
        return HsvToRgb(h, s, v);
    }

    public static int[] HsvToRgb(double h, double S, double V) {
        double H = h;
        while (H < 0) {
            H += 360;
        }
        while (H >= 360) {
            H -= 360;
        }
        double R = 0, G = 0, B = 0;
        if (V <= 0) {
            R = G = B = 0;
        } else if (S <= 0) {
            R = G = B = V;
        } else {
            double hf = H / 60.0;
            int i = (int) Math.floor(hf);
            double f = hf - i;
            double pv = V * (1 - S);
            double qv = V * (1 - S * f);
            double tv = V * (1 - S * (1 - f));
            switch (i) {
                case 0:
                    R = V;
                    G = tv;
                    B = pv;
                    break;
                case 1:
                    R = qv;
                    G = V;
                    B = pv;
                    break;
                case 2:
                    R = pv;
                    G = V;
                    B = tv;
                    break;
                case 3:
                    R = pv;
                    G = qv;
                    B = V;
                    break;
                case 4:
                    R = tv;
                    G = pv;
                    B = V;
                    break;
                case 5:
                    R = V;
                    G = pv;
                    B = qv;
                    break;
                case 6:
                    R = V;
                    G = tv;
                    B = pv;
                    break;
                case -1:
                    R = V;
                    G = pv;
                    B = qv;
                    break;
            }
        }
        int r = clamp((int) (R * 255.0));
        int g = clamp((int) (G * 255.0));
        int b = clamp((int) (B * 255.0));
        return new int[]{r, g, b};
    }

    public static int clamp(int i) {
        if (i < 0) {
            return 0;
        }
        if (i > 255) {
            return 255;
        }
        return i;
    }

    public static double[] parseParameters(String parameterSet) {
        String[] segments = parameterSet.split(":");
        double[] params = new double[segments.length];
        for (int i = 0; i < segments.length; i++) {
            params[i] = Double.parseDouble(segments[i]);
        }
        return params;
    }

    public static boolean isMaterialDye(final Material material) {
        return ArrayUtils.contains(Storage.FW_COLOR_ICON_MATS, material);
    }
}
