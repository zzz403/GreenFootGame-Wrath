import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EXP1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EXP extends Points
{
    /**
     * Act - do whatever the EXP1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
     private int sx,sy;
    private int waitTime;
    private int speed = 10;
    private boolean creat = true;
    public EXP(){
        setImage("EXP.png");
        waitTime = 75;
    }

    public void act()
    {
        // Add your action code here.
        creat();
        if(waitTime<=0){
            go();
            if(isTouching(Player.class)){
                MyWorld world= (MyWorld)getWorld();
                world.addExp();
                getWorld().removeObject(this);
            }
        }else{
            waitTime --;
        }

    }

    private void creat(){
        if(creat == true){
            setLocation(getX() + Greenfoot.getRandomNumber(40)-20,getY() + Greenfoot.getRandomNumber(40)-20);
            creat = false;
        }
    }

    private void go(){
        MyWorld world= (MyWorld)getWorld();
        if(world !=null){
            sx = world.getPlayerX();
            sy = world.getPlayerY();
        }
        setRotation((int)Math.toDegrees(Math.atan2((sy - getY()), (sx - getX()))));
        move(speed);
    }
}
