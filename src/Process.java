import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Process {

    int processNo = 1;
    String[][] data = {{"1", "0", "12"},
                        {"2", "2", "6"},
                        {"3", "4", "8"},
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
    }



    public void fcfs() {
        System.out.println("FCFS Method called");
        sortData();

        // finish time
        finishTimeArr[0] = Integer.toString(intData[0][1] + intData[0][2]);
        for (int i=1; i< data.length;i++) {
             int f = Integer.parseInt(finishTimeArr[i-1]) + intData[i][2]; // finish time + burst
             finishTimeArr[i] = Integer.toString(f);
        }
        System.out.println("FINISH TIME: " + Arrays.toString(finishTimeArr));

        // waiting time
        for (int i=0; i<data.length; i++) {
            int w = Integer.parseInt(finishTimeArr[i]) - intData[i][1] - intData[i][2]; // burst-arrival
            waitingTimeArr[i] = Integer.toString(w);
        }
        System.out.println("WAITING TIME: " + Arrays.toString(waitingTimeArr));

        // turnaround time
        for (int i=0; i<data.length; i++) {
            int t = Integer.parseInt(finishTimeArr[i]) - intData[i][1]; // finish time - arrival time
            turnaroundTimeArr[i] = Integer.toString(t);
        }
        System.out.println("TURNAROUND TIME: " + Arrays.toString(turnaroundTimeArr));

        // average waiting time
        int sumW=0;
        for (int i=0; i<data.length; i++) {
            sumW+=Integer.parseInt(waitingTimeArr[i]);
        }
        System.out.println("SUM: " + sumW);
        averageWT = Double.toString((double) sumW /waitingTimeArr.length);

        //average turnaround time
        int sumT=0;
        for (int i=0; i<data.length; i++) {
            sumT+=Integer.parseInt(turnaroundTimeArr[i]);
        }
        averageTAT = Double.toString((double) sumT /turnaroundTimeArr.length);

    }

    public void srtf() {
        System.out.println("SRTF Method Called.");
        sortData();

    }

    public void resetValues() {
        averageWT = "";
        averageTAT = "";
        waitingTimeArr = new String[data.length];
        turnaroundTimeArr = new String[data.length];
        finishTimeArr = new String[data.length];

    }
}
