package be4rjp.blockstudiotest;

import be4rjp.blockstudio.api.BSObject;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class RotateRunnable extends BukkitRunnable {
    
    private final BSObject bsObject;
    
    private double tickAngle;
    private Vector direction;
    double rotate = 0;
    
    public RotateRunnable(BSObject bsObject, Vector direction, double tickAngle){
        this.bsObject = bsObject;
        this.direction = direction;
        this.tickAngle = tickAngle;
    }
    
    @Override
    public void run() {
        
        if(rotate >= 360.0)
            rotate-=360.0;
        
        bsObject.setDirectionRotation(direction, rotate);
        bsObject.move();
        
        rotate+=tickAngle;
    }
}
