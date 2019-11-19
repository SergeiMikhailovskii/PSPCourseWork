package contoller;

import view.YearPieChartWindow;

public class ChartsController {
    public void showCharts(){
        new YearPieChartWindow("Year chart").setVisible(true);
    }
}
