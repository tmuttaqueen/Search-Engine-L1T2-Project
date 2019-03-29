/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tanveer Muttaqueen
 */
class DownloadWebsite implements Runnable
{

    MyCrawlerData dataController;
    UrlExtract urlExtract;
    WebDownloader webDownloader;
    ExtractKeyWord keywordExtract;
    Thread t;

    DownloadWebsite(MyCrawlerData dataController, UrlExtract urlExtract, ExtractKeyWord keywordExtract, WebDownloader webDownloader)
    {
        this.dataController = dataController;
        this.urlExtract = urlExtract;
        this.keywordExtract = keywordExtract;
        this.webDownloader = webDownloader;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run()
    {
        System.out.println("Website Download + Url Extract Starting");
        //int k = 0;
        //try{ while()
        while (dataController.getWebsiteProcessed() < CoolCrawler.MAXPROCESSEDLIST && dataController.runFlag())
        {
            //System.out.println("download " + k++);
            String s = dataController.getUrl();
            if (s == null || s.contains(".pdf") || s.contains(".jpg") || s.contains(".jpeg") || s.contains(".mp3") || s.contains(".png") )
            {
                //System.out.println(s);
                continue;
            }
            //System.out.println( "download website thread " +  s);
            String html = webDownloader.downloadWebsite(s);
            if( html == null )
                continue;
            try
            {
                dataController.htmlData.put(html); //System.out.println(ex);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(DownloadWebsite.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println( "html data size after putting: " + dataController.htmlData.size());
            if( dataController.totalSite() < CoolCrawler.MAXWEBSITELIST )
                urlExtract.extractUrl(html, s, dataController);
            //System.out.println( dataController.allWebsiteList.size() + " current " + dataController.current + " url get:" + s);
        }
        System.out.println("coming out of download website thread");
    }
    

}