package norseninja.bankers_algorithm;

public class Allocator {

    //resources currently allocated
    private static final int[][] allocated = {{0,0,1,2},{1,0,0,0},{1,3,5,4},{0,6,3,2},{0,0,1,4}};

    //resources necessary for completion of task
    private static final int[][] max = {{0,0,1,2},{1,7,5,0},{2,3,5,6},{0,6,5,2},{0,6,5,6}};

    //finished tasks
    private static final boolean[] finished = {false, false, false, false, false};

    //total resources
    private static final int a = 3, b = 14, c = 12, d = 12;


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
        for (int j = 0; j < max[i].length; j++) {
            if (max[i][j] - allocated[i][j] > getAvailable()[j]) {
                return false;
            }
        }
        finished[i] = true;
        return true;
    }

    /**
     * Finds the task that will free the most resources upon completion.
     * @return the index of the most resource-demanding task able
     * to execute with the resources on hand, or -1 if dead-locked.
     */
    private static int getNextTask() {
        int optimalTask = -1;
        int optimalTaskAlloc = 0;
        for (int i = 0; i < max.length; i++) {
            if (!finished[i]) {
                int[] task = max[i];
                int[] alloc = allocated[i];
                int[] available = getAvailable();
                int ra = available[0] - task[0] + alloc[0];
                int rb = available[1] - task[1] + alloc[1];
                int rc = available[2] - task[2] + alloc[2];
                int rd = available[3] - task[3] + alloc[3];

                int totalAlloc = 0;
                for (int y : alloc) {
                    totalAlloc += y;
                }

                if (ra >= 0 && rb >= 0 && rc >= 0 && rd >= 0) {
                    int leftovers = ra + rb + rc + rd;
                    if (leftovers >= 0 && totalAlloc > optimalTaskAlloc) {
                        optimalTask = i;
                        optimalTaskAlloc = totalAlloc;
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
        int ad = d;
        for (int i = 0; i < max.length; i++) {
            if (!finished[i]) {
                aa -= allocated[i][0];
                ab -= allocated[i][1];
                ac -= allocated[i][2];
                ad -= allocated[i][3];
            }
        }
        return new int[]{aa,ab,ac,ad};
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
