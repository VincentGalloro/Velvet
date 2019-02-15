package core.main.ui;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.ColumnElement;
import core.main.ui.elements.ElementFactory;
import core.main.ui.elements.IContainer;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IListContainer;
import core.main.ui.elements.PaddingElement;
import core.main.ui.elements.RowElement;
import core.main.ui.elements.ScaleElement;
import core.main.ui.elements.TextElement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ElementManager {

    public static class Factory{
        
        public static ElementManager fromFile(File file){
            ElementManager em = new ElementManager();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                ArrayList<IElement> chain = new ArrayList<>();
                while((line = br.readLine()) != null){
                    int indent = 0;
                    while(line.charAt(indent)==' '){ indent++; }
                    String[] tokens = line.substring(indent).split(" ");
                    IElement current = ElementFactory.fromString(tokens[0]);
                    if(tokens.length >= 2){
                        em.namedElements.put(tokens[1], current);
                    }
                    if(tokens.length >= 3){
                        if(current instanceof TextElement){ 
                            String[] words = new String[tokens.length-2];
                            for(int i = 0; i < words.length; i++){
                                words[i] = tokens[i+2];
                            }
                            ((TextElement)current).setText(String.join(" ", words)); 
                        }
                        if(current instanceof PaddingElement){ 
                            ((PaddingElement)current).setPadding(Integer.parseInt(tokens[2])); 
                        }
                        if(current instanceof ColumnElement){ 
                            ((ColumnElement)current).setSeperation(Integer.parseInt(tokens[2])); 
                        }
                        if(current instanceof RowElement){ 
                            ((RowElement)current).setSeperation(Integer.parseInt(tokens[2])); 
                        }
                    }
                    if(tokens.length >= 4){
                        if(current instanceof ScaleElement){ 
                            ((ScaleElement)current).setSize(
                                    new Vector(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]))); 
                        }
                    }
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
    
    private IElement root;
    private HashMap<String, IElement> namedElements;
    
    public ElementManager(){
        namedElements = new HashMap<>();
    }
    
    public IElement getElementByName(String name){
        return namedElements.get(name);
    }
    
    public void render(VGraphics g){
        root.render(g);
    }
}