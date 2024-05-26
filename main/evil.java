import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class evel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class evil extends buff
{
    /**
     * Act - do whatever the evel wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage[][] image;
    private int index = 0;
    private int bet;
    private int sx = 160,sy = 80;
    public evil(){
        setImage("Evil Aura_1.png");
        image = new GreenfootImage[2][4];
        for(int i = 1; i <= 4; i++){
            GreenfootImage right = new GreenfootImage("Evil Aura_"+i+".png");
            right.scale(sx,sy);
            image[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Evil Aura_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            image[1][i-1] = left;
        }
    }

    public void act()
    {
        // Add your action code here.
        animation();
        MyWorld world= (MyWorld)getWorld();
        if(world.getEnyoHp() <= 0){
            getWorld().removeObject(this);
        }
    }

    private void animation(){
        MyWorld world= (MyWorld)getWorld();
        if(world != null){
            if(((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getSide()){
                setLocation(((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getX()- 10,((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getY());
                if(bet <= 1){
                    if(index > 3){
                        index = 0;
                    }
                    setImage(image[0][index]);
                    index ++;
                    bet = 10;
                }else{
                    bet --;
                }
            }else{
                setLocation(((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getX() + 10,((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).getY());
                if(bet <= 1){
                    if(index > 3){
                        index = 0;
                    }
                    setImage(image[1][index]);
                    index ++;
                    bet = 10;
                }else{
                    bet --;
                }
            }
        }
    }
}
