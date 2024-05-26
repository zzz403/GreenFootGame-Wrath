import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ContrlCenter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ContrlCenter extends Actor
{
    /**
     * Act - do whatever the ContrlCenter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private String[] Keys;
    private int[] HP;
    private int[] PlayerDamage;
    private int[] EnemieDamage;
    private int[] PlayerCooling;
    private int[] EnemieCooling;
    private int[] PlayerSkillRange;

    public ContrlCenter(){
        getImage().setTransparency(10);
        
        Keys = new String [10];
        Keys[0] = "k"; // jump
        Keys[1] = "j"; //attack
        Keys[2] = "u"; //skill1
        Keys[3] = "i"; //skill2
        Keys[4] = "h"; //skill3
        Keys[5] = "space"; //ult
        Keys[6] = "1"; //leave
        Keys[7] = "2"; //bell
        
        HP = new int[7]; 

        HP[0] = 100; //Player
        HP[1] = 400; //Demons
        HP[2] = 3500; //Satan
        HP[3] = 250; //reapper
        HP[4] = 4000; //Enyo
        HP[5] = 300; //ball
        HP[6] = 1000; //knife
        
        PlayerDamage = new int[10];

        PlayerDamage[0] = 40; //Attake //35
        PlayerDamage[1] = 40; //Down Attake
        PlayerDamage[2] = 15; //upKill
        PlayerDamage[3] = 100; //skill1 100
        PlayerDamage[4] = 80; //skill2 80
        PlayerDamage[5] = 20; //skill3 
        PlayerDamage[6] = 40; //ult

        EnemieDamage = new int [10];

        EnemieDamage[0] = 7; //Demons
        EnemieDamage[1] = 7; //Satan Attake for 2
        EnemieDamage[2] = 5; //Satan_Ghost
        EnemieDamage[3] = 15; //Satan_doun
        EnemieDamage[4] = 10; //fire
        EnemieDamage[5] = 5; //reaper skill
        EnemieDamage[6] = 15; //evil_triSword
        EnemieDamage[7] = 5; //reaper skill


        PlayerCooling = new int [5];
        
        PlayerCooling[0] = 150; //skill 1
        PlayerCooling[1] = 300; //skill 2
        PlayerCooling[2] = 500; //skill 3
        
        EnemieCooling = new int [30];
        
        EnemieCooling[0] = 20; //demon_recover
        EnemieCooling[1] = 75; //Satan_recover
        EnemieCooling[2] = 2000; //Satan_getDemon
        EnemieCooling[4] = 500; //Satan_getGoust
        EnemieCooling[5] = 1000; //Satan_jump
        EnemieCooling[6] = 0; //Ghost_recover
        EnemieCooling[7] = 1000; //Satan_Fire
        EnemieCooling[8] = 500; //Fire
        EnemieCooling[9] = 400; //FireTime
        EnemieCooling[10] = 200; //reapper kill skill
        EnemieCooling[11] = 30; //reapper skill recover 
        EnemieCooling[12] = 500; // reapper move skill
        
        EnemieCooling[13] = 1000; //evil_skill1
        EnemieCooling[14] = 700; //evil_skill2
        EnemieCooling[15] = 3000; //evil_skill3
        EnemieCooling[16] = 500; //evil_skill4
        
        EnemieCooling[17] = 175; //evil_skill1 recover
        
        
        PlayerSkillRange = new int[10];
        
        PlayerSkillRange[0] = 20;

    }

    public void act()
    {
        // Add your action code here.
    }
    
    
    //return all the keys and vulue
    public String getKeys(int i){
       return Keys[i]; 
    }
    
    public int getHP(int i){
       return HP[i]; 
    }
    
    public int getEnemieDamage(int i){
       return EnemieDamage[i]; 
    }
    
    public int getPlayerCooling(int i){
       return PlayerCooling[i]; 
    }
    
    public int getEnemieCooling(int i){
       return EnemieCooling[i]; 
    }
    
    public int getPlayerSkillRange(int i){
       return PlayerSkillRange[i]; 
    }
    
    public int getPlayerDamage(int i){
        return PlayerDamage[i]; 
    }
}
