import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class q here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RecoveryBall extends skills
{
    /**
     * Act - do whatever the q wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int [][] cooling;
    private int [] damage;
    private boolean seting = true;
    private int hp = 300;
    public RecoveryBall(){
        cooling = new int [20][2];
        damage = new int [20];
        setImage("Evil Warrior_skill4_1.png");
    }

    public void act()
    {
        for(int i = 1; i <= 19; i++){
            cooling[i-1][0] --;
        }
        seting();
        touchingSkill();
        if(hp < 300 && hp >= 200){
            setImage("Evil Warrior_skill4_2.png");
        }else if(hp < 200 && hp >= 100){
            setImage("Evil Warrior_skill4_3.png");
        }else if(hp < 100 && hp > 0){
            setImage("Evil Warrior_skill4_4.png");
        } 
        if(hp <= 0){
            for(int i=1; i<= 10;i++){
                getWorld().addObject(new  EXP  (), getX(), getY());
            }
            for(int i=1; i<= 4;i++){
                getWorld().addObject(new  replyP  (), getX(), getY());
            }
            for(int i=1; i<= 2;i++){
                getWorld().addObject(new  Peach  (), getX(), getY());
            }
            if(getWorld().getObjects(evil_Warrior.class).isEmpty() != true){
                ((evil_Warrior) getWorld().getObjects(evil_Warrior.class).get(0)).ball();
            }
            getWorld().removeObject(this);
        } 

        // Add your action code here.
    }

    private void seting(){
        if(seting){
            setLocation(400,160);
            hp =((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(5);
            cooling[0][1] = 14; //index of animation
            cooling[1][1] = Greenfoot.getRandomNumber(10) + ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(1);;//recover
            cooling[2][1] = 40;//for skill 1 and 2 cooling
            cooling[4][1] = 13;//for attack and down attack cooling
            cooling[6][1] = 10;//Fly Time
            cooling[7][1] = 2;//Fly recover
            cooling[8][1] = 20;//recover for all exct hit
            cooling[9][1] = 10; //hit in 
            cooling[10][1] = 4444; //died
            cooling[11][1] = 20; //died in
            cooling[12][1] = 20; //upkill
            cooling[13][1] = 20; //ice_1
            cooling[14][1] = 300; //after get attake
            cooling[15][1] = 70; //get hit time
            cooling[16][1] = 200; //downskill recover

            damage[0] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieDamage(1); // demo damage
            for(int i = 0; i <= 6; i++){
                damage[i+1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getPlayerDamage(i);
                //1 Attake
                //2 Down Attake
                //3 upKill
                //4 skill1
                //5 skill2
                //6 skill3
            }
            seting  = false;
        }
    }

    private void touchingSkill(){
        if(Player1_Skill1.class != null){
            if(isTouching(Player1_Skill1.class)){
                Greenfoot.playSound("get_hurt3.mp3");
                if(cooling[2][0] <= 0){
                    hp = hp - damage[4];
                    //get hit
                    cooling[2][0] = cooling[2][1];
                }
            }
        }

        if(Player1_Skill2.class != null){
            if(cooling[3][0] <= 0){
                if(isTouching(Player1_Skill2.class)){
                    Greenfoot.playSound("get_hurt4.mp3");
                    hp = hp - damage[5];
                    setLocation(getX(),getY()-20);
                    cooling[6][0] = cooling[6][1]; // uptime
                    cooling[3][0] = cooling[2][1];
                }
            }  
        }

        if(Player1_Attake.class != null){
            if(cooling[4][0] <= 0){
                if(isTouching(Player1_Attake.class)){
                    Greenfoot.playSound("get_hurt0.mp3");
                    hp = hp - damage[1];
                    cooling[4][0] = cooling[4][1];
                }
                if(isTouching(Player1_Attake_ult.class)){
                    Greenfoot.playSound("get_hurt0.mp3");
                    hp = hp - damage[7];
                    cooling[4][0] = cooling[4][1];
                }
            }

        }

        if(Player1_DropAttake.class != null){    
            if(cooling[5][0] <= 0){
                if(isTouching(Player1_DropAttake.class)){
                    hp = hp - damage[2];
                    cooling[5][0] = cooling[4][1];
                }
            }
        }

        if(Player1_Upskill.class != null){
            if(cooling[12][0] <= 0){
                if(isTouching(Player1_Upskill.class)){
                    Greenfoot.playSound("get_hurt2.mp3");
                    hp = hp - damage[3];
                    cooling[12][0] = cooling[12][1];
                }
            }
        }

        if(Player1_Skill3.class != null){
            if(cooling[13][0] <= 0){
                if(isTouching(Player1_Skill3.class)){
                    Greenfoot.playSound("get_hurt5.mp3");
                    hp = hp - damage[6];
                    getWorld().addObject(new buff_ice  (), getX(), getY());
                    cooling[13][0] = cooling[13][1];
                }
            }
        }
    }
}
