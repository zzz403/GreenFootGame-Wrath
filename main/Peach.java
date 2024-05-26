import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Peach here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Peach extends Icon
{
    /**
     * Act - do whatever the Peach wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int moveY;
    private int waitTime = 30;
    
    public Peach (){
        setImage("peach.png");
    }

    public void act()
    {
        // Add your action code here.
        fall();
        if(waitTime<=0){
            if(isTouching(Player.class)){
                MyWorld world= (MyWorld)getWorld();
                world.deductHp(-10);
                getWorld().removeObject(this);
            }
        }else{
            waitTime --;
        }
    }

    //fall to step
    private void fall(){
        Actor Flooring = getOneObjectAtOffset(0, getImage().getHeight()/2, Flooring.class);
        if(Flooring == null){
            checkStep(); 
        }else{ 
            if(getY() >= 566){
                setLocation(getX(),565);
            }
        }
    }

    //stop fall
    public void checkStep(){
        Actor Step = getOneObjectAtOffset(0, getImage().getHeight()/2, Step.class);
        if(Step == null){
            setLocation(getX(),getY()+moveY);
            moveY += 1;
        }else{
            if(getY()> 339 && getY() < 380){
                setLocation(getX(),340);
            }
        }
    }
}
