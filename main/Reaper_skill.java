import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Reaper_skill here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Reaper_skill extends Enemie_min_reapper
{
    /**
     * Act - do whatever the Reaper_skill wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private GreenfootImage[][] goust;
    private int sx = 37;
    private int sy = 90;

    private boolean seting = true;
    private int waitTime = 100;
    private int index = 0;
    private int intervel = 0;
    private int side;

    private boolean died = false;
    private int waitDie = 20;
    private boolean didTouch = false;
    public Reaper_skill(){

        goust = new GreenfootImage[2][4];
        for(int i = 1; i <= 4; i++){
            GreenfootImage right = new GreenfootImage("Reaper_skill_"+i+".png");
            right.scale(sx,sy);
            goust[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Reaper_skill_"+i+".png");
            left.mirrorVertically();
            left.scale(sx,sy);
            goust[1][i-1] = left;
        }
    }

    public void act()
    {
        // Add your action code here.
        if(seting){
            location();
            seting = false;
        }
        waitTime --;
        intervel --;
        go();
        if(died){
            if(waitDie <= 0){
                getWorld().removeObject(this);
            }else{
                waitDie --;
            }
        }
    }

    private void location(){
        setImage(goust[0][0]);
        waitTime = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(11);
        MyWorld world= (MyWorld)getWorld();
        setLocation(world.getPlayerX(),world.getPlayerY());
    }

    private void go(){
        if(waitTime <= 0){
            setImage(goust[0][index]);
            if(intervel <= 0){
                if(index >= 3){
                    died = true;
                }else{
                    index ++;
                }
                intervel = 10;
            }
            if(didTouch != true){
                touch();
            }
        }
    }

    private void touch(){
        if(isTouching(Player.class)){
            MyWorld world= (MyWorld)getWorld();
            world. deductHp(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieDamage(5));
            didTouch = true;
        }else if(getY() > 540){ 
        }
    }
}
