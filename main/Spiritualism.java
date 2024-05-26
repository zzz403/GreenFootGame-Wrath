import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class qer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spiritualism extends column
{
    /**
     * Act - do whatever the qer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int time = 500;
    private int moveY;
    public Spiritualism(){
        setImage("Spiritualism.png");
    }

    public void act()
    {
        // Add your action code here.
        fall();
        time --;
        if(time <= 0){
            getWorld().removeObject(this);
        }
    }

    private void fall(){
        Actor Flooring = getOneObjectAtOffset(0, getImage().getHeight()/2, Flooring.class);
        if(Flooring == null){
            checkStep(); 

        }else{ 
            moveY = 0;
            // if(getY() >= 580){
            // setLocation(getX(),580);
            // }
        }
    }

    public void checkStep(){
        Actor Step = getOneObjectAtOffset(0, getImage().getHeight()/2, Step.class);
        if(Step == null){
            setLocation(getX(),getY()+moveY);
            moveY += 1;
        }else{
            moveY = 0;
            // if(getY()> 339 && getY() < 380){
            // setLocation(getX(),370);
            // }else if(getY()> 139 && getY() < 180){
            // setLocation(getX(),170);
            // }else if(getY()> 239 && getY() < 280){
            // setLocation(getX(),270);
            // }
        }
    }
}
