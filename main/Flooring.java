import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class wall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Flooring extends Actor
{
    private int type;
    
    public Flooring(int type){
        this. type = type;
        switch (type) {
            case 1: 
                setImage("wall.png");
                break;
            case 2: 
                setImage("wall.png");
                break;
            case 3: 
                setImage("mob2_l_2.png");
                break;
            default:
                break;
        }
    }
    public void act()
    {
    }
    public void movement(int i){
        setLocation(getX()+i,getY());
    }    

    
    public int getType(){
        return type;
    }
}
