import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Z_pic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class z_pic extends Actor
{
    /**
     * Act - do whatever the Z_pic wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int type;
    private int interval;
    private int bet = 0;
    private boolean change;
    public z_pic(int num){
        if (num <= 4){
            setImage("crystal"+num+".png");
        }else if(num == 5){
            setImage("title.png");
        }else if(num == 6){
            setImage("title_light.png");
        }
        type = num;
        interval --;

    }

    public void act()
    {

        // if(Greenfoot.isKeyDown("space")){
            // System.out.println("type"+type+getX()+getY()); 
        // }
        action();
        interval --;
        move();
    }

    private void action(){
        if(type == 1){
            setLocation(670,234);
            click();
        }
        if(type == 2){
            if(interval <= 0){
                if(change == true){
                    setLocation(808,280 + bet);
                    bet = bet+1;
                    interval = 3;
                    if(bet >= 54){
                        change = false;
                    }
                }else{
                    setLocation(808,280 + bet);
                    bet = bet-1;
                    interval = 3;
                    if(bet <= 0){
                        change = true;
                    }
                }
            }
        }
        if(type == 3){
            setLocation(529,123);
        }
        if(type == 4){
            if(interval <= 0){
                if(change == true){
                    setLocation(540,300 + bet);
                    bet = bet+2;
                    interval = 4;
                    if(bet >= 54){
                        change = false;
                    }
                }else{
                    setLocation(540,300 + bet);
                    bet = bet-2;
                    interval =4 ;
                    if(bet <= 0){
                        change = true;
                    }
                }
            }
        }
        if(type == 5){
            setLocation(680,231);
        }
        if(type == 6){
            setLocation(623,226);
        }
    }

    public void click(){
        if(Greenfoot.isKeyDown("space")){
            MyWorld world= (MyWorld)getWorld();
            world.getLevel(1);
        }
    }
    
    public void move(){
        MyWorld world= (MyWorld)getWorld();
        if(world.getLevel() != 0){
            getWorld().removeObject(this);
        }
    }
}

