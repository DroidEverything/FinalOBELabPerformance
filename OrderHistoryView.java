import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OrderHistoryView {
    private JFrame jf;
    private JPanel jp;
    private JButton backBtn, dltBtn;
    private JTextArea area;

  
    public OrderHistoryView(final JFrame mainFrame) {
        jf = new JFrame("Order History");
        jf.setSize(800, 500);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jp = new JPanel();
        jp.setLayout(null);

        
        area = new JTextArea();
        area.setBounds(50, 50, 680, 300);
        area.setEditable(false);
        jp.add(area);

        
        backBtn = new JButton("Back to main");
        backBtn.setBounds(240, 370, 150, 30);
        jp.add(backBtn);

        
        dltBtn = new JButton("Delete History");
        dltBtn.setBounds(410, 370, 150, 30);
        jp.add(dltBtn);

        
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
                mainFrame.setVisible(true);
            }
        });

        
        dltBtn.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        jf,
                        "Delete order history ?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        OrderRepository.deleteOrderHistory();
                        area.setText("Order history deleted");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(jf,
                                "Failed to delete order history.",
                                "I/O Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        
        String historyText = OrderRepository.loadOrderHistory();
        area.setText(historyText);

        jf.add(jp);
        jf.setVisible(true);
    }
}
