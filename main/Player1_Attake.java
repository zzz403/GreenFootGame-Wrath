import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class a here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player1_Attake extends Player
{
    /**
     * Act - do whatever the a wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private boolean side;
    private int skillTime = 0;
    private int move = 15;

    private int attackTime = 5;

    public Player1_Attake(){
        setImage("Player_Attackr.png");
        getImage().scale(88,76);
    }

    public void act()
    {
        // Add your action code here.
        attake();
        attackTime --;
        if(attackTime <= 0){
            getWorld().removeObject(this);
        }
    }

    private void attake(){
        if(skillTime == 0){
            MyWorld world= (MyWorld)getWorld();
            if(world != null){
                    side = world.get_side("Player",0);
                if(side){
                    setImage("Player_Attackr.png");
                    getImage().scale(88,76);
                }else{
                    setImage("Player_Attackl.png");
                    getImage().scale(88,76);
                }
                skillTime++;
            }else{
                skillTime ++;
                if(side){
                    setLocation(getX()+move,getY());
                }else{
                    setLocation(getX()-move,getY());
                }
            }
        }
    }
}
