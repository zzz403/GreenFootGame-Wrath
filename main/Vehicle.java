import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Vehicle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Vehicle extends Actor
{
    private GreenfootImage[][] moving;
    
    private boolean start = true;
    private int size = 10;
    
    private int index = 6;
    private int anindex = 1;
    private int side = 0; // 0 = right 1 = left
    private int speed = 3;
    
    private int dropNum = 0;
    
    private boolean died = false;
    /**
     * Act - do whatever the Vehicle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Vehicle(){
        moving = new GreenfootImage[2][4];
        
        for(int i = 1; i <= 3; i++){
            GreenfootImage right = new GreenfootImage("vehicle_"+i+".png");
            moving[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("vehicle_"+i+".png");
            left.mirrorHorizontally();
            moving[1][i-1] = left;
        }
    }
    
    public void act()
    {
        // Add your action code here.
        if(start){
            if(index <= 0){
                setImage("vehicle_1.png");
                getImage().scale(176/size,125/size);
                size--;
                if(size <= 0){
                    start = false;
                    size = 1;
                }
                index = 3;
            }else {
                index --;
            }
            
        }else if(dropNum >= 5){
            if(index <= 0){
                setImage("vehicle_1.png");
                getImage().scale(176/size,125/size);
                size++;
                if(size >=10){
                    start = false;
                    died = true;
                }
                index = 3;
            }else {
                index --;
            }
        }else{
            move(speed);
            if(index <= 0){
                animation();
                index = 3;
            }else {
                index --;
            }
            
            if(Greenfoot.getRandomNumber(500) < 1){
                MyWorld world= (MyWorld)getWorld();
                world.addKnife(1,getX());
                dropNum ++;
            }
            touchWall();
        }
        if(died){
            MyWorld world= (MyWorld)getWorld();
            world. EnemieNum(-1);
            getWorld().removeObject(this);
        }
    }
    
    //animation change 
    private void animation(){
        if(anindex >= 3){
            anindex = 1;
        }
        setImage(moving[side][anindex]);
        anindex ++;
    }
    
    //touching wall and change position
    private void touchWall(){
        List<BackGround> s = getWorld().getObjects(BackGround.class);
        if(getX() <= 20){
            speed = 5;
            side = 0;
        }else if(s.get(0).getBackWidth() - 200 <= getX()){
            speed = -5;
            side = 1;
        }
    }
    
    //movemernt backrond
    public void movement(int i){
        setLocation(getX() + i,getY());
    }
}
