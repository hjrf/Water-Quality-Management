package com.android.chart;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SimpleValueFormatter;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class SetLineChart {

	public static void showTuBiao(LineChartView chartView, List<Float> one, List<Float> two,List<Float>three,List<Float>four,String[] days) {
		if (one == null || one.size() == 0) {
			// showTuBiao(chartView);
			return;
		}
		LineChartData data;
		int numberOfLines = 4;
		int maxNumberOfLines = 5;
		int numberOfPoints = one.size();
		float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

		boolean hasLines = true;
		boolean hasPoints = true;//让折线处的点消失
		ValueShape shape = ValueShape.CIRCLE;//折线处点的形状
		boolean isFilled = false;
		boolean hasLabels = false;
		boolean isCubic = false;
		boolean hasLabelForSelected = false;

		float maxTop = 0;// 后来设置为这些点当中最小的点 这个是设置滑动的时候得当前的视图要用的，不设置滑动的时候就不要这个了
		for (int i = 0; i < maxNumberOfLines; ++i) {
			if (i == 0) {
				for (int j = 0; j < numberOfPoints; ++j) {
					randomNumbersTab[i][j] = one.get(j);
					if (maxTop < randomNumbersTab[i][j]) {
						maxTop = randomNumbersTab[i][j];
					}
				}
			} else if(i==1){
				for (int j = 0; j < numberOfPoints; ++j) {
					if (j < two.size()) {
						randomNumbersTab[i][j] = two.get(j);
						if (maxTop < randomNumbersTab[i][j]) {
							maxTop = randomNumbersTab[i][j];
						}
					}
				}
			}else if(i==2){
				for (int j = 0; j < numberOfPoints; ++j) {
					if (j < three.size()) {
						randomNumbersTab[i][j] = three.get(j);
						if (maxTop < randomNumbersTab[i][j]) {
							maxTop = randomNumbersTab[i][j];
						}
					}
				}
			}
			else if(i==3){
				for (int j = 0; j < numberOfPoints; ++j) {
					if (j < four.size()) {
						randomNumbersTab[i][j] = four.get(j);
						if (maxTop < randomNumbersTab[i][j]) {
							maxTop = randomNumbersTab[i][j];
						}
					}
				}
			}
		}
		List<AxisValue> axisValues = new ArrayList<AxisValue>(); ;
		List<Line> lines = new ArrayList<Line>();
		for (int i = 0; i < numberOfLines; ++i) {
			 
			List<PointValue> values = new ArrayList<PointValue>();
			for (int j = 0; j < numberOfPoints; ++j) {
				// 传入横坐标的值数据加入到折线表中
				values.add(new PointValue(j, randomNumbersTab[i][j]));
				axisValues.add(new AxisValue(j, days[j].toCharArray()));
			}
			Line line = new Line(values);
			//设置线条的颜色
			if (i == 0) {
				line.setColor(Color.parseColor("#9FA0A0"));// 灰色线条
			} else if (i == 1) {
				line.setColor(Color.parseColor("#FFAD0E"));// 橘色线条
			} else if(i == 2) {
				line.setColor(Color.parseColor("#EA5514"));//红色线条
			}else if(i==3)
			{
				line.setColor(Color.parseColor("#0647CD"));//蓝色线条
			}
			line.setShape(shape);
			line.setCubic(isCubic);
			line.setFilled(isFilled);
			line.setHasLabels(hasLabels);
			line.setHasLabelsOnlyForSelected(hasLabelForSelected);
			line.setHasLines(hasLines);
			line.setHasPoints(hasPoints);
			lines.add(line);
		}

		data = new LineChartData(lines);
		Axis axisY = new Axis().setHasLines(true);// 添加y轴的线
		//自定义的坐标显示的格式，这里就是横坐标的数值之后加上".0%"字样
		axisY.setFormatter(new SimpleValueFormatter(0, true, new char[] { 1, 2, 3, 4 }, new char[] { '.','0','%' }));
		data.setAxisYLeft(axisY);
       //data.setBaseValue(Float.NEGATIVE_INFINITY);
		resetViewportY(chartView);

		Axis axisX = new Axis(axisValues); // X轴
		data.setAxisXBottom(axisX);
		chartView.setLineChartData(data);
		// chartView.setViewportCalculationEnabled(false);
		//设置了当前的视图的 范围（y轴是整个范围，x轴为1/3的视图，所以此时能够在x轴方向上滑动）
		chartView.getChartComputator().setCurrentViewport(0, maxTop, numberOfPoints/2, -6);
       //chartView.setZoomEnabled(true);

	}

	/* y轴的起始点和终点 */
	private static void resetViewportY(LineChartView chart) {
		// Reset viewport height range to (0,100)
		final Viewport v = new Viewport(chart.getMaximumViewport());
		v.bottom = -6;
		v.top = 12;
		chart.setMaximumViewport(v);
		chart.setCurrentViewport(v, false);
	}
}
