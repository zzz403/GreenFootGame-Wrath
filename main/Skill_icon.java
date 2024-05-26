import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Skill_icon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Skill_icon extends Icon
{
    private int type;
    //Skill_icon
    public Skill_icon(int num){
        setImage("skill_"+num+"_1.png");
        if(num== 4){
            getImage().scale(100,100);
        }else{
            getImage().scale(48,48);
        }
        
            
        type = num;
    }

    public void act()
    {
        // Add your action code here.
        if(type == 4){
            Exp();
        }else{
            skill();
        }
        
        switch(type){
            case 1 :
                setLocation(39,502);
                break;
            case 2 :
                setLocation(115,521);
                break;
            case 3 :
                setLocation(139,583);
                break;
            case 4 :
                setLocation(56,585);
                break;
            }
        // if(Greenfoot.isKeyDown("space")){
            // System.out.println("type"+type+getX()+getY()); 
        // }
    }

    private void Exp(){
        getImage().setTransparency(100);
        
        // MyWorld world= (MyWorld)getWorld();
        // if(world != null){
            // if(world.getEXP() >= 100 || world.getUlt() == true){
                // getImage().setTransparency(225);
            // }else{
                // getImage().setTransparency(100);
            // }
        // }
    }
    
    private void skill(){
        MyWorld world= (MyWorld)getWorld();
        if(world != null){
            if(world.getSkillCooling_world(type) >0 ){
                getImage().setTransparency(100);
            }else{
                getImage().setTransparency(225);
            }
        }
    }

}
