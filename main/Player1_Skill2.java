import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player1_Skill2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player1_Skill2 extends Player
{
    private boolean side;
    private int skillTime = 0;
    private int imageN = 1;
    private char rf = 'r';

    private boolean died = false;

    private int imageChange = 0;
    private int imageB = 4;

    /**
     * Act - do whatever the Player1_Skill2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Player1_Skill2(){
        setImage("Player_skill2_r1.png");
    }

    public void act()
    {
        // Add your action code here.
        skill2();
        skill2_strat();
        imageChange ++;
        if(died){
            ((Player) getWorld().getObjects(Player.class).get(0)).skill2Stop();
            getWorld().removeObject(this);
        }
    }

    private void skill2_strat(){
        if(skillTime == 0){
            MyWorld world= (MyWorld)getWorld();
            if(world != null){
                side = world.get_side("Player",(int)0);
                if(side){
                    setImage("Player_skill2_r1.png");
                    rf = 'r';
                }else{
                    setImage("Player_skill2_l1.png");
                    rf = 'l';
                }
                skillTime++;
            }
        }else{
            skillTime ++;
            if(side){
                setLocation(getX() + ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getPlayerSkillRange(0),getY());
            }else{
                setLocation(getX() - ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getPlayerSkillRange(0),getY());
            }
        }
    }

    private void skill2(){
        if(imageChange == imageB){
            setImage("Player_skill2_"+ rf + imageN + ".png");
            imageN ++;
        }
        if(imageChange == imageB*2){
            setImage("Player_skill2_"+ rf + imageN + ".png");
            imageN ++;
        }
        if(imageChange == imageB*3){
            setImage("Player_skill2_"+ rf + imageN + ".png");
            imageN ++;
        }
        if(imageChange == imageB*4){
            setImage("Player_skill2_"+ rf + imageN + ".png");
            imageN ++;
        }
        if(imageChange == imageB*5){
            died = true;
        }
    }
}
