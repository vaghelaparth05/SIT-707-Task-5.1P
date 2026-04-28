package sit707_week5;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeatherControllerTest {

	private static WeatherController wController;
	private static double[] hourlyTemperatures;
	private static double expectedMinTemperature;
	private static double expectedMaxTemperature;
	private static double expectedAverageTemperature;

	@BeforeClass
	public static void setUpOnce() {
		System.out.println("=== @BeforeClass: Initialising shared WeatherController test data ===");

		// Arrange
		wController = WeatherController.getInstance();
		int nHours = wController.getTotalHours();
		hourlyTemperatures = new double[nHours];

		expectedMinTemperature = 1000;
		expectedMaxTemperature = -1;
		double sumTemperature = 0;

		// Read all temperatures only once and reuse for all tests.
		for (int i = 0; i < nHours; i++) {
			hourlyTemperatures[i] = wController.getTemperatureForHour(i + 1);
			System.out.println("Captured temperature for hour " + (i + 1) + ": " + hourlyTemperatures[i]);

			if (expectedMinTemperature > hourlyTemperatures[i]) {
				expectedMinTemperature = hourlyTemperatures[i];
			}
			if (expectedMaxTemperature < hourlyTemperatures[i]) {
				expectedMaxTemperature = hourlyTemperatures[i];
			}
			sumTemperature += hourlyTemperatures[i];
		}

		expectedAverageTemperature = sumTemperature / nHours;

		System.out.println("Expected minimum temperature: " + expectedMinTemperature);
		System.out.println("Expected maximum temperature: " + expectedMaxTemperature);
		System.out.println("Expected average temperature: " + expectedAverageTemperature);
		System.out.println("=== @BeforeClass complete ===");
	}

	@AfterClass
	public static void tearDownOnce() {
		System.out.println("=== @AfterClass: Closing shared WeatherController ===");
		if (wController != null) {
			wController.resetClock();
			wController.close();
		}
		System.out.println("=== @AfterClass complete ===");
	}

	@Test
	public void testStudentIdentity() {
		String studentId = "225139485";
		Assert.assertNotNull("Student ID is null", studentId);
		Assert.assertFalse("Student ID is empty", studentId.trim().isEmpty());
	}

	@Test
	public void testStudentName() {
		String studentName = "Parth Vaghela";
		Assert.assertNotNull("Student name is null", studentName);
		Assert.assertFalse("Student name is empty", studentName.trim().isEmpty());
	}

	@Test
	public void testTemperatureMin() {
		System.out.println("+++ testTemperatureMin +++");

		// Arrange
		double expected = expectedMinTemperature;

		// Act
		double actual = wController.getTemperatureMinFromCache();
		System.out.println("Expected min: " + expected + ", Actual min: " + actual);

		// Assert
		Assert.assertEquals(expected, actual, 0.001);
	}

	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");

		// Arrange
		double expected = expectedMaxTemperature;

		// Act
		double actual = wController.getTemperatureMaxFromCache();
		System.out.println("Expected max: " + expected + ", Actual max: " + actual);

		// Assert
		Assert.assertEquals(expected, actual, 0.001);
	}

	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");

		// Arrange
		double expected = expectedAverageTemperature;

		// Act
		double actual = wController.getTemperatureAverageFromCache();
		System.out.println("Expected avg: " + expected + ", Actual avg: " + actual);

		// Assert
		Assert.assertEquals(expected, actual, 0.001);
	}
	
	@Test
	public void testTemperaturePersist() {
		System.out.println("+++ testTemperaturePersist +++");

		// Arrange
		LocalDateTime fixedDateTime = LocalDateTime.of(2026, 4, 28, 10, 15, 30);
		Clock fixedClock = Clock.fixed(
				fixedDateTime.atZone(ZoneId.systemDefault()).toInstant(),
				ZoneId.systemDefault()
		);

		wController.setClock(fixedClock);

		String expectedPersistTime = "10:15:30";

		// Act
		String actualPersistTime = wController.persistTemperature(10, 19.5);
		System.out.println("Expected persist time: " + expectedPersistTime);
		System.out.println("Actual persist time: " + actualPersistTime);

		// Assert
		Assert.assertEquals(expectedPersistTime, actualPersistTime);

		// Cleanup
		wController.resetClock();
	}
}