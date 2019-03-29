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
class ExtractWord implements Runnable
{

    MyCrawlerData dataController;
    SingleIndexHolder singleindex;
    DoubleIndexHolder doubleindex;
    ExtractKeyWord keywordExtract;
    Thread t;

    ExtractWord(MyCrawlerData dataController, ExtractKeyWord keywordExtract, SingleIndexHolder singleindex, DoubleIndexHolder doubleindex)
    {
        this.dataController = dataController;
        this.singleindex = singleindex;
        this.keywordExtract = keywordExtract;
        this.doubleindex = doubleindex;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run()
    {
        System.out.println("Word Extract Starting");
        //int k = 0;
        while (dataController.getWebsiteProcessed() < CoolCrawler.MAXPROCESSEDLIST && dataController.runFlag())
        {
            //System.out.println("extract " + k++);
            //System.out.println( "html data size: " + dataController.htmlData.size());
            String html = null;
            try
            {
                html = dataController.htmlData.take();
            } catch (InterruptedException ex)
            {
                Logger.getLogger(ExtractWord.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                //Logger.getLogger(ExtractWord.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if( dataController.getWebsiteProcessed() >= CoolCrawler.MAXPROCESSEDLIST )
                System.out.println("Max limit here :(");
        }
        System.out.println("coming out of extract word thread");
    }

}
