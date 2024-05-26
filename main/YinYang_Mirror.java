import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class w here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class YinYang_Mirror extends skills
{
    /**
     * Act - do whatever the w wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int showTime = 500;
    public YinYang_Mirror(){
        setImage("Evil Warrior_skill3_1.png");
    }

    public void act()
    {
        // Add your action code here.
        show();
    }

    public void show(){
        if(getWorld().getObjects(evil_Warrior.class).isEmpty() != true){
            if(((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).yingyang() == true){
                getImage().setTransparency(225);
                if(showTime > 0){
                    showTime --;
                }else{
                    ((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).changYingyang();
                }
            }else{
                getImage().setTransparency(0);
            }
            setLocation(((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getX(),((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getY()- 40);
        }else{
            getWorld().removeObject(this);
        }
    }
}
