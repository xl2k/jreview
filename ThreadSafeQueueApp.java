interface IQue {
    public void enQue(Object o) throws Exception; 
    public Object deQue() throws Exception;
    public void logHeadTail(String tname);
}
class Producer implements Runnable {
    private IQue _queue = null;

    public Producer(IQue queue) {
        _queue = queue;
    }
    public void run() {
        for (;;) {
            try {
                _queue.enQue(new String("X"));
                _queue.logHeadTail("producer");
                Thread.sleep(1000);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
    }

}
class Consumer implements Runnable {
    private IQue _queue = null;

    public Consumer(IQue queue) {
        _queue = queue;
    }
    public void run() {
        for (;;) {
            try {
                Thread.yield();
                _queue.deQue();
                _queue.logHeadTail("consumer");
                Thread.sleep(1000);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
    }
}
class ThreadSafeQueue implements IQue {
    /* using array for queue */

    private int maxSize = 10;
    private int head = 0;
    private int tail = 0;

    private Object[] queue = new Object[maxSize];

    public synchronized void enQue(Object o) throws Exception {

        while (tail == head && queue[tail] != null) { // queue is full

            wait();

        } 
        queue[tail] = o;
        tail++;
        if (tail == maxSize) {
            tail = 0;
        }
        notifyAll();

    }
    public synchronized Object deQue() throws Exception {

        Object o = null;

        while (head == tail && queue[head] == null) { 

            wait();

        }
        o = queue[head];
        queue[head] = null;
        head++;
        if (head == maxSize) {
            head = 0;
        }    
        notifyAll();
        return o; 
    }
    public synchronized void logHeadTail(String threadName) {

        System.out.printf("[%s] head=%d, tail=%d%n",threadName, head,tail);
        display();
    }
    
    void display() {
        for (Object o: queue) {
            System.out.print(o + " ");
        }
        System.out.println();
    }
}
public class ThreadSafeQueueApp {

    static ThreadSafeQueue q = new ThreadSafeQueue();
    static Thread t_producer = new Thread(new Producer(q)); 
    static Thread t_consumer = new Thread(new Consumer(q));

    public static void main(String[] args) throws Exception {

        
        t_producer.start();
        t_consumer.start();
        Thread.yield();
        System.out.println(Thread.activeCount());

    }
}
