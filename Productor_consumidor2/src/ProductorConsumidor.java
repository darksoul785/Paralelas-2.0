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

public class ProductorConsumidor extends JFrame {

    private static final long serialVersionUID = 1L;
    private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    private JTextArea productorTextArea;
    private JTextArea consumidorTextArea;
    private final List<Productor> productores = new ArrayList<>();
    private final List<Consumidor> consumidores = new ArrayList<>();
    
    public ProductorConsumidor() {
        setTitle("Productor Consumidor");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel buttonPanel = new JPanel();
        JButton addProducerButton = new JButton("Agregar Productor");
        addProducerButton.addActionListener(e -> {
            Productor productor = new Productor();
            productores.add(productor);
            new Thread(productor).start();
        });
        buttonPanel.add(addProducerButton);
        
        JButton addConsumerButton = new JButton("Agregar Consumidor");
        addConsumerButton.addActionListener(e -> {
            Consumidor consumidor = new Consumidor();
            consumidores.add(consumidor);
            new Thread(consumidor).start();
        });
        buttonPanel.add(addConsumerButton);
        
        add(buttonPanel, BorderLayout.NORTH);
        
        productorTextArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret)productorTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        productorTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(productorTextArea);
        scrollPane.setPreferredSize(new Dimension(190, 300));
        add(scrollPane, BorderLayout.WEST);

        consumidorTextArea = new JTextArea();
        DefaultCaret caret2 = (DefaultCaret)consumidorTextArea.getCaret();
        caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        consumidorTextArea.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(consumidorTextArea);
        scrollPane2.setPreferredSize(new Dimension(190,300));
        add(scrollPane2, BorderLayout.EAST);
    }
    
    private class Productor implements Runnable {
        public void run() {
            try {
                Random random = new Random();
                while (queue.size() < 10) {
                    int num = random.nextInt(100);
                    queue.put(num);
                    productorTextArea.append("Producido: " + num + "\n");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private class Consumidor implements Runnable {
        public void run() {
            try {
                while (true) {
                    int num = queue.take();
                    consumidorTextArea.append("Consumido: " + num + "\n");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public static void main (String[] args){
        ProductorConsumidor ui = new ProductorConsumidor();
        ui.setVisible(true);
    }
}
