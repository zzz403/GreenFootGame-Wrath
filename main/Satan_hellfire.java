import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Satan_hellfire here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Satan_hellfire extends Satan
{
    /**
     * Act - do whatever the Satan_hellfire wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int index = 1;
    private int bet;
    private int bet_int = 30;
    private int attackTime;
    private int attack;
    private boolean touched = false;

    public Satan_hellfire(){
        setImage("Demon2_Hellfire_1.png");
    }

    public void act()
    {
        // Add your action code here.
        if(bet<= 0){
            kill();
        }
        attack --;
        bet --;
        attackTime --;
        MyWorld world= (MyWorld)getWorld();
        if(world!= null){
        if((world.getSatanHp() <= 0)){
        getWorld().removeObject(this);
        }
        }
    }

    //after killed
    private void kill(){
        if(attack <= 0){
            if(index >= 5){
                if(attackTime <= 0){
                    attack = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(8);
                    setLocation(getX(),600);
                    index = 1;
                    setImage("Demon2_Hellfire_"+1+".png");
                }else{
                    setLocation(getX(),340);
                    setImage("Demon2_Hellfire_5.png");
                    if(touched == false){
                        touch();
                    }
                }
            }else{
                bet = bet_int;
                setImage("Demon2_Hellfire_"+index+".png");
                touched = false;
                index ++;
                attackTime = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(9);
            }
        }
    }

    //after touching
    private void touch(){
        if(isTouching(Player.class)){
            MyWorld world= (MyWorld)getWorld();
            world. deductHp(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieDamage(4));
            touched = true;
        }
    }
}
