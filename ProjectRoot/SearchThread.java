/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.print.Collation;
import mysearchengine.DoubleIndexHolder;
import mysearchengine.ExtractKeyWord;
import mysearchengine.Pair;
import mysearchengine.SingleIndexHolder;

/**
 *
 * @author Tanveer Muttaqueen
 */
class comparator implements Comparator
{

    @Override
    public int compare(Object o1, Object o2)
    {
        Pair e1 = (Pair) o1;
        Pair e2 = (Pair) o2;
        return e2.freq - e1.freq;
    }
}

class SearchThread
{

    public MyEngine myengine;
    public Thread t;
    public SingleIndexHolder singleindex;
    public DoubleIndexHolder doubleindex;
    public HashSet<String> hs;
    public ArrayList<String> al;
    public ArrayList<String> websiteList;

    public SearchThread(MyEngine me, SingleIndexHolder si, DoubleIndexHolder di)
    {
        myengine = me;
        singleindex = si;
        doubleindex = di;
        hs = new HashSet<String>();
//        t = new SearchThread();
        hs = new HashSet<String>();
        al = new ArrayList<>();
        websiteList = new ArrayList<>();
        BufferedReader brProcessedWebsite;
        System.out.println("input taking Processed Website List: ");
        long l1 = System.currentTimeMillis();
        try
        {
            String s;
            brProcessedWebsite = new BufferedReader(new FileReader("TextContent\\ProcessedWebSite.txt"));
            while (true)
            {
                s = brProcessedWebsite.readLine();
                if (s == null)
                {
                    break;
                }
                websiteList.add(s);
            }
            brProcessedWebsite.close();
        } catch (IOException ex)
        {
            System.out.println(ex);
        }
        System.out.println("processed website taking finished..time needed->" + (System.currentTimeMillis() - l1) );
        l1 = System.currentTimeMillis();
        System.out.println("input taking Stop Word List: ");
        try
        {
            String s;
            BufferedReader br = new BufferedReader(new FileReader("TextContent\\HaramWord.txt"));
            while (true)
            {
                s = br.readLine();
                //System.out.println(s);
                if (s == null)
                {
                    break;
                }
                hs.add(s);
            }
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(ExtractKeyWord.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(ExtractKeyWord.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("All website taking finished...time needed->" +  (System.currentTimeMillis() - l1) );
    }

    public void runner(String s)
    {
        String TobeUsedInHtml = "Search Reasult For \'" + s + "\'";
        long l1 = System.currentTimeMillis();
        ArrayList<String> str;
        str = processParagraph(s);
        System.out.println(str);
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
        for (int i = 0; i < str.size(); i++)
        {
            String key = str.get(i);
            ArrayList<Pair> al = singleindex.peek(key);
            if( al == null ) continue;
            System.out.println(key);
            System.out.println(al);
            for (Pair x : al)
            {
                if (!hm.containsKey(x.web))
                {
                    hm.put(x.web, 0);
                }
                hm.put(x.web, hm.get(x.web) + 1 * x.freq);
            }
        }
        for (int i = 1; i < str.size(); i++)
        {
            String key = str.get(i - 1) + str.get(i);
            ArrayList<Pair> al = doubleindex.peek(key);
            if( al == null ) continue;
            System.out.println(key + " #double");
            System.out.println(al);
            for (Pair x : al)
            {
                if (!hm.containsKey(x.web))
                {
                    hm.put(x.web, 0);
                }
                hm.put(x.web, hm.get(x.web) + 100 * x.freq);
            }
        }
        ArrayList<Pair> mysite = new ArrayList<Pair>();
        for (Entry<Integer, Integer> x : hm.entrySet())
        {
            mysite.add(new Pair(x.getKey(), x.getValue()));
        }
        Collections.sort(mysite, new comparator());
        long l2 = System.currentTimeMillis();
        System.out.println("final result");
        System.out.println(mysite);
        TobeUsedInHtml = TobeUsedInHtml + "<br> Tatal Time Taken: " + (l2 - l1) + " ms";
        String template1 = "<html><head><title>AnterosEngine</title></head><body><h3>" + TobeUsedInHtml + "</h3><ul>";
        String template2 = "</ul></body></html>";
        File file = new File("TextContent\\Website.html");
        try
        {
            BufferedWriter bwHtml = new BufferedWriter(new FileWriter("TextContent\\Website.html"));
            System.out.println("start");
            bwHtml.write(template1);
            for (Pair x : mysite)
            {
                String temp = websiteList.get(x.web);
                bwHtml.write("<li><a href=\"" + temp + "\">" + temp + "</a></li>");
            }
            bwHtml.write(template2);
            bwHtml.close();
            System.out.println("end");
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(SearchThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(SearchThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {
            if (Desktop.isDesktopSupported())
            {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException ex)
        {
            Logger.getLogger(SearchThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<String> processParagraph(String paragraph)
    {
        al.clear();
        paragraph = paragraph.toLowerCase();

        paragraph = paragraph.replaceAll("[^a-zA-Z]", " ");
        paragraph = paragraph.replaceAll("\\s{2,}", " ").trim(); ////delete all extra space
        System.out.println(paragraph);
        StringTokenizer token = new StringTokenizer(paragraph, " ");

        while (token.hasMoreElements())
        {
            String temp = token.nextToken();
            temp = temp.replaceAll("[^a-zA-Z]", "");
            if (temp.length() > 1 && !hs.contains(temp) && temp.length() < 50)
            {
                al.add(temp);
            }
        }
        return al;
    }

}
