import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ghost here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ghost extends Satan
{
    /**
     * Act - do whatever the Ghost wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage[][] goust;
    private int sx = 128;
    private int sy = 68;

    private boolean rotation = true;
    private int waitTime = 100;
    private int index = 0;
    private int intervel = 0;
    private int side;

    private boolean died = false;
    private int waitDie = 20;
    
    private int dAbs;
    private int yAbs;
    public Ghost(){
        setImage("ghost1.png");
        goust = new GreenfootImage[2][5];
        for(int i = 1; i <= 5; i++){
            GreenfootImage right = new GreenfootImage("ghost"+i+".png");
            right.scale(sx,sy);
            goust[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("ghost"+i+".png");
            left.mirrorVertically();
            left.scale(sx,sy);
            goust[1][i-1] = left;
        }
    }

    public void act()
    {
        // Add your action code here.
        if(rotation){
            rotation();
            rotation = false;
        }
        waitTime --;
        go();
        if(died){
            if(waitDie <= 0){
                getWorld().removeObject(this);
            }else{
                waitDie --;
            }
        }else{
            touch();
        }
    }

    private void rotation(){
        waitTime = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(6);
        MyWorld world= (MyWorld)getWorld();
        int x = getX() ,y = getY();
        if(getX() > world.getPlayerX()){
            side = 1;
        }else{
            side = 0;
        }
        setImage(goust[side][index]);
        if(world !=null){
            sx = world.getPlayerX();
            sy = world.getPlayerY();
        }
        setRotation((int)Math.toDegrees(Math.atan2((sy - y), (sx - x))));
    }

    private void go(){
        if(waitTime <= 0){
            move(6);
            setImage(goust[side][index]);
            if(intervel <= 0){
                if(index >= 4){
                    index = 0;
                }else{
                    index ++;
                }
                intervel = 3;
            }else{
                intervel --;
            }
        }
    }

    private void touch(){
        MyWorld world= (MyWorld)getWorld();
        dAbs = Math.abs(getX() - world.getPlayerX());
        yAbs = Math.abs(getX() - world.getPlayerY());
        if(isTouching(Player.class) && dAbs < 127 && yAbs < 150){
            Greenfoot.playSound("goust.mp3");
            getWorld().addObject(new buff_goust(), getX(), getY());
            world. deductHp(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieDamage(2));
            died = true;
        }else if(getY() > 540 || getX()< 20 || getX() > 1300){
            died = true;
        }
    }
}
