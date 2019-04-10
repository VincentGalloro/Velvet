package core.main.ui;

import core.main.Mouse;
import core.main.ui.elements.ElementFactory;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import core.main.ui.elements.IElementFactory;
import core.main.ui.elements.IElementBuilder;

public class UIController{

    public static class Factory{
        
        private static Pattern argPattern = Pattern.compile("([^,=]+)=([^,]+)");
        
        private static IElement createElement(String line, Mouse mouse, IElementFactory ebf){
            String[] tokens = line.split(":", 2);
            if(tokens.length == 1){
                tokens = new String[]{tokens[0], ""};
            }
            IElement element = ebf.fromString(tokens[0], mouse);
            if(element == null){
                System.err.println("NOT A VALID ELEMENT: "+tokens[0]);
                return null;
            }
            IElementBuilder eb = element.getBuilder();
            Matcher matches = argPattern.matcher(tokens[1]);
            while(matches.find()){
                eb.handleString(matches.group(1).trim(), matches.group(2).trim());
            }
            return element;
        }
        
        private static int getIndent(String line){
            int indent = 0;
            while(indent < line.length() && line.charAt(indent) == ' '){ indent++; }
            return indent;
        }
        
        public static UIController fromFile(File f, Mouse mouse, IElementFactory ebf){
            UIController controller = new UIController();
            ArrayList<IElement> chain = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                controller.root = createElement(br.readLine(), mouse, ebf);
                chain.add(controller.getRoot());
                String line;
                while((line = br.readLine()) != null){
                    int indent = getIndent(line);
                    if(indent >= line.length()){ continue; }
                    IElement e = createElement(line.substring(indent), mouse, ebf);
                    if(e == null){ continue; }
                    
                    if(indent >= chain.size()){ chain.add(e); }
                    else{ chain.set(indent, e); }
                    
                    if(chain.get(indent-1) instanceof IContainer){ 
                        if(((IContainer)chain.get(indent-1)).getElement() != null){
                            System.err.println("Warning: overriding child of "+chain.get(indent-1)+" in "+f.getName());
                        }
                        ((IContainer)chain.get(indent-1)).setElement(e); 
                    }
                    if(chain.get(indent-1) instanceof IListContainer){ ((IListContainer)chain.get(indent-1)).addElement(e); }
                    
                    controller.addElement(e);
                }
            } catch (FileNotFoundException ex) {
                System.err.println("UI FILE DOES NOT EXIST: "+f.getName());
                return null;
            } catch (IOException ex) {}
            return controller;
        }
        
        public static UIController fromFile(File f, Mouse mouse){
            return fromFile(f, mouse, new ElementFactory());
        }
    }
    
    private IElement root;
    private HashMap<String, IElement> elements;
    
    private UIController(){
        elements = new HashMap<>();
    }
    
    public IElement getRoot(){ return root; }
    public IElement getElement(String name){ return elements.get(name); }
    public Stream<IElement> getNamedElements(){ return elements.values().stream(); }
            
    public void addElement(IElement e){ elements.put(e.getName(), e); }
    
}