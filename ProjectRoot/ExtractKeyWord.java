/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class ExtractKeyWord
{

    HashSet<String> hs;
    

    public ExtractKeyWord()
    {
        hs = new HashSet<String>();
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
    }

    public void extractText(String str, ArrayList<String> list)
    {
        //final ArrayList<String> list = new ArrayList<String>();
        StringReader reader = new StringReader(str);
        ParserDelegator parserDelegator = new ParserDelegator();
        HTMLEditorKit.ParserCallback parserCallback = new HTMLEditorKit.ParserCallback()
        {
            @Override
            public void handleText(final char[] data, final int pos)
            {
                list.add(new String(data));
            }
        };
        try
        {
            parserDelegator.parse(reader, parserCallback, true);
        } catch (IOException ex)
        {
            Logger.getLogger(ExtractKeyWord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String processParagraph(String paragraph)
    {
        paragraph = paragraph.replaceAll("-", " ");
        paragraph = paragraph.replaceAll("\\.", " "); ////delete .
        paragraph = paragraph.replaceAll("\\:", " "); //delete :
        paragraph = paragraph.replaceAll("\\,", " "); //delete ,
        paragraph = paragraph.replaceAll("<[^>]*>", ""); //delete all in <>
        //paragraph = paragraph.replaceAll("^[a-z.\\s_-]+$", "#"); //delete all except lowercase and space
        paragraph = paragraph.replaceAll("\\s{2,}", " ").trim(); ////delete all extra space
        return paragraph;
    }

    public void process(ArrayList<String> al, int websiteNumber, SingleIndexHolder singleindex, DoubleIndexHolder doubleindex, String web)
    {
        web = web.replaceAll("[^a-zA-Z]", " ");
        web = web.replaceAll("\\s{2,}", " ").trim(); ////delete all extra space
        StringTokenizer token = new StringTokenizer(web, " ");
        StringBuffer sb = new StringBuffer("");
        while (token.hasMoreElements())
        {
            String temp = token.nextToken();
            temp = temp.replaceAll("[^a-zA-Z]", "");
            if (temp.length() > 1 && !hs.contains(temp) && temp.length() < 50)
            {
                sb.append(temp);
            }
        }
        web = sb.toString();

        HashMap<String, Pair> si = new HashMap<String, Pair>();
        for (String s : al)
        {
            if (!si.containsKey(s))
            {
                si.put( s, new Pair(websiteNumber, 0) );
            }
            si.get(s).increase(1);
            if( web.contains(s) )
            {
                si.get(s).increase(30);
            }
        }
        singleindex.push(si);
        
        ArrayList<String> dl = new ArrayList<String>();
        for( int i = 1; i < al.size(); i++ )
        {
            dl.add(al.get(i-1) + al.get(i));
        }
        HashMap<String, Pair> di = new HashMap<String, Pair>();
        for (String s : dl)
        {
            if (!di.containsKey(s))
            {
                di.put( s, new Pair(websiteNumber, 0) );
            }
            di.get(s).increase(1);
            if( web.contains(s) )
            {
                di.get(s).increase(10000);
            }
        }
        doubleindex.push(di);
        System.out.println("Extracted keyword of: "+ web + "->" + websiteNumber );
        //System.out.println(web);
    }

    public void getWord(String str, int websiteNumber, SingleIndexHolder singleindex, DoubleIndexHolder doubleindex)
    {
        String web = null;
        web = str.substring(0, str.indexOf("$$"));
        web = web.toLowerCase();
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<String> words = new ArrayList<String>();
        extractText(str, lines);
        for (int i = 0; i < lines.size(); i++)
        {
            String ln = lines.get(i);
            ln = ln.toLowerCase();
            ln = processParagraph(ln);
            //System.out.println(ln);
            if (ln.length() < 2)
            {
                lines.remove(i);
                i--;
                continue;
            }
            StringTokenizer token = new StringTokenizer(ln, " ");
            while (token.hasMoreElements())
            {
                String temp = token.nextToken();
                temp = temp.replaceAll("[^a-zA-Z]", "");
                if (temp.length() > 1 && !hs.contains(temp) && temp.length() < 50)
                {
                    //System.out.println(temp);
                    words.add(temp);
                }
            }
        }
        process(words, websiteNumber, singleindex, doubleindex, web);
        
    }
}
