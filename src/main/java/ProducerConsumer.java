public class ProducerConsumer {
    private static Object lock = new Object();

    static class SharedResource{
        private static int counter = 10;

        public static boolean isCounterReadyToProduce(){
            return counter < 10;
        }

        public static boolean isCounterReadyToConsume(){
            return counter > 5;
        }

        public static void increaseCounter(){
            counter++;
        }

        public static void decreaseCounter(){
            counter--;
        }

        public static int getCounterValue(){
            return counter;
        }
    }

    static class Producer{
        public void produce() {
            synchronized (lock){
                log("Actual counter value: " + SharedResource.getCounterValue());
                if (!SharedResource.isCounterReadyToProduce()){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log("Start producing");
                SharedResource.increaseCounter();
                lock.notifyAll();
            }
        }
    }

    static class Consumer{
        public void consume() {
            synchronized (lock){
                log("Actual counter value: " + SharedResource.getCounterValue());
                if (!SharedResource.isCounterReadyToConsume()){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log("Start consuming");
                SharedResource.decreaseCounter();
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Producer p = new Producer();
        Consumer c = new Consumer();

        Runnable producerTask = () -> {
            for (int i=0; i<100; i++){
                p.produce();
            }
        };

        Runnable consumerTask = () -> {
            for (int i=0; i<100; i++){
                c.consume();
            }
        };

        Thread producerThread = new Thread(producerTask);
        Thread consumerThread = new Thread(consumerTask);

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

        log("Process finished. Actual counter value: " + SharedResource.getCounterValue());
    }

    static void log(String message){
        System.out.println(Thread.currentThread().getName() + " " + message);
    }
}
