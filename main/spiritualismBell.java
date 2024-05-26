import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class spiritualismBell here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class spiritualismBell extends column
{
    private int use = 2000;
    private int useNow = 2000;
    private int moveY = 0;
    private boolean upDown = true;
    private boolean animation = false;
    private int animationTime = 20;
    
    private boolean useTime = false;
    public spiritualismBell(){
        setImage("spiritualismBell.png");
        getImage().scale(32,32);
    }

    public void act()
    {
        // Add your action code here.
        move();
        show();
        use();
        useNow ++;
    }

    private void use(){
        if(Greenfoot.isKeyDown(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(7)) ){
            if(useNow >= use){
                ((Player) getWorld().getObjects(Player.class).get(0)).changeTutprial(7);
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
                getWorld().addObject(new Spiritualism(), getX(), getY());
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
}
