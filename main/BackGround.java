import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class BackGround here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BackGround extends Actor
{
    /**
     * Act - do whatever the BackGround wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        movetion(); 
    }
    
    //action when player move 
    private void movetion(){
        List<Player> p = getWorld().getObjects(Player.class);
        int speed = 6;
        if(Greenfoot.isKeyDown("a")){
            if(getX() <= 776 && p.get(0).getX() < 1000){
                changeMove(+speed);
            }else{
                p.get(0).setLocation(p.get(0).getX() - speed,p.get(0).getY());
                if(p.get(0).isAtEdge()){
                    p.get(0).setLocation(p.get(0).getX() + speed,p.get(0).getY());
                }
            }
        }else if(Greenfoot.isKeyDown("d")){
            if(getX() >= 484 && p.get(0).getX() > 300){
                changeMove(-speed);
            }else{
                p.get(0).setLocation(p.get(0).getX() + speed,p.get(0).getY());
                if(p.get(0).isAtEdge()){
                    p.get(0).setLocation(p.get(0).getX() - speed,p.get(0).getY());
                }
            }
        }
    }
    
    //change all object move 
    private void changeMove(int speed){
        List<Step> s = getWorld().getObjects(Step.class);
        List<Flooring> f = getWorld().getObjects(Flooring.class);
        List<Enemie_min> e = getWorld().getObjects(Enemie_min.class);
        List<Icon> c = getWorld().getObjects(Icon.class);
        List<buff> b = getWorld().getObjects(buff.class);
        List<Enemie_boss> boss = getWorld().getObjects(Enemie_boss.class);
        List<Vehicle> v = getWorld().getObjects(Vehicle.class);
        List<Portal> p = getWorld().getObjects(Portal.class);
        
        if(s != null){
            for(int i = 0; i < s.size(); i++){
                s.get(i).movement(speed);
            }
        }
        if(f != null){
            for(int i = 0; i < f.size(); i++){
                f.get(i).movement(speed);
            }
        }
        if(e != null){
            for(int i = 0; i < e.size(); i++){
                e.get(i).movement(speed);
            }
        }
        if(c != null){
            for(int i = 0; i < c.size(); i++){
                c.get(i).movement(speed);
            }
        }
        if(b != null){
            for(int i = 0; i < b.size(); i++){
                b.get(i).movement(speed);
            }
        }
        if(boss != null){
            for(int i = 0; i < boss.size(); i++){
                boss.get(i).movement(speed);
            }
        }
        if(v != null){
            for(int i = 0; i < v.size(); i++){
                v.get(i).movement(speed);
            }
        }
        if(p != null){
            for(int i = 0; i < p.size(); i++){
                p.get(i).movement(speed);
            }
        }
        setLocation(getX() + speed,getY());
        
    }
    
    //return width
    public int getBackWidth(){
        GreenfootImage image = getImage();
        return image.getWidth();
    }
    
    //return less
    public  int returnLes(){
        return 776;
    }
    
    //return max
    public  int returnMax(){
        return 484;
    }
}
