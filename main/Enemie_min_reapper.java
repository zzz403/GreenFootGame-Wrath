import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemie_min_bats here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemie_min_reapper extends Enemie_min
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
    private GreenfootImage[][] attake;
    private GreenfootImage[][] die;
    private GreenfootImage[][] move;
    private GreenfootImage[][] stand;

    private int [][] cooling;
    private int [] damage;
    private int index = 0;

    private String status = "right_stand";

    private boolean startSeting = true;

    private int pX;
    private int pY;
    private int dAbs;
    private boolean side;
    private boolean iced = false;

    private boolean died = false;

    private boolean attacking = false;

    private boolean moveSkill2 = false;
    private boolean moveSkillTime = false;

    private boolean firstTimeDie = true;

    public Enemie_min_reapper (){
        setImage("Reaper_walk_1.png");
        getImage().scale(86,86);

        walk = new GreenfootImage[2][7];
        attake = new GreenfootImage[2][7];
        die = new GreenfootImage[2][2];
        move = new GreenfootImage[2][7];
        stand = new GreenfootImage[2][7];

        
        //Cooling

        for(int i = 1; i <= 7; i++){
            GreenfootImage right = new GreenfootImage("Reaper_walk_"+i+".png");
            right.scale(sx,sy);
            walk[1][i-1] = right;
            GreenfootImage left = new GreenfootImage("Reaper_walk_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            walk[0][i-1] = left;
        }
        for(int i = 1; i <= 7; i++){
            GreenfootImage right = new GreenfootImage("Reaper_attack_"+i+".png");
            right.scale(sx,sy);
            attake[1][i-1] = right;
            GreenfootImage left = new GreenfootImage("Reaper_attack_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            attake[0][i-1] = left;
        }
        for(int i = 1; i <= 7; i++){
            GreenfootImage right = new GreenfootImage("Reaper_move_"+i+".png");
            right.scale(sx,sy);
            move[1][i-1] = right;
            GreenfootImage left = new GreenfootImage("Reaper_move_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            move[0][i-1] = left;
        }
        for(int i = 1; i <= 7; i++){
            GreenfootImage right = new GreenfootImage("Reaper_ready_"+i+".png");
            right.scale(sx,sy);
            stand[1][i-1] = right;
            GreenfootImage left = new GreenfootImage("Reaper_ready_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            stand[0][i-1] = left;
        }
        GreenfootImage right = new GreenfootImage("Reaper_die.png");
        right.scale(sx,sy);
        die[1][0] = right;
        GreenfootImage left = new GreenfootImage("Reaper_die.png");
        left.mirrorHorizontally();
        left.scale(sx,sy);
        die[0][1] = left;
    }

    public void act(){
        // System.out.println(getX()); 
        if(startSeting){
            seting(3);
            cooling = getSetting();
            damage = getDamage();
            cooling[1][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(10);
            cooling[16][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(12);//recover
            cooling[17][1] = 20;//wait to die         
            startSeting = false;
        }
        touchWall();
        hp = getHp();
        
        if(died == true){
            MyWorld world= (MyWorld)getWorld();
            world. EnemieNum(-1);
            world.drops(getX(),getY());
            Greenfoot.setSpeed(50);
            getWorld().removeObject(this);
        }else{
            for(int i = 1; i <= 19; i++){
                cooling[i-1][0] --;
            }
            if(hp <= 0){
                died();
            }else{
                if(iced){
                    moveX = 0;
                }else{
                    getPlayerLocation(moveX);
                    side = getSide();
                    movetion();
                    fall(0);
                }
                touchingSkill(cooling);
                touchingBuff();
            }
        }
        // Add your action code here.
    }


    private void touchingBuff(){
        if(isTouching(buff_ice.class)){
            iced = true;
        }else{
            iced = false;
        }
    }

    private void died(){
        cooling[10][0] = cooling[10][1];
        if(firstTimeDie){
            cooling[17][0] = cooling[17][1];
            firstTimeDie = false;
        }
        // index = 0;
        if(side != true){
            status = "right_die";
            animation();
        }else{
            status = "left_die";
            animation();
        }
        if(cooling[17][0] <= 0){
            died = true;
        }
    }

    private void movetion(){
        if( cooling[10][0] <= 0){ // didn't die
            if( cooling[8][0] <= 0){ // not hit
                if(cooling[0][0] <= 0 ){
                    MyWorld world= (MyWorld)getWorld();
                    dAbs = Math.abs(getX() - world.getPlayerX());
                    if(dAbs < 200|| moveSkillTime == true ){
                        
                        if(cooling[16][0] <= 0 || moveSkillTime == true || getX() > 1300 || getX() < 20){
                            moveskill();
                            cooling[16][0] = cooling[16][1];
                            if(moveSkillTime == false){ 
                                moveSkillTime = true;
                                index = 0;
                            }
                        }else{
                            walk();
                        }
                    }else if( dAbs >= 200  ){
                        move_and_stand();
                    }
                    cooling[0][0] = cooling[0][1];
                    animation();
                }
            }
        }
    }

    private void moveskill(){

        if(side != true){
            status = "right_hit";
        }else{
            status = "left_hit";
        }
    }

    private void move_and_stand(){
        if(cooling[1][0] <= 0 ){
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

    private void walk(){
        cooling[1][0]= cooling[1][1];
        moveX = -4;
        
        if(side != true){
            status = "left_walk";
        }else{
            status = "right_walk";
        }
    }

    private void animation(){
        //run
        if(status.contains("right_walk")){
            if(index > 6 || index < 0){
                index = 0;
            }
            setImage(walk[0][index]);
            index ++;
        }else if(status.contains("left_walk")){
            if(index > 6 || index < 0){
                index = 0;
            }
            setImage(walk[1][index]);
            index ++;

        }
        else if(status.contains("right_die")&& cooling[11][0] <= 0){
            setImage(die[0][0]);
        }else if(status.contains("left_die")&& cooling[11][0] <= 0){
            setImage(die[1][0]);
        }else if(status.contains("right_attack")){
            if(index > 6|| index < 0){
                getWorld().addObject(new Reaper_skill(), getX(), getY()-20);
                index = 0;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            setImage(attake[0][index]);
            index ++;
        }else if(status.contains("left_attack")){
            if(index > 6|| index < 0){
                getWorld().addObject(new Reaper_skill(), getX(), getY()-20);
                index = 0;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            setImage(attake[1][index]);
            index ++;
        }else if(status.contains("right_hit") && cooling[9][0] <= 0){
            if(moveSkill2){
                if(index < 1){
                    index = 0;
                    moveSkill2 = false;
                    moveSkillTime = false;
                }
                setImage(move[0][index]);
                cooling[9][0] = cooling[9][1];
                index --;
            }else{
                if(index > 6 ){
                    index = 5;
                    setLocation(Greenfoot.getRandomNumber(900)+50,getY());
                    moveSkill2 = true;
                }
                setImage(move[0][index]);
                cooling[9][0] = cooling[9][1];
                index ++;
            }
        }else if(status.contains("left_hit") && cooling[9][0] <= 0){
            if(moveSkill2){
                if(index < 0){
                    index = 0;
                    moveSkill2 = false;
                    moveSkillTime = false;
                }
                setImage(move[1][index]);
                cooling[9][0] = cooling[9][1];
                index --;
            }else{
                if(index > 6){
                    index = 5;
                    setLocation(Greenfoot.getRandomNumber(900)+50,getY());
                    moveSkill2 = true;
                }
                setImage(move[1][index]);
                cooling[9][0] = cooling[9][1];
                index ++;
            }
        }else if(status.contains("right_stand")){
            if(index > 6 || index < 0){
                index = 0;
            }
            setImage(stand[0][index]);
            index ++;
        }else if(status.contains("left_stand")){
            if(index > 6 || index < 0){
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
