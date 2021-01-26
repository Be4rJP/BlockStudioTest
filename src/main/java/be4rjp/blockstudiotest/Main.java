package be4rjp.blockstudiotest;

import be4rjp.blockstudio.BlockStudio;
import be4rjp.blockstudio.api.BSObject;
import be4rjp.blockstudio.api.BlockStudioAPI;
import be4rjp.blockstudio.file.ObjectData;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class Main extends JavaPlugin {
    
    private static Config config;
    private static Config movementsConfig;
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        
        //Load config files
        config = new Config(this, "config.yml");
        config.saveDefaultConfig();
        config.getConfig();
    
        movementsConfig = new Config(this, "movement.yml");
        movementsConfig.saveDefaultConfig();
        movementsConfig.getConfig();
        
        
        //Load all ObjectData
        BlockStudioAPI api = BlockStudio.getBlockStudioAPI();
        api.loadAllObjectData();
        
    
        //Create object and movements task
        for (String movement : movementsConfig.getConfig().getConfigurationSection("movements").getKeys(false)){
            
            //Get object's name
            String objectName = movementsConfig.getConfig().getString("movements." + movement + ".object-name");
            
            //Get location
            String locationData = movementsConfig.getConfig().getString("movements." + movement + ".location");
            Location location = ConfigUtil.toLocation(locationData);
            
            //Get direction
            String directionData = movementsConfig.getConfig().getString("movements." + movement + ".direction");
            String[] xyz = directionData.split(",");
            Vector direction = new Vector(Double.valueOf(xyz[0]), Double.valueOf(xyz[1]), Double.valueOf(xyz[2]));
    
            //Get ObjectData
            String objectDataName = movementsConfig.getConfig().getString("movements." + movement + ".object-data");
            ObjectData objectData = api.getObjectData(objectDataName);
            
            //Get rotate speed
            double tickAngle = movementsConfig.getConfig().getDouble("movements." + movement + ".tick-angle");
            
            //Get draw distance
            double drawDistance = movementsConfig.getConfig().getDouble("movements." + movement + ".draw-distance");
            
            
            //Create an object
            BSObject bsObject = api.createObjectFromObjectData(objectName, location, objectData, drawDistance, false);
            bsObject.startTaskAsync(40);
            
            //Set location
            bsObject.setBaseLocation(location);
            
            //Set direction
            bsObject.setDirection(direction);
            
            //Create and start movement's task
            RotateRunnable runnable = new RotateRunnable(bsObject, direction, tickAngle);
            runnable.runTaskTimer(this, 0, 1);
        }
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
