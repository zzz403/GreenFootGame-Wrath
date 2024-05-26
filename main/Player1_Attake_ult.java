import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ult here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player1_Attake_ult extends Player
{
    private int move = 5;
    private boolean sideMove;
    

    private boolean start = true;
    private int attackTime = 20;

    private GreenfootImage[][] side;

    public Player1_Attake_ult(){
        side = new GreenfootImage[2][2];
        setImage("Player_Attack_ult.png");
        getImage().scale(120,175);

        for(int i = 1; i <= 2; i++){
            GreenfootImage right = new GreenfootImage("Player_Attack_ult.png");
            right.scale(120,175);
            side[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Player_Attack_ult.png");
            left.mirrorHorizontally();
            left.scale(120,175);
            side[1][i-1] = left;
        }
    }

    public void act()
    {
        if(start == true){
            MyWorld world= (MyWorld)getWorld();
            sideMove = world.get_side("Player",0);
            if(world.get_side("Player",0)){
                setLocation(getX() + 40,getY()-30);
                setImage(side[1][0]);
            }else{
                setLocation(getX() - 40,getY()-30);
                setImage(side[0][0]);
            }
            start = false;
        }
        // Add your action code here.
        attake();
        attackTime --;
        if(attackTime <= 0){
            getWorld().removeObject(this);
        }
    }

    private void attake(){
            MyWorld world= (MyWorld)getWorld();
            if(world != null){
                if(sideMove){
                    setLocation(getX()+move,getY());
                }else{
                    setLocation(getX()-move,getY());
                }
            }
    }
}
