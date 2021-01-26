package be4rjp.blockstudiotest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ConfigUtil {
    public static Location toLocation(String locString){
        
        locString = locString.replace(" ", "");
        
        String[] args = locString.split(",");
        
        if(args.length != 4) return null;
        
        World world = Bukkit.getWorld(args[0]);
        double x = Double.valueOf(args[1]);
        double y = Double.valueOf(args[2]);
        double z = Double.valueOf(args[3]);
        
        Location location = new Location(world, x, y, z, 0, 0);
        
        return location;
    }
}
