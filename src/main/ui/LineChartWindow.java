package ui;

import model.Month;
import model.RunningLog;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

// class that displays a line chart for total monthly running distances across all 12 months
public class LineChartWindow extends JFrame {
    private RunningLog log;

    // EFFECTS: constructs the frame for the line chart and adds the chart to the main panel
    public LineChartWindow(String title, RunningLog log) {
        super(title);
        this.log = log;
        JPanel content = createChart();
        getContentPane().add(content);
        pack();
        setVisible(true);

    }

    // EFFECTS: constructs the line chart with 12 months on the x-axis, and total running distance within each month
    //          on the y-axis
    public JPanel createChart() {

        // create the plot
        String[] month = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        SymbolAxis horizontalAxis = new SymbolAxis("Month", month);
        NumberAxis verticalAxis = new NumberAxis("Total Distance (km)");
        //verticalAxis.setLowerBound(0);
        verticalAxis.setRange(0, 100);

        XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesFilled(0, true);
        XYPlot plot = new XYPlot(createDataset(), horizontalAxis, verticalAxis, renderer);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(4, 4, 4, 4));

        // create and return chart panel
        JFreeChart lineChart = new JFreeChart("Total Monthly Distance ",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(600, 400));

        return chartPanel;
    }

    // EFFECTS: constructs a dataset for all the monthly total distances from the running log
    public XYDataset createDataset() {
        int janDistance = log.totalMonthlyDistance(Month.JAN);
        int febDistance = log.totalMonthlyDistance(Month.FEB);
        int marDistance = log.totalMonthlyDistance(Month.MAR);
        int aprDistance = log.totalMonthlyDistance(Month.APR);
        int mayDistance = log.totalMonthlyDistance(Month.MAY);
        int junDistance = log.totalMonthlyDistance(Month.JUN);
        int julDistance = log.totalMonthlyDistance(Month.JUL);
        int augDistance = log.totalMonthlyDistance(Month.AUG);
        int sepDistance = log.totalMonthlyDistance(Month.SEP);
        int octDistance = log.totalMonthlyDistance(Month.OCT);
        int novDistance = log.totalMonthlyDistance(Month.NOV);
        int decDistance = log.totalMonthlyDistance(Month.DEC);

        DefaultXYDataset dataset = new DefaultXYDataset();

        // data
        double[][] data = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {janDistance, febDistance, marDistance, aprDistance, mayDistance, junDistance, julDistance,
                augDistance, sepDistance, octDistance, novDistance, decDistance}};
        dataset.addSeries("", data);

        return dataset;

    }

}
