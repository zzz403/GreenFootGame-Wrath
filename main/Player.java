import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private static int sx = 128; //scaleX
    private static int sy = 88; //scaleY
    
    private GreenfootImage[][] walk;
    private GreenfootImage[][] fall;
    private GreenfootImage[][] attack;
    private GreenfootImage[][] stand;
    private GreenfootImage[][] hurt;
    private GreenfootImage[][] die;
    
    private boolean died = false;
    private boolean end = false;

    private int [][] cooling;
    private int [][] touchCooling;
    private boolean [] tutorial;
    private boolean UpKill = false;

    private String status = "right_stand";
    private int moveX_state = 20;
    private int moveX = 20;

    private int moveY;
    private int index = 1;
    private boolean side = true;

    private int wallX;
    private int wallY;

    private boolean attakeOpen = false;
    private int diedFirst = 0;
    private boolean skillTime = false;
    private boolean startSetting = true;
    private boolean onWall = false;
    private boolean skill2Stop = true;
    private boolean fly = false;

    private boolean BlackHole = false;
    public Player(){
        setImage("Warrior_Attack_1.png");

        getImage().scale(sx,sy);
        //Loading images
        walk = new GreenfootImage[2][8];
        fall = new GreenfootImage[2][3];
        attack = new GreenfootImage[2][12];
        stand = new GreenfootImage[2][6];
        hurt = new GreenfootImage[2][4];
        die = new GreenfootImage[2][11];

        cooling = new int [15][2];
        touchCooling = new int [15][2];
        tutorial = new boolean[10];
        for(int i = 1; i <= 9; i++){
            tutorial[i-1] = false;
        }
        //Cooling

        for(int i = 1; i <= 8; i++){
            GreenfootImage right = new GreenfootImage("Warrior_Run_"+i+".png");
            right.scale(sx,sy);
            walk[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Warrior_Run_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            walk[1][i-1] = left;
        }
        for(int i = 1; i <= 3; i++){
            GreenfootImage right = new GreenfootImage("Warrior_Fall_r"+i+".png");
            right.scale(sx,sy);
            fall[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Warrior_Fall_r"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            fall[1][i-1] = left;
        }
        for(int i = 1; i <= 12; i++){
            GreenfootImage right = new GreenfootImage("Warrior_Attack_"+i+".png");
            right.scale(sx,sy);
            attack[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Warrior_Attack_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            attack[1][i-1] = left;
        }
        for(int i = 1; i <= 6; i++){
            GreenfootImage right = new GreenfootImage("Warrior_Idle_"+i+".png");
            right.scale(sx,sy);
            stand[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Warrior_Idle_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            stand[1][i-1] = left;
        }
        for(int i = 1; i <= 4; i++){
            GreenfootImage right = new GreenfootImage("Warrior_hurt_"+i+".png");
            right.scale(sx,sy);
            hurt[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Warrior_hurt_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            hurt[1][i-1] = left;
        }
        for(int i = 1; i <= 10; i++){
            GreenfootImage right = new GreenfootImage("Warrior_Death_"+i+".png");
            right.scale(sx,sy);
            die[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Warrior_Death_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            die[1][i-1] = left;
        }
    }    

    public void act()
    {
        if(startSetting){
            seting();
        }

        for(int i = 1; i <= 15; i++){
            cooling[i-1][0] --;
            touchCooling [i-1][0] --;
        }
        if(died != true){
            rebound();
            touchedBuff();
            fall();
            attake();
            movement();
            fly();
            BlackHole();
            tutorial();
            touchWall();
        }else{
            die();
            // System.out.println(index); 
        }
        if(end){
            setImage("death.png");
            //getWorld().stopped();
            Greenfoot.stop();
        }
    }

    private void touchWall(){
        if(isAtEdge()){
            MyWorld world= (MyWorld)getWorld();
            if(world.getWidth() < getX()){
                setLocation(world.getWidth(),getY());
            }else if(getX() <= 0){
                setLocation(0,getY());
            }
        }
    }
    
    private void ult_slow(){
        MyWorld world= (MyWorld)getWorld();
        if(world.getUlt() == true){
            moveX_state = 10;
        }else{
            moveX_state = 20;
        }
    }

    private void seting(){
        for(int i = 1; i <= 15; i++){
            cooling[i-1][0] = 0;
            touchCooling[i-1][0] = 0;
        }
        cooling[0][1] = 20; //attake cooling
        cooling[1][1] = 1; //attake interval cooling
        cooling[2][1] = 12; //attake number
        cooling[3][1] = 45; //jump time
        cooling[4][1] = 3; //move time
        cooling[5][1] = 5; //stop stand for move
        cooling[6][1] = 25; //stop stand for attake
        cooling[7][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getPlayerCooling(0);//skill 1
        cooling[8][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getPlayerCooling(1);; //skill 2
        cooling[9][1] = 4; // stand cool
        cooling[10][1] = 5; //died
        cooling[11][1] = 100; //upkill cool
        cooling[12][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getPlayerCooling(2); //skill3 cool
        cooling[13][1] = 10; // fly
        touchCooling[0][1] = 200; //ghost

        startSetting = false;
    }

    private void BlackHole(){
        if(BlackHole == true){
            if(((Black_Hole) getWorld().getObjects(Black_Hole.class).get(0)).getX() > getX()){
                setLocation(getX() +4 , getY());
            }else if(((Black_Hole) getWorld().getObjects(Black_Hole.class).get(0)).getX() < getX()){
                setLocation(getX() -4 , getY());
            }
            if(((Black_Hole) getWorld().getObjects(Black_Hole.class).get(0)).getY() > getY()){
                setLocation(getX()  , getY()+4);
            }else if(((Black_Hole) getWorld().getObjects(Black_Hole.class).get(0)).getY() < getY()){
                setLocation(getX()  , getY()-4);
            }
        }
    }

    private void touchedBuff(){
        if(isTouching(buff_goust.class)){
            moveX =  - moveX_state ;
        }else{
            moveX = moveX_state;
        }
    }

    private void movement(){
        MyWorld world= (MyWorld)getWorld();
        if(cooling[4][0] <= 0){
            if(Greenfoot.isKeyDown("a")){
                cooling[5][0] = cooling[5][1];
                leftmove();
                cooling[4][0] = cooling[4][1];
                UpKill = false;
            }else if(Greenfoot.isKeyDown("d")){
                cooling[5][0] = cooling[5][1];
                rightmove();
                cooling[4][0] = cooling[4][1];
                UpKill = false;
            }else if(Greenfoot.isKeyDown(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(1)) && cooling[0][0] <= 0 ){
                tutorial[1] = true;
                if(status.indexOf("jump") != -1){
                    setLocation(getX(),540);
                    getWorld().addObject(new Player1_DropAttake  (), getX(), getY());
                    cooling[0][0] = cooling[0][1];
                }else{
                    UpKill = true;

                    cooling[5][0] = cooling[6][1];
                    cooling[2][0] = cooling[2][1];
                    attakeOpen = true;
                    if(world.getUlt() == true){
                        cooling[0][1] = 55;
                        getWorld().addObject(new Player1_Attake  _ult(), getX(), getY());
                    }else{
                        cooling[0][1] = 20;
                        getWorld().addObject(new Player1_Attake  (), getX(), getY());
                    }
                    cooling[0][0] = cooling[0][1];  
                }
            }else{
                if(cooling[5][0] <= 0){
                    if (status.indexOf("left") != -1){
                        status = "left_stop";
                        animation();
                    }else{
                        status = "right_stop";
                        animation();
                    }

                }
            }

            if(Greenfoot.isKeyDown(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(0)) && cooling[3][0] <=0 ){
                if(status.indexOf("jump") == -1){
                    tutorial[0] = true;
                    cooling[5][0] = cooling[6][1] -4;
                    jump();

                    cooling[3][0] = cooling[3][1];
                }
            }
            if(Greenfoot.isKeyDown(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(2)) && cooling[7][0] <=0){
                tutorial[2] = true;
                cooling[7][0] = cooling[7][1];
                getWorld().addObject(new Player1_Skill1  (), getX(), getY());
                UpKill = false;
            }else if(Greenfoot.isKeyDown(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(3)) && cooling[8][0] <=0){
                tutorial[3] = true;
                cooling[8][0] = cooling[8][1];
                skill2Stop = false;
                getWorld().addObject(new Player1_Skill2  (), getX(), getY());
                UpKill = false;
            }else if(Greenfoot.isKeyDown(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(4)) && cooling[12][0] <=0){
                tutorial[4] = true;
                cooling[12][0] = cooling[12][1];
                getWorld().addObject(new Player1_Skill3(), getX(), getY());
                UpKill = false;
            }else if(Greenfoot.isKeyDown(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(5)) && world.getExp() >= 100){
                tutorial[5] = true;
                getWorld().addObject(new Wushuang(), getX(), getY());
                world.ult();
            }

            if(skill2Stop == false){
                world.invincible(true);
                if (status.indexOf("left") != -1){
                    setLocation(getX() - ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getPlayerSkillRange(0),getY());
                }else{
                    setLocation(getX() + ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getPlayerSkillRange(0),getY());
                }
            }else{
                world.invincible(false);
            }
        }
    }

    private void tutorial(){
        MyWorld world= (MyWorld)getWorld();

        if(tutorial[0] == false){
            world.changeText("skill","Press "+ ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(0)  +" to jump");
        }else if(tutorial[1] == false){
            world.changeText("skill","Press "+ ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(1) +" to use the attack");
        }else if(tutorial[2] == false){
            world.changeText("skill","Press "+((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(2) +" to use skill1");
        }else if(tutorial[3] == false){
            world.changeText("skill","Press "+((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(3) +" to use skill2");
        }else if(tutorial[4] == false){
            world.changeText("skill","Press "+((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(4) +" to use skill3");
        }else if(tutorial[5] == false && world.getExp() >= 100){
            world.changeText("skill","Press "+((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(5) +" to use ult");
        }else if(tutorial[6] == false && ((ReplyLeave) getWorld().getObjects(ReplyLeave.class).get(0)).useTime() == true){
            world.changeText("skill","Press "+((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(6) +" to use ReplyLeave");
        }else if(tutorial[7] == false && getWorld().getObjects(Spiritualism.class).isEmpty()!= true){
            if(((spiritualismBell) getWorld().getObjects(spiritualismBell.class).get(0)).useTime() == true){
                world.changeText("skill","Press "+((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getKeys(7) +" to use SpiritBell");
            }
        }else{
            world.changeText("skill",null);
        }
    }

    public void changeTutprial(int i){
        tutorial[i] = true;
    }

    private void jump(){
        if(UpKill == true &&  cooling[11][0] <= 0){
            getWorld().addObject(new Player1_Upskill  (), getX(), getY()-50);
            moveY = -25;
            UpKill = false;
            cooling[11][0] =  cooling[11][1];
        }else{
            moveY = -20;
        }
        if (status.indexOf("left") != -1){
            status = "left_jump";
            animation();
        }else{
            status = "right_jump";
            animation();
        }
        setLocation(getX(),getY()+moveY);
        moveY += 1;
    }

    private void fly(){
        if(fly == true){

            setLocation(getX(),getY()+moveY);
            moveY += 1;
        }
    }

    private void fall(){
        Actor Flooring = getOneObjectAtOffset(0, getImage().getHeight()/2, Flooring.class);
        if(Flooring == null){
            checkStep(); 
        }else{ 
            if(fly == true && cooling[13][0] <= 0){
                fly = false;
            }
        }
    }

    public void checkStep(){
        Actor Step = getOneObjectAtOffset(0, getImage().getHeight()/2, Step.class);
        if(Step == null){
            setLocation(getX(),getY()+moveY);
            moveY += 1;
        }else{
            if(fly == true && cooling[13][0] <= 0){
                fly = false;
            }
            if(getY()> 339 && getY() < 380){
                setLocation(getX(),340);
            }else if(getY()> 139 && getY() < 180){
                setLocation(getX(),140);
            }else if(getY()> 239 && getY() < 280){
                setLocation(getX(),240);
            }
        }
    }

    private void attake(){
        if (attakeOpen){
            if(cooling[2][0]>=0){
                if (status.indexOf("left") != -1){
                    status = "left_attack";
                    setLocation(getX()-1,getY());
                }else{
                    status = "right_attack";
                    setLocation(getX()+1,getY());
                }
                animation();
                cooling[1][0] = cooling[1][1];
            }else{
            }
        }

    }

    private void die(){
        if (diedFirst == 0){
            if(status.indexOf("right") == -1){
                status = "left_die";
            }else{
                status = "right_die";
            }
            diedFirst = 1;
            index = 0;
            animation();
        }else{
            animation();
        }
    }

    private void rightmove(){
        status = "right_move";
        //setLocation(getX() + moveX,getY());
        animation();
    }

    private void leftmove(){
        status = "left_move";
        //setLocation(getX() - moveX,getY());
        animation();
    }

    //animation
    private void animation(){
        //run
        if(status.contains("right_move")){
            if(index > 7){
                index = 0;
            }
            setImage(walk[0][index]);
            index ++;
        }else if(status.contains("left_move")){
            if(index > 7){
                index = 0;
            }
            setImage(walk[1][index]);
            index ++;
            //jump
        }else if(status.contains("right_jump")){
            if(index > 2){
                index = 0;
            }
            setImage(fall[0][index]);
            index ++;
        }else if(status.contains("left_jump")){
            if(index > 2){
                index = 0;
            }
            setImage(fall[1][index]);
            index ++;
            //attack
        }else if(status.contains("right_attack")){
            if(index > 11){
                index = 0;
            }
            setImage(attack[0][index]);
            index ++;
        }else if(status.contains("left_attack")){
            if(index > 11){
                index = 0;
            }
            setImage(attack[1][index]);
            index ++;
            //stand
        }else if(status.contains("right_stop") && cooling[9][0] <= 0){

            if(index > 5){
                index = 0;
            }
            setImage(stand[0][index]);
            cooling[9][0] = cooling[9][1];
            index ++;
        }else if(status.contains("left_stop")&& cooling[9][0] <= 0){
            if(index > 5){
                index = 0;
            }
            setImage(stand[1][index]);
            cooling[9][0] = cooling[9][1];
            index ++;
        }else if(status.contains("right_die")&& cooling[10][0] <= 0){
            if(index > 10){
                index = 0;
                end = true;
            }
            setImage(die[0][index]);
            cooling[10][0] = cooling[10][1];
            index ++;
        }else if(status.contains("left_die")&& cooling[10][0] <= 0){
            if(index > 10){
                index = 0;
                end = true;
            }
            setImage(die[1][index]);
            cooling[10][0] = cooling[10][1];
            index ++;
        }

    }

    private void rebound(){
        if(getY() >= 544){
            setLocation(getX(),544);
        }

    }

    //get
    public boolean getSide(){
        if (status.indexOf("left") != -1){
            side = false;
        }else{
            side = true;
        }
        return side;
    }

    public int getSkillCooling_1(){
        return cooling[7][0];
    }

    public int getSkillCooling_2(){
        return cooling[8][0];
    }

    public int getSkillCooling_3(){
        return cooling[12][0];
    }

    public void died(boolean b){
        died = b;
    }

    public void skill2Stop(){
        skill2Stop = true;
    }

    public void fly(int y){
        cooling[13][0] = cooling[13][1];
        fly = true;
        moveY = y;
    }

    public void changeBlackHole(boolean b){
        BlackHole = b;
    }
}
