package sit707_week5;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class WeatherController {
	
	public static final int HOURS_PER_DAY = 3;

	private static WeatherController instance;
	
	/**
	 * Factory method for single instance WeatherController.
	 * @return
	 */
	public static WeatherController getInstance() {
		if (instance == null) {
			instance = new WeatherController();
		}
		return instance;
	}
	
	// Initialise 10 hourly temperature
	private static double[] todaysHourlyTemperature = new double[HOURS_PER_DAY];
	
	/**
	 * Private constructor prevents to create new instance manually.
	 * A factory method needs to be used.
	 */
	private WeatherController() {
		System.out.println("Creating new weather controller.");
		
		// sleep a while to simulate a delay
		sleep(2 + new Random().nextInt(5));
		
		// Insert 10 random temperature values in today's hourly list.
		Random random = new Random();
		for (int i=0; i<HOURS_PER_DAY; i++) {
			todaysHourlyTemperature[i] = 1 + random.nextInt(30);
		}
		System.out.println(Arrays.toString(todaysHourlyTemperature));
	}
	
	public void close() {
		System.out.println("Closing weather controller.");
		instance = null;
		
		// sleep a while to simulate a delay
		sleep(2 + new Random().nextInt(5));
	}
	
	/**
	 * Calculate minimum of today's hourly temperatures.
	 * @return
	 */
	public double getTemperatureMinFromCache() {
		double minVal = 1000;
		for (int i=0; i<todaysHourlyTemperature.length; i++) {
			if (minVal > todaysHourlyTemperature[i]) {
				minVal = todaysHourlyTemperature[i];
			}
		}
		return minVal;
	}

	/**
	 * Calculate maximum of today's hourly temperatures.
	 * @return
	 */
	public double getTemperatureMaxFromCache() {
		double maxVal = -1;
		for (int i=0; i<todaysHourlyTemperature.length; i++) {
			if (maxVal < todaysHourlyTemperature[i]) {
				maxVal = todaysHourlyTemperature[i];
			}
		}
		return maxVal;
	}
	
	/**
	 * Calculate average of today's hourly temperatures.
	 * @return
	 */
	public double getTemperatureAverageFromCache() {
		double sumVal = 0;
		for (int i=0; i<todaysHourlyTemperature.length; i++) {
			sumVal += todaysHourlyTemperature[i];
		}
		return sumVal/todaysHourlyTemperature.length;
	}
	
	/**
	 * Return temperature for given hour of current day.
	 * @param hour
	 * @return
	 */
	public double getTemperatureForHour(int hour) {
		// sleep a while to simulate a delay
		sleep(1 + new Random().nextInt(5));
		
		// Let's return a randomly selected temperature from hourly list if hour does not exist.
		if (hour > todaysHourlyTemperature.length) {
			hour = 1 + new Random().nextInt(todaysHourlyTemperature.length);
		}
		
		// Hour index starts from 0 instead of 1 due to array indexing.
		return todaysHourlyTemperature[hour-1];
	}
	
	/**
	 * Persist reported temperature to data store and return recorded time. 
	 * @param hour
	 * @param temperature
	 * @return
	 */
	public String persistTemperature(int hour, double temperature) {
		SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
		String savedTime = sdf.format(new Date());
		System.out.println("Temperature: " + temperature + " of hour: " + hour + ", saved at " + savedTime);
		
		// sleep a while to simulate a delay
		sleep(1 + new Random().nextInt(2));
		
		return savedTime;
	}
	
	/**
	 * Calculated the number of hours temperature data is available for today.
	 * @return
	 */
	public int getTotalHours() {
		return todaysHourlyTemperature.length;
	}
	
	/**
	 * Sleep for specified seconds.
	 * @param sec
	 */
	public static void sleep(int sec) {
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
