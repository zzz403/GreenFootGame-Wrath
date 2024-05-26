import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class r here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tri_Sword extends skills
{
    /**
     * Act - do whatever the r wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int sx = 120;
    private int sy = 48;
    private int type;

    private boolean rotation = true;
    private int waitTime;
    private int index = 0;
    private int intervel = 0;
    private int side;

    private boolean died = false;
    private int waitDie = 20;

    private int dAbs;
    private int yAbs;

    private int interval = 0, interval2= 2;
    private boolean change = true;
    private boolean change2 = false;
    private int bet = 0;

    private boolean seting = true;
    public Tri_Sword(int i){
        setImage("Evil Warrior_skill1_"+i+".png");
        type = i;
        setRotation(270);

        getImage().scale(sx,sy);
    }

    public void act()
    {
        // Add your action code here.
        if(seting){
            waitTime = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(17) * type;
            seting = false;
        }
        ready();
        go();
        interval--;
        waitTime --;
        MyWorld world= (MyWorld)getWorld();
        if(world.getEnyoHp() <= 0){
            died = true;
        }
        if(died){
            if(waitDie <= 0){
                getWorld().removeObject(this);
            }else{
                waitDie --;
            }
        }

    }

    private void go(){
        if(waitTime <= 0){
            rotation();
            move(6);
            if(died == false){
                touch();
            }
        }
    }

    private void touch(){
        MyWorld world= (MyWorld)getWorld();
        if(getWorld().getObjects(Spiritualism.class).isEmpty()){
            if(isTouching(Player.class)){
                world. deductHp(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieDamage(6));
                died = true;
            }else if(getY() > 600 ||getY() < 10|| getX()< 20 || getX() > 1300){
                died = true;
            }
        }else{
            if(getY() > 600 ||getY() < 10|| getX()< 20 || getX() > 1300){
                died = true;
            }else if(isTouching(Spiritualism.class)){
                died = true;
            }
        }
    }

    private void rotation(){
        if(rotation){
            Greenfoot.playSound("tri.mp3");
            if(getWorld().getObjects(Spiritualism.class).isEmpty()){
                MyWorld world= (MyWorld)getWorld();
                if(world !=null){
                    sx = world.getPlayerX();
                    sy = world.getPlayerY();
                }
                setRotation((int)Math.toDegrees(Math.atan2((sy - getY()), (sx - getX()))));
                rotation = false;
            }else{
                MyWorld world= (MyWorld)getWorld();
                if(world !=null){
                    sx = ((Spiritualism) getWorld().getObjects(Spiritualism.class).get(0)).getX();
                    sy = ((Spiritualism) getWorld().getObjects(Spiritualism.class).get(0)).getY();
                }
                setRotation((int)Math.toDegrees(Math.atan2((sy - getY()), (sx - getX()))));
                rotation = false;
            }
        }
    }

    private void ready(){
        if(getWorld().getObjects(evil_Warrior.class).isEmpty() != true){
            if(waitTime >= 0){
                if(type == 1){
                    setLocation(((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getX(),getY());
                }else if(type == 2){
                    setLocation(((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getX() - 50,getY());
                }else{
                    setLocation(((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getX() + 50,getY());
                }
                if(type == 1){
                    if(interval <=0){
                        if(change == true){
                            setLocation(getX(),((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getY()-70 +bet);
                            bet = bet+2;
                            interval = interval2;
                            if(bet >= 54){
                                change = false;
                            }
                        }else{
                            setLocation(getX(),((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getY()-70 +bet);
                            bet = bet-2;
                            interval =interval2 ;
                            if(bet <= 0){
                                change = true;
                            }
                        }
                    }
                }else{
                    if(interval <=0){
                        if(change2 == true){
                            setLocation(getX(),((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getY() +bet);
                            bet = bet+2;
                            interval = interval2;
                            if(bet >= 54){
                                change2 = false;
                            }
                        }else{
                            setLocation(getX(),((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getY() +bet);
                            bet = bet-2;
                            interval =interval2 ;
                            if(bet <= 0){
                                change2 = true;
                            }
                        }
                    }
                }
            }
        }
    }
}
