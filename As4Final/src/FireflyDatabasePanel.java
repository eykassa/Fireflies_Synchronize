import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FireflyDatabasePanel extends JPanel {
    private ArrayList<Firefly> list;
    private ArrayList<Point>flashingBeforeCycle;
    private Lock lock;
    private int next;



    FireflyDatabasePanel(){
        lock=new ReentrantLock();
        list=new ArrayList<>();
        for(int i=0; i<100; i++) list.add(new Firefly());


    }
    public void advance() throws InterruptedException {
        while(!Thread.interrupted()) {
            lock.lock();
            flashingBeforeCycle=new ArrayList<>();
            for(Firefly f:list){
                if(f.checkFlasing()){
                    flashingBeforeCycle.add(f.getLocation());
                }
            }
            for (Firefly f : list) {
                f.advance(flashingBeforeCycle);
            }
            lock.unlock();
            pause(1);
        }


    }
    public void paintComponent(Graphics g){
        lock.lock();
        for(Firefly f: list){
            f.draw(g);
        }
        lock.unlock();

    }
    public ArrayList<Firefly>getList(){
        return list;
    }
    public void setList(ArrayList<Firefly>l){
        list=l;
    }


    public void pause(int step) throws InterruptedException {
        repaint();
        Thread.sleep(step * 100);

    }


}
