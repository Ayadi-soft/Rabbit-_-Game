import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.Class.*;
import javax.sound.sampled.*;
import javax.swing.*;
//import game2D.*;

/**
 * 
 *
 */
public class GCTileMapDemo extends GameCore {

    // It is often useful to move the whole screen image off a bit
    // and it is helpful if store these values so they are available
    // to the rest of the class
    int xoffset = 50; // Move tile map 32 pixels to the right
    int yoffset = 50; // Move tile map 64 pixels down
    private Sprite rabbit;
    private Sprite sprite;
    int speed=50;
    long total;                     // Total time elapsed
    TileMap tmap = new TileMap();   // Our tile map, note that we load it in init()
    Animation anim;
    SoundPlayer son;
    Score s;
     int time_limit= 15000;    //time for stage of game
    int re= 2000, ri= 2000;
     boolean stop=false;
    int h1_x =0 ;
    int h1_y=0;
    int h2_x ,h2_y ,p=1,p2=1;
    
    /**
     * The obligatory main method that creates
     * an instance of our GCTester class and
     * starts it running
     * 
     * @param args  The list of parameters this program might use (ignored)
     */
    public static void main(String[] args) {

        //difficulty();
        
        GCTileMapDemo gct = new GCTileMapDemo();
        gct.init();
        
        
        // Start in windowed mode at 736x256 pixels
        gct.run(false,1000,1000);
    }

    /**
     * Un exemple de méthode - remplacez ce commentaire par le vôf4tre
     *
     * @param  y   un paramètre pour cette méthode
     * @param  x   un autre paramètre
     * @return     la somme des deux paramètres
     */
    public void difficulty()
    {
        //chose difficulty
        String[] operateurs = new String[]{"easy","medium","hard"};
 
        String operateur = (String)JOptionPane.showInputDialog(null,"choose difficulty",
            "difficulty",JOptionPane.QUESTION_MESSAGE, null, operateurs, operateurs[0]);
 
        if("easy".equals(operateur)){
        time_limit=20000;
        }
        else if ("medium".equals(operateur)){
            time_limit=15000; 
        }
        else if ("hard".equals(operateur)){
            time_limit=10000;
        }else
        System.exit(0);
        
    }

    /**
     * Initialise the class, e.g. set up variables, load images,
     * create animations, register event handlers etc.
     */
    public void init()
    {
        total = 0;
        
        son=new SoundPlayer(); son.init();
        s=new Score(this);
        // load images
       
        Image player1 = loadImage("images/img1.png");
        Image player2 = loadImage("images/img2.png");
        Image player3 = loadImage("images/img3.png");

        // create sprite
        Animation anim = new Animation();
        anim.addFrame(player1, 250);
        anim.addFrame(player2, 250);
        anim.addFrame(player3, 200);
        
      
       rabbit = new Sprite(anim);
       
            
        /*
        Animation anim = new Animation();
        Image rock1 = loadImage("Images/rock.png");
        anim.addFrame(rock1, 250);
        rock = new Sprite(anim);
        */
        rabbit.setX(100);
        rabbit.setY(100);
        rabbit.setVelocityX(0.1F);
        rabbit.setVelocityY(0.1F);
        
        rabbit.show();
        // Load the tile map and print it out so we can check it is valid
        tmap.loadMap("maps", "level1-map.txt");
        System.out.println(tmap);
       //h1_x= 8; h1_y=1; p=1;
       hunter_init();
    }

    /**
     * Draw the current state of the game
     */
    public void draw(Graphics2D g)
    {       
        // Be careful about the order in which you draw objects - you
        // should draw the background first, then work your way 'forward'
        g.setColor(Color.white);
        g.fillRect(0,0,xoffset+904,yoffset+500);
        
        // Draw the tile map
        tmap.draw(g,xoffset,yoffset);
        
        // Show the 'score'
        g.setColor(Color.GRAY);
        Font myFont = new Font ("Courier New", 1, 15);
        g.setFont (myFont);
        g.drawString("Time Expired:" + total+"     Score: " + s.getscore()+"   "+"  Level: " + s.getlevel()+"   ",xoffset,yoffset-8);
        
        
        rabbit.draw(g);
    }

    /**
     * Update any sprites and check for collisions
     * 
     * @param elapsed The elapsed time between this call and the previous call of elapsed
     */    
    public void update(long elapsed)
    {
        if(!stop){
        total += elapsed;
       // sprite.update(total);
        if(total >= re && total  <= re+70){hunter1_move(); re+=1000; if (re>15000)re=1000;}
        if(s.level==2 && total >= ri && total  <= ri+70){hunter2_move(); ri+=1000; if (ri>15000)ri=1000;}
        if (total > time_limit) {
            stop=true;
            son.play("maybe-next-time");
         JFrame frame = new JFrame("showMessageDialog");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JOptionPane.showMessageDialog(frame, "you loose end of time",            "finish",            JOptionPane.ERROR_MESSAGE);
//replay
JOptionPane jop = new JOptionPane();            
int option = jop.showConfirmDialog(null, "Do you want to replay ?", "game replay", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            total=0;
if(option == JOptionPane.OK_OPTION)
game_replay();          
else

System.exit(0);
            
            
           // stop();
        
        }
        }
        
    }
    
    
    
    /**
     * Un exemple de méthode - remplacez ce commentaire par le vôf4tre
     *
     * @param  y   un paramètre pour cette méthode
     * @param  x   un autre paramètre
     * @return     la somme des deux paramètres
     */
    public void detect_border()
    {
        if (rabbit.getX()<(xoffset+tmap.getTileWidth()))
        {
        rabbit.setX(rabbit.getX()+speed);
             son.play("over-her");
        }
        
        if(rabbit.getX()>(tmap.getMapWidth()*tmap.getTileWidth()-xoffset))
        {
        rabbit.setX(rabbit.getX()-speed);
             son.play("over-her");
        }
        
        if(rabbit.getY()<yoffset+tmap.getTileHeight())
        {
            rabbit.setY(rabbit.getY()+speed);
            son.play("over-her");
        }
         if(rabbit.getY()>(tmap.getTileHeight()*tmap.getMapHeight()-yoffset))
        {
            rabbit.setY(rabbit.getY()-speed);
            son.play("over-her");
        }
    }

    /**
     *  methode detect collision
     *
     * 
     */
    public void detect_collision()
    {
        // 
        for (int r=0; r< tmap.getMapHeight() ; r++)
        {
            for (int c=0; c< tmap.getMapWidth()  ; c++)
                {
                        if(rabbit.getX()-speed==tmap.getTileXC(c,r) && rabbit.getY()-speed==tmap.getTileYC(c,r))
                        { //System.out.println("collision");
                        if(tmap.getTileChar(c,r)=='c')
                        eat(c,r);
                        if(tmap.getTileChar(c,r)=='x')
                        hunted(c,r);
                        if(tmap.getTileChar(c,r)=='g')
                        you_lose(c,r);
                        }
                }
        }
        
     }

    public void eat(int x,int y)
    { 
        son.play("this-is-delicious");
        tmap.setTileChar('.',x,y);
        s.add_point();
        
    }
    
    
     public void hunted(int x,int y)
    { 
        son.play("gunshot");
        tmap.setTileChar('w',x,y);
    stop=true;
     JFrame frame = new JFrame("showMessageDialog");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JOptionPane.showMessageDialog(frame, "You lose this stage",  "defeat",    JOptionPane.ERROR_MESSAGE);
//replay
JOptionPane jop = new JOptionPane();            
int option = jop.showConfirmDialog(null, "Do you want to replay ?", "game replay", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
if(option == JOptionPane.OK_OPTION)
game_replay();          
else
System.exit(0);
        
    }
    
        public void you_lose(int x,int y)
    {
        son.play("oh-my-god");
    tmap.setTileChar('d',x,y);
    stop=true;
   // long start=System.nanoTime(); 
//while((System.nanoTime()-start)<600000000); 


  JFrame frame = new JFrame("showMessageDialog");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JOptionPane.showMessageDialog(frame, "You lose this stage",  "defeat",    JOptionPane.ERROR_MESSAGE);
//replay
JOptionPane jop = new JOptionPane();            
int option = jop.showConfirmDialog(null, "Do you want to replay ?", "game replay", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
if(option == JOptionPane.OK_OPTION)
game_replay();          
else
System.exit(0);
    }
    
    public void move_to_level2()
    {
        stop=true;
        rabbit.setX(100);
        rabbit.setY(100);
        son.play("nice-work");
        JFrame frame = new JFrame("showMessageDialog");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JOptionPane.showMessageDialog(frame, "Level accomplished successfully", "Great",            JOptionPane.INFORMATION_MESSAGE);
    
    
            tmap.loadMap("maps", "level2-map.txt");
            s.setscore(0);
            s.level=2;
            total=0;
            hunter_init();
           // h1_x =11;
            //h1_y=1;
          //  h2_x =5;
           // h2_y =6;     p=1;p2=1;
            stop=false;
    
    }
    public void game_replay()
        {
            rabbit.setX(100);
        rabbit.setY(100);
        if(s.getlevel()==1)
           { tmap.loadMap("maps", "level1-map.txt");
           // re=2000; h1_x= 8; h1_y=1; p=1; 
           hunter_init();
            }
            else
           { tmap.loadMap("maps", "level2-map.txt");
           // re=2000; ri=2000; h1_x =11;
          //  h1_y=1;
          //  h2_x =5;
          //  h2_y =6;     p=1;p2=1;
          hunter_init();
            }
            s.setscore(0);
              son.play("come-on");
            total=-2000; 
           
            stop=false;
    }
    
    public void you_win()
    {
        stop=true;
        son.play("nice-work");
          JFrame frame = new JFrame("showMessageDialog");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JOptionPane.showMessageDialog(frame, "Nice work you win the game",  "Vectory", JOptionPane.INFORMATION_MESSAGE);
    System.exit(0);
   
    }
    
     public void hunter_init()
    { re=2000; ri=2000; p=1;p2=1; h1_x =0 ;h1_y=0;
    for (int r=0; r< tmap.getMapHeight() ; r++)
        {
            for (int c=0; c< tmap.getMapWidth()  ; c++)
                {
                    if(tmap.getTileChar(c,r)=='h')
                    {
                        if(h1_x==0 && h1_y==0)
                    {h1_x=c;
                    h1_y=r;}
                    else
                    {
                    h2_x=c;
                    h2_y=r;
                    }
                    
                    //System.out.println(p+"the hunter in position "+ h1_x +"  "+ h1_y);
                }}
            }
        }
    public void hunter1_move()
    {
        
        
       if( p<3)
        {
            tmap.setTileChar('x',h1_x,h1_y);
            h1_x++;
           // h1_y++;
            tmap.setTileChar('h',h1_x,h1_y);
             p++;           //right
        }else if(p<5)
        {
            //bas
            tmap.setTileChar('x',h1_x,h1_y);
            //h1_x++;
            h1_y++;
            tmap.setTileChar('h',h1_x,h1_y);
             p++;
        }else if(p<7)
        {
            //left
            tmap.setTileChar('x',h1_x,h1_y);
            h1_x--;
           // h1_y++;
            tmap.setTileChar('h',h1_x,h1_y);
            p++;
        }else if(p<9)
        {
            //haut
            tmap.setTileChar('x',h1_x,h1_y);
           // h1_x++;
            h1_y--;
            tmap.setTileChar('h',h1_x,h1_y);
            p++;
    }else if(p==9)
        {
            tmap.setTileChar('x',h1_x,h1_y);
            h1_x++;
           // h1_y++;
            tmap.setTileChar('h',h1_x,h1_y);
                       p=2;
        } 
        
    }
   
    public void hunter2_move()
    {
        
        System.out.println(p+"the hunter 222 in position "+ h2_x +"  "+ h2_y);
       if( p2<3)
        {
            tmap.setTileChar('x',h2_x,h2_y);
            h2_x++;
           // h1_y++;
            tmap.setTileChar('h',h2_x,h2_y);
             p2++;           //right
        }
    else if(p2==3)
        {
            tmap.setTileChar('x',h2_x,h2_y);
            h2_x-=2;
           // h1_y++;
            tmap.setTileChar('h',h2_x,h2_y);
                       p2=1;
        }
      
    
        
    }
    /**
     * Override of the keyPressed event defined in GameCore to catch our
     * own events
     * 
     *  @param e The event that has been generated
     */
    public void keyPressed(KeyEvent e) 
    { 
        // Did the user press the 'C' key?
        if (e.getKeyCode() == KeyEvent.VK_C)
        {
            // Change some tile map entries
           // tmap.setTileChar('.',3,2);
          // tmap.setTileChar('c',4,2);
        }
        
        if (e.getKeyCode() == KeyEvent.VK_M)
        {
            // Load a different tile map
           // hunter1_move();
         move_to_level2();
        //  hunter_init();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            // Load a different tile map
            rabbit.setX(rabbit.getX()+speed);
            detect_border();
            detect_collision();
            
             //s.play("a");
            // System.out.println("object is in position "+rock.getX() +"  "+rock.getY());
            //Animation anim = new Animation();
        //Image player2 = loadImage("images/img2.png");
        //anim.addFrame(player2, 350);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            // Load a different tile map
            rabbit.setX(rabbit.getX()-speed);
            detect_border();
            detect_collision();
            //s.play("b");
            
        }
        
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            // Load a different tile map
            rabbit.setY(rabbit.getY()+speed);
            detect_border();
            detect_collision();
            //s.play("c");
          
        }
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            // Load a different tile map
            rabbit.setY(rabbit.getY()-speed);
            detect_border();
            detect_collision();
            
            //s.play("d");
        }
        
    }
    
   
    
    
     
}
