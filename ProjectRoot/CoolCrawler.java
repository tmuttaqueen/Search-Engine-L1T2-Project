/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Input implements Runnable
{
    MyCrawlerData dataController;
    Thread t;
    public Input( MyCrawlerData cd )
    {
        dataController = cd;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run()
    {
        Scanner in = new Scanner(System.in);
        String s = "a";
        while (!s.equalsIgnoreCase("stop"))
        {
            s = in.nextLine();
            System.out.println(s);
        }
        dataController.FalseRunFlag();
    }
    
}

public class CoolCrawler
{

    MyCrawlerData dataController;
    UrlExtract urlExtract;
    ExtractKeyWord keywordExtract;
    WebDownloader webDownloader;
    SingleIndexHolder singleindex;
    DoubleIndexHolder doubleindex;
    public static final int MAXWEBSITELIST = 100000;
    public static final int MAXPROCESSEDLIST = 10000;

    CoolCrawler()
    {
        System.out.println("Crawling Starting");
        try
        {
            dataController = new MyCrawlerData();
        } catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }

        urlExtract = new UrlExtract();
        keywordExtract = new ExtractKeyWord();
        webDownloader = new WebDownloader();
        singleindex = new SingleIndexHolder();
        doubleindex = new DoubleIndexHolder();
        StartCrawling();
    }

    public void StartCrawling()
    {
        GetIndexFromFile();
        System.out.println("initialize crawler with basic sites");

        if (dataController.totalSite() < MAXWEBSITELIST)
        {
            String root = "https://en.wikipedia.org/wiki/Bangladesh";
            dataController.insertUrl(root);
        }

        System.out.println("initialize crawler threads");

        //CrawlerThread[] dw = new CrawlerThread[100];
        DownloadWebsite[] dw = new DownloadWebsite[100];
        ExtractWord[] ew = new ExtractWord[100];
        int nt = 1;
        for (int i = 0; i < nt; i++)
        {
            //dw[i] = new CrawlerThread(dataController, urlExtract, keywordExtract, webDownloader, singleindex, doubleindex);
            dw[i] = new DownloadWebsite(dataController, urlExtract, keywordExtract, webDownloader);
            ew[i] = new ExtractWord(dataController, keywordExtract, singleindex, doubleindex);
        }
        new Input(dataController);
        try
        {
            for (int i = 0; i < nt; i++)
            {
                dw[i].t.join();
                System.out.println("download Thread " + i + " died");
            }
            for (int i = 0; i < nt; i++)
            {
                ew[i].t.join();
                System.out.println("extract Thread " + i + " died");
            }
        } catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }

        System.out.println("Threads stopped");
        saveAndExit();
        dataController.saveAndExit();
        System.out.println("Crawler closed");
    }

    public void GetIndexFromFile()
    {
        long l1 = System.currentTimeMillis();
        System.out.println("input taking existing single index");

        try
        {
            FileInputStream fin = new FileInputStream("BinaryContent\\SingleIndex.bin");
            if (fin.available() > 0)
            {

                ObjectInputStream ois = new ObjectInputStream(fin);
                singleindex = (SingleIndexHolder) ois.readObject();
                //System.out.println(test.peek("world"));
                ois.close();
            }

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("single taking finished...input taking existing double index...time needed->" + (System.currentTimeMillis() - l1));
        l1 = System.currentTimeMillis();
        try
        {
            ObjectInputStream ois;
            FileInputStream fin = new FileInputStream("BinaryContent\\DoubleIndex.bin");
            if (fin.available() > 0)
            {
                ois = new ObjectInputStream(fin);
                doubleindex = (DoubleIndexHolder) ois.readObject();
                //System.out.println(test.peek("world"));
                ois.close();
            }
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("finished taking all index...time needed ->" + (System.currentTimeMillis() - l1));
    }

    public void saveAndExit()
    {
        long l1 = System.currentTimeMillis();
        System.out.println("file writing existing single index");
        try
        {
            ObjectOutputStream oos;
            try (FileOutputStream fout = new FileOutputStream("BinaryContent\\SingleIndex.bin"))
            {
                oos = new ObjectOutputStream(fout);
                oos.writeObject(singleindex);
                //System.out.println(test.peek("world"));
            }
            oos.close();

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("singleindex writing finished...writing existing double index...time needed->" + (System.currentTimeMillis() - l1));
        l1 = System.currentTimeMillis();
        try
        {
            ObjectOutputStream oos;
            try (FileOutputStream fout = new FileOutputStream("BinaryContent\\DoubleIndex.bin"))
            {
                oos = new ObjectOutputStream(fout);
                oos.writeObject(doubleindex);
                //System.out.println(test.peek("world"));
            }
            oos.close();

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(CoolCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("finished writing all index..time needed->" + (System.currentTimeMillis() - l1));
    }

    public static void main(String[] args)
    {
        new CoolCrawler();
    }

}
