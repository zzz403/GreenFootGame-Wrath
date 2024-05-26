import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class buff_goust here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class buff_goust extends buff
{
    /**
     * Act - do whatever the buff_goust wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int skillTime = 100;
    public buff_goust(){
        setImage("buff_goust.png");
    }
    public void act()
    {
        // Add your action code here.
        skillTime --;
        MyWorld world= (MyWorld)getWorld();
        if(world!= null){
            setLocation(world.getPlayerX(),world.getPlayerY());
        }
        if(skillTime <= 0){
            getWorld().removeObject(this);
        }
    }
}
