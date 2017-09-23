package sample;

/**
 * Created by jens on 23.09.2017.
 */
public class Maxdate
{
    public static Comparable max(Comparable c1,Comparable c2,Comparable c3)
    {
        if(c1.compareTo(c2) >0&&c1.compareTo(c3) >0)
            return c1;
        if(c2.compareTo(c1) >0&&c2.compareTo(c3) >0)
            return c2;
        else
            return c3;
    }
}