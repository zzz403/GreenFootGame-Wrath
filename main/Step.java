import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Step here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Step extends Actor
{
    /**
     * Act - do whatever the Step wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    public void movement(int i){
        setLocation(getX() + i,getY());
    }
    public int getStepY(){
        int y = getY();
        return y;
    }
    public void StepDis(){
        getWorld().removeObject(this);
    }
}
