import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Satan here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Satan extends Enemie_boss
{
    private int hp = 2000;
    private int hp_last;
    
    private int sx = 600; //scaleX
    private int sy = 300; 
    private int moveX = 0;
    private int moveY = 0;

    private GreenfootImage[][] walk;
    private GreenfootImage[][] jump;
    private GreenfootImage[][] attake;
    private GreenfootImage[][] die;
    private GreenfootImage[][] hit;
    private GreenfootImage[][] stand;

    private boolean startSeting = true;
    private int [] damage;

    private int [][] cooling;
    private int [][] skillCooling;
    private int index = 0;

    private String status = "right_stand";

    private int pX;
    private int pY;

    private int playerX;// WHEN DOWNSKILL
    private int playerY;

    private int dAbs;
    private boolean side;
    private boolean attakeNotMove = false;
    private boolean iced = false;

    private boolean died = false;

    private boolean downSkill = false;
    private boolean downSkillFirst = true;

    private int dropGold = 500;
    private int dropHp = 0;
    public Satan (){
        setImage("Demon2_walk_1.png");
        getImage().scale(sx,sy);

        walk = new GreenfootImage[2][6];
        jump = new GreenfootImage[2][6];
        attake = new GreenfootImage[2][6];
        die = new GreenfootImage[2][5];
        hit = new GreenfootImage[2][3];
        stand = new GreenfootImage[2][6];

        cooling = new int [20][2];
        skillCooling = new int [20][2];

        damage = new int [20];
        //Cooling
        for(int i = 1; i <= 19; i++){
            cooling[i-1][0] = 0;
            skillCooling[i-1][0] = 0;
        }

        for(int i = 1; i <= 6; i++){
            GreenfootImage right = new GreenfootImage("Demon2_walk_"+i+".png");
            right.scale(sx,sy);
            walk[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon2_walk_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            walk[1][i-1] = left;
        }
        for(int i = 1; i <= 5; i++){
            GreenfootImage right = new GreenfootImage("Demon2_jump_"+i+".png");
            right.scale(sx,sy);
            jump[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon2_jump_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            jump[1][i-1] = left;
        }
        for(int i = 1; i <= 6; i++){
            GreenfootImage right = new GreenfootImage("Demon2_attack1_"+i+".png");
            right.scale(sx,sy);
            attake[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon2_attack1_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            attake[1][i-1] = left;
        }
        for(int i = 1; i <= 4; i++){
            GreenfootImage right = new GreenfootImage("Demon2_fall_back_"+i+".png");
            right.scale(sx,sy);
            die[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon2_fall_back_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            die[1][i-1] = left;
        }
        for(int i = 1; i <= 3; i++){
            GreenfootImage right = new GreenfootImage("Demon2_hit_"+i+".png");
            right.scale(sx,sy);
            hit[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon2_hit_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            hit[1][i-1] = left;
        }
        for(int i = 1; i <= 5; i++){
            GreenfootImage right = new GreenfootImage("Demon2_ready_"+i+".png");
            right.scale(sx,sy);
            stand[0][i-1] = right;
            GreenfootImage left = new GreenfootImage("Demon2_ready_"+i+".png");
            left.mirrorHorizontally();
            left.scale(sx,sy);
            stand[1][i-1] = left;
        }
    }

    public void act()
    {
        if(startSeting){
            seting();
        }
        for(int i = 1; i <= 19; i++){
            cooling[i-1][0] --;
            skillCooling[i-1][0] --;
        }
        if(hp <= 0){
            died();
        }else{
            if(iced){
                moveX = 0;
            }else{

                fall();
                skill();
            }
            getPlayerLocation();
            touchingSkill();
            touchingBuff();
        }
        if(died){
            MyWorld world= (MyWorld)getWorld();
            world. EnemieNum(-1);
            world.changeText("BossName",null);
            getWorld().removeObject(this);
        }
        // Add your action code here.
    }

    private void seting(){
        hp =((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2);
        hp_last = hp;
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

        skillCooling[0][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(2); // get enem
        skillCooling[1][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(4); // get ghost
        skillCooling[2][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(5); // crush
        skillCooling[3][1] = ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getEnemieCooling(7); // fire

        startSeting = false;
    }

    private void skill(){
        if(hp < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 2 && skillCooling[0][0] <= 0){
            MyWorld world= (MyWorld)getWorld();
            world.addReapper(1);
            world.addDemons(2);
            skillCooling[0][0] = skillCooling[0][1];
        }else if(hp < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 5 * 4 && skillCooling[1][0] <= 0){
            getWorld().addObject(new Ghost(), getX()+ Greenfoot.getRandomNumber(300), getY()-500);
            getWorld().addObject(new Ghost(), getX()- Greenfoot.getRandomNumber(300), getY()-500);
            skillCooling[1][0] = skillCooling[1][1];            
        }else if ( hp < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 3 * 2 && skillCooling[2][0] <= 0){
            if(downSkillFirst){   
                getWorld().addObject(new Satan_dounskill(), getX() , getY() + 50);
                cooling[16][0] = cooling[16][1];
                downSkillFirst = false;
            }else if(cooling[16][0] == 30){
                ((Satan_dounskill) getWorld().getObjects(Satan_dounskill.class).get(0)).setLocation();
                MyWorld world= (MyWorld)getWorld();
                playerX = world.getPlayerX();
            }else if(cooling[16][0] <= 10){
                downSkill = true;
                downSkillFirst = true;
                setLocation(playerX,playerY);
                skillCooling[2][0] = skillCooling[2][1];
            }
        }else if(hp < ((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(2) / 4 && skillCooling[3][0] <= 0){
            getWorld().addObject(new Satan_hellfire(), getX() , 600);
            skillCooling[3][0] = skillCooling[3][1];
        }
    }

    private void touchingBuff(){
        if(isTouching(buff_ice.class)){
            iced = true;
        }else{
            iced = false;
        }
    }

    private void touchPlayer(){
        if(isTouching(Player.class) && dAbs < 300){
            MyWorld world= (MyWorld)getWorld();
            world. deductHp(damage[0]);
        }
    }

    private void supportSkillTouch(){
        if(cooling[14][0] < 0){
            cooling[8][0] = cooling[8][1];
            attakeNotMove = false;
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

    private void getPlayerLocation(){
        MyWorld world= (MyWorld)getWorld();
        if(cooling[10][0] <= 0){
            if(world != null){
                pX = world.getPlayerX();
                pY = world.getPlayerY();
                // if(pY > 341){
                // attakeNotMove = false;
                // }
                dAbs = Math.abs(getX() - pX);
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
        if(iced!= true){
            movetion();
        }
    }

    private void movetion(){
        if( cooling[10][0] <= 0){ // didn't die
            if( cooling[8][0] <= 0){ // not hit
                if(cooling[0][0] <= 0){
                    if(dAbs <= 200){
                        move_and_stand();
                    }else if(dAbs > 200 && attakeNotMove != true){
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

    private void move_and_stand(){
        if(cooling[1][0] <= 0 ){ //&& pY > 341
            moveX = 0;
            if(side != true){
                if(status.indexOf("attack") == -1){
                    index = 0;
                    attakeNotMove = true;
                }
                status = "right_attack";
            }else{
                if(status.indexOf("attack") == -1){
                    index = 0;
                    attakeNotMove = true;
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

    private void run(){
        cooling[1][0]= cooling[1][1];
        moveX = 3;
        if(side != true){
            status = "right_run";
        }else{
            status = "left_run";
        }
    }

    private void walk(){
        cooling[1][0]= cooling[1][1];
        moveX = 1;
        if(side != true){
            status = "right_walk";
        }else{
            status = "left_walk";
        }
    }

    private void fall(){
        // Actor Flooring = getOneObjectAtOffset(0, getImage().getHeight()/2, Flooring.class);

        if(isTouching (Flooring.class) ){
            setLocation(getX(),470);
            if(downSkill){
                ((Satan_dounskill) getWorld().getObjects(Satan_dounskill.class).get(0)).start();
                MyWorld world= (MyWorld)getWorld();
                world.stepDie();
                move_and_stand();
            }
            downSkill = false;
        }else{ 
            if(cooling[7][0] <= 0){
                if(cooling[6][0] <= 0){
                    if(downSkill){

                        setLocation(getX(),getY()+ 30);
                        if(status.indexOf("left") == -1){
                            status = "right_jump";
                        }else{
                            status = "left_jump";
                        }
                    }else{
                        setLocation(getX(),getY()+ 15);
                    }
                }else{
                    setLocation(getX(),getY()-20);
                }
                cooling[7][0] = cooling[7][1];
            }
        }
    }

    public int getHP(){
        return hp;
    }

    public boolean getSide(){
        return side;
    }

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
            if(index > 5){
                index = 0;
                attakeNotMove = false;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            if(index == 3){
                touchPlayer();
            }
            setImage(attake[0][index]);
            index ++;
        }else if(status.contains("left_attack")){
            if(index > 5){
                index = 0;
                attakeNotMove = false;
                touchPlayer();
                cooling[1][0]= cooling[1][1];
            }
            if(index == 3){
                touchPlayer();
            }
            setImage(attake[1][index]);
            index ++;

        }else if(status.contains("right_jump")){
            if(index > 5){
                index = 0;
            }
            setImage(jump[0][index]);
            index ++;
        }else if(status.contains("left_jump")){
            if(index > 5){
                index = 0;
            }
            setImage(jump[1][index]);
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

    private void touchingSkill(){
        if(Player1_Skill1.class != null){
            if(isTouching(Player1_Skill1.class) && dAbs < 200){
                Greenfoot.playSound("get_hurt3.mp3");
                if(cooling[2][0] <= 0){
                    hp = hp - damage[4];
                    dropHp = dropHp + damage[4];
                    //get hit
                    cooling[2][0] = cooling[2][1];
                    supportSkillTouch();
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
                    hp = hp - damage[5];
                    dropHp = dropHp + damage[5];
                    setLocation(getX(),getY()-20);
                    cooling[6][0] = cooling[6][1]; // uptime
                    cooling[3][0] = cooling[2][1];
                    supportSkillTouch();
                }
            }  
        }

        if(Player1_Attake.class != null){
            if(cooling[4][0] <= 0){
                if(isTouching(Player1_Attake.class) && dAbs < 100 ){
                    Greenfoot.playSound("get_hurt0.mp3");
                    hp = hp - damage[1];
                    dropHp = dropHp + damage[1];
                    cooling[4][0] = cooling[4][1];
                    supportSkillTouch();
                }
                if(isTouching(Player1_Attake_ult.class)){
                    Greenfoot.playSound("get_hurt0.mp3");
                    hp = hp - damage[7];
                    dropHp = dropHp + damage[7];
                    cooling[4][0] = cooling[4][1];
                    supportSkillTouch();
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
                    hp = hp - damage[3];
                    dropHp = dropHp + damage[3];
                    cooling[12][0] = cooling[12][1];
                    supportSkillTouch();
                }
            }
        }

        if(Player1_Skill3.class != null){
            if(cooling[13][0] <= 0){
                if(isTouching(Player1_Skill3.class)){
                    Greenfoot.playSound("get_hurt5.mp3");
                    hp = hp - damage[6];
                    dropHp = dropHp + damage[6];
                    getWorld().addObject(new buff_ice  (), getX(), getY());
                    cooling[13][0] = cooling[13][1];
                    supportSkillTouch();
                }
            }
        }
        if(hp_last != hp){
            getWorld().addObject(new Damage(hp_last - hp),getX(),getY() - 20);
            hp_last = hp;
        }
    }

}
