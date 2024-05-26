import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Boss_HP here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boss_HP extends Enemie_boss
{
    /**
     * Act - do whatever the HP wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private double hp_O;
    private double hp;
    private int hp2;
    private int colar;
    private int boss;

    int type;
    public Boss_HP(int a ,int b){
        switch (b) {
            case 1:
                setImage("hpBar_bg.png");
                setLocation(660,50);
                break;
            case 2:
                setImage("hpBar_green.png");
                break;
            default:
                break;

        }

        boss = a;
        type = b;
        getImage().scale(600,40);
    }

    public void act()
    {
        // Add your action code here.
        if(boss == 1){
            satan();
        }else if(boss == 2){
            enyo();
        }
    }
    
    //hp change for satan
    private void satan(){
        MyWorld world= (MyWorld)getWorld();
        if(world!= null){
            if((world.getSatanHp() > 0)){
                if(type == 2){
                    hp = ((1 - (world.getSatanHp() /(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2)))) * 600 );
                    hp2 = (int)Math.ceil(hp);
                    changeColar((int)world.getSatanHp());
                    if(hp2 < 550){
                        getImage().scale((600 - hp2),40);
                        setLocation(660 - (hp2 / 2),50);
                    }else{
                        getImage().scale((600 - 590),40);
                        setLocation(660-(590 / 2), 50);
                    }
                }
            }else{
                getWorld().removeObject(this);
            }
        }
    }

    //change colar
    private void changeColar(int hp){
        if(boss == 1){
            MyWorld world= (MyWorld)getWorld();
            if(world.getSatanHp() < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 2 && 
            (world.getSatanHp() < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 5)){
                setImage("hpBar_yellow.png");
                colar = 2;
            }else if(world.getSatanHp() < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 5){
                setImage("hpBar_red.png");
                colar = 3;
            }
        }else{
            MyWorld world= (MyWorld)getWorld();
            if(world.getEnyoHp() < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 2 && 
            (world.getEnyoHp() < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 5)){
                setImage("hpBar_yellow.png");
                colar = 2;
            }else if(world.getEnyoHp() < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 5){
                setImage("hpBar_red.png");
                colar = 3;
            }
        }
    }

    //hp change for enyo
    private void enyo(){
        MyWorld world= (MyWorld)getWorld();
        if(world!= null){
            if((world.getEnyoHp() >= 0)){
                if(type == 2){
                    hp = ((1 - (world.getEnyoHp() /(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(4)))) * 600 );
                    hp2 = (int)Math.ceil(hp);
                    changeColar((int)world.getEnyoHp());
                    if(hp2 < 550){
                        getImage().scale((600 - hp2),40);
                        setLocation(660 - (hp2 / 2),50);
                    }else{
                        getImage().scale((600 - 590),40);
                        setLocation(660-(590 / 2), 50);
                    }

                }
            }else{
                getWorld().removeObject(this);
            }
        }
    }
    
    public void movement(int i){
        
    }
}
