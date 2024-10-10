import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
Completed by Charles VandenBosch
 */
public class Percolation {
    private int[][] grid;
    private int n;
    private int openedSites = 0;
    private WeightedQuickUnionUF UF;
    // private QuickFindUF UF;
    private int virtualTop;
    private int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    // a 0 is blocked, 1 is open
    public Percolation(int n) {
        // initialize variables
        this.n = n;
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
        grid = new int[n][n];

        // create a new UF object with length n*n + 2 because it is acting as a 1d array
        // so 1 spot for each value of the grid and + 2 because we are adding a virtual top and bottom
        UF = new WeightedQuickUnionUF(n * n + 2);
        // UF = new QuickFindUF(n * n + 2);

        // build the grid
        for (int i = 0; i < n; i++) {
            for (int x = 0; x < n; x++) {
                grid[i][x] = 0;
            }
        }
    }

    // opens the site (row, col)
    public void open(int row, int col) {
        // check for out of bounds error
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new IllegalArgumentException();
        }

        // check if closed (if open we don't want to open it again)
        if (grid[row][col] == 0) {
            grid[row][col] = 1;

            // if the grid is a 1x1 grid, do nothing (after opening it)
            if (n == 1) {
            }

            // top left corner
            else if (row == 0 && col == 0) {
                UF.union(virtualTop, convert(row, col));

                // check right
                if (grid[row][col + 1] == 1) {
                    UF.union(convert(row, col), convert(row, col + 1));
                }
                // check down
                if (grid[row + 1][col] == 1) {
                    UF.union(convert(row, col), convert(row + 1, col));
                }
            }

            // bottom left corner
            else if (row == n - 1 && col == 0) {
                UF.union(virtualBottom, convert(row, col));

                // check right
                if (grid[row][col + 1] == 1) {
                    UF.union(convert(row, col), convert(row, col + 1));
                }
                // check up
                if (grid[row - 1][col] == 1) {
                    UF.union(convert(row, col), convert(row - 1, col));
                }
            }

            // top right corner
            else if (row == 0 && col == n - 1) {
                UF.union(virtualTop, convert(row, col));

                // check left
                if (grid[row][col - 1] == 1) {
                    UF.union(convert(row, col), convert(row, col - 1));
                }

                // check down
                if (grid[row + 1][col] == 1) {
                    UF.union(convert(row, col), convert(row + 1, col));
                }
            }

            // bottom right corner
            else if (row == n - 1 && col == n - 1) {
                UF.union(virtualBottom, convert(row, col));

                // check left
                if (grid[row][col - 1] == 1) {
                    UF.union(convert(row, col), convert(row, col - 1));
                }
                // check up
                if (grid[row - 1][col] == 1) {
                    UF.union(convert(row, col), convert(row - 1, col));
                }
            }

            // top row
            else if (row == 0) {
                UF.union(virtualTop, convert(row, col));

                // check left
                if (grid[row][col - 1] == 1) {
                    UF.union(convert(row, col), convert(row, col - 1));
                }
                // check right
                if (grid[row][col + 1] == 1) {
                    UF.union(convert(row, col), convert(row, col + 1));
                }
                // check down
                if (grid[row + 1][col] == 1) {
                    UF.union(convert(row, col), convert(row + 1, col));
                }
            }

            // bottom row
            else if (row == n - 1) {
                UF.union(virtualBottom, convert(row, col));

                // check left
                if (grid[row][col - 1] == 1) {
                    UF.union(convert(row, col), convert(row, col - 1));
                }
                // check right
                if (grid[row][col + 1] == 1) {
                    UF.union(convert(row, col), convert(row, col + 1));
                }
                // check up
                if (grid[row - 1][col] == 1) {
                    UF.union(convert(row, col), convert(row - 1, col));
                }
            }

            // left col
            else if (col == 0) {
                // check right
                if (grid[row][col + 1] == 1) {
                    UF.union(convert(row, col), convert(row, col + 1));
                }
                // check up
                if (grid[row - 1][col] == 1) {
                    UF.union(convert(row, col), convert(row - 1, col));
                }
                // check down
                if (grid[row + 1][col] == 1) {
                    UF.union(convert(row, col), convert(row + 1, col));
                }
            }

            // right col
            else if (col == n - 1) {
                // check left
                if (grid[row][col - 1] == 1) {
                    UF.union(convert(row, col), convert(row, col - 1));
                }
                // check up
                if (grid[row - 1][col] == 1) {
                    UF.union(convert(row, col), convert(row - 1, col));
                }
                // check down
                if (grid[row + 1][col] == 1) {
                    UF.union(convert(row, col), convert(row + 1, col));
                }
            }

            // connected with 4 (everything else)
            else {
                // check right
                if (grid[row][col + 1] == 1) {
                    UF.union(convert(row, col), convert(row, col + 1));
                }
                // check left
                if (grid[row][col - 1] == 1) {
                    UF.union(convert(row, col), convert(row, col - 1));
                }
                // check up
                if (grid[row - 1][col] == 1) {
                    UF.union(convert(row, col), convert(row - 1, col));
                }
                // check down
                if (grid[row + 1][col] == 1) {
                    UF.union(convert(row, col), convert(row + 1, col));
                }
            }

            // increment num opened sites
            openedSites += 1;
        }
    }

    // converts the 2 ints row and col of the 2d array into its location in the 1d array
    private int convert(int row, int col) {
        return (n * (row)) + col;
    }

    // check if the site is open
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new IllegalArgumentException();
        }
        if (grid[row][col] == 1) {
            return true;
        }
        return false;
    }

    // check if the site is full
    public boolean isFull(int row, int col) {
        if (row < 0 || row > n - 1 || col < 0 || col > n - 1) {
            throw new IllegalArgumentException();
        }
        return UF.connected(virtualTop, convert(row, col));
    }

    // returns num open sites
    public int numberOfOpenSites() {
        return openedSites;
    }

    // check if grid percolates
    public boolean percolates() {
        return UF.connected(virtualTop, virtualBottom);
    }

    // main
    public static void main(String[] args) {
    }
}