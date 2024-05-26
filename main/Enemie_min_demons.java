import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class demon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemie_min_demons extends Enemie_min
{
    /**
     * Act - do whatever the demon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int hp ;
    private int hp_last;  
    
    private int sx = 150; //scaleX
    private int sy = 97; 
    private int moveX = 0;

    private GreenfootImage[][] walk;
    private GreenfootImage[][] run;
    private GreenfootImage[][] attake;
    private GreenfootImage[][] die;
    private GreenfootImage[][] hit;
    private GreenfootImage[][] stand;

    private int [][] cooling;
    private int [] damage;
    private int index = 0;

    private String status = "right_stand";

    private boolean startSeting = true;

    private int dAbs;
    private boolean side;
    private boolean iced = false;

    private boolean died = false;

    private boolean attacking = false;

    public Enemie_min_demons (){
        setImage("Demon_walk_1.png");
        getImage().scale(150,97);

        walk = new GreenfootImage[2][6];
        run = new GreenfootImage[2][6];
        attake = new GreenfootImage[2][6];
        die = new GreenfootImage[2][5];
        hit = new GreenfootImage[2][3];
        stand = new GreenfootImage[2][6];

        cooling = new int [20][2];
        damage = new int [20];
        //Cooling

        for(int i = 1; i <= 6; i++){
            GreenfootImage right = new GreenfootImage("Demon_walk_"+i+".png");
            right.scale(sx,sy);
            walk[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon_walk_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            walk[1][i-1] = left;
        }
        for(int i = 1; i <= 6; i++){
            GreenfootImage right = new GreenfootImage("Demon_run_"+i+".png");
            right.scale(sx,sy);
            run[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon_run_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            run[1][i-1] = left;
        }
        for(int i = 1; i <= 6; i++){
            GreenfootImage right = new GreenfootImage("Demon_attack1_"+i+".png");
            right.scale(sx,sy);
            attake[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon_attack1_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            attake[1][i-1] = left;
        }
        for(int i = 1; i <= 5; i++){
            GreenfootImage right = new GreenfootImage("Demon_fall_back_"+i+".png");
            right.scale(sx,sy);
            die[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon_fall_back_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            die[1][i-1] = left;
        }
        for(int i = 1; i <= 3; i++){
            GreenfootImage right = new GreenfootImage("Demon_hit_"+i+".png");
            right.scale(sx,sy);
            hit[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon_hit_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            hit[1][i-1] = left;
        }
        for(int i = 1; i <= 5; i++){
            GreenfootImage right = new GreenfootImage("Demon_ready_"+i+".png");
            right.scale(sx,sy);
            stand[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon_ready_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            stand[1][i-1] = left;
        }
    }

    public void act(){
        // System.out.println(iced); 
        if(startSeting){
            super.seting(1);
            cooling = getSetting();
            damage = getDamage();
            startSeting = false;
            
        }
        hp = getHp();
        
        for(int i = 1; i <= 19; i++){
            cooling[i-1][0] --;
        }
        touchWall();
        
        if(hp <= 0){
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
        if(died){
            MyWorld world= (MyWorld)getWorld();
            world. EnemieNum(-1);
            world.drops(getX(),getY());
            Greenfoot.setSpeed(50);
            getWorld().removeObject(this);
        }
        // Add your action code here.
    }

    //diffrent buff and get
    private void touchingBuff(){
        if(isTouching(buff_ice.class)){
            iced = true;
        }else{
            iced = false;
        }
    }

    //if died 
    private void died(){
        cooling[10][0] = cooling[10][1];
        // index = 0;
        if(side != true){
            status = "right_die";
            animation();
        }else{
            status = "left_die";
            animation();
        }
    }

    
    //diffrent move
    private void movetion(){
        if( cooling[10][0] <= 0){ // didn't die
            if( cooling[8][0] <= 0){ // not hit
                if(cooling[0][0] <= 0 && attacking == false){
                    MyWorld world= (MyWorld)getWorld();
                    dAbs = Math.abs(getX() - world.getPlayerX());
                    if(dAbs < 85){
                        move_and_stand();
                    }else if(dAbs < 500 && dAbs >= 85  ){
                        run();
                    }else if(dAbs >= 500){
                        walk();
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

    //difind move and stand
    private void move_and_stand(){
        MyWorld world= (MyWorld)getWorld();
        //System.out.println(status); 
        if(cooling[1][0] <= 0 && world.getPlayerY() > 341){
            moveX = 0;
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
            moveX = 0;
            if(side != true){
                status = "right_stand";
            }else{
                status = "left_stand";
            }
        }
    }

    //run 
    private void run(){
        cooling[1][0]= cooling[1][1];
        moveX = 3;
        if(side != true){
            status = "right_run";
        }else{
            status = "left_run";
        }
    }

    //walk
    private void walk(){
        cooling[1][0]= cooling[1][1];
        moveX = 1;
        if(side != true){
            status = "right_walk";
        }else{
            status = "left_walk";
        }
    }

    //animation
    private void animation(){
        //run
        if(status.contains("right_walk")){
            if(index > 5){
                index = 0;
            }
            setImage(walk[0][index]);
            index ++;
        }else if(status.contains("left_walk")){
            if(index > 5){
                index = 0;
            }
            setImage(walk[1][index]);
            index ++;

        }else if(status.contains("right_die")&& cooling[11][0] <= 0){
            if(index > 4){
                index = 0;
                died = true;
            }
            setImage(die[0][index]);
            cooling[11][0] = cooling[11][1];
            index ++;
        }else if(status.contains("left_die")&& cooling[11][0] <= 0){
            if(index > 4){
                index = 0;
                died = true;
            }
            setImage(die[1][index]);
            cooling[11][0] = cooling[11][1];
            index ++;

        }else if(status.contains("right_attack")){
            if(index > 4){
                index = 0;
                attacking = false;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            attacking = true;
            setImage(attake[0][index]);
            index ++;
        }else if(status.contains("left_attack")){
            if(index > 5){
                index = 0;
                attacking = false;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            attacking = true;
            setImage(attake[1][index]);
            index ++;

        }else if(status.contains("right_run")){
            if(index > 5){
                index = 0;
            }
            setImage(run[0][index]);
            index ++;
        }else if(status.contains("left_run")){
            if(index > 5){
                index = 0;
            }
            setImage(run[1][index]);
            index ++;

        }else if(status.contains("right_hit") && cooling[9][0] <= 0){
            if(index > 2){
                index = 0;
            }
            setImage(hit[0][index]);
            cooling[9][0] = cooling[9][1];
            index ++;
        }else if(status.contains("left_hit") && cooling[9][0] <= 0){
            if(index > 2){
                index = 0;
            }
            setImage(hit[1][index]);
            cooling[9][0] = cooling[9][1];
            index ++;

        }else if(status.contains("right_stand")){
            if(index > 4){
                index = 0;
            }
            setImage(stand[0][index]);
            index ++;
        }else if(status.contains("left_stand")){
            if(index > 4){
                index = 0;
            }
            setImage(stand[1][index]);
            index ++;
        }
    }
    

    // private void rebound(){
    // if(getY() >= 555){
    // setLocation(getX(),550);
    // }
    // }
}

