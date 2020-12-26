import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Firefly implements Serializable {
    private int WIDTH=10;
    private int HEIGHT=10;
    private int x;
    private int y;
    public boolean flash;
    public double currentPhase;
    private final double FLASHING_FREQUENCY=0.785;
    private final double K=0.1;
    Lock lock;


    Firefly(){
        Random r=new Random();
        x=r.nextInt(400);
        y=r.nextInt(400);
        currentPhase=Math.random()*(2*Math.PI);
        lock=new ReentrantLock();

    }

    public int getN(ArrayList<Point> list){
        int N=0;
        if(list==null){
            return 0;
        }
        lock.lock();
        for(Point p: list){
            double distance=Math.pow((p.getX()-x),2) + Math.pow((p.getY()-y),2);
            distance=Math.sqrt(distance);
            if(distance<=100&&p!=getLocation()){
                N++;
            }
        }

        lock.unlock();
        return N;
    }

    /**
     * so the only trick is in this method i pass it all the firflys that are flashing at that time stap
     *  and it will use getN method to see if there are its neigbors or not then if they are increame t n
     * @param list
     * @throws InterruptedException
     */
    public void advance(ArrayList<Point>list) throws InterruptedException {
        lock.lock();
        try {
            currentPhase += (FLASHING_FREQUENCY+ (K * getN(list) * Math.sin((2 * Math.PI) - currentPhase)));
            if(currentPhase<(2*Math.PI)){
                flash=false;
            }
            else{
                flash=true;
                currentPhase=currentPhase%(2*Math.PI);
            }

        }finally {
            lock.unlock();
        }

    }

    public void draw(Graphics g){
        lock.lock();;
        g.setColor(Color.RED);
        g.drawRect(x,y,WIDTH,HEIGHT);
        if(flash){
            g.setColor(Color.YELLOW);
        }
        else{
            g.setColor(Color.BLACK);
        }
        g.fillRect(x,y,WIDTH,HEIGHT);
        lock.unlock();
    }

    public Point getLocation(){
        return new Point(x,y);
    }

    public boolean checkFlasing(){
        return flash;
    }




}




