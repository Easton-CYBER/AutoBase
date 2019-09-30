import javax.swing.*;

public class AutoBase{
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TableSQL();
            }
        });

    }
}