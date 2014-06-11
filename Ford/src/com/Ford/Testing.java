package com.Ford;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.FindFailed;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.sikuli.script.SikuliScript;

public class Testing  
{  
    //Sikuli script object  
    private SikuliScript m_sikscr;  
  
    //Computer screen object  
    private Screen m_screen;  
      
    //Image of Firefox address bar  
    private Pattern m_attachedResume;  
      
    //Image of Firefox go image  
    private Pattern m_uploadResume;  
      
    //Image of Yahoo ID label  
    private Pattern m_chosefile;  
      
    //Image of Yahoo password label  
    private Pattern m_eneterField;  
      
    //Image of Yahoo Signin button  
    private Pattern m_open;  
    
    //Constructor  
    public Testing()  
    {  
        //Load images from files  
        m_attachedResume = new Pattern("C:/Users/mkarthik/Documents/programs.sikuli/1381483961441.png");  
        m_uploadResume = new Pattern("C:/Users/mkarthik/Documents/programs.sikuli/1381483992076.png");  
        m_chosefile = new Pattern("C:/Users/mkarthik/Documents/programs.sikuli/1381484033404.png");  
        m_eneterField = new Pattern("C:/Users/mkarthik/Documents/programs.sikuli/1381484222221.png");  
        m_open = new Pattern("C:/Users/mkarthik/Documents/programs.sikuli/1381484236332.png");  
   
        
        //Create Sikuli script and screen objects  
        try  
        {  
            m_sikscr = new SikuliScript();  
            m_screen = new Screen(); 
            m_screen.click(m_attachedResume);
            m_screen.click(m_uploadResume);
            m_screen.click(m_chosefile);
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        
    }  
}
      
    //This method is invoked before JUnite test case executes  
  