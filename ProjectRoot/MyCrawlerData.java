/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tanveer Muttaqueen
 */
//class ArrayBlockingQueue<String>
//{
//    public String[] str;
//    public int cur, total;
//    final int max = 100;
//    MyCrawlerData cd;
//    public AwesomeStack( MyCrawlerData c )
//    {
//        cd = c;
//        str = new String[max];
//        cur = 0;
//        total = max;
//    }
//    public synchronized int size()
//    {
//        return cur;
//    }
//    public synchronized void put( String s )
//    {
//        while( cur == total )
//        {
//            try
//            {
//                //System.out.println("Waiting");
//                if( cd.runFlag() )
//                    wait();
//                else
//                    notifyAll();
//            } catch (InterruptedException ex)
//            {
//                Logger.getLogger(AwesomeStack.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        str[cur] = s;
//        cur++;
//        notify();
//    }
//    public synchronized String take()
//    {
//        while( cur <= 0 )
//        {
//            try
//            {
//                //System.out.println("Waiting");
//                wait();
//            } catch (InterruptedException ex)
//            {
//                Logger.getLogger(AwesomeStack.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if( cur >= max )
//            notify();
//        return str[--cur];
//    }
//}

public class MyCrawlerData
{

    public boolean runFlag;
    public HashSet<String> websiteSet;
    public ArrayList<String> allWebsiteList;
    public ArrayList<String> processedWebsiteList;
    public ArrayBlockingQueue<String> htmlData;
    public int websiteProcessed, totalwebsite;
    public int current, counter;
    public String allWebsiteListFile;
    public String processedWebsiteListFile;
    public String currentFile;
    public BufferedReader brAllWebsite;
    public BufferedWriter bwAllWebsite;
    public BufferedReader brProcessedWebsite;
    public BufferedWriter bwProcessedWebsite;

    public MyCrawlerData() throws FileNotFoundException
    {
        currentFile = "TextContent\\Current.txt";
        allWebsiteListFile = "TextContent\\AllWebSite.txt";
        processedWebsiteListFile = "TextContent\\ProcessedWebSite.txt";
        websiteSet = new HashSet<String>();
        allWebsiteList = new ArrayList<String>();
        processedWebsiteList = new ArrayList<String>();
        htmlData = new ArrayBlockingQueue<String>(100);
        websiteProcessed = 0;
        totalwebsite = 0;
        current = 0;
        counter = 0;
        runFlag = true;
        //input website that has processed 
        try
        {
            String s;
            brProcessedWebsite = new BufferedReader(new FileReader(processedWebsiteListFile));
            while (true)
            {
                s = brProcessedWebsite.readLine();
                if (s == null)
                {
                    break;
                }
                processedWebsiteList.add(s);
                websiteProcessed++;
            }
            brProcessedWebsite.close();
        } catch (IOException ex)
        {
            System.out.println(ex);
        }
        //set from whaere to start download again
        current = websiteProcessed;

        //input website on hand both processed and unprocessed
        try
        {
            String s;
            brAllWebsite = new BufferedReader(new FileReader(allWebsiteListFile));
            while (true)
            {
                s = brAllWebsite.readLine();
                if (s == null)
                {
                    break;
                }
                websiteSet.add(s);
                allWebsiteList.add(s);
                totalwebsite++;
            }
            brAllWebsite.close();
        } catch (IOException ex)
        {
            System.out.println(ex);
        }
    }

    public void saveAndExit()
    {
        System.out.println("saving all website list");
        try
        {
            bwAllWebsite = new BufferedWriter(new FileWriter(allWebsiteListFile));
            for (String s : allWebsiteList)
            {
                bwAllWebsite.write(s);
                bwAllWebsite.newLine();
            }
            bwAllWebsite.close();
        } catch (IOException ex)
        {
            System.out.println(ex);
        }
        System.out.println("all website saving finished, saving processed website list");
        try
        {
            bwProcessedWebsite = new BufferedWriter(new FileWriter(processedWebsiteListFile));
            for (String s : processedWebsiteList)
            {
                bwProcessedWebsite.write(s);
                bwProcessedWebsite.newLine();
            }
            bwProcessedWebsite.close();
        } catch (IOException ex)
        {
            System.out.println(ex);
        }
    }

    public synchronized int appendWebsiteProcessed(String web)
    {
        System.out.println("adding: " + web);
        processedWebsiteList.add(web);
        increaseProcessedSite();
        return (websiteProcessed-1);
    }

    public synchronized int getWebsiteProcessed()
    {
        return websiteProcessed;
    }

    public synchronized int totalSite()
    {
        return totalwebsite;
    }

    public synchronized void increaseProcessedSite()
    {
        websiteProcessed++;
    }

    public synchronized void increasetotalSite()
    {
        totalwebsite++;
    }

    public synchronized void insertUrl(String s)
    {
        if (!websiteSet.contains(s))
        {
            websiteSet.add(s);
            allWebsiteList.add(s);
            increasetotalSite();
        }
    }

    public synchronized boolean runFlag()
    {
        return runFlag;
    }

    public synchronized void FalseRunFlag()
    {
        runFlag = false;
    }

    public synchronized String getUrl()
    {
        int temp = current;
        if (current < totalSite())
        {
            current++;
            return getUrl(temp);
        } else
        {
            return null;
        }
    }

    public synchronized String getUrl(int t)
    {
        if (t < totalSite())
        {
            return allWebsiteList.get(t);
        } else
        {
            current--;
            return null;
        }
    }

}
