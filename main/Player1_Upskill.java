import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player1_Upskill here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player1_Upskill extends Player
{
    /**
     * Act - do whatever the Player1_Upskill wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private GreenfootImage[][] skill;
    private int sx = 161;
    private int sy = 200;

    private int imageChange = 0;
    private int imageB = 5;

    private int index = 0;
    private boolean side;
    private boolean firstTime = true;
    private boolean died = false;
    public Player1_Upskill (){
        setImage("Player_upskill_1.png");
        skill = new GreenfootImage[2][4];
        for(int i = 1; i <= 4; i++){
            GreenfootImage right = new GreenfootImage("Player_upskill_"+i+".png");
            right.scale(sx,sy);
            skill[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Player_upskill_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            skill[1][i-1] = left;
        }
    }

    public void act()
    {
        // Add your action code here.
        upkill();
        imageChange --;
        if(died){
            getWorld().removeObject(this);
        }
    }

    private void upkill(){
        MyWorld world= (MyWorld)getWorld();
        if(firstTime){
            if(world != null){
                side = world.get_side("Player",(int)0);
                firstTime = false;
            }
        }
        if(imageChange <= 0 ){
            if(index < 3){
                if(side){
                    setImage(skill[0][index]);
                }else{
                    setImage(skill[1][index]);
                }
                index ++;
                imageChange = imageB;
            }else{
                died = true;
            }
        }
    }
}
