import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class evel_Warrior here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class evil_Warrior extends Enemie_boss
{
    /**
     * Act - do whatever the evel_Warrior wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int hp;
    private int sx = 128; //scaleX
    private int sy = 88; //scaleY

    private GreenfootImage[][] walk;
    private GreenfootImage[][] fall;
    private GreenfootImage[][] attack;
    private GreenfootImage[][] stand;
    private GreenfootImage[][] hurt;
    private GreenfootImage[][] die;

    private boolean died = false;
    private boolean end = false;

    private int [] damage;
    private int [][] cooling;
    private int [][] skillCooling;

    private boolean UpKill = false;

    private String status = "right_stop";
    private int moveX_state = 20;
    private int moveX = 5;

    private int moveY;
    private int index = 1;
    private boolean side = true;

    private boolean attakeOpen = false;
    private int diedFirst = 0;
    private boolean skillTime = false;
    private boolean startSetting = true;
    private boolean ballAlive = false;

    private boolean iced = false;
    private int dropGold = 500;
    private int dropHp = 0;
    private int dAbs;

    private boolean yingyang = false;
    public evil_Warrior(){
        //Loading images
        walk = new GreenfootImage[2][8];
        fall = new GreenfootImage[2][3];
        attack = new GreenfootImage[2][12];
        stand = new GreenfootImage[2][6];
        hurt = new GreenfootImage[2][4];
        die = new GreenfootImage[2][11];

        cooling = new int [20][2];
        skillCooling = new int [20][2];

        damage = new int [20];

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

        setImage(stand[0][0]);
    }    

    public void act()
    {
        if(startSetting){
            seting();
        }

        for(int i = 1; i <= 19; i++){
            cooling[i-1][0] --;
            skillCooling[i-1][0] --;
        }
        if(hp > 0){
            if(iced){
                moveX = 0;
            }else{
                moveX = 5;
                getPlayerLocation();
                rebound();
                fall();
            }
            touchingSkill();
            touchingBuff();
        }else{
            die();
        }
        if(end){
            MyWorld world= (MyWorld)getWorld();
            world. EnemieNum(-1);
            world.changeText("BossName",null);
            getWorld().removeObject(this);
        }
    }

    //get Player location and move 
    private void getPlayerLocation(){
        MyWorld world= (MyWorld)getWorld();
        if(cooling[10][0] <= 0){
            if(world != null){
                if(getWorld().getObjects(Spiritualism.class).isEmpty()){
                    dAbs = Math.abs(getX() - world.getPlayerX());
                    if(cooling[8][0] <= 0){
                        if(world.getPlayerX() - getX() <= 0){
                            side = true;
                        }else{
                            side = false;
                        }
                    }
                }else{
                    dAbs = Math.abs(getX() - ((Spiritualism) getWorld().getObjects(Spiritualism.class).get(0)).getX());
                    if(cooling[8][0] <= 0){
                        if(((Spiritualism) getWorld().getObjects(Spiritualism.class).get(0)).getX() - getX() <= 0){
                            side = true;
                        }else{
                            side = false;
                        }
                    }
                }
            }
        }
        movement();
    }

    //movement 
    private void movement(){
        if( cooling[10][0] <= 0){ // didn't die
            if( cooling[8][0] <= 0 ){ // not hit
                if(attakeOpen == false){
                    if(cooling[0][0] <= 0){
                        if(dAbs <= 500){
                            skill();
                        }else if(dAbs > 500){
                            transmission();
                        }
                        cooling[0][0] = cooling[0][1];
                        animation();
                    }else if (dAbs > 200){
                        move();
                    }else{
                        if(side){
                            status = "left_stop";
                        }else{
                            status = "right_stop";
                        }
                    }

                }else{
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

    private void transmission(){

    }

    //move left and right 
    private void move(){
        if(side){
            leftmove();
        }else{
            rightmove();
        }
    }

    private void rightmove(){
        status = "right_move";
        setLocation(getX() + moveX,getY());
    }

    private void leftmove(){
        status = "left_move";
        setLocation(getX() - moveX,getY());
    }

    //setting in the map
    private void seting(){
        for(int i = 1; i <= 15; i++){
            cooling[i-1][0] = 0;
        }
        hp =((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(4);
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

        skillCooling[0][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(13); // skill1
        skillCooling[1][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(14); // skill2
        skillCooling[2][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(15); // skill3
        skillCooling[3][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(16); // skill4

        startSetting = false;
    }

    private void skill(){
        if(skillCooling[0][0] <= 0){
            attake();
            attakeOpen = true;
            getWorld().addObject(new Tri_Sword(1), getX() , getY());
            getWorld().addObject(new Tri_Sword(2), getX() , getY());
            getWorld().addObject(new Tri_Sword(3), getX() , getY());
            skillCooling[0][0] = skillCooling[0][1];
        }else if(hp < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(4) / 5 * 4 && skillCooling[1][0] <= 0){
            getWorld().addObject(new Black_Hole(), getX()+ 50 , getY());
            skillCooling[1][0] = skillCooling[1][1];
        }else if (skillCooling[2][0] <= 0 && ballAlive == false){
            ballAlive = true;
            getWorld().addObject(new RecoveryBall(), getX() , getY());
            skillCooling[2][0] = skillCooling[2][1];
        }else if(hp < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(4) / 4 && skillCooling[3][0] <= 0){
            yingyang = true;
            skillCooling[3][0] = skillCooling[3][1];
        }
    }

    public boolean yingyang(){
        return yingyang;
    }

    public void changYingyang(){
        yingyang = false;
    }

    public void ball(){
        ballAlive = false;
    }

    private void jump(){
        moveY = -25;
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

    private void fall(){
        Actor Flooring = getOneObjectAtOffset(0, getImage().getHeight()/2, Flooring.class);
        if(Flooring == null){
            checkStep(); 
        }
    }

    public void checkStep(){
        Actor Step = getOneObjectAtOffset(0, getImage().getHeight()/2, Step.class);
        if(Step == null){
            setLocation(getX(),getY()+moveY);
            moveY += 1;
        }else{
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
        if (status.indexOf("left") != -1){
            status = "left_attack";
            setLocation(getX()-1,getY());
        }else{
            status = "right_attack";
            setLocation(getX()+1,getY());
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
                attakeOpen = false;
            }
            setImage(attack[0][index]);
            index ++;
        }else if(status.contains("left_attack")){
            if(index > 11){
                index = 0;
                attakeOpen = false;
            }
            setImage(attack[1][index]);
            index ++;
            //stand
        }else if(status.contains("right_stop")){

            if(index > 5){
                index = 0;
            }
            setImage(stand[0][index]);
            cooling[9][0] = cooling[9][1];
            index ++;
        }else if(status.contains("left_stop")){
            if(index > 5){
                index = 0;
            }
            setImage(stand[1][index]);
            cooling[9][0] = cooling[9][1];
            index ++;
        }else if(status.contains("right_die")){
            if(index > 10){
                index = 0;
                end = true;
            }
            setImage(die[0][index]);
            cooling[10][0] = cooling[10][1];
            index ++;
        }else if(status.contains("left_die")){
            if(index > 10){
                index = 0;
                end = true;
            }
            setImage(die[1][index]);
            cooling[10][0] = cooling[10][1];
            index ++;
        }

    }

    private void touchingBuff(){
        if(isTouching(buff_ice.class)){
            iced = true;
        }else{
            iced = false;
        }
    }

    private void touchingSkill(){
        if(Player1_Skill1.class != null){
            if(isTouching(Player1_Skill1.class)){
                Greenfoot.playSound("get_hurt3.mp3");
                if(cooling[2][0] <= 0){
                    if(yingyang == true){
                        hp = hp+ damage[4];
                    }else{
                        hp = hp - damage[4];
                        supportSkillTouch();
                    }
                    dropHp = dropHp + damage[4];
                    //get hit
                    cooling[2][0] = cooling[2][1];
                    
                }
                if(side){
                    setLocation(getX() + 60, getY());
                }else{
                    setLocation(getX() - 60, getY());
                }
            }
        }

        if(Player1_Skill2.class != null){
            if(cooling[3][0] <= 0){
                if(isTouching(Player1_Skill2.class)){
                    Greenfoot.playSound("get_hurt4.mp3");
                    if(yingyang == true){
                        hp = hp+ damage[5];
                    }else{
                        hp = hp - damage[5];
                        supportSkillTouch();
                    }
                    dropHp = dropHp + damage[5];
                    setLocation(getX(),getY()-20);
                    cooling[6][0] = cooling[6][1]; // uptime
                    cooling[3][0] = cooling[2][1];
                }
            }  
        }

        if(Player1_Attake.class != null){
            if(cooling[4][0] <= 0){
                if(isTouching(Player1_Attake.class) ){
                    Greenfoot.playSound("get_hurt0.mp3");
                    if(yingyang == true){
                        hp = hp+ damage[1];
                    }else{
                        hp = hp - damage[1];
                        supportSkillTouch();
                    }
                    dropHp = dropHp + damage[1];
                    cooling[4][0] = cooling[4][1];
                    supportSkillTouch();
                }
                if(isTouching(Player1_Attake_ult.class)){
                    Greenfoot.playSound("get_hurt0.mp3");
                    if(yingyang == true){
                        hp = hp+ damage[7];
                    }else{
                        hp = hp - damage[7];
                        supportSkillTouch();
                    }
                    dropHp = dropHp + damage[7];
                    cooling[4][0] = cooling[4][1];
                }
            }

        }

        if(Player1_DropAttake.class != null){    
            if(cooling[5][0] <= 0){
                if(isTouching(Player1_DropAttake.class) && dAbs < 100 ){
                    hp = hp - damage[2];
                    dropHp = dropHp + damage[2];
                    cooling[5][0] = cooling[4][1];
                    supportSkillTouch();
                }
            }
        }

        if(Player1_Upskill.class != null){
            if(cooling[12][0] <= 0){
                if(isTouching(Player1_Upskill.class)){
                    Greenfoot.playSound("get_hurt2.mp3");
                    if(yingyang == true){
                        hp = hp+ damage[3];
                    }else{
                        hp = hp - damage[3];
                        supportSkillTouch();
                    }
                    dropHp = dropHp + damage[3];
                    cooling[12][0] = cooling[12][1];
                }
            }
        }

        if(Player1_Skill3.class != null){
            if(cooling[13][0] <= 0){
                if(isTouching(Player1_Skill3.class)){
                    Greenfoot.playSound("get_hurt5.mp3");
                    if(yingyang == true){
                        hp = hp+ damage[6];
                    }else{
                        hp = hp - damage[6];
                        supportSkillTouch();
                    }
                    dropHp = dropHp + damage[6];
                    getWorld().addObject(new buff_ice  (), getX(), getY());
                    cooling[13][0] = cooling[13][1];
                }
            }
        }
    }

    private void supportSkillTouch(){
        if(cooling[14][0] < 0){
            cooling[8][0] = cooling[8][1];
            if( cooling[15][0] + cooling[15][1] < 0){
                cooling[15][0] = 0;
            }else{
                cooling[15][0] = cooling[15][0] + cooling[15][1];
            }
            if(cooling[15][0] >= 100){
                cooling[14][0] = cooling[14][1];
            }
        }
        if(dropHp >=dropGold){
            MyWorld world= (MyWorld)getWorld();
            world.drops(getX(),getY());
            dropHp = 0;
        }
        //get hit

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

    public void died(boolean b){
        died = b;
    }

    public int getHP(){
        return hp;
    }

}
