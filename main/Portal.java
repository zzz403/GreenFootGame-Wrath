import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Portal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Portal extends Actor
{
    /**
     * Act - do whatever the Portal wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int a;
    public Portal(int i){
        setImage("Portal_1.png");
        a = i;
    }

    public void act()
    {
        // Add your action code here.
        touch();
    }

    private void touch(){
        MyWorld world= (MyWorld)getWorld();
        if(isTouching(Player.class)){
            world.changeText("Slogan","Please press [w] to go to the next level");
            if(Greenfoot.isKeyDown("w")){
                
                world.getLevel(a+1);
                
                world.changeText("Slogan",null);
                getWorld().removeObject(this);
            }
        }else{
            world.changeText("Slogan",null);
        }
    }
    
    public void movement(int i){
        setLocation(getX() + i,getY());
    }
}
