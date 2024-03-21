package ui;
import java.awt.EventQueue;

// Main class to launch the application
public class MainGUI {

// launch the application
        public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        MainAppWindow app = new MainAppWindow();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

}
