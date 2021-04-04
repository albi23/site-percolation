package pwr.edu.percolation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;

final class ParamHandler {

    private static final String PARAM_FILE_NAME = "perc_init.txt";
    private static final byte REQUIRED_PARAMETERS_COUNT = 5;

    public static PercolationParams readInputParams() {
        try (final InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream(PARAM_FILE_NAME);
             final BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));) {
            final String[] strings = reader.lines().
                    filter(line -> !line.startsWith("//"))
                    .map(line -> line.split("//", 2)[0].trim())
                    .toArray(String[]::new);
            return validateParams(strings);
        } catch (IOException e) {
            System.err.println("Unexpected error with reading file \"" + PARAM_FILE_NAME + "\"");
            System.exit(1);
        } catch (NullPointerException npEx) {
            System.err.println("Missing file " + PARAM_FILE_NAME + " in resources folder");
            System.exit(1);
        }
        return null;
    }


    private static PercolationParams validateParams(final String[] params) {
        if (params.length != REQUIRED_PARAMETERS_COUNT)
            showErrorAndExit("Incorrect arguments count");

        Map<Integer, Function<Double, Boolean>> extraValidators = Map.of(
                0, s -> s > 0,
                1, s -> s > 0,
                2, s -> s <= 1.0 && s > 0.0,
                3, s -> s <= 1.0 && s > 0.0,
                4, s -> s <= 1.0 && s > 0.0);

        double[] parsedArgs = new double[5];
        for (int i = 0; i < REQUIRED_PARAMETERS_COUNT; i++) {
            try {
                final double value = Double.parseDouble(params[i]);
                if (!extraValidators.get(i).apply(value))
                    showErrorAndExit("Value out of range for " + (i+1) + " argument");
                parsedArgs[i] = value;
            } catch (NumberFormatException ex) {
                showErrorAndExit("Incorrect value for " + (i+1) + " argument");
            }
        }
        return new PercolationParams((int) parsedArgs[0], (int) parsedArgs[1], parsedArgs[2], parsedArgs[3], parsedArgs[4]);
    }


    private static void showErrorAndExit(final String message) {
        System.err.println(message);
        System.exit(1);
    }


}
