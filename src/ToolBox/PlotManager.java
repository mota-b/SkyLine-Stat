package ToolBox;

import Schema.Data;
import Schema.Tools.DataManager;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;


public class PlotManager {

    /**
     * Attribute
     */
    private XYChart chart;


    /**
     * Constructor
     */
    public PlotManager(){

        // Create Chart (Plot Window)
        this.chart = new XYChartBuilder().width(600).height(600).xAxisTitle("X").yAxisTitle("Y").build();
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setMarkerSize(6);
    }

    /**
     * Methodes
     */

    // Add list of coordinate ex: (Array of X, Array of Y)
    public void addSeries(String serie_name, Marker marker_type, XYSeries.XYSeriesRenderStyle render_style,  ArrayList<Double> x, ArrayList<Double> y){
        XYSeries series = this.chart.addSeries(serie_name, x, y);
        series.setMarker(marker_type);
        series.setXYSeriesRenderStyle(render_style);
    }
    public void addSeries(String serie_name, Marker marker_type, XYSeries.XYSeriesRenderStyle render_style,  double[] x, double[] y){
        XYSeries series = this.chart.addSeries(serie_name, x, y);
        series.setMarker(marker_type);
        series.setXYSeriesRenderStyle(render_style);
    }

    // Update existant serie of coordinates
    public void updateSeries(String serie_name, ArrayList<Double> x, ArrayList<Double> y){
        this.chart.updateXYSeries(serie_name, x, y, null);
    }

    // Display plot frame
    public void  display_plot(String title){
        SwingWrapper s = new SwingWrapper<>(chart);
        s.displayChart(title);
    }


    // Create list of plotFrameNames
    public static ArrayList<String> create_plots(int nb_c){
        ArrayList<String> plots = new ArrayList<>();

        for (int i = 0; i < nb_c; i++) {
            for (int j = 0; j < nb_c; j++) {
                if (j!=i &&
                        plots.indexOf(i+"/"+j) ==-1 &&
                        plots.indexOf(j+"/"+i) ==-1) {
                    plots.add(i+"/"+j);

                }
            }
        }

        return plots;
    }

    // Cerate liste of PlotFrames
    public static ArrayList<PlotManager> create_pms(ArrayList<String> plots, ArrayList<Data> data ){
        ArrayList<PlotManager> pms = new ArrayList<>();

        for (int i = 0; i < plots.size(); i++) {
            pms.add (new PlotManager());
            pms.get(pms.size()-1).addSeries("S"+plots.get(i), SeriesMarkers.CROSS, XYSeries.XYSeriesRenderStyle.Scatter,  DataManager.get_c_s(data,Integer.valueOf(plots.get(i).split("/")[0])), DataManager.get_c_s(data, Integer.valueOf(plots.get(i).split("/")[1])));
        }

        return pms;
    }


}
