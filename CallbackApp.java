import java.util.List;
import java.util.ArrayList;

interface IListen {
    public void triger(); 
}
class Listener implements IListen{ // will call back after long running proc

    public void triger() {
        System.out.printf("trigger....this is a call back%n");
    }
    
}
public class CallbackApp extends Thread {
    
    private List<IListen> lns = new ArrayList<IListen>();

    public CallbackApp() {
    }

    public CallbackApp(IListen ln) {

        addListener(ln);

    }

    public void addListener(IListen ln) {
        lns.add(ln);
    }

    public void trigger() {
        for (IListen ln : lns) {
            ln.triger();
        }
    }

    private void longRunProc() {

        System.out.println("long running proc...");

    }

    public void run() {

        longRunProc();        
        trigger();   // here is the call back

    }

    public static void main(String[] args) {


        new CallbackApp(new Listener()).start();

        System.out.println("done");


    }
}
