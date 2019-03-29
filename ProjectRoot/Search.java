/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import mysearchengine.CoolCrawler;
import mysearchengine.DoubleIndexHolder;
import mysearchengine.SingleIndexHolder;

/**
 *
 * @author Tanveer Muttaqueen
 */

class MyEngine
{
    SingleIndexHolder singleindex;
    DoubleIndexHolder doubleindex;
    SearchThread st;
    public MyEngine()
    {
        singleindex = new SingleIndexHolder();
        doubleindex = new DoubleIndexHolder();
        GetIndexFromFile();
        st = new SearchThread(this, singleindex, doubleindex);
    }
    public void search( String s )
    {
        st.runner(s);
    }
    public void GetIndexFromFile()
    {
        System.out.println("input taking existing single index->" + System.currentTimeMillis());
        long l1 = System.currentTimeMillis();
        try
        {
            FileInputStream fin = new FileInputStream("BinaryContent\\SingleIndex.bin");
            System.out.println(fin.available());
            if (fin.available() > 0)
            {
                ObjectInputStream ois = new ObjectInputStream(fin);
                singleindex = (SingleIndexHolder) ois.readObject();
                //System.out.println(test.peek("world"));
                ois.close();
            }
            fin.close();

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("single taking finished...input taking existing double index..time needed->" + (System.currentTimeMillis() - l1));
        l1 = System.currentTimeMillis();
        try
        {        
            FileInputStream fin = new FileInputStream("BinaryContent\\DoubleIndex.bin");
            if (fin.available() > 0)
            {
                ObjectInputStream ois = new ObjectInputStream(fin);
                doubleindex = (DoubleIndexHolder) ois.readObject();
                //System.out.println(test.peek("world"));
                ois.close();
            }
            fin.close();
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("finished taking double index..time needed->" + (System.currentTimeMillis() - l1));
    }
    
}


//public class Search
//{
//    public static void main( String[] args )
//    {
//        new MyEngine();
//    }
//}
