import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Screen extends JPanel {
    private ArrayList<Firefly> list;
    FireflyDatabasePanel fireflyDatabasePanel;
    JButton save;
    JButton load;
    Thread t;

    Screen(){
        fireflyDatabasePanel=new FireflyDatabasePanel();
        fireflyDatabasePanel.setPreferredSize(new Dimension(500,500));
        save=new JButton("save");
        load=new JButton("Load");
        MybuttonListener listener=new MybuttonListener();
        save.addActionListener(listener);
        load.addActionListener(listener);

        JPanel p=new JPanel();
        p.add(save);
        p.add(load);
        add(p,BorderLayout.NORTH);
        add(fireflyDatabasePanel,BorderLayout.CENTER);

    }



    public void start(){
        class Animation implements Runnable{
            public void run(){
                try {
                    fireflyDatabasePanel.advance();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Runnable r=new Animation();
        t=new Thread(r);
        t.start();
    }

    class MybuttonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==save){
                try {
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DataBase"));
                    out.writeObject(fireflyDatabasePanel.getList());
                } catch (IOException f) {
                    f.printStackTrace();
                }
                fireflyDatabasePanel.getList();
            }
            else if (e.getSource()==load){
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream("DataBase"));
                    t.interrupt();
                    fireflyDatabasePanel.setList((ArrayList<Firefly>) in.readObject());
                    start();
                } catch (IOException | ClassNotFoundException f) {
                    f.printStackTrace();
                }

            }

        }
    }

}
