import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class HP here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HP extends Icon
{
    /**
     * Act - do whatever the HP wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private double hp;
    private int hp2;
    private int colar;

    private int type;
    private String name;
    private int num;

    private GreenfootImage[] animation;
    private int img = 0;
    private Enemie_min enemie;
    
    private int enemieHpO = 0;
    
    private boolean setting = true;
    
    private int enemieNum = 0;
    public HP(String s, int num1 ,int type1){
        if(s =="Player"){
            switch (type1) {
                case 1:
                    setImage("hud_bg.png");
                    getImage().scale(230,78);
                    break;
                case 2:
                    setImage("hp_bar10.png");
                    getImage().scale(140,50);
                    break;
                case 3:
                    setImage("headPic.png");
                    getImage().scale(55,55);
                    break;
                default:
                    break;

            }
        }else if(s == "EXP"){
            setLocation(56,585);
            animation = new GreenfootImage[13];
            for(int i = 0; i <=12; i++){
                GreenfootImage right = new GreenfootImage("skill_4_1_"+i+".png");
                right.scale(100,100);
                animation[i] = right;
            }
        }else if(s == "enemie"){
            
            switch (type1) {
                case 1:
                    setImage("hpBar_bg.png");
                    break;
                case 2:
                    setImage("hpBar_green.png");
                    break;
                default:
                    break;
            }
            getImage().scale(60,8);
        }

        name = s;
        num = num1;
        type = type1;
    }

    public HP(String s, int num1 ,int type1,int num){
        enemieNum = num;
        switch (type1) {
            case 1:
                setImage("hpBar_bg.png");
                break;
            case 2:
                setImage("hpBar_green.png");
                break;
            default:
                break;
        }
        getImage().scale(60,8);
            
        name = s;
        num = num1;
        type = type1;
    }
    
    public String getName(){
        return name;
    }
    
    public void act()
    {
        // Add your action code here.
        if(setting){
            //System.out.println("setting"); 
            List<Enemie_min> enemieALL = getWorld().getObjects(Enemie_min.class);
            
            enemie = enemieALL.get(enemieNum);
                
            if(enemie != null){
                    
                //System.out.println(enemie); 
                enemieHpO = enemie.getHp();
                setting = false;
                    
            }
        }
        
        if(name == "Player"){
            move();
            //died();
        }else if(name == "EXP" ){
            EXP();
            // Exp2();
        }else if(name == "enemie" && setting == false){
            if(enemie != null){
                enemie_move();
                if(enemie.getHp() <= 0){
                getWorld().removeObject(this);
                }
            }
            
        }

        
    }

    //for getting exp
    private void EXP(){
        setLocation(56,585);
        MyWorld world= (MyWorld)getWorld();
        if(world != null){
            hp = ((1 - (world.getEXP() / 100)) * 12 );
            hp2 = (int)Math.ceil(hp);
            if(hp2 > 0){
            setImage(animation[hp2]);
            }else{
            setImage(animation[0]);
            }
        }
    }

    //for monster's exp
    private void Exp2(){
        MyWorld world= (MyWorld)getWorld();
        if(world != null){
            if(world.getEXP() >= 100 || world.getUlt() == true){
                getImage().setTransparency(225);
            }else{
                getImage().setTransparency(180);
            }
        }
    }
    
    private void enemie_move(){
        if(type == 1){
            
            if(enemie.getRotation() == 0){ 
                
                setLocation(enemie.getX()-10,enemie.getY()- (enemie.getImage().getHeight()/2));
            }else{
                setLocation(enemie.getX()+10,enemie.getY()- (enemie.getImage().getHeight()/2));
            }
        }else {
            enemie_hp();
        }
    }
    
    //move backrongd
    private void move(){
        if(type == 1){
            setLocation(120,70);
        }else if (type == 2){
            setLocation(138,58);
            hp();
        }else if (type == 3){
            setLocation(45,58);
        }
        /*MyWorld world= (MyWorld)getWorld();
        if(world != null){
            if(type == 1){
                if(world.get_side(name,num)){
                    setLocation(world.getPlayerX()-10,world.getPlayerY()-30);

                }else{
                    setLocation(world.getPlayerX()+10,world.getPlayerY()-30);
                }
            }else {
                if(world.getUlt() == true && world.getPlayerShield() > 0){
                    shield();
                }else{
                    hp();
                }
            }
        }*/
    }

    //hp heal
    private void enemie_hp(){
        hp = ((1 - (((double)enemie.getHp()/(double)enemieHpO)))* 60);
        hp2 = (int)Math.ceil(hp);
        changeColar((int)enemie.getHp());
        
    
        
        if(hp2 < 56){
            getImage().scale((60 - hp2),8);
            if(enemie.getRotation() == 0){
                setLocation(enemie.getX()-(10+(hp2 / 2)),enemie.getY()- (enemie.getImage().getHeight()/2));
            }else{
                setLocation(enemie.getX()-(-10+(hp2 / 2)),enemie.getY()- (enemie.getImage().getHeight()/2));
            }
        }else{
            getImage().scale(4,8);
            if(enemie.getRotation() == 0){
                setLocation(enemie.getX()- 38,enemie.getY()-(enemie.getImage().getHeight()/2));
              }else{
                setLocation(enemie.getX()- 18,enemie.getY()-(enemie.getImage().getHeight()/2));
            }
        }
    }
    
    //hp player
    private void hp(){
        MyWorld world= (MyWorld)getWorld();
        hp = (world.getPlayerHp()/(((ContrlCenter) getWorld().getObjects(ContrlCenter.class).get(0)).getHP(0))) * 10;
        hp2 = (int)Math.ceil(hp);
        
        if(hp2 > 0){
            setImage("hp_bar"+hp2+".png");
            getImage().scale(140,50);  
            //setLocation(145-((hp2 / 2)),58);
        }else if(hp <= 0){
            getImage().scale(1,1);  
        }
        
        if(hp2 < 56){
            //
            //setLocation(world.getPlayerX()-(10+(hp2 / 2)),world.getPlayerY()-30);
        }else{

        }
    }

    //shield
    private void shield(){
        MyWorld world= (MyWorld)getWorld();
        hp = ((1 - (world.getPlayerShield() / 50)) * 60 );
        hp2 = (int)Math.ceil(hp);
        setImage("hpBar_yellow.png");

        if(hp2 < 56){
            getImage().scale((60 - hp2),8);
            if(world.get_side(name,num)){
                setLocation(world.getPlayerX()-(10+(hp2 / 2)),world.getPlayerY()-30);
            }else{
                setLocation(world.getPlayerX()-(-10+(hp2 / 2)),world.getPlayerY()-30);
            }
        }else{
            getImage().scale(4,8);
            if(world.get_side(name,num)){
                setLocation(world.getPlayerX()- 38,world.getPlayerY()-30);
            }else{
                setLocation(world.getPlayerX()- 18,world.getPlayerY()-30);
            }
        }
    }

    //after dide
    private void died(){
        MyWorld world= (MyWorld)getWorld();
        if(world.getPlayerHp()  <= 0){
            getWorld().removeObject(this);
        }   
    }

    //diffrent type and change colar
    private void changeColar(int hp){
        if (hp >= 30){
            setImage("hpBar_green.png");
            colar = 1;
        }else if(hp < 30){
            setImage("hpBar_red.png");
            colar = 2;
        }
    }
}
