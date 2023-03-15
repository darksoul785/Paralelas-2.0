import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class ProducerConsumerUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
    private JTextArea textArea;
    private JTextArea textArea_Consumer;
    private final List<Producer> producers = new ArrayList<Producer>();
    private final List<Consumer> consumers = new ArrayList<Consumer>();
    
    /**
     * 
     */
    public ProducerConsumerUI() {
        setTitle("Producer-Consumer Example");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel buttonPanel = new JPanel();
        JButton addProducerButton = new JButton("Add Producer");
        addProducerButton.addActionListener(e -> {
            Producer producer = new Producer();
            producers.add(producer);
            new Thread(producer).start();
        });
        buttonPanel.add(addProducerButton);
        
        JButton addConsumerButton = new JButton("Add Consumer");
        addConsumerButton.addActionListener(e -> {
            Consumer consumer = new Consumer();
            consumers.add(consumer);
            new Thread(consumer).start();
        });
        buttonPanel.add(addConsumerButton);
        
        JButton startAllProducersButton = new JButton("Start All Producers");
        startAllProducersButton.addActionListener(e -> {
            for (Producer producer : producers) {
                new Thread(producer).start();
            }
        });
        buttonPanel.add(startAllProducersButton);
        
        JButton startAllConsumersButton = new JButton("Start All Consumers");
        startAllConsumersButton.addActionListener(e -> {
            for (Consumer consumer : consumers) {
                new Thread(consumer).start();
            }
        });
        buttonPanel.add(startAllConsumersButton);
        
        add(buttonPanel, BorderLayout.NORTH);
        
        textArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(190, 300));
        add(scrollPane, BorderLayout.WEST);

        textArea_Consumer = new JTextArea();
        DefaultCaret caret2 = (DefaultCaret)textArea_Consumer.getCaret();
        caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        textArea_Consumer.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(textArea_Consumer);
        scrollPane2.setPreferredSize(new Dimension(190,300));
        add(scrollPane2, BorderLayout.EAST);
    }
    
    private class Producer implements Runnable {
        public void run() {
            try {
                Random random = new Random();
                while (true) {
                    int num = random.nextInt(100);
                    queue.put(num);
                    textArea.append("Produced: " + num + "\n");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private class Consumer implements Runnable {
        public void run() {
            try {
                while (true) {
                    int num = queue.take();
                    textArea_Consumer.append("Consumed: " + num + "\n");
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public static void main (String[] args){
        ProducerConsumerUI ui = new ProducerConsumerUI();
        ui.setVisible(true);
    }
}
