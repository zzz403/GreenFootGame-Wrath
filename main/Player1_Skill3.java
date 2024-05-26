import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class skill3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player1_Skill3 extends Player
{
    /**
     * Act - do whatever the skill3 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage[][] skill;
    private int sx = 138;
    private int sy = 65;

    private boolean side;
    private int skillTime = 0;
    private int move = 15;

    private boolean died = false;
    
    public Player1_Skill3(){
        setImage("Player_skill3.png");
        
        skill = new GreenfootImage[2][2];
        GreenfootImage right = new GreenfootImage("Player_skill3.png");
        right.scale(sx,sy);
        skill[0][0] = right;
        GreenfootImage left = new GreenfootImage("Player_skill3.png");
        left.mirrorHorizontally();
        left.scale(sx,sy);
        skill[1][0] = left;
    }
    
    public void act()
    {
        // Add your action code here.
        skill2_strat();
        if(skillTime >= 40){
            getWorld().removeObject(this);
        }
    }
    
     private void skill2_strat(){
        if(skillTime == 0){
            MyWorld world= (MyWorld)getWorld();
            if(world != null){
                side = world.get_side("Player",(int)0);
                if(side){
                    setImage(skill[0][0]);
                }else{
                    setImage(skill[1][0]);
                }
                skillTime++;
            }
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
