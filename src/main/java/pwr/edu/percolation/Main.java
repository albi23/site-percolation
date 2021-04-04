package pwr.edu.percolation;

public class Main {


    public static void main(String[] args)  {

        final PercolationParams params = ParamHandler.readInputParams();
        System.out.println(params);

        final PercolationModel percolationModel = new PercolationModel(params);
//        final int[][] model = percolationModel.generateLatticeModel(0.9);
//
//        int[][] model = {
//                {0, 0, 0, 1, 1},
//                {1, 0, 1, 1, 1},
//                {1, 1, 1, 0, 1},
//                {1, 1, 0, 1, 1},
//                {1, 1, 0, 1, 0}
//        };
//        final int i = percolationModel.shortestPathInModel(model);

        int[][] model2 = {

                {1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };


        percolationModel.printMatrix(model2);
        percolationModel.distributionOfClusters(model2);
//        System.out.println("Shortest = " + i);
    }

}
