package core.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable{  
    
    public static final Color BACKGROUND_COLOR = Color.WHITE;

    private JFrame frame;
    
    private ILevel level;
    private Keyboard keyboard;
    private Mouse mouse;
    
    public Main(ILevel level, String name){
        this.level = level;
        
        frame = new JFrame(name);
        setPreferredSize(new Dimension(level.getSize().x, level.getSize().y));
	frame.add(this);
        frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        start();
        //TinySound.init();
        keyboard = new Keyboard();
        addKeyListener(keyboard); 
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
        level.setKeyboard(keyboard);
        level.setMouse(mouse);
    }

    public synchronized void start() {
        new Thread(this).start();
    }

    public void run() {	
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60D;
        double delta = 0;

        while (true){
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while(delta >= 1){   
                delta -= 1;       
                update();     
            }
            render();
        }
    }
    
    public void update(){
        keyboard.update();
        mouse.update();
        level.update();
    }
    
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(2);
            return;
        }      
        Graphics g = bs.getDrawGraphics();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, level.getSize().x, level.getSize().y);
        
        level.render((Graphics2D)g);
        
        g.dispose();
        bs.show();     
    }
}