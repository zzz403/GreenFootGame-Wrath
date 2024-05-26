import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ReplyLeave here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ReplyLeave extends column
{
    /**
     * Act - do whatever the ReplyLeave wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int use = 6;
    private int useNow = 0;
    private int moveY = 0;
    private boolean upDown = true;
    private boolean animation = false;
    private int animationTime = 20;
    
    private boolean useTime = false;
    public ReplyLeave(){
        setImage("replyLeave.png");
        getImage().scale(32,32);
    }

    public void act()
    {
        // Add your action code here.
        move();
        show();
        use();
    }

    private void use(){
        if(Greenfoot.isKeyDown(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(6)) ){
            if(useNow >= use){
                ((Player) getWorld().getObjects(Player.class).get(0)).changeTutprial(6);
                useNow =0;
                animationTime = 20;
                animation = true;
            }
        }
    }

    private void move(){
        MyWorld world= (MyWorld)getWorld();
        if(animation != true){

            if(world.get_side("Player",(int)0)){
                setLocation(world.getPlayerX() - 50,world.getPlayerY() - 5+moveY);
            }else{
                setLocation(world.getPlayerX() + 50,world.getPlayerY() - 5+moveY);
            }

            if(upDown == true){
                moveY ++;
                if(moveY > 15){
                    upDown = false;
                }
            }else if(upDown == false){
                moveY --;
                if(moveY < -15){
                    upDown = true;
                }
            }
        }else{
            if(animationTime <=0){
                world.deductHp(-30);
                getImage().scale(32,32);
                animation = false;
            }else{
                animationTime--;
                getImage().scale(128,128);
                setLocation(world.getPlayerX(),world.getPlayerY());
            }
        }
    }

    private void show(){
        if(useNow < use && animation == false){
            getImage().setTransparency(0);
            useTime = false;
        }else{
            getImage().setTransparency(225);
            useTime = true;
        }
    }

    public boolean useTime(){
        return useTime;      
    }
    
    public void add(){
        if(animation != true){
            useNow++;
        }
    }
}
