package core.main.ui;

import core.main.Mouse;
import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.ElementBuilder;
import core.main.ui.elements.ElementBuilderFactory;
import core.main.ui.elements.IContainer;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IListContainer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ElementManager {

    public static class Factory{
        
        public static ElementManager fromFile(File file, Mouse mouse){
            ElementManager em = new ElementManager(mouse);
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                ArrayList<IElement> chain = new ArrayList<>();
                while((line = br.readLine()) != null){
                    int indent = 0;
                    while(line.charAt(indent)==' '){ indent++; }
                    String[] tokens = line.substring(indent).split(" ");
                    ElementBuilder currentBuilder = ElementBuilderFactory.fromString(tokens[0]);
                    String elemName = null;
                    for(int i = 1; i < tokens.length; i++){
                        String[] argTokens = tokens[i].split("=");
                        if(argTokens[0].equals("name")){
                            elemName = argTokens[1];
                        }
                        else{
                            currentBuilder.handleString(argTokens[0], argTokens[1]);
                        }
                    }
                    IElement current = currentBuilder.create();
                    em.namedElements.put(elemName, current);
                    
                    if(indent > 0){
                        if(chain.get(indent-1) instanceof IContainer){
                            ((IContainer)chain.get(indent-1)).setElement(current);
                        }
                        else if(chain.get(indent-1) instanceof IListContainer){
                            ((IListContainer)chain.get(indent-1)).addElement(current);
                        }
                    }
                    else{ em.root = current; }
                    
                    if(indent >= chain.size()){ chain.add(current); }
                    else{ chain.set(indent, current); }
                }
            } catch (FileNotFoundException ex) {} catch (IOException ex) {}
            return em;
        }
    }
    
    private Mouse mouse;
    private IElement root, currentHover;
    private HashMap<String, IElement> namedElements;
    private Vector pos;
    
    public ElementManager(Mouse mouse){
        this.mouse = mouse;
        namedElements = new HashMap<>();
        pos = new Vector();
    }
    
    public void setPos(Vector pos){ this.pos = pos; }
    
    public void update(){
        if(mouse.isReleased(Mouse.LEFT) && currentHover != null){
            currentHover.onMouseRelease();
        }
        
        IElement newHover = root.getHover(mouse.getPos().subtract(pos));
        if(currentHover != newHover && !mouse.isDown(Mouse.LEFT)){
            if(currentHover != null){ currentHover.onHoverEnd(); }
            currentHover = newHover;
            if(currentHover != null){ currentHover.onHoverStart(); }
        }
        
        if(mouse.isPressed(Mouse.LEFT) && currentHover != null){
            currentHover.onMousePress();
        }
    }
    
    public IElement getElementByName(String name){
        return namedElements.get(name);
    }
    
    public void render(VGraphics g){
        g.save();
        g.translate(pos);
        root.render(g);
        g.reset();
    }
}