import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class e here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Black_Hole extends skills
{
    /**
     * Act - do whatever the e wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int liveTime = 200;
    public Black_Hole(){
        setImage("Evil Warrior_skill2_1.png");
    }

    public void act()
    {
        if(getWorld().getObjects(Player.class).isEmpty()){
            getWorld().removeObject(this);
        }else{
            ((Player) getWorld().getObjects(Player.class).get(0)).changeBlackHole(true);
            // Add your action code here.
            liveTime --;
            if(liveTime <= 0){
                ((Player) getWorld().getObjects(Player.class).get(0)).changeBlackHole(false);
                getWorld().removeObject(this);
            }
        }
    }
}
