import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DropAttack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player1_DropAttake extends Player
{
    /**
     * Act - do whatever the DropAttack wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int imageChange = 0;
    private int imageB = 3;
    public Player1_DropAttake(){
        setImage("Player_dropAttack_1.png");
    }
    public void act()
    {
        // Add your action code here.
        DropAttake();
        imageChange ++;
    }
    private void DropAttake(){
        if(imageChange == imageB){
            setImage("Player_dropAttack_2.png");
        }
        if(imageChange == imageB*2){
            setImage("Player_dropAttack_3.png");
        }
        if(imageChange == imageB*3){
            setImage("Player_dropAttack_4.png");
        }
        if(imageChange == imageB*4){
            // start = true;
            getWorld().removeObject(this);
        }
    }
}
