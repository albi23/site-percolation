package pwr.edu.percolation;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class PercolationModel {

    private static final Random rand = new Random();
    private final PercolationParams params;
    private final HosenKopelmanAlgorithm hka = new HosenKopelmanAlgorithm();

    record Pair(int x, int y) {
    }

    public PercolationModel(PercolationParams percolationParams) {
        this.params = percolationParams;
    }

    public int[][] generateLatticeModel(final double probability) {
        int[][] model = this.getEmptyModel();
        for (int i = 0, size = params.L(); i < size; i++)
            for (int j = 0; j < size; j++)
                if (rand.nextDouble() <= probability)
                    model[i][j] = 1;
        return model;
    }

    private int[][] getEmptyModel() {
        int[][] model = new int[params.L()][params.L()];
        for (int i = 0, size = params.L(); i < size; i++) model[i] = new int[size];
        return model;
    }

    public static int[][] arrayCopy(int[][] source) {
        final int[][] destination = new int[source.length][source.length];
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source[i].length);
        }
        return destination;
    }

    /**
     * Burning method for finding shortest path in model
     *
     * @param model - input model
     * @return length of shortest path or -1 if not exists
     */
    public int shortestPathInModel(final int[][] model) {
        Queue<Pair> queue = new ArrayDeque<>();

        for (int i = 0, len = model.length; i < len; i++) {
            if (model[0][i] == 1) {
                model[0][i] = 2;
                queue.add(new Pair(0, i));
            }
        }

        final int lastRowIndex = model.length - 1;
        while (!queue.isEmpty()) {
            final Pair cell = queue.poll();
            // North neighbour
            if (cell.x - 1 > -1 && model[cell.x - 1][cell.y] == 1) {
                model[cell.x - 1][cell.y] += model[cell.x][cell.y];
                queue.add(new Pair(cell.x - 1, cell.y));
            }
            // South neighbour
            if (cell.x + 1 < model.length && model[cell.x + 1][cell.y] == 1) {
                if (cell.x + 1 != lastRowIndex) queue.add(new Pair(cell.x + 1, cell.y));
                model[cell.x + 1][cell.y] += model[cell.x][cell.y];
            }
            // West neighbour
            if (cell.y + 1 < model.length && model[cell.x][cell.y + 1] == 1) {
                queue.add(new Pair(cell.x, cell.y + 1));
                model[cell.x][cell.y + 1] += model[cell.x][cell.y];
            }
            // East neighbour
            if (cell.y - 1 > -1 && model[cell.x][cell.y - 1] == 1) {
                queue.add(new Pair(cell.x, cell.y - 1));
                model[cell.x][cell.y - 1] += model[cell.x][cell.y];
            }
        }

        int min = Integer.MAX_VALUE;
        final int[] lastRow = model[lastRowIndex];
        for (int b : lastRow) if (b > 1 && b < min) min = b;
        return min == Integer.MAX_VALUE ? 0 : min - 1;
    }

    public int clusterCountFinding(final int[][] model) {
        return hka.maxClusterFinding(model);
    }

    public Map<Integer, Long> distributionOfClusters(final int[][] model) {
        return hka.distributionOfClusters(model);
    }

    public int maxClusterSize(final int[][] model) {
        return distributionOfClusters(model).keySet().stream().max(Integer::compareTo).orElse(0);
    }

    private static class HosenKopelmanAlgorithm {

        /**
         * Hoshen???Kopelman algorithm for cluster finding
         *
         * @param model percolation model
         *              <p>
         *              return max cluster size
         */
        int maxClusterFinding(final int[][] model) {

            final int[] label = new int[model.length * model.length / 2];

            for (int i = 0, len = model.length; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if (model[i][j] > 0) { // check if it is occupied
                        int left = (i - 1 < 0) ? 0 : model[i - 1][j];
                        int up = (j - 1 < 0) ? 0 : model[i][j - 1];
                        int x = left != 0 ? 1 : 0;
                        int y = up != 0 ? 1 : 0;
                        switch (x + y) {
                            case 0 -> model[i][j] = updateCluster(label);      // a new cluster
                            case 1 -> model[i][j] = Math.max(up, left);        // part of an existing cluster whichever is nonzero is labelled
                            case 2 -> model[i][j] = unionFind(up, left, label); // this site binds two clusters
                        }
                    }
                }
            }

           /* create a mapping from the canonical labels
              determined by union/find into a new set of canonical labels, which are
              guaranteed to be sequential. */
            final int[] new_labels = new int[model.length * model.length / 2];
            for (int i = 0; i < model.length; i++)
                for (int j = 0; j < model.length; j++)
                    if (model[i][j] != 0) {
                        int x = find(model[i][j], label);
                        if (new_labels[x] == 0) {
                            new_labels[0]++;
                            new_labels[x] = new_labels[0];
                        }
                        model[i][j] = new_labels[x];
                    }
            return new_labels[0];
        }

        Map<Integer, Long> distributionOfClusters(final int[][] model) {
            this.maxClusterFinding(model);

            final HashMap<Integer, Integer> labelOnOccurrences = new HashMap<>();
            for (int[] rows : model) {
                for (int colValue : rows) {
                    if (colValue != 0) {
                        if (!labelOnOccurrences.containsKey(colValue)) {
                            labelOnOccurrences.put(colValue, 1);
                        } else {
                            int val = labelOnOccurrences.get(colValue);
                            labelOnOccurrences.put(colValue, ++val);
                        }
                    }
                }
            }
            return labelOnOccurrences.values().stream()
                    .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
        }

        private int updateCluster(final int[] labels) {
            labels[0]++;
            assert (labels[0] < labels.length);
            labels[labels[0]] = labels[0];
            return labels[0];
        }

        private int unionFind(int x, int y, final int[] labels) {
            return labels[find(x, labels)] = find(y, labels);
        }

        private int find(int i, final int[] labels) {
            int y = i;
            while (labels[y] != y)
                y = labels[y];
            while (labels[i] != i) {
                int z = labels[i];
                labels[i] = y;
                i = z;
            }
            return y;
        }
    }


    public void printMatrix(final int[][] model) {
        final StringBuilder sb = new StringBuilder();
        for (int[] ints : model)
            sb.append(Arrays.toString(ints)).append("\n");
        System.out.print(sb.append("\n\n"));
    }


}
