import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Wushuang2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Wushuang extends buff
{
    /**
     * Act - do whatever the Player2 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage[][] image;
    private int index = 0;
    private int bet;
    public Wushuang(){
        setImage("Player_wushuang_2.png");
        image = new GreenfootImage[2][4];
        for(int i = 1; i <= 4; i++){
            GreenfootImage right = new GreenfootImage("Player_wushuang_"+i+".png");
            image[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Player_wushuang_"+i+".png");
            left.mirrorHorizontally();
            image[1][i-1] = left;
        }
    }

    public void act()
    {
        // Add your action code here.
        animation();
        MyWorld world= (MyWorld)getWorld();
        if(world.getUlt() != true){
            getWorld().removeObject(this);
        }
    }

    private void animation(){
        MyWorld world= (MyWorld)getWorld();
        if(world != null){
            if(world.get_side("Player",(int)0)){
                setLocation(world.getPlayerX() - 10,world.getPlayerY());
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
                setLocation(world.getPlayerX() + 10,world.getPlayerY());
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
