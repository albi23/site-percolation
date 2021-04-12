package pwr.edu.percolation;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class MonteCarloSimulation {

    private final static String outFilePattern = "Ave_L%sT%s.txt";
    private final static String distFilePattern = "Dist_p%sL%sT%s.txt";
    private static final double MAX_DOUBLE_ERROR = 9.0E-14;

    public static void main(String... args) {
        visualizationOfSimpleConfiguration();
        modelSimulationForFlowAndClusters();
        clusterDistributionSimulation();
    }


    private static void writeStringToFile(final String outPath, final String content) {
        try (final FileWriter fileWriter = new FileWriter(outPath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Visualize sample configurations for L = 10 and 3 values of p = 0.4, 0.6, 0.8 within two methods:
     * (1) use the burning algorithm and describe each site by the number,
     * (2) use the HK algorithm and color each cluster with the different color.
     */
    private static void visualizationOfSimpleConfiguration() {
        final PercolationParams params = new PercolationParams(10, 1, 0.4, 0.8, 0.2);
        final PercolationModel pModel = new PercolationModel(params);


        for (double prob = params.p0(); prob <= params.pk(); prob += params.dp()) {
            final int[][] model = pModel.generateLatticeModel(prob);

            System.out.println("----- Probability = " + (int) (prob * 100) + "% --- \n----- Burning method ---- ");
            int[][] modelCopy = PercolationModel.arrayCopy(model);
            saveContent(modelCopy, "gen_matrix_l10_p" + (int) (prob * 100) + ".txt");
            pModel.shortestPathInModel(modelCopy);
            saveContent(modelCopy, "burn_l10_p" + (int) (prob * 100) + ".txt");
            pModel.printMatrix(modelCopy);

            System.out.println("----- HK algorithm ---- ");
            modelCopy = PercolationModel.arrayCopy(model);
            pModel.clusterCountFinding(modelCopy);
            pModel.printMatrix(modelCopy);
            saveContent(modelCopy, "hk_l10_p" + (int) (prob * 100) + ".txt");
        }
    }


    private static void saveContent(int[][] model, String outFilePattern) {
        final StringBuilder sb = new StringBuilder();
        for (int[] ints : model)
            sb.append(String.join(" ", IntStream.of(ints).mapToObj(s -> "" + s).toArray(String[]::new))).append('\n');
        writeStringToFile(outFilePattern, sb.toString());
    }

    /**
     * Probability P f low that the path connecting the first and the last row exists as a function of p
     * for L = 10, 50, 100 (use legend).
     * The average size of the maximum cluster hs max i as a function of p for L = 10, 50, 100 (use legend).
     */

    private static void modelSimulationForFlowAndClusters() {
        final PercolationParams params = ParamHandler.readInputParams();
        System.out.println("Start at: " + new Timestamp(System.currentTimeMillis()) + "\n" + params);
        final PercolationModel pModel = new PercolationModel(params);
        StringBuilder sb = new StringBuilder(params.T() * 10);
        for (double probability = params.p0(), end = params.pk() + MAX_DOUBLE_ERROR; probability <= end; probability += params.dp()) {
            System.out.print("\rProgress [" + (probability * 100) + "]%");
            int flow = 0;
            int clusterSum = 0;

            for (int j = 0; j < params.T(); j++) {
                final int shortest = pModel.shortestPathInModel(pModel.generateLatticeModel(probability));
                if (shortest > 0) flow++;
                clusterSum += pModel.maxClusterSize(pModel.generateLatticeModel(probability));
            }
            final double d = ((double) flow) / (double) params.T();
            sb.append(probability).append("  ").append(d).append("  ").append(clusterSum / params.T()).append('\n');
        }
        writeStringToFile(String.format(outFilePattern, params.L(), params.T()), sb.toString());
        System.out.println("\nEnd at: " + new Timestamp(System.currentTimeMillis()));
    }


    private static void clusterDistributionSimulation() {
        double[] probabilitiesToCheck = {0.2, 0.3, 0.4, 0.5, 0.592746, 0.6, 0.7, 0.8};
        int[] linearSizes = {10, 50, 100};
        for (double prob : probabilitiesToCheck) {
            for (int linearSize: linearSizes)
                simulateClusterDistribution(linearSize, prob);
        }
    }

    private static void simulateClusterDistribution(final int linearSize, final double probability) {
        final PercolationParams params = new PercolationParams(linearSize, 10_000, 1, 1, 1);
        final PercolationModel pModel = new PercolationModel(params);
        final Map<Integer, Long> distributionOfClusters = new HashMap<>();

        for (int i = 0; i < params.T(); i++) {
            final int[][] model = pModel.generateLatticeModel(probability);
            final Map<Integer, Long> partialDistribution = pModel.distributionOfClusters(model);
            partialDistribution.forEach((newKey, v) -> distributionOfClusters.compute(newKey, (k2, oldValue) -> {
                if (oldValue == null) return v;
                oldValue += v;
                return oldValue;
            }));
        }
        final Long sumOfOccurrences = distributionOfClusters.values().stream().reduce(Long::sum).orElse(0L);
        final StringBuilder sb = new StringBuilder();
        distributionOfClusters.forEach((k, v) -> sb.append(k).append("  ").append(v / (double) sumOfOccurrences).append("\n"));
        writeStringToFile(String.format(distFilePattern, probability, linearSize, params.T()), sb.toString());
    }
}
