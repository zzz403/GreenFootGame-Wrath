import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class buff_ice here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class buff_ice extends buff
{
    /**
     * Act - do whatever the buff_ice wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int skillTime = 60;

    public buff_ice(){
        setImage("buff_ice.png");
    }

    public void act()
    {
        // Add your action code here.
        skillTime --;
        if(skillTime <= 0){
            getWorld().removeObject(this);
        }
    }
}
