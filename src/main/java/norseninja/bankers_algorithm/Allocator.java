package norseninja.bankers_algorithm;

public class Allocator {

    //resources currently allocated
    private static final int[][] allocated = {{1,0,0},{0,1,1},{0,2,1},{1,1,1},{0,0,0}};

    //resources necessary for completion of task
    private static final int[][] max = {{1,2,0},{1,1,1},{0,3,2},{4,4,5},{3,0,3}};

    //finished tasks
    private static final boolean[] finished = {false, false, false, false, false};

    //total resources
    private static final int a = 5, b = 7, c = 9;


    public static void main(String[] args) {
        while (!finished()) {
            int nextTask = getNextTask();
            if (nextTask >= 0) {
                if (completeTask(nextTask)) {
                    System.out.println("Finished task number " + nextTask);
                } else {
                    System.out.println("Unexpected resource discrepancy.");
                }
            } else {
                System.out.println("Not enough resources to proceed.");
            }
        }


    }

    /**
     * Allocates the necessary resources to the task at given index, executes it,
     * and marks it as finished; freeing up its resources.
     * @param i the index of the task.
     * @return true if finished, false in case of resource discrepancy.
     */
    private static boolean completeTask(int i) {
        boolean ableToFinish = true;
        for (int j = 0; j < max[i].length; j++) {
            if (max[i][j] - allocated[i][j] > getAvailable()[j]) {
                ableToFinish = false;
                break;
            }
        }
        if (ableToFinish) {
            finished[i] = true;
        }
        return ableToFinish;
    }

    /**
     * Finds the task that will spend the most of the available resources in order to execute.
     * @return the index of the most resource-demanding task able
     * to execute with the resources on hand, or -1 if dead-locked.
     */
    private static int getNextTask() {
        int optimalTask = -1;
        int optimalTaskLeftovers = 100;
        for (int i = 0; i < max.length; i++) {
            if (!finished[i]) {
                int[] task = max[i];
                int[] alloc = allocated[i];
                int[] available = getAvailable();
                int ra = available[0] - task[0] + alloc[0];
                int rb = available[1] - task[1] + alloc[1];
                int rc = available[2] - task[2] + alloc[2];
                if (ra >= 0 && rb >= 0 && rc >= 0) {
                    int leftovers = ra + rb + rc;
                    if (leftovers > 0 && leftovers < optimalTaskLeftovers) {
                        optimalTask = i;
                        optimalTaskLeftovers = leftovers;
                    }
                }
            }
        }
        return optimalTask;
    }

    /**
     * Gets the currently unassigned resources.
     * @return int array containing the free resources.
     */
    private static int[] getAvailable() {
        int aa = a;
        int ab = b;
        int ac = c;
        for (int i = 0; i < max.length; i++) {
            if (!finished[i]) {
                aa -= allocated[i][0];
                ab -= allocated[i][1];
                ac -= allocated[i][2];
            }
        }
        return new int[]{aa,ab,ac};
    }

    /**
     * Checks if any tasks remain unfinished.
     * @return true if all tasks are finished, or false if not.
     */
    private static boolean finished() {
        for (boolean b : finished) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
}
