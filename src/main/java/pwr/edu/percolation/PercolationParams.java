package pwr.edu.percolation;

/**
 * Param L   : the linear size of the system <br>
 * Param T   : number of trials <br>
 * Param p0  : minimum value of p in the loop <br>
 * Param pk  : maximum value of p in the loop <br>
 * Param dp  : step with which p changes inside of the loop <br>
 */
record PercolationParams(int L, int T, double p0, double pk, double dp) {
}
