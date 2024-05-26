import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Damage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Damage extends Actor
{
    private int damage;
    private int tern;
    private boolean Tern = false;
    private boolean setting = true;

    private int index = 30;
    /**
     * Act - do whatever the Damage wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Damage(int num){
        damage = num;
    }

    //when get the damag twice the damage will retrun to a new damage number
    public Damage(int num,int ternNum){
        damage = num;
        Tern = true;
        tern = ternNum;
    }

    public void act()
    {
        // Add your action code here.
        if(setting){
            if(Tern){
                if(tern == 0){
                    setImage("damage_normal_"+(damage%10)+".png");
                }else{
                    setImage("damage_normal_"+((damage/(10*tern))%10)+".png");
                    getWorld().addObject(new Damage(damage,(tern - 1)),getX() + 20,getY());
                }
                
            }else{
                if(damage < 10){
                    setImage("damage_normal_"+damage+".png");
                    
                }else if(damage < 100) {
                    setImage("damage_normal_"+((damage/10)%10)+".png");
                    getWorld().addObject(new Damage(damage,0),getX() + 23,getY());
                }else if(damage < 1000){
                    setImage("damage_normal_"+((damage/100)%10)+".png");
                    getWorld().addObject(new Damage(damage,1),getX() + 23,getY());
                }
            }
            getImage().scale(30,30);
            setting = false;
        }

        index --;
        if(index%3 == 0){
            setLocation(getX(),getY() - 2);
        }

        if(index <- 0){
            getWorld().removeObject(this);
        }
    }
}
