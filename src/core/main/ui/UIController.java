package core.main.ui;

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

public class UIController{

    public static class Factory{
        
        private static IElement createElement(String line){
            String[] tokens = line.split(" ");
            ElementBuilder eb = ElementBuilderFactory.fromString(tokens[0]);
            for(int i = 1; i < tokens.length; i++){
                String[] parts = tokens[i].split("=");
                eb.handleString(parts[0], parts[1]);
            }
            return eb.get();
        }
        
        private static int getIndent(String line){
            int indent = 0;
            while(indent < line.length() && line.charAt(indent) == ' '){ indent++; }
            return indent;
        }
        
        public static UIController fromFile(File f){
            UIController controller = new UIController();
            ArrayList<IElement> chain = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                controller.setRoot(createElement(br.readLine()));
                chain.add(controller.getRoot());
                String line;
                while((line = br.readLine()) != null){
                    int indent = getIndent(line);
                    if(indent >= line.length()){ continue; }
                    IElement e = createElement(line.substring(indent));
                    
                    if(indent >= chain.size()){ chain.add(e); }
                    else{ chain.set(indent, e); }
                    
                    if(chain.get(indent-1) instanceof IContainer){ ((IContainer)chain.get(indent-1)).setElement(e); }
                    if(chain.get(indent-1) instanceof IListContainer){ ((IListContainer)chain.get(indent-1)).addElement(e); }
                    
                    controller.addElement(e);
                }
            } catch (FileNotFoundException ex) {} catch (IOException ex) {}
            return controller;
        }
    }
    
    private IElement root;
    
    private HashMap<String, IElement> elements;
    
    public UIController(){
        elements = new HashMap<>();
    }
    
    public IElement getRoot(){ return root; }
    
    public void setRoot(IElement r){ root = r; } 
    public void addElement(IElement e){ elements.put(e.getName(), e); }
    
    public IElement getElement(String name){ return elements.get(name); }
}