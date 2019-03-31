package core.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable{  
    
    public static final Color BACKGROUND_COLOR = Color.WHITE;

    private JFrame frame;
    
    private Velvet level;
    private Keyboard keyboard;
    private Mouse mouse;
    
    public Main(Velvet level, String name){
        this.level = level;
        
        frame = new JFrame(name);
        frame.setPreferredSize(new Dimension(level.getSize().x, level.getSize().y));
	frame.add(this);
        frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        BufferedImage icon;
        try {
            icon = ImageIO.read(new File("Velvet_Icon.png"));
            frame.setIconImage(icon);
        } catch (IOException ex) {
            System.err.println("Could not find Velvet Logo :(");
        }
        
        keyboard = new Keyboard();
        addKeyListener(keyboard); 
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
        
        level.setKeyboard(keyboard);
        level.setMouse(mouse);
        
        level.init();
        
        start();
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
            if(delta >= 1){   
                delta -= 1;       
                update();
                render();     
            }
        }
    }
    
    public void update(){
        keyboard.update();
        mouse.update();
        level.update();
    }
    
    public void render(){
        if (getBufferStrategy() == null){ createBufferStrategy(2); } 
        
        Graphics g = getBufferStrategy().getDrawGraphics();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, level.getSize().x, level.getSize().y);
        
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                            RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        level.render(new VGraphics((Graphics2D)g));
        
        g.dispose();
        getBufferStrategy().show();     
    }
}