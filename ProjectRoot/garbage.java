/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//void extractUrl(String html, String webSite) throws MalformedURLException
//    {
//        String pattern1 = "a href=";
//        int start = 0, index = html.indexOf(pattern1, start);
//        URL baseUrl = new URL(webSite);
//        String hostName = baseUrl.getHost();
//        while (index != -1)
//        {
//
//            int left = html.indexOf("\"", index);
//            int right = html.indexOf("\"", left + 1);
//            if (left != -1 && right != -1)
//            {
//                String s = html.substring(left + 1, right);
//                URL myUrl = new URL(baseUrl, s);
//                String derivedHost = myUrl.getHost();
//                if (derivedHost.contains(hostName))
//                {
//                    try
//                    {
//                        System.out.println(myUrl);
//                        InputStream is = myUrl.openStream();
//                        //BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                        System.out.println("YES"); //here push url in database
//
//                    } catch (Exception e)
//                    {
//                        System.out.println("WEBSITE READ ERROR");
//                    }
//                }
//            }
//            start = right;
//            if (start != -1)
//            {
//                index = html.indexOf(pattern1, start);
//            } else
//            {
//                index = -1;
//            }
//        }
//    }


//void extractUrl(String html, String webSite, MyUrlHash dataController) throws MalformedURLException, IOException
//    {
//        //System.out.println(html);
//        String pattern1 = "a href=";
//        int start = 0, index = html.indexOf(pattern1, start);
//        URL baseUrl = new URL(webSite);
//        //String hostName = baseUrl.getHost();
//        while (index != -1)
//        {
//
//            int left = html.indexOf("\"", index);
//            int right = html.indexOf("\"", left + 1);
//            if (left != -1 && right != -1)
//            {
//                String s = html.substring(left + 1, right);
//                int i;
//                for (i = s.length() - 1; i >= 0; i--)
//                {
//                    if ((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') || (s.charAt(i) >= '0' && s.charAt(i) <= '9'))
//                    {
//                        break;
//                    }
//                }
//                i++;
//                s = s.substring(0, i);
//                URL myUrl = new URL(baseUrl, s);
//              
//                String host = myUrl.toString();
//                try
//                {
//                    InetAddress inetAddress = InetAddress.getByName(host);
//                    //show the Internet Address as name/address
//                    inetAddress.getHostName();
//                    inetAddress.getHostAddress();
//                    dataController.insertUrl(myUrl.toString());
//                    //System.out.println(inetAddress.getHostName() + " " + inetAddress.getHostAddress());
//                    
//                }catch (UnknownHostException exception)
//                {
//                    System.err.println("ERROR: Cannot access '" + host + "'");
//                }
//                //System.out.println(myUrl);
//                //System.out.println("YES"); //here push url in database
//                //dataController.insertUrl(myUrl.toString());
//                
//                //System.out.println(myUrl);
//                //System.out.println("YES"); //here push url in database
//                dataController.insertUrl(myUrl.toString());
//            }
//            start = right;
//            if (start != -1)
//            {
//                index = html.indexOf(pattern1, start);
//            } else
//            {
//                index = -1;
//            }
//        }
//    }


/**
 *
 * @author Tanveer Muttaqueen
 */
public class garbage
{
    
}
