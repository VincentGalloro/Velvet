package velvet.main;

import velvet.velements.interact.UIEventHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends Canvas implements Runnable{  
    
    public static final Color BACKGROUND_COLOR = Color.WHITE;

    private JFrame frame;
    
    private String title;
    private Velvet level;
    private Keyboard keyboard;
    private Mouse mouse;
    private UIEventHandler uiEventHandler;
    
    public Main(Velvet level, String name){
        this.level = level;
        
        Dimension d = level.getSize().getDimension();
        
        this.title = name;
        frame = new JFrame(name);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setResizable(false);

	    setPreferredSize(d);
	    setMaximumSize(d);
	    setMinimumSize(d);
        
    	frame.add(this);
        frame.pack();
        frame.setVisible(true);
	    frame.setLocationRelativeTo(null);
        
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
        FileDrop fileDrop = new FileDrop();
        frame.setDropTarget(fileDrop);
        uiEventHandler = new UIEventHandler(mouse, keyboard);
        
        level.setKeyboard(keyboard);
        level.setMouse(mouse);
        level.setUiEventHandler(uiEventHandler);
        level.setFileDrop(fileDrop);

        level.init();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                level.onClose();
            }
        });
        
        start();
    }

    public synchronized void start() {
        new Thread(this).start();
    }

    public void run() {	
        long lastTime = System.nanoTime(), secondTimer = System.currentTimeMillis();
        double nsPerTick = 1000000000D/60D;
        double delta = 0;

        while (true){
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            
            if(System.currentTimeMillis() > secondTimer+1000){
                secondTimer = System.currentTimeMillis();
                if(delta >= 10){ frame.setTitle(title+" | WARNING [RUNNING BELOW 60FPS] | Frame Deficit = "+delta);}
                else{ frame.setTitle(title); }
            }
            
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
        uiEventHandler.update();
        level.update();
    }
    
    public void render(){
        if (getBufferStrategy() == null){ createBufferStrategy(2); } 
        
        Graphics g = getBufferStrategy().getDrawGraphics();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, level.getSize().getX(), level.getSize().getY());

        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        level.render(new VGraphics((Graphics2D) g));

        g.dispose();
        getBufferStrategy().show();     
    }
}