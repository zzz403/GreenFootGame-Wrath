import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player_ef here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player_ef extends z_Light
{
    /**
     * Act - do whatever the Player_ef wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage[] animation;
    private int showTime = 20;

    private int imageChange = 0;
    private int imageB = 5;
    public Player_ef(){
        animation = new GreenfootImage[5];
        for(int i = 1; i <= 4; i++){
            GreenfootImage right = new GreenfootImage("ef1_"+i+".png");
            right.scale(100,100);
            animation[i-1] = right;
        }
        setImage(animation[0]);
    }

    public void act()
    {
        
        // Add your action code here.
        MyWorld world= (MyWorld)getWorld();
        if(world!= null){
            setLocation(world.getPlayerX(),world.getPlayerY() - 20);
        }
        imageChange ++;
        action();
    }

    private void action(){
        if(imageChange == imageB){
            setImage(animation[1]);
        }
        if(imageChange == imageB*2){
            setImage(animation[2]);
        }
        if(imageChange == imageB*3){
            setImage(animation[3]);
        }
        if(imageChange == imageB*4){
            getWorld().removeObject(this);
        }
    }
}
