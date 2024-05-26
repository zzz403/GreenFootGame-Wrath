import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemie_min_knife here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemie_min_knife extends Enemie_min
{
    private int hp ;
    private int hp_last;  
    
    private int sx = 150; //scaleX
    private int sy = 97; 
    private int moveX = 0;

    private GreenfootImage[][] run;
    private GreenfootImage[][] attake;
    private GreenfootImage[][] hard;
    private GreenfootImage[][] die;
    private GreenfootImage[][] hit;
    private GreenfootImage[][] stand;
    private GreenfootImage[][] fall;

    private int [][] cooling;
    private int [] damage;
    private int index = 0;
    
    private int index2 = 0;
    private int index3 = 40;

    private String status = "left_fall";

    private boolean startSeting = true;

    private int dAbs;
    private boolean side;
    private boolean iced = false;

    private boolean died = false;

    private boolean attacking = false;
    
    private int fallNum = 530;
    /**
     * Act - do whatever the Enemie_min_knife wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Enemie_min_knife (){
        setImage("Knife_fall_1.png");
        getImage().scale(150,97);

        run = new GreenfootImage[2][10];
        attake = new GreenfootImage[2][10];
        hard = new GreenfootImage[2][10];
        die = new GreenfootImage[2][10];
        hit = new GreenfootImage[2][10];
        stand = new GreenfootImage[2][12];
        fall = new GreenfootImage[2][10];

        cooling = new int [20][2];
        damage = new int [20];
        //Cooling

        for(int i = 1; i <= 8; i++){
            GreenfootImage right = new GreenfootImage("Knife_Run_"+i+".png");
            right.scale(95,119);
            run[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Knife_Run_"+i+".png");
            left.mirrorHorizontally();
            left.scale(95,119);
            run[1][i-1] = left;
        }
        for(int i = 1; i <= 7; i++){
            GreenfootImage right = new GreenfootImage("Knife_Attack_"+i+".png");
            right.scale(98,150);
            attake[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Knife_Attack_"+i+".png");
            left.mirrorHorizontally();
            left.scale(108,147);
            attake[1][i-1] = left;
        }
        for(int i = 1; i <= 7; i++){
            GreenfootImage right = new GreenfootImage("Knife_Hard_"+i+".png");
            right.scale(239,168);
            hard[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Knife_Hard_"+i+".png");
            left.mirrorHorizontally();
            left.scale(239,168);
            hard[1][i-1] = left;
        }
        for(int i = 1; i <= 4; i++){
            GreenfootImage right = new GreenfootImage("Knife_hit_"+i+".png");
            right.scale(116,118);
            hit[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Knife_hit_"+i+".png");
            left.mirrorHorizontally();
            left.scale(116,118);
            hit[1][i-1] = left;
        }
        for(int i = 1; i <= 3; i++){
            GreenfootImage right = new GreenfootImage("Knife_fall_"+i+".png");
            right.scale(116,118);
            fall[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Knife_fall_"+i+".png");
            left.mirrorHorizontally();
            left.scale(116,118);
            fall[1][i-1] = left;
        }
        for(int i = 1; i <= 11; i++){
            GreenfootImage right = new GreenfootImage("Knife_stand_"+i+".png");
            right.scale(104,112);
            stand[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Knife_stand_"+i+".png");
            left.mirrorHorizontally();
            left.scale(104,112);
            stand[1][i-1] = left;
        }
        for(int i = 1; i <= 9; i++){
            GreenfootImage right = new GreenfootImage("Knife_die_"+i+".png");
            right.scale(104,112);
            die[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Knife_die_"+i+".png");
            left.mirrorHorizontally();
            left.scale(104,112);
            die[1][i-1] = left;
        }
    }
    
    public void act()
    {
        // Add your action code here.
        //setImage(hard[1][1]);
        if(startSeting){
            super.seting(6);
            cooling = getSetting();
            damage = getDamage();
            startSeting = false;
            
        }
        hp = getHp();
        
        for(int i = 1; i <= 19; i++){
            cooling[i-1][0] --;
        }
        touchWall();
        
        fall(0);
        if(getOneObjectAtOffset(0, getImage().getHeight()/2, Flooring.class)==null){
            touchingSkill(cooling);
            if(side != true){
                status = "right_fall";
            }else{
                status = "left_fall";
            }
            
            if(cooling[0][0]<=0){
                animation();
                cooling[0][0] = cooling[0][1];
            }
            
        }else{
            changeSetFall();
            if(hp <= 0){
                cooling[0][1] = 6;
                died();
            }else{
                if(iced){
                    moveX = 0;
                }else{
                    super.getPlayerLocation(moveX);
                    side = getSide();
                    movetion();
                    fall(10);
                    MyWorld world= (MyWorld)getWorld();
                    if(world.getPlayerY() > 341){
                        attacking = false; //demon无法攻击台阶上的敌人
                    }
                }
                touchingSkill(cooling);
                touchingBuff();
            }
        }
        
        if(died){
            MyWorld world= (MyWorld)getWorld();
            world. EnemieNum(-1);
            world.drops(getX(),getY());
            Greenfoot.setSpeed(50);
            getWorld().removeObject(this);
        }
    }
    
    private void died(){
        cooling[10][0] = cooling[10][1];
        if(status.indexOf("die") == -1){
            Greenfoot.setSpeed(50);
            index = 0;
        }
        // index = 0;
        if(cooling[0][0] < 0){
            cooling[0][0] = cooling[0][1];
            if(side != true){
                status = "right_die";
                animation();
            }else{
                status = "left_die";
                animation();
            }
        }
        
    }
    
    private void movetion(){
        if( cooling[10][0] <= 0){ // didn't die
            if( cooling[8][0] <= 0){ // not hit
                if(cooling[0][0] <= 0 && attacking == false){
                    MyWorld world= (MyWorld)getWorld();
                    dAbs = Math.abs(getX() - world.getPlayerX());
                    if(dAbs < 85){
                        move_and_stand();
                    }else{
                        run();
                    }
                    cooling[0][0] = cooling[0][1];
                    
                    animation();
                }
            }else{
                if(side != true){
                    status = "right_hit";
                }else{
                    status = "left_hit";
                }
                animation();
            }
        }
    }
    
    private void move_and_stand(){
        MyWorld world= (MyWorld)getWorld();
        //System.out.println(status); 
        moveX = 0;
        if(cooling[1][0] <= 0 && world.getPlayerY() > 341){
            if(side != true){
                if(status.indexOf("attack") == -1){
                    index = 0;
                }
                status = "right_attack";

            }else{
                if(status.indexOf("attack") == -1){
                    index = 0;
                }
                status = "left_attack";

            }
        }else{
            changeSetFall();
            //setLocation(getX(),getY()-10);
            if(side != true){
                status = "right_stand";
            }else{
                status = "left_stand";
            }
        }
    }
    
    private void run(){
        cooling[1][0]= cooling[1][1];
        moveX = 5;
        if(side != true){
            status = "right_run";
        }else{
            status = "left_run";
        }
    }
    
    private void touchingBuff(){
        if(isTouching(buff_ice.class)){
            iced = true;
        }else{
            iced = false;
        }
    }
    
    private void animation(){
        //run
        if(status.contains("right_attack")){
            fallNum = 0;
            if(index > 6){
                index = 0;
                attacking = false;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            attacking = true;
            setImage(attake[0][index]);
            index ++;
        }else if(status.contains("left_attack")){
            fallNum = 0;
            if(index > 6){
                index = 0;
                attacking = false;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            attacking = true;
            setImage(attake[1][index]);
            index ++;

        }else if(status.contains("right_hard")){
            if(index > 6){
                index = 0;
                attacking = false;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            attacking = true;
            setImage(hard[0][index]);
                        
            index ++;
        }else if(status.contains("left_hard")){
            if(index > 6){
                index = 0;
                attacking = false;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            attacking = true;
            setImage(hard[1][index]);
            index ++;

        }else if(status.contains("right_run")){
            fallNum = 0;
            if(index > 7){
                index = 0;
            }
            setImage(run[0][index]);
            index ++;
        }else if(status.contains("left_run")){
            fallNum = 0;
            if(index > 7){
                index = 0;
            }
            setImage(run[1][index]);
            index ++;

        }else if(status.contains("right_hit")){
            if(index > 3){
                index = 0;
            }
            setImage(hit[0][index]);
            index ++;
        }else if(status.contains("left_hit")){
            if(index > 3){
                index = 0;
            }
            setImage(hit[1][index]);
            index ++;
        }else if(status.contains("right_fall")){
            if(index > 2){
                index = 0;
            }
            setImage(fall[0][index]);
            index ++;
        }else if(status.contains("left_fall")){
            if(index > 2){
                index = 0;
            }
            setImage(fall[1][index]);
            index ++;
        }else if(status.contains("right_stand")){
            fallNum = 3;
            if(index > 10){
                index = 0;
            }
            setImage(stand[0][index]);
            index ++;
        }else if(status.contains("left_stand")){
            if(index > 10){
                index = 0;
            }
            setImage(stand[1][index]);
            index ++;
        }else if(status.contains("right_die")){
            if(index > 8){
                died = true;
            }
            setImage(die[0][index]);
            index ++;
        }else if(status.contains("left_die")){
            fallNum = 3;
            if(index > 8){
                died = true;
            }
            setImage(die[1][index]);
            index ++;
        }
    }
}
