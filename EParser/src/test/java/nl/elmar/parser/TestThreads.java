package nl.elmar.parser;

import java.util.Calendar;

import org.junit.Test;

public class TestThreads {

   

    public static void main(String args[]){
        Calendar end, start = Calendar.getInstance();
        String filename = "JI630.xml";
        Thread t1;
        
        for(int i=0;i<10;i++){
            t1 = new Thread(new ThreadParser(filename),"t"+i);
            t1.start();  
        }
        
        end = Calendar.getInstance();
        System.out.println("Total ms: " + (end.getTimeInMillis() - start.getTimeInMillis()));
        
    }
}
