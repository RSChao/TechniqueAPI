package com.rschao.plugins.techniqueAPI.itemsel;

import com.rschao.plugins.techniqueAPI.TechAPI;
import com.rschao.plugins.techniqueAPI.tech.Technique;
import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;
import com.rschao.plugins.techniqueAPI.tech.register.TechRegistry;
import com.rschao.plugins.techniqueAPI.tech.util.PlayerTechniqueManager;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemSelectorConfig implements Listener {
    public static final Map<UUID, Integer> playerGroupIdIndex = new HashMap<>();

    @EventHandler
    void onPlayerUseTech(PlayerInteractEvent ev){
        Player p = ev.getPlayer();
        if(ev.getItem() == null) return;
        if(ev.getItem().getItemMeta() == null) return;

        if(!ev.getItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(TechAPI.INSTANCE, "channeler"), PersistentDataType.BOOLEAN)) return;
        // Get current group id index for player (default 0)
        int groupIndex = playerGroupIdIndex.getOrDefault(p.getUniqueId(), 0);
        String groupId = getGroupId(p, groupIndex);
        if (groupId == null || groupId.equals("none")) {
            p.sendMessage("You have not yet harnessed the abyss.");
            return;
        }

        // Get current technique index for this group id
        int techIndex = PlayerTechniqueManager.getCurrentTechnique(p.getUniqueId(), groupId);

        if(ev.getAction().toString().contains("LEFT")){
            if(p.isSneaking()){
                // Cycle group id index (0-2)
                int maxGroups = getGroupIdCount(p);
                if (maxGroups == 0) {
                    p.sendMessage("You have no abyss to switch to! Ask an admin if you think this is an error");
                    return;
                }
                int newIndex = (groupIndex + 1) % maxGroups;
                playerGroupIdIndex.put(p.getUniqueId(), newIndex);
                String newGroupId = getGroupId(p, newIndex).replace("_", " ");
                p.sendMessage("Your abyss has switched to " + newGroupId);
            }
            else{
                Technique technique = TechRegistry.getAllTechniques(groupId).get(techIndex);
                if(technique == null) return;
                technique.use(new TechniqueContext(p, ev.getItem()));
            }
        }
        else if(ev.getAction().toString().contains("RIGHT")){
            if(p.isSneaking()){
                if (techIndex == 0) {
                    PlayerTechniqueManager.setCurrentTechnique(p.getUniqueId(), groupId,TechRegistry.getAllTechniques(groupId).size() - 1);
                    techIndex = PlayerTechniqueManager.getCurrentTechnique(p.getUniqueId(), groupId);
                } else {
                    PlayerTechniqueManager.setCurrentTechnique(p.getUniqueId(), groupId, techIndex - 1);
                    techIndex = PlayerTechniqueManager.getCurrentTechnique(p.getUniqueId(), groupId);
                }
            }
            else{
                PlayerTechniqueManager.setCurrentTechnique(p.getUniqueId(), groupId, (techIndex + 1) % TechRegistry.getAllTechniques(groupId).size());
                techIndex = PlayerTechniqueManager.getCurrentTechnique(p.getUniqueId(), groupId);
            }
            p.sendMessage("You have switched to technique: " + TechRegistry.getAllTechniques(groupId).get(techIndex).getDisplayName());
        }
    }

    // Get the group id for a player at a given index (0-2)
    public String getGroupId(Player p, int index){
        FileConfiguration config = TechAPI.INSTANCE.getConfig();
        String playerName = sanitizePlayerName(p.getName());
        java.util.List<String> groupIds = config.getStringList(playerName + ".groupids");
        if (groupIds.isEmpty() || index < 0 || index >= groupIds.size()) {
            return "none";
        }
        return groupIds.get(index);
    }

    // Get the number of group ids a player has
    public int getGroupIdCount(Player p) {
        FileConfiguration config = TechAPI.INSTANCE.getConfig();
        String playerName = sanitizePlayerName(p.getName());
        java.util.List<String> groupIds = config.getStringList(playerName + ".groupids");
        return groupIds.size();
    }

    // Utility method to sanitize player names (remove leading dot)
    private String sanitizePlayerName(String name) {
        if (name != null && name.startsWith(".")) {
            return name.substring(1);
        }
        return name;
    }
}
