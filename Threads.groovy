Object monitor = new Object();

class Runner  implements Runnable {
    private Object monitor;
    
    public Runner(Object monitor){
        this.monitor=monitor
    }
    
    public void run() {
        println "run"
        synchronized (monitor) {
            monitor.wait();
        }
        println "ran"
    }
}

Runner run = new Runner(monitor);

List<Thread> threads = new ArrayList<Thread>();

for (int i = 0 ; i < 10 ; i++) {
    threads.add(new Thread(run));
}

for (int i = 0 ; i < 10 ; i++) {
    threads.get(i).start();
    //threads.get(i).join();
}

Thread.sleep(3000);

println "notified"
synchronized(monitor) {monitor.notifyAll();}

//for (int i = 0 ; i < 10 ; i++) {
//    println "notified"
//    synchronized(monitor) {monitor.notify();}
//    threads.get(i).join();
//}

//println join

//synchronized(monitor) {monitor.notify();}
//synchronized(monitor) {monitor.notify();}
