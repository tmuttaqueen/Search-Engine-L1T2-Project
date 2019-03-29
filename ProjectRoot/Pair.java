/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

public class Pair implements Serializable
{

    public int web;
    public int freq;
    public static final long serialVersionUID = 432L;
    
    public Pair(int a, int b)
    {
        web = a;
        freq = b;
    }
    public void increase(int n)
    {
        freq+=n;
    }
    public void increase()
    {
        freq++;
    }
    public String toString()
    {
        return "{" + web + ":" + freq + "}";
    }
}



    //public ArrayList<String> getWord(String str) throws Exception
//    public static void main(String[] args)
//    {
//        NewClass nc = new NewClass();
//        nc.push("hello", 3, 3);
//        nc.push("hello", 4, 4);
//        nc.push("world", 1, 2);
//        nc.push("world", 2, 4);
//        try
//        {
//            FileOutputStream fout = new FileOutputStream("myindex.txt");
//
//            ObjectOutputStream oos = new ObjectOutputStream(fout);
//            oos.writeObject(nc);
//            //return words;
//            oos.close();
//            fout.close();
//        } catch (FileNotFoundException ex)
//        {
//            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex)
//        {
//            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        FileInputStream fin;
//        try
//        {
//            fin = new FileInputStream("myindex.txt");
//            ObjectInputStream ois = new ObjectInputStream(fin);
//            NewClass test = (NewClass)ois.readObject();
//            System.out.println(test.peek("world"));
//            fin.close();
//            ois.close();
//            
//        } catch (FileNotFoundException ex)
//        {
//            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex)
//        {
//            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex)
//        {
//            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//            
//    }
//}


