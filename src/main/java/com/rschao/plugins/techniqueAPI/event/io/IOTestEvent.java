package com.rschao.plugins.techniqueAPI.event.io;

import com.rschao.plugins.techniqueAPI.tech.Technique;
import com.rschao.plugins.techniqueAPI.tech.TechniqueAction;
import com.rschao.plugins.techniqueAPI.tech.cancel.SimpleCancellationToken;
import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;
import com.rschao.plugins.techniqueAPI.tech.register.TechRegistry;
import com.rschao.plugins.techniqueAPI.tech.test.FileSerializationTest;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.io.IOException;
import java.util.List;

public class IOTestEvent implements Listener {

    @EventHandler
    void onChat(PlayerChatEvent ev) {
        ev.getPlayer().sendMessage("IOTestEvent received");
        if (ev.getMessage().equalsIgnoreCase("saveTechs")) {
            ev.setCancelled(true);
            for (Technique t : TechRegistry.getAllTechniques("io_test_group")) {
                try {
                    FileSerializationTest.saveTechsToGroup("io_test_group", t);
                } catch (IOException | ClassNotFoundException e) {
                    Bukkit.getLogger().info("--------------------------");
                    e.printStackTrace();
                    Bukkit.getLogger().info("--------------------------");
                    Bukkit.getLogger().severe("Failed to save technique " + t.getId() + " to file: Exception occurred");
                    return;
                }
            }
        } else if (ev.getMessage().equalsIgnoreCase("loadTechs")) {
            ev.setCancelled(true);
            try {
                for (Technique t : FileSerializationTest.getTechsFromGroup("io_test_group")) {
                    Bukkit.getLogger().info("Loaded technique: " + t.getId());
                    t.use(ev.getPlayer());
                }
            } catch (IOException | ClassNotFoundException e) {
                Bukkit.getLogger().info("--------------------------");
                e.printStackTrace();
                Bukkit.getLogger().info("--------------------------");
                Bukkit.getLogger().severe("Failed to load techniques from file: Exception occurred");
            }
        } else if (ev.getMessage().equalsIgnoreCase("saveTechsHard")) {
            ev.setCancelled(true);
            List<String> groups = List.of("chao", "delta", "roaring_soul");
            for (String group : groups) {
                List<Technique> t = TechRegistry.getAllTechniques(group);
                try {
                    FileSerializationTest.saveTechsInGroup(group);
                } catch (IOException | ClassNotFoundException e) {
                    Bukkit.getLogger().info("--------------------------");
                    e.printStackTrace();
                    Bukkit.getLogger().info("--------------------------");
                    Bukkit.getLogger().severe("Failed to save technique group " + group + " to file: Exception occurred");
                    return;
                }
            }
        } else if (ev.getMessage().equalsIgnoreCase("loadTechsHard")) {
            ev.setCancelled(true);
            List<String> groups = List.of("chao", "delta", "roaring_soul");
            for (String group : groups) {
                Bukkit.getLogger().info("Loading techniques for group: " + group);
                try {
                    List<Technique> techniques = FileSerializationTest.getTechsFromGroup(group);
                    Bukkit.getLogger().info("Loaded " + techniques.size() + " techniques for group: " + group);
                    for (Technique t : techniques) {
                        Bukkit.getLogger().info("Loaded technique: " + t.getId());
                        ev.getPlayer().sendMessage("Loaded technique: " + t.getId());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    Bukkit.getLogger().info("--------------------------");
                    e.printStackTrace();
                    Bukkit.getLogger().info("--------------------------");
                    Bukkit.getLogger().severe("Failed to load techniques from file for group " + group + ": Exception occurred");
                }
            }

        }
        //save technique ids and actions only
        else if (ev.getMessage().equalsIgnoreCase("saveTechsIds")) {
            ev.setCancelled(true);
            for (Technique t : TechRegistry.getAllTechniques("chao")) {
                try {
                    FileSerializationTest.saveTechAction(t);
                } catch (IOException e) {
                    Bukkit.getLogger().info("--------------------------");
                    e.printStackTrace();
                    Bukkit.getLogger().info("--------------------------");
                    Bukkit.getLogger().severe("Failed to save technique " + t.getId() + " to file: Exception occurred");
                    return;
                }
            }

        }
        //save technique ids and actions only
        else if (ev.getMessage().equalsIgnoreCase("loadTechsIds")) {
            ev.setCancelled(true);
            for (Technique t : TechRegistry.getAllTechniques("chao")) {
                try {
                    TechniqueAction a = FileSerializationTest.getTechAction(t.getId());
                    ev.getPlayer().sendMessage("Loaded technique action for: " + t.getId());
                    a.execute(new TechniqueContext(ev.getPlayer()), new SimpleCancellationToken());
                } catch (IOException | ClassNotFoundException e) {
                    Bukkit.getLogger().info("--------------------------");
                    e.printStackTrace();
                    Bukkit.getLogger().info("--------------------------");
                    Bukkit.getLogger().severe("Failed to save technique " + t.getId() + " to file: Exception occurred");
                    return;
                }
            }

        }
        //save technique ids and actions only
        else if (ev.getMessage().equalsIgnoreCase("saveTechParts")) {
            ev.setCancelled(true);
            for (Technique t : TechRegistry.getAllTechniques("chao")) {
                try {
                    FileSerializationTest.saveTechInParts("chao", t);
                } catch (IOException | ClassNotFoundException e) {
                    Bukkit.getLogger().info("--------------------------");
                    e.printStackTrace();
                    Bukkit.getLogger().info("--------------------------");
                    Bukkit.getLogger().severe("Failed to save technique " + t.getId() + " to file: Exception occurred");
                    return;
                }
            }

        } else if (ev.getMessage().equalsIgnoreCase("saveEverything")) {
            List<String> errorGroups = new java.util.ArrayList<>();
            ev.setCancelled(true);
            for(String id : TechRegistry.getRegisteredFruitIds()){
                Bukkit.getLogger().info("Saving techniques for group: " + id);
                try {
                    FileSerializationTest.saveTechsInGroup(id);
                    Bukkit.getLogger().info("Saved techniques for group: " + id);
                } catch (IOException | ClassNotFoundException e) {
                    Bukkit.getLogger().info("--------------------------");
                    e.printStackTrace();
                    Bukkit.getLogger().info("--------------------------");
                    Bukkit.getLogger().severe("Failed to save technique group " + id + " to file: Exception occurred");
                    errorGroups.add(id);
                    continue;
                }
            }
            if (!errorGroups.isEmpty()) {
                ev.getPlayer().sendMessage("Failed to save techniques for the following groups: " + String.join(", ", errorGroups));
                Bukkit.getLogger().severe("Failed to save techniques for the following groups: " + String.join(", ", errorGroups));
            } else {
                ev.getPlayer().sendMessage("Successfully saved techniques for all groups.");
            }
        } else if (ev.getMessage().equalsIgnoreCase("loadEverything")) {
            ev.setCancelled(true);
            for(String id : TechRegistry.getRegisteredFruitIds()){
                Bukkit.getLogger().info("Loading techniques for group: " + id);
                try {
                    List<Technique> techniques = FileSerializationTest.getTechsFromGroup(id);
                    Bukkit.getLogger().info("Loaded " + techniques.size() + " techniques for group: " + id);
                    for (Technique t : techniques) {
                        ev.getPlayer().sendMessage("Loaded technique: " + t.getId());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    Bukkit.getLogger().info("--------------------------");
                    e.printStackTrace();
                    Bukkit.getLogger().info("--------------------------");
                    Bukkit.getLogger().severe("Failed to load techniques from file for group " + id + ": Exception occurred");
                }
            }

        }
    }
}
