import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Enemie_min here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemie_min extends Actor
{
    private int hp;
    private int hp_last;
    
    private HP hpObject;
    
    private boolean side;
    
    private int [][] cooling;
    private int [] damage;
    
    private boolean attacking = false;
    /**
     * Act - do whatever the Enemie_min wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private String a = "0";
    
    private int shiting = 0;
    
    private boolean setFall = true;
    public Enemie_min (){
        cooling = new int [20][2];
        damage = new int [20];
    }

    public void act()
    {
        // Add your action code here.
        
    }
    
    //don't touch the wall
    public void touchWall(){
        List<BackGround> s = getWorld().getObjects(BackGround.class);
        if(getX() <= 0){
            setLocation(0,getY());
        }else if(s.get(0).getBackWidth() <= getX()){
            setLocation(s.get(0).getBackWidth(),getY());
        }
    }

    //setting one time 
    public void seting(int num){
        hp = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(num);
        
        hp_last = hp;
        for(int i = 1; i <= 19; i++){
            cooling[i-1][0] = 0;
        }
        cooling[0][1] = 5; //index of animation
        cooling[1][1] = Greenfoot.getRandomNumber(10) + ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(0);//recover
        cooling[2][1] = 40;//for skill 1 and 2 cooling
        cooling[4][1] = 13;//for attack and down attack cooling
        cooling[6][1] = 10;//Fly Time
        cooling[7][1] = 0;//Fly recover
        cooling[8][1] = 20;//recover for all exct hit
        cooling[9][1] = 4; //hit in 
        cooling[10][1] = 4444; //died
        cooling[11][1] = 20; //died in
        cooling[12][1] = 20; //upkill
        cooling[13][1] = 20; //ice_1
        cooling[14][1] = 300; //after get attake
        cooling[15][1] = 70; //get hit time

        damage[0] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieDamage(0); // demo damage
        for(int i = 0; i <= 6; i++){
            damage[i+1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getPlayerDamage(i);
            //1 Attake
            //2 Down Attake
            //3 upKill
            //4 skill1
            //5 skill2
            //6 skill3
        }
    }
    
    //if skill here and go
    public void touchingSkill(int[][] cooling){
        shiting --;
        if(shiting >= 0){
            Greenfoot.setSpeed(47);
        }else{
            Greenfoot.setSpeed(50);
        }
        if(cooling[2][0] <= 0){
            if(isTouching(Player1_Skill1.class)){
                shiting = 3;
                hp = hp - damage[4];
                Greenfoot.playSound("get_hurt3.mp3");
                if(side){
                    setLocation(getX() + 15, getY());
                }else{
                    setLocation(getX() - 15, getY());
                }
                //get hit
                cooling[2][0] = cooling[2][1];
                supportSkillTouch();
            }
        }else if(isTouching(Player1_Skill1.class)){
            Greenfoot.playSound("get_hurt3.mp3");
            if(side){
                setLocation(getX() + 15, getY());
            }else{
                setLocation(getX() - 15, getY());
            }
        }  
        if(cooling[3][0] <= 0){
            
            if(isTouching(Player1_Skill2.class)){
                Greenfoot.playSound("get_hurt4.mp3");
                hp = hp - damage[5];
                setLocation(getX(),getY()-20);
                cooling[6][0] = cooling[6][1]; // uptime
                cooling[3][0] = cooling[2][1];
                supportSkillTouch();
            }
        }
        if(cooling[4][0] <= 0){
            if(isTouching(Player1_Attake.class)){
                shiting = 3;
                Greenfoot.playSound("get_hurt0.mp3");
                hp = hp - damage[1];
                cooling[4][0] = cooling[4][1];
                supportSkillTouch();
            }
            if(isTouching(Player1_Attake_ult.class)){
                shiting = 4;
                Greenfoot.playSound("get_hurt0.mp3");
                hp = hp - damage[7];
                cooling[4][0] = cooling[4][1];
                supportSkillTouch();
            }
        }
        if(cooling[5][0] <= 0){
            if(isTouching(Player1_DropAttake.class)){
                hp = hp - damage[2];
                cooling[5][0] = cooling[4][1];
                supportSkillTouch();
            }
        }
        if(cooling[12][0] <= 0){
            if(isTouching(Player1_Upskill.class)){
                Greenfoot.playSound("get_hurt2.mp3");
                hp = hp - damage[3];
                setLocation(getX(),getY()-200);
                cooling[12][0] = cooling[12][1];
                supportSkillTouch();
            }
        }
        if(cooling[13][0] <= 0){
            if(isTouching(Player1_Skill3.class)){
                Greenfoot.playSound("get_hurt5.mp3");
                hp = hp - damage[6];
                getWorld().addObject(new buff_ice  (), getX(), getY());
                cooling[13][0] = cooling[13][1];
                supportSkillTouch();
            }
        }
    }
    
    // after touch skill
    private void supportSkillTouch(){
        
        
        getWorld().addObject(new Damage(hp_last - hp),getX(),getY() - 20);
        hp_last= hp;
        if(cooling[14][0] < 0){
            cooling[8][0] = cooling[8][1];
            if( cooling[15][0] + cooling[15][1] < 0){
                cooling[15][0] = 0;
            }else{
                cooling[15][0] = cooling[15][0] + cooling[15][1];
            }
            if(cooling[15][0] >= 50){
                cooling[14][0] = cooling[14][1];
            }

        }
        //get hit

    }
    
    
    //get player loction adn move
    public void getPlayerLocation(int moveX){
        MyWorld world= (MyWorld)getWorld();
        if(cooling[10][0] <= 0 && attacking == false){
            if(world != null){
                int pX = world.getPlayerX();
                int pY = world.getPlayerY();
                
                if(cooling[8][0] <= 0){
                    if(pX - getX() <= 0){
                        side = true;
                        setLocation(getX() - moveX, getY());
                    }else{
                        side = false;
                        setLocation(getX() + moveX, getY());
                    }
                }
            }
        }
        
    }
    
    public void touchPlayer(){
        if(isTouching(Player.class)){
            MyWorld world= (MyWorld)getWorld();
            world. deductHp(damage[0]);
        }
    }
    
    //fall
    public void fall(int height){
            //Actor Flooring = getOneObjectAtOffset(0, getImage().getHeight()/2, Flooring.class);
        //System.out.println(getImage().getHeight()/2);
        if(getOneObjectAtOffset(0, getImage().getHeight()/2, Flooring.class)!=null ){
            if(setFall){
                while(getOneObjectAtOffset(0, getImage().getHeight()/2, Flooring.class)!=null){
                    setLocation(getX(),getY()-1);
                }
                setLocation(getX(),getY()+1 + height);
                setFall = false;
            }
        }else{ 
            if(cooling[7][0] <= 0){
                if(cooling[6][0] <= 0)
                    setLocation(getX(),getY()+10);
                else{
                    setLocation(getX(),getY()-20);
                    
                }
                setFall = true;
                cooling[7][0] = cooling[7][1];
            }
        }
    }
    
    public void changeSetFall(){
        setFall = true;
    }
    
    public void movement(int i){
        setLocation(getX() + i,getY());
    }
    
    public int[][] getSetting(){
        return cooling;
    }
    
    public int[] getDamage(){
        return damage;
    }
    
    public boolean getSide(){
        return side;
    }
    
    public int getHp(){
        return hp;
    }
}
