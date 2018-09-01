public class ThreadByThread extends Thread{
    private String name;

    public ThreadByThread(String name) {
        super(name);
    }

    public void run(){
        for (int i=1;i<=5;i++){
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " i = " + i);
        }
    }
}