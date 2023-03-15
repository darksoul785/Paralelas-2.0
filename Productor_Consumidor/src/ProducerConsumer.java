import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ProducerConsumer extends JFrame{
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
    private JTextArea textArea;

    public ProducerConsumer(){
        setTitle("Productor Consumidor");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel();
        JButton producerButton = new JButton("Productor");
        producerButton.addActionListener(e -> new Thread(new Producer()).start());
        buttonPanel.add(producerButton);

        JButton consumerButton = new JButton("Consumidor");
        consumerButton.addActionListener(e -> new Thread(new Consumer()).start());

        buttonPanel.add(consumerButton);

        add(buttonPanel, BorderLayout.NORTH);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500,300));
        add(scrollPane, BorderLayout.CENTER);
    }

    private class Producer implements Runnable{
        public void run(){
            try{
                final Random random = new Random();
                while(true){
                    int num = random.nextInt(100);
                    queue.put(num);
                    textArea.append("Producido: " + num + "\n");
                    Thread.sleep(2000);
                }
            }catch (final InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private class Consumer implements Runnable{
        public void run(){
            try {
                while (true){
                    int num = queue.take();
                    textArea.append("Consumido: " + num + "\n");
                }
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        ProducerConsumer ui = new ProducerConsumer();
        ui.setVisible(true);
    }
}