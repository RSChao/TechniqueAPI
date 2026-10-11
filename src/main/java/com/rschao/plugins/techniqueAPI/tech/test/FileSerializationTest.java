package com.rschao.plugins.techniqueAPI.tech.test;

import com.rschao.plugins.techniqueAPI.TechAPI;
import com.rschao.plugins.techniqueAPI.tech.Technique;
import com.rschao.plugins.techniqueAPI.tech.TechniqueAction;
import com.rschao.plugins.techniqueAPI.tech.register.TechRegistry;
import com.rschao.plugins.techniqueAPI.tech.selectors.TargetSelector;
import com.rschao.plugins.techniqueAPI.tech.selectors.TargetSelectors;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSerializationTest {

    public static final String PATH = TechAPI.INSTANCE.getDataFolder() + "/data/techniques";

    public static boolean saveTechsToGroup(String group, Technique st) throws IOException, ClassNotFoundException {
        //get techniques
        List<Technique> techs = getTechsFromGroup(group);
        if(techs == null) {
            techs = new ArrayList<>();
        }
        if(techs.isEmpty()) {
            techs = new ArrayList<>();
        }
        techs.add(st);
        //save data to file
        File file = getFile(group);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        st.serialize(oos);
        oos.close();
        fos.close();
        return !techs.isEmpty();
    }
    public static boolean saveTechsInGroup(String group) throws IOException, ClassNotFoundException {
        //get techniques
        List<Technique> techs = TechRegistry.getAllTechniques(group);
        if(techs == null) {
            techs = new ArrayList<>();
        }
        if(techs.isEmpty()) {
            techs = new ArrayList<>();
        }
        //save data to file
        File file = getFile(group);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(techs);
        oos.close();
        fos.close();
        return !techs.isEmpty();
    }

    public static boolean saveTechAction(Technique st) throws IOException {
        //get techniques
        //save data to file
        File file = getFile(st.getId());
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(st.getAction());
        oos.close();
        fos.close();
        return true;
    }

    public static TechniqueAction getTechAction(String techId) throws IOException, ClassNotFoundException {
        File file = getFile(techId);
        if (file.length() == 0) {
            return null;
        }
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        TechniqueAction action = (TechniqueAction) ois.readObject();
        ois.close();
        fis.close();
        return action;
    }

    public static boolean saveTechInParts(String group, Technique st) throws IOException, ClassNotFoundException {
        //save technique meta
        File file = getFile(group + "_" + st.getId() + "_meta");
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(st.getMeta());
        oos.close();
        fos.close();
        Bukkit.getLogger().info("Saved technique meta for: " + st.getId());
        file = getFile(group + "_" + st.getId() + "_target_selector");
        fos = new FileOutputStream(file);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(st.getTargetSelector());
        oos.close();
        fos.close();
        Bukkit.getLogger().info("Saved technique target selector for: " + st.getId());
        file = getFile(group + "_" + st.getId() + "_prerun");
        fos = new FileOutputStream(file);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(st.getPreRunHook());
        oos.close();
        fos.close();
        Bukkit.getLogger().info("Saved technique prerun for: " + st.getId());
        file = getFile(group + "_" + st.getId() + "_action");
        fos = new FileOutputStream(file);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(st.getAction());
        oos.close();
        fos.close();
        Bukkit.getLogger().info("Saved technique action for: " + st.getId());

        return true;
    }



    public static List<Technique> getTechsFromGroup(String group) throws IOException, ClassNotFoundException {
        File file = getFile(group);
        if (file.length() == 0) {
            return new ArrayList<>();
        }
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Technique> techs = (List<Technique>) ois.readObject();
        ois.close();
        fis.close();
        return techs;
    }

    public static File getFile(String group) throws IOException {
        File dir = new File(PATH);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(PATH, group + ".tgp");
        if(!file.isFile()){
            file.createNewFile();
        }
        return file;
    }

    public static void createTestTechniques(){
        Technique t1 = new Technique("test_technique_1", "Test Technique 1", false, 0, List.of(), TargetSelectors.self(), (context, token) -> {

            context.caster().sendMessage("Test Technique 1 executed!");

        });
        Technique t2 = new Technique("test_technique_2", "Test Technique 2", false, 0, List.of(), TargetSelectors.self(), (context, token) -> {

            Bukkit.getScheduler().runTaskLater(TechAPI.INSTANCE, () -> {
                context.caster().sendMessage("Test Technique 2 executed after delay!");
            }, 20L); // Delay of 20 ticks (1 second)
        });

        //technique 3: executes every 5 seconds, for 3 times, and then stops
        Technique t3 = new Technique("test_technique_3", "Test Technique 3", false, 0, List.of(), TargetSelectors.self(), (context, token) ->{

            new BukkitRunnable() {
                int count = 0;
                @Override
                public void run() {
                    if(count < 3){
                        context.caster().sendMessage("Test Technique 3 executed! Count: " + (count + 1));
                        count++;
                        return;
                    }
                    this.cancel();
                }
            }.runTaskTimer(TechAPI.INSTANCE, 0L, 100L);
        });

        TechRegistry.registerTechnique("io_test_group", t1);
        TechRegistry.registerTechnique("io_test_group", t2);
        TechRegistry.registerTechnique("io_test_group", t3);
    }
}
