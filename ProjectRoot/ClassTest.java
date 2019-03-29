/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

class data implements Serializable
{
    int a;
    int b;
    data()
    {
        a = 0;
        b = 0;
    }
}

public class ClassTest
{
    public static void main( String[] args ) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fin = new FileInputStream("BinaryContent\\SingleIndex.bin");
        
            ObjectInputStream ois = new ObjectInputStream(fin);
            System.out.println("working");
            data singleindex = (data) ois.readObject();
            //System.out.println(test.peek("world"));
            ois.close();
    }
    
}
