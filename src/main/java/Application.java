public class Application {
    public static void main(String[] args) {
        //Thread t1 = new Thread(new ThreadByRunnable());
        Thread t2 = new ThreadByThread("t2");
        Thread t3 = new ThreadByThread("t3");
        Thread t4 = new ThreadByThread("t4");
        t2.start();
        t3.start();
        try {
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //t1.start();
        t4.start();
    }
}
