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
class CrawlerThread implements Runnable
{

    MyCrawlerData dataController;
    UrlExtract urlExtract;
    WebDownloader webDownloader;
    ExtractKeyWord keywordExtract;
    SingleIndexHolder singleindex;
    DoubleIndexHolder doubleindex;
    Thread t;

    CrawlerThread(MyCrawlerData dataController, UrlExtract urlExtract, ExtractKeyWord keywordExtract, WebDownloader webDownloader, SingleIndexHolder singleindex, DoubleIndexHolder doubleindex)
    {
        this.dataController = dataController;
        this.urlExtract = urlExtract;
        this.keywordExtract = keywordExtract;
        this.webDownloader = webDownloader;
         this.doubleindex = doubleindex;
         this.singleindex = singleindex;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run()
    {
        System.out.println("Website Download + Url Extract Starting + indexing");
        while (dataController.getWebsiteProcessed() < CoolCrawler.MAXPROCESSEDLIST && dataController.runFlag())
        {
            String s = dataController.getUrl();
            if (s == null || s.contains(".pdf") || s.contains(".jpg") || s.contains(".jpeg") || s.contains(".mp3") || s.contains(".png") )
            {
                continue;
            }
            String html = null;
            try
            {
                 html = webDownloader.downloadWebsite(s);
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            
            if( html == null )
                continue;
            if( dataController.totalSite() < CoolCrawler.MAXWEBSITELIST )
                urlExtract.extractUrl(html, s, dataController);
            try
            {
                if (html != null)
                {
                    String web = html.substring(0, html.indexOf("$$"));
                    keywordExtract.getWord(html, dataController.appendWebsiteProcessed(web), singleindex, doubleindex);
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            if( dataController.getWebsiteProcessed() >= CoolCrawler.MAXPROCESSEDLIST )
                System.out.println("Max limit here :(");
        }
        System.out.println("coming out of Crawler thread");
    }
    

}