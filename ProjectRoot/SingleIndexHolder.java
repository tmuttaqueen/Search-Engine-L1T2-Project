/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tanveer Muttaqueen
 */
public class SingleIndexHolder implements Serializable
{

    HashMap<String, ArrayList<Pair>> hmap;

    /**
     *
     */
    public static final long serialVersionUID = 42L;
    synchronized void push(String s, int web, int freq)
    {
        if (!hmap.containsKey(s))
        {
            hmap.put(s, new ArrayList<Pair>());
        }
        hmap.get(s).add(new Pair(web, freq));
    }

    synchronized void push(HashMap<String, Pair> map)
    {
        for (Map.Entry<String, Pair> x : map.entrySet())
        {
            push( x.getKey(), x.getValue().web, x.getValue().freq);
        }
    }

    public synchronized ArrayList<Pair> peek(String s)
    {
        if (!hmap.containsKey(s))
        {
            return null;
        }
        return hmap.get(s);
    }

    public SingleIndexHolder()
    {
        hmap = new HashMap<String, ArrayList<Pair>>();
    }
}