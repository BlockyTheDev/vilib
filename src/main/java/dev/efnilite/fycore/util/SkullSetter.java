package dev.efnilite.fycore.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Method;

public class SkullSetter {

    private static boolean isPaper;
    private static Method getPlayerProfileMethod;
    private static Method hasTexturesMethod;
    private static Method setPlayerProfileMethod;

    static {
        try {
            Class<?> playerProfileClass = Class.forName("com.destroystokyo.paper.profile.PlayerProfile");
            getPlayerProfileMethod = Player.class.getDeclaredMethod("getPlayerProfile");
            hasTexturesMethod = playerProfileClass.getDeclaredMethod("hasTextures");
            setPlayerProfileMethod = SkullMeta.class.getDeclaredMethod("setPlayerProfile", playerProfileClass);
            isPaper = true;
        } catch (Throwable e) {
            isPaper = false;
        }
    }

    public static void setPlayerHead(Player player, SkullMeta meta) {
        if (isPaper) {
            try {
                Object playerProfile = getPlayerProfileMethod.invoke(player);
                boolean hasTexture = (boolean) hasTexturesMethod.invoke(playerProfile);
                if (hasTexture) {
                    setPlayerProfileMethod.invoke(meta, playerProfile);
                }
            } catch (Throwable e) {
                meta.setOwningPlayer(player); // if the paper version doesn't work, try the default one
            }
        } else {
            meta.setOwningPlayer(player);
        }
    }
}
