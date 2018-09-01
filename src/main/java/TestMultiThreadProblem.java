public class TestMultiThreadProblem {
    static Double a = 10d;
    static Double b;

    public static void main(String[] args) {
        Runnable r1 = () -> {
            synchronized (a) {
                if (a == 10) {
                    try {
                        Thread.sleep(0);
                        b = a / 2.0;
                        System.out.println(Thread.currentThread().getName() + ": " + b);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Runnable r2 = () -> {
            synchronized (a){
                a = 12d;
            }
        };

        Thread ta = new Thread(r1, "Thread A");
        Thread tb = new Thread(r2, "Thread B");

        ta.start();
        tb.start();
    }
}
