package net.encmys.ykdz;

import java.util.*;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.DoubleStat;
import net.Indyuce.mmoitems.stat.type.BooleanStat;
import net.Indyuce.mmoitems.stat.type.StringStat;
import net.Indyuce.mmoitems.stat.type.StringListStat;

public class StatRegister {
    static JavaPlugin plugin = JavaPlugin.getPlugin(CustomMMOItemsStats.class);
    static ConfigurationSection config = plugin.getConfig();
    static ConfigurationSection configSection = config.getConfigurationSection("stats");
    static String[] stats = configSection.getKeys(false).toArray(new String[0]);
    public static void registerCustomStats() {

        for (String statId : stats) {

            plugin.getLogger().info("Try to register stat " + statId + " ...");

            String mat = config.getString("stats." + statId + ".displayed-material");
            String statType = config.getString("stats." + statId + ".type");
            String name = config.getString("stats." + statId + ".displayed-name");
            String[] lore = config.getStringList("stats." + statId + ".displayed-lore").toArray(new String[0]);
            String[] disTypesRaw = config.getStringList("stats." + statId + ".disallowed-types").toArray(new String[0]);;
            Set<String> disTypesSet = new HashSet<>();

            if (Material.matchMaterial(mat) == null) {
                plugin.getLogger().info("Fail to register " + statType + " stat " + statId + ". Material is invalid.");
                return;
            }

            Material material = Material.valueOf(mat);

            for (String type : disTypesRaw) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("!");
                stringBuilder.append(type.toLowerCase());
                disTypesSet.add(stringBuilder.toString());
            }

            disTypesSet.add("all");
            String[] disTypesArr = disTypesSet.toArray(new String[0]);

            if (Objects.equals(statType.toUpperCase(), "DOUBLE")) {
                DoubleStat stat = new DoubleStat(statId, material, name, lore, disTypesArr, new Material[0]);
                MMOItems.plugin.getStats().register(stat);
            } else if (Objects.equals(statType.toUpperCase(), "STRING")) {
                StringStat stat = new StringStat(statId, material, name, lore, disTypesArr, new Material[0]);
                MMOItems.plugin.getStats().register(stat);
            } else if (Objects.equals(statType.toUpperCase(), "BOOLEAN")) {
                BooleanStat stat = new BooleanStat(statId, material, name, lore, disTypesArr, new Material[0]);
                MMOItems.plugin.getStats().register(stat);
            } else if (Objects.equals(statType.toUpperCase(), "STRING_LIST")) {
                StringListStat stat = new StringListStat(statId, material, name, lore, disTypesArr, new Material[0]);
                MMOItems.plugin.getStats().register(stat);
            } else {
                plugin.getLogger().info("Fail to register " + statType + " stat " + statId + ". Stat type is invalid.");
                return;
            }

            plugin.getLogger().info("Successfully register " + statType + " stat " + statId + "!");

        }

    }
}
