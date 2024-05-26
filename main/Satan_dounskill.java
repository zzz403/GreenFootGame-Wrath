import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Satan_dounskill here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Satan_dounskill extends Satan
{
    /**
     * Act - do whatever the Satan_dounskill wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int skillTime = 75;
    private boolean damage = false;
    private boolean died = false;
    private boolean attack;
    private boolean start = true;
    private boolean setLocation = true;
    public Satan_dounskill(){
        setImage("Demon2_down_ready.png");
        getImage().scale(100,100);
    }

    
    public void act()
    {
        // Add your action code here.
        if(start){
            if(setLocation){
                MyWorld world= (MyWorld)getWorld();
                if(world!= null){
                    setLocation(world.getPlayerX(),world.getPlayerY());
                }
            }
        }else{
            skillTime --;
            if(attack){
                    if(isTouching(Player.class)){
                        MyWorld world= (MyWorld)getWorld();
                        world. deductHp(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieDamage(3));
                        attack = false;
                    }
                }
            if(skillTime <= 20 && skillTime > 0){
                setImage("Demon2_downskill_2.png");
                damage = true;
                
            }
            if (skillTime <= 0){
                died = true;
            }
        }
        MyWorld world= (MyWorld)getWorld();

        if(world!= null){
            if((world.getSatanHp() <= 0)){
                died = true;
            }
        }
        if (died){
                getWorld().removeObject(this);
            }
    }

    //start
    public void start(){
        setImage("Demon2_downskill_2.png");
        setLocation(((Satan) getWorld().getObjects(Satan.class).get(0)).getX(),((Satan) getWorld().getObjects(Satan.class).get(0)).getY() + 40);
        start = false;
    }

    //set location
    public void setLocation(){
        setLocation = false;
    }
}
