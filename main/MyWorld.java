import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.util.List;
import java.util.ArrayList;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    //Game base initialization
    private int Level = 0; //set World Level: max 3
    private int LastLevel = 100; //judge is level changed
    private boolean creatPlayer = false; //creat Plaer just for once
    private int enemieNum = 0; // set enemie number in the screen
    private int enemieTime = 0; //set the diffrent anemie every time
    private boolean StartGame = false;

    //Player Basic Settings
    private double PlayerHp = 100; //set Player Hp
    private double PlayerShield = 0; //set Player ult shield
    private boolean invincible = false;//Invincible when the Player uses skill2
    private int Exp = 0; //Determine if the Player can use ult
    private boolean ult = false; //determine if the Player using ult
    private int Expdec; //Used to determine the time of ult

    //bgms in the game
    GreenfootSound sound0 = new GreenfootSound("bgm_level_0.mp3");
    GreenfootSound sound1 = new GreenfootSound("bgm_level_1.mp3");
    GreenfootSound sound2 = new GreenfootSound("bgm_level_2.mp3");
    GreenfootSound sound3 = new GreenfootSound("bgm_level_3.mp3");

    //Wall design
    private int wallType = 1; //Distinguish different walls
    private int wallX,wallY; //wall's x and y coordinates
    private int cooling;//player skill cooldowns
    private boolean side;//the direction Players faced
    private Flooring wall = new Flooring(wallType); 

    //The amount of HP of different monsters
    private double satanHP;
    private double enyoHP;

    private int forReturn;
    private String name;
    private String[] text = new String[5];//text

    int numbersss = 0;
    
    private String plaerNames[] = new String[10];
    private int plaerTimes[] = new int[10];
    private String plaerName = "";
    private int plaerTime = 0;
    
    public MyWorld()
    {    
        super(1260, 640, 1,false); 
        setPaintOrder(Vehicle.class,Skill_icon.class,buff_goust.class,z_Light.class,Player.class,column.class,HP.class,Damage.class,Wushuang.class,
            Icon.class,buff.class,Satan.class,evil.class,evil_Warrior.class,Tri_Sword.class,Enemie_min.class,
            Step.class,Flooring.class,Enemie_boss.class,Portal.class,BackGround.class); 
        WorldLevel(Level);//World initialization
        Greenfoot.setSpeed(50);
    }

    //For real-time refreshing of monsters, player ult and text parts in the map
    public void act(){
        if(Level != LastLevel){
            LastLevel = Level;
            WorldLevel(Level);
            enemieTime = 0;
        }
        if(Level !=0 ){
            reflash_enemie();
        }
        ultS();
        showText();
        plaerTime++;
        
    }

    //Store records
    public void write() {
        String data = "";
        numbersss ++;
        try {
          File myObj = new File("Marks.txt");
          Scanner myReader = new Scanner(myObj);
          int j = 0;
          int k = 0;
          while (myReader.hasNextLine()) {
            if(k == 0){
                plaerNames[j] = myReader.nextLine();
                k = 1;
            }else{
                plaerTimes[j] = Integer.valueOf(myReader.nextLine());
                k = 0;
                j++;
            }
          }
          plaerNames[j] = plaerName;
          plaerTimes[j] = plaerTime;
          
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        
        if(plaerNames.length > 2){
            Arrays.sort(plaerTimes);
            int j = 0;
            for(int i = 0;i <= 3; i++){
                
            }
        }
            
        
        try{
            FileWriter myWriter = new FileWriter("Marks.txt");
            myWriter.write(data + "\n" + numbersss);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
              e.printStackTrace();
        }
      }
    
    
    //Refresh monsters when the number of monsters present is 0
    public void reflash_enemie(){
        if(enemieNum <= 0){
            enemieTime = enemieTime + 1;//Define different monsters that refresh in different states
            switch (Level) {
                case 1: 
                    Level_1_enemy();
                    break;
                case 2:
                    Level_2_enemy();
                    break;
            }
        }
    }

    //add hp bar
    private void newHP(){
        List<Enemie_min> enemie = getObjects(Enemie_min.class);
        List<HP> hp = getObjects(HP.class);
        for(int i = 0; i < hp.size(); i++){
            if(hp.get(i).getName() == "enemie"){
                removeObject(hp.get(i));
            }
        }
        for(int i = 0; i < enemie.size(); i++){
            addObject(new HP("enemie",0,1,i),100,540);
            addObject(new HP("enemie",0,2,i),100,540);
            
        }
    }

    //add demons
    public void addDemons(int num){
        for (int i = 1; i <=num; i++){
            addObject(new  Enemie_min_demons(),Greenfoot.getRandomNumber(1000)+300,350);
            enemieNum ++;
        }
        newHP();
    }
    
    //add reapper 
    public void addReapper(int num){
        for (int i = 1; i <=num; i++){
            addObject(new  Enemie_min_reapper(),Greenfoot.getRandomNumber(1100)+200,350);
            enemieNum ++;
        }
        newHP();
    }
    
    //add knife
    public void addKnife(int num,int X){
        //addObject(new  Enemie_min_knife(),Greenfoot.getRandomNumber(1100)+200,350);
        for (int i = 1; i <=num; i++){
            addObject(new  Enemie_min_knife(),X,0);
            enemieNum ++;
        }
        newHP();
    }
    
    //Define different monsters that refresh in different states
    private void Level_1_enemy(){
        if(enemieTime == 1){
            addDemons(2);
            //addKnife(1,1300);
            //addObject(new Vehicle(),1100,100);
            //enemieNum ++;
        }else if(enemieTime == 2){
            addDemons(3);
            addReapper(2);
        }else if(enemieTime == 3){
            addDemons(5);
            addReapper(2);
        }else if(enemieTime == 4){
            text[0] = "Satan";
            addObject(new Satan(),800,470);
            addObject(new Boss_HP(1,1),660,50);
            addObject(new Boss_HP(1,2),660,50);
            enemieNum ++;
        }else if(enemieTime == 5){
            addObject(new Portal(1),700,490);
            
            
        }
    }

    //level 2 enemy
    private void Level_2_enemy(){
        if(enemieTime == 1){
            addObject(new Vehicle(),1100,100);
            
            newHP();
            enemieNum++;
        }else if(enemieTime == 2){
            text[0] = "Enyo";
            addObject(new evil_Warrior(),400,100);
            addObject(new Boss_HP(2,1),660,50);
            addObject(new Boss_HP(2,2),660,50);
            addObject(new evil(),600,100);
            addObject(new YinYang_Mirror(),400,100);
            enemieNum++;
        }else if(enemieTime == 3){
            addObject(new Portal(2),700,490);
        }
    }

    //text part
    public void changeText(String type,String s){
        switch(type){
            case "BossName":
                text[0] = s;
                break;
            case "Slogan" :
                text[1] = s;
                break;
            case "Start" :
                text[2] = s;
                break;
                case"skill":
                text[3] = s;
                break;
        }
    }

    public void showText(){
        showText(text[0],300,50);
        showText(text[1],700,300);
        showText(text[2],675,500);
        if(getObjects(Player.class).isEmpty()!= true){
            showText(text[3],600,300);
        }
    }

    //set locations for skill icons
    public void skillLocation(){
        for (int i =1; i <5; i++){
            // name = "skill_"+ i ;
            addObject(new Skill_icon(i),0,0);
        }
        addObject(new HP("EXP",0,1),167,622);
    }

    //add player
    public void addPlayer(){
        if(creatPlayer == false){
            addObject(new Player(),300,540);
            addObject(new HP("Player",0,3),120,70);
            addObject(new HP("Player",0,2),135,58);
            addObject(new HP("Player",0,1),120,70);
            addObject(new ContrlCenter(),1275,50);
            addObject(new ReplyLeave(),1275,50);
            skillLocation();
            creatPlayer = true;
            PlayerHp = ((getObjects(ContrlCenter.class).get(0)).getHP(0)); 
        }
    }

    //world level and change music
    public void WorldLevel(int Level){
        this.Level = Level;
        setBackground​("ground"+ Level + ".png");
        if(Level != 0){
            addPlayer();
            for (int i =0; i <9; i++){
                wallType = 1 ;
                wallX = i*200;
                wallY = 600;
                addObject(new Flooring(wallType),wallX,wallY);
            }
        }

        switch (Level) {
            case 0 :
                changeText("Start","Press [space] to start the game");
                setBackground​("ground0.png");
                addObject(new  z_pic(1),640,320);
                addObject(new  z_pic(5),640,320);
                for (int i =2; i <= 4; i++){
                    addObject(new  z_pic(i),640,320);
                }

                sound0.playLoop();
                break;
            case 1: 
                addObject(new BackGround(),776,320);
                changeText("Start",null);
                for (int i = 240; i <=463; i+=223){
                    wallType = 2 ;
                    wallX = i;
                    wallY = 400;
                    addObject(new Step(),wallX,wallY);
                }
                wallType = 2;
                wallX = 900;
                wallY = 400;
                addObject(new Step(),wallX,wallY);

                sound1.playLoop();
                sound0.stop();

                break;
            case 2: 

                addObject(new spiritualismBell(),660,50);
                newStep(400, 200);
                newStep(1000, 300);

                sound2.playLoop();
                sound1.stop();
                
                write();
                
                break;
            case 3: 

                addObject(new sub_ti(2), 650, 200);

                sound3.playLoop();
                sound2.stop();

                break;
            default:
                break;
        }
    }

    //creat new step
    public void newStep(int x, int y){
        wallX = x;
        wallY = y;
        addObject(new Step(),wallX,wallY);
    }

    //when player use ult 
    public void ultS(){
        if(ult == true){
            if(Exp <= 0){
                ult = false;
            }else{
                if(Expdec<=0){
                    Exp --;
                    Expdec = 4;
                }else{
                    Expdec --;
                }
            }

        }
    }

    //get Player
    public boolean get_side(String s, int n){
        switch (s) {
            case "Player":
                List<Player> p = (List<Player>)getObjects(Player.class);
                if(p.size()!=0){
                    side =  p.get(0).getSide();
                }
                break;
            case "Enemie_min_demons":
                List<Enemie_min_demons> demon = (List<Enemie_min_demons>)getObjects(Enemie_min_demons.class);
                side =  demon.get(0).getSide();
                break;
        }

        return side; //side
    }

    public void drops(int x,int y){
        if(ReplyLeave.class != null){
            addObject(new replyP(), x, y);
        }
        for(int i = 1; i <= Greenfoot.getRandomNumber(1)+3; i++){
            addObject(new EXP(), x, y);
        }
        if(Greenfoot.getRandomNumber(10) > 8){
            addObject(new Peach(), x, y);
        }
    }

    //Get player skill cooldowns
    public int getSkillCooling_world(int a){
        List<Player> p = (List<Player>)getObjects(Player.class);
        if(p.size()!=0){
            switch (a) {
                case 1 :
                    cooling =  p.get(0).getSkillCooling_1();
                    break;
                case 2 :
                    cooling =  p.get(0).getSkillCooling_2();
                    break;
                case 3 :
                    cooling =  p.get(0).getSkillCooling_3();
                    break;
                default:
                    break;
            }
        }
        return cooling;
    }

    //return player X vulue
    public int getPlayerX(){
        List<Player> p = (List<Player>)getObjects(Player.class);
        if(p.size()!=0){
            forReturn =  p.get(0).getX();
        }
        return forReturn;
    }

    //return player Y vulue
    public int getPlayerY(){
        List<Player> p = (List<Player>)getObjects(Player.class);
        if(p.size()!=0){
            forReturn =  p.get(0).getY();
        }
        return forReturn;
    }

    //return player hp
    public double getPlayerHp(){
        return PlayerHp;
    }

    //return player shield 
    public double getPlayerShield(){
        return PlayerShield;
    }

    //get exp
    public double getExp(){
        return Exp;
    }

    //get EnyoHp
    public double getEnyoHp(){
        List<evil_Warrior> e = (List<evil_Warrior>)getObjects(evil_Warrior.class);
        if(e.size()!=0){
            enyoHP =  e.get(0).getHP();
        }
        return enyoHP;
    }

    //get Stan hp
    public double getSatanHp(){
        List<Satan> s = (List<Satan>)getObjects(Satan.class);
        if(s.size()!=0){
            satanHP =  s.get(0).getHP();
        }
        return satanHP;
    }

    //deduct hp
    public void deductHp(int damage){
        if(damage >= 1){
            if(invincible != true){
                if(PlayerHp > 0){
                    addObject(new Player_ef(), getPlayerX(), getPlayerY());
                }else{
                    addObject(new sub_ti(1), 650, 200);
                }
                if(ult == true){
                    PlayerShield = PlayerShield - damage;
                    if(PlayerShield <= 0){
                        ult = false;
                    }
                }else{
                    PlayerHp = PlayerHp - damage;
                    if(PlayerHp <= 0){
                        List<Player> p = (List<Player>)getObjects(Player.class);
                        if(p.size()!=0){
                            p.get(0).died(true);
                        }
                    }
                }
            }
        }else{
            if(PlayerHp -damage >= 100){
                PlayerHp = 100;
            }else{
                PlayerHp = PlayerHp - damage;
            }
        }
    }

    //invincible time 
    public void invincible(boolean a){
        invincible =a;
    }

    //add exp
    public void addExp(){
        if(Exp <= 100){
            Exp = Exp +3;
        }
    }

    //get exp
    public double getEXP(){
        return Exp;
    }

    //ult use
    public void ult(){
        PlayerShield = 50;
        ult = true;
    }

    //when get ult and use
    public boolean getUlt(){
        return ult;
    }

    //when player die
    public void stepDie(){
        for(int i = 1; i < 3 ; i++){
            List<Step> s = (List<Step>)getObjects(Step.class);
            if(s.size()!=0){
                s.get(i-1).StepDis();
            }
        }
        List<Player> p = (List<Player>)getObjects(Player.class);
        p.get(0).fly(-35);
    }

    //decrease enemie number 
    public void EnemieNum(int i){
        enemieNum = enemieNum + i;
    }

    //get numbers of enemy
    public void getenemy(){
        if(enemieTime == 2){
            for (int i = 1; i <=3; i++){
                addObject(new  Enemie_min_demons(),600 + i*20,350);
                enemieNum ++;
            }
        }
    } 

    //return level
    public void getLevel(int i){
        Level = i;
        StartGame = true;
    }

    //return level 2
    public int getLevel(){
        return Level;
    }

}
