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
public class CallbackApp {
    
    private List<IListen> lns = new ArrayList<IListen>();

    public void addListener(IListen ln) {
        lns.add(ln);
    }

    public void trigger() {
        for (IListen ln : lns) {
            ln.triger();
        }
    }

    public void longRunProc(CallbackApp app) {

        new Thread(new Runnable() {
            public void run() {
                System.out.println("long running proc...");
                app.trigger(); // here is the call back
            }
        }).start();

    }

    public static void main(String[] args) {


        CallbackApp app = new CallbackApp();

        app.addListener(new Listener());

        app.longRunProc(app);

        System.out.println("done");


    }
}
