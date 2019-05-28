package pnrs.rtrk.myapplication;

public class MyNDK {

    static{
        System.loadLibrary("MyJNI");
    }

    public native int convert(double x, int type);
}
