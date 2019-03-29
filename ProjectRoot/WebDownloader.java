

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class WebDownloader
{
    String downloadWebsite(String siteName)
    {
        
        String line;
        StringBuffer strbuffer = new StringBuffer("");
        strbuffer.append(siteName);
        strbuffer.append("$$ ");
        int t = 0;
        try
        {
            URL url = new URL(siteName);
            InputStream is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null)
            {
                strbuffer.append(line);
            }
            t = 1;
        } catch ( IOException e)
        {
            e.printStackTrace();
            //System.out.println("WEBSITE READ ERROR");
        } catch( Exception e )
        {
            e.printStackTrace();
        }
        if( t == 0 ) 
            return null;
        String str = strbuffer.toString();
        //System.out.println(strbuffer);
        //System.out.println("downloaded:  " + siteName);
        return str;
    }
    
    /*public static void main(String[] args)
    {
        String startsite;
        if( args.length > 0 )
        {
            startsite = args[0];
        }
        else
        {
            Scanner cin = new Scanner(System.in);
            startsite = cin.nextLine();
        }
        WebDownloader ms = new WebDownloader();
        //System.out.println(ms.downloadWebsite(startsite));
        UrlExtract us = new UrlExtract();
        try
        {
            us.extractUrl( ms.downloadWebsite(startsite), startsite);
        } catch (MalformedURLException ex)
        {
            Logger.getLogger(WebDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
}
