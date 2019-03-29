/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tanveer Muttaqueen
 */
public class UrlExtract
{

    void extractUrl(String html, String webSite, MyCrawlerData dataController)
    {
        StringBuffer temp = new StringBuffer("");
        String pattern1 = "a href=";
        int cur = 0;
        URL baseUrl = null;
        //System.out.println("extracting url of: " +  webSite);
        try
        {
            baseUrl = new URL(webSite);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if (baseUrl == null)
        {
            System.out.println("Null baseurl");
            return;
        }

        while (true)
        {
            temp.delete(0, temp.length());
            int ind = html.indexOf(pattern1, cur);
            if (ind == -1)
            {
                break;
            }
            ind += 8;
            while (html.charAt(ind) != '"')
            {
                temp.append(html.charAt(ind));
                ind++;
                if (ind >= html.length())
                {
                    break;
                }
            }
            cur = ind;
            if (cur >= html.length())
            {
                break;
            }
            String url = temp.toString();
            if (url.contains("javascript") || url.length() < 2)
            {
                continue;
            }
            
            if (url.contains("http"))
            {
                int c = -1;
                for (int i = url.length() - 1; i >= 0; i--)
                {
                    if (url.charAt(i) == '#')
                    {
                        c = i;
                        break;
                    }
                }
                if (c != -1)
                {
                    url = url.substring(0, c);
                }
                if (url.length() < 2)
                {
                    continue;
                }
                if ((url.charAt(url.length() - 1) == '\\') || (url.charAt(url.length() - 1) == '/'))
                {
                    url = url.substring(0, url.length() - 1);
                }
                if (url.length() < 2)
                {
                    continue;
                }
                dataController.insertUrl(url);
            } else
            {
                try
                {
                    URL myUrl = new URL(baseUrl, url);
                    url = myUrl.toString();
                    //System.out.println(url);
                    int c = -1;
                    for (int i = url.length() - 1; i >= 0; i--)
                    {
                        if (url.charAt(i) == '#')
                        {
                            c = i;
                            break;
                        }
                    }
                    if (c != -1)
                    {
                        url = url.substring(0, c);
                    }
                    if (url.length() < 2)
                    {
                        continue;
                    }
                    if ((url.charAt(url.length() - 1) == '\\') || (url.charAt(url.length() - 1) == '/'))
                    {
                        url = url.substring(0, url.length() - 1);
                    }
                    if (url.length() < 2)
                    {
                        continue;
                    }
                    //System.out.println( "inserting: " + url);
                    dataController.insertUrl(url);

                } catch (Exception ex)
                {
                    //System.out.println(ex);
                    ex.printStackTrace();
                    //Logger.getLogger(UrlExtract.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
//        System.out.print("outside  ");
//        System.out.println(dataController.total + " " + dataController.current);
    }

}
