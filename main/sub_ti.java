import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class sub_ti here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class sub_ti extends Actor
{
    /**
     * Act - do whatever the sub_ti wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public sub_ti(int i){
        if(i == 1){
            setImage("youDie.png");   
        }else{
            setImage("youWin.png");   
        }
    }

    public void act()
    {
        // Add your action code here.
    }
}
