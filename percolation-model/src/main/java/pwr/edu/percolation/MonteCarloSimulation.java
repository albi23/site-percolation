package pwr.edu.percolation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

public class MonteCarloSimulation {

    private final static String outFilePattern = "Ave_L%sT%s.txt";

    public static void main(String... args) {

        final PercolationParams params = ParamHandler.readInputParams();
        System.out.println(params);
        final PercolationModel pm = new PercolationModel(params);

        StringBuilder sb = new StringBuilder(params.T() * 10);
        for (int i = 1; i <= params.T(); i++) {
            final double probability = params.p0() + (i) * params.dp();
            int flows = 0;
            final int[] maxClusters = new int[params.T()];
            for (int j = 0; j < params.T(); j++) {
                final int[][] model = pm.generateLatticeModel(probability);
                if (pm.shortestPathInModel(PercolationModel.arrayCopy(model)) > 0) {
                    flows++;
                };
                maxClusters[j] = pm.maxClusterSize(PercolationModel.arrayCopy(model));
            }
            final double avg = IntStream.of(maxClusters).summaryStatistics().getAverage();
            final double d = flows / (double) params.T();
            sb.append(probability).append("  ").append(d).append("  ").append(avg).append("\n");

        }
        try (final FileWriter fileWriter = new FileWriter(String.format(outFilePattern, params.L(), params.T()))) {
            fileWriter.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
