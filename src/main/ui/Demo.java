package ui;
import java.awt.EventQueue;
import javax.swing.JFrame;
import com.toedter.calendar.JDateChooser;

public class Demo {


        private JFrame frame;
        private App app;

        /**
         * Launch the application.
         */
        public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        App app = new App();
                        //app.frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

}
