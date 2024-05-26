import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class skill1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player1_Skill1 extends Player
{
    /**
     * Act - do whatever the skill1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int imageChange = 0;
    private int imageB = 5;
    // private boolean start = false;
    public Player1_Skill1(){
        setImage("Player_skill1_1.png");
    }
    public void act()
    {
        // Add your action code here.
        skill1();
        
        MyWorld world= (MyWorld)getWorld();
        if(world!= null){
            setLocation(world.getPlayerX(),world.getPlayerY());
        }
    
        imageChange ++;
    }
    private void skill1(){
        if(imageChange == imageB){
            setImage("Player_skill1_2.png");
        }
        if(imageChange == imageB*2){
            setImage("Player_skill1_3.png");
        }
        if(imageChange == imageB*3){
            setImage("Player_skill1_4.png");
        }
        if(imageChange == imageB*4){
            // start = true;
            getWorld().removeObject(this);
        }
    }
    // public boolean getStart(){
        // return start;
    // }
}
