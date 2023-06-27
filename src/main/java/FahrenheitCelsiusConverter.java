
public class FahrenheitCelsiusConverter {

    public static int toCelcius(int fahrenheit) {
        return (int)Math.round(((double) fahrenheit - 32.0) * 5.0 / 9.0);
    }

    public static int toFahrenheit(int celcius) {
        return (int) Math.round((double) celcius * 9.0 / 5.0 + 32.0);
    }
}
