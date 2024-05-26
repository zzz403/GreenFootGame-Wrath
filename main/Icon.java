import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Skill_icon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Icon extends Actor
{
    /**
     * Act - do whatever the Skill_icon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    //Skill_icon
    public Icon(){
    }

    public void act()
    {
        // Add your action code here.
    }
    
    public void movement(int i){
        setLocation(getX() + i,getY());
    }
}
