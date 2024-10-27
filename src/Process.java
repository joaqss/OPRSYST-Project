import java.util.Arrays;

public class Process {

    int processNo = 1;
    String[][] data = {{"1", "5", "12"},
                        {"2", "1", "6"},
                        {"3", "3", "8"},
                        {"4", "6", "4"}};
//    String[][] data = {}; // process no, arrival time, burst time
    int[][] intData;
    String[] waitingTimeArr = new String[data.length];
    String[] turnaroundTimeArr = new String[data.length];
    String[] finishTimeArr = new String[data.length];
    String averageWT, averageTAT;

    public Process() {
        System.out.println("Process instance created.");
    }

    public void addRow(String[] row) {
        String[][] newData = new String[data.length + 1][row.length];
        for (int i = 0; i<data.length;i++) {
            newData[i] = data[i];
        }
        newData[data.length] = row;
        data = newData;

        resetValues();

        for (String[] a : data) {
            System.out.print(Arrays.toString(a));
        }
        System.out.println();
    }

    public void sortData() {
        System.out.println("Data Length:" + data.length);

        // convert data made from strings to int
        intData = new int[data.length][3];
        for (int i=0;i<data.length;i++) {
            for (int j=0;j<3;j++) {
                intData[i][j] = Integer.parseInt(data[i][j]);
            }
        }

        int min, innerElement;
        int[] tempArr;

        // sorts the double array using selection sort https://www.youtube.com/watch?v=EwjnF7rFLns&t=113s
        for (int i=0; i<data.length;i++) {
//            minArr = intData[i];
            min = intData[i][1];
            innerElement = i;

            for (int j=i+1; j<data.length; j++) {
                if (min > intData[j][1]) {
                    min = intData[j][1];
//                    minArr = intData[j];
                    innerElement = j;
                }
            }
            // used 1D array to move the whole array itself instead of 1 element inside inner array
            tempArr = intData[i]; //places outerLoop pointer element to temp
            intData[i] = intData[innerElement]; // places minVal to outerLoop pointer
            intData[innerElement] = tempArr; // places temp to innerLoop pointer

        } // end of selection sort

        // convert sortedInt back to String
        for (int i=0;i<data.length;i++) {
            for (int j=0;j<3;j++) {
                data[i][j] = Integer.toString(intData[i][j]);
            }
        }

        System.out.println("SORTED DATA based on Arrival Time: ");
        for (int[] b : intData) {
            System.out.println(Arrays.toString(b));
        }
    } // end of sortData method

    public void resetValues() {
        averageWT = "";
        averageTAT = "";
        waitingTimeArr = new String[data.length];
        turnaroundTimeArr = new String[data.length];
        finishTimeArr = new String[data.length];
        processNo = 1;

    }

    public void fcfs() {
        System.out.println("FCFS Method called");
        sortData();

        int n = data.length;
        int[] remainingBurstTime = new int[n];
        int currentTime = 0;
        int completedProcesses = 0;

        // places burst time of each process into one single array
        for (int i = 0; i < n; i++) {
            remainingBurstTime[i] = intData[i][2];
        }

        while (completedProcesses < n) {
            int BTIndex = -1;

            // find process to execute (scan will stop once process is found w/ BT
            for (int i = 0; i<n; i++) {
                int arrivalTime = intData[i][1];

                if (arrivalTime <= currentTime && remainingBurstTime[i] > 0) {
                    BTIndex = i;
                    break;
                }
            }

            if (BTIndex == -1) {
                currentTime++;
            } else {
                remainingBurstTime[BTIndex]--;
                currentTime++;

                // if process is complete
                if (remainingBurstTime[BTIndex] == 0) {
                    completedProcesses++;
                    finishTimeArr[BTIndex] = String.valueOf(currentTime);

                    int turnaroundTime = currentTime - intData[BTIndex][1];
                    int waitingTime = turnaroundTime - intData[BTIndex][2];
                    turnaroundTimeArr[BTIndex] = String.valueOf(turnaroundTime);
                    waitingTimeArr[BTIndex] = String.valueOf(waitingTime);

                }
            }
        }

        // print table
        for (int i=0; i<data.length; i++) {
            System.out.println("Process " + intData[i][0] + " Finish Time: " + finishTimeArr[i] +
                    " Turnaround Time: " + turnaroundTimeArr[i] + " Waiting Time: " + waitingTimeArr[i]);
        }

        // average waiting time
        int sumW=0;
        for (int i=0; i<data.length; i++) {
            sumW+=Integer.parseInt(waitingTimeArr[i]);
        }
        averageWT = Double.toString((double) sumW /waitingTimeArr.length);
        System.out.println("Average waiting time: " + averageWT);

        //average turnaround time
        int sumT=0;
        for (int i=0; i<data.length; i++) {
            sumT+=Integer.parseInt(turnaroundTimeArr[i]);
        }
        averageTAT = Double.toString((double) sumT /turnaroundTimeArr.length);
        System.out.println("Average turnaround time: " + averageTAT);

    } // end of fcfs method

    public void srtf() {
        System.out.println("SRTF Method Called.");
        sortData();

        int n = intData.length;
        int[] remainingBurstTime = new int[n];
        int[] finishTime = new int[n];
        boolean[] isCompleted = new boolean[n];
        int completedProcesses = 0;
        int currentTime = 0;
        int minBurstTime;
        int shortestBTIndex = -1;

        // places burst time of each process into one single array
        for (int i = 0; i < n; i++) {
            remainingBurstTime[i] = intData[i][2];
        }

        while (completedProcesses < n) {
            System.out.println("remaining burst time array: " + Arrays.toString(remainingBurstTime));
            minBurstTime = Integer.MAX_VALUE;

            // find shortest BT under the current time (scan until the end of array)
            for (int i=0; i< data.length; i++) {
                int arrivalTime = intData[i][1];

                System.out.println("current time: " + currentTime);
                System.out.println("Arrival time: " + arrivalTime);

                // replaces minBurstTime and shortestBTIndex if conditions are met
                if ((currentTime >= arrivalTime) && (!isCompleted[i]) && (remainingBurstTime[i] < minBurstTime)) {
                    minBurstTime = remainingBurstTime[i];
                    shortestBTIndex = i;

                    System.out.println("NEW SHORTEST BT Index: " + shortestBTIndex);
                    System.out.println("Remaining Burst Time: " + remainingBurstTime[i]);
                    System.out.println("Minimum BT: " + minBurstTime);
                }
            }

            if (shortestBTIndex == -1) { // if no process is found, add time
                System.out.println("No new shortest BT. ");
                System.out.println();
                currentTime++;
            } else { // if shortestBTIndex is updated/new process is pointed
                remainingBurstTime[shortestBTIndex]--;
                currentTime++;

                System.out.println("shortestBTIndex UPDATED: " + shortestBTIndex + " ProcessNo: " +
                        intData[shortestBTIndex][0]);
                System.out.println("remaining burst time: " + remainingBurstTime[shortestBTIndex]);
                System.out.println("Array RBT: " + Arrays.toString(remainingBurstTime));
                System.out.println();

                // if process is complete
                if (remainingBurstTime[shortestBTIndex] == 0) {
                    isCompleted[shortestBTIndex] = true;
                    completedProcesses++;
                    finishTime[shortestBTIndex] = currentTime;

                    // Calculate turnaround time and waiting time
                    int turnaroundTime = finishTime[shortestBTIndex] - intData[shortestBTIndex][1];
                    int waitingTime = turnaroundTime - intData[shortestBTIndex][2];
                    turnaroundTimeArr[shortestBTIndex] = Integer.toString(turnaroundTime);
                    waitingTimeArr[shortestBTIndex] = Integer.toString(waitingTime);
                    finishTimeArr[shortestBTIndex] = Integer.toString(finishTime[shortestBTIndex]);
                }
            }
        }

        // print table
        for (int i=0; i<data.length; i++) {
            System.out.println("Process " + intData[i][0] + " Finish Time: " + finishTimeArr[i] +
                    " Turnaround Time: " + turnaroundTimeArr[i] + " Waiting Time: " + waitingTimeArr[i]);
        }

        // average waiting time
        int sumW = 0;
        for (int i=0; i< waitingTimeArr.length; i++) {
            sumW+=Integer.parseInt(waitingTimeArr[i]);
        }
        averageWT = Double.toString((double) sumW /waitingTimeArr.length);
        System.out.println("Average waiting time: " + averageWT);

        // average turnaround time
        int sumT = 0;
        for (int i=0; i< turnaroundTimeArr.length; i++) {
            sumT+=Integer.parseInt(turnaroundTimeArr[i]);
        }
        averageTAT = Double.toString((double) sumT /turnaroundTimeArr.length);
        System.out.println("Average turnaround time: " + averageTAT);
    } // end of srtf method

} // end of process class
