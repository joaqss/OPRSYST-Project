import java.util.Arrays;

public class Process {

    int processNo = 1;
    String[][] data = {{"1", "3", "3"},
                        {"2", "1", "3"},
                        {"3", "2", "3"}};
    int[] arrayQueue; // queue of each process based on their position element

    public Process() {
        System.out.println("process instance created");
    }

    public void addRow(String[] row) {
        String[][] newData = new String[data.length + 1][row.length];
        for (int i = 0; i<data.length;i++) {
            newData[i] = data[i];
        }
        newData[data.length] = row;
        data = newData;

        for (String[] a : data) {
            System.out.print(Arrays.toString(a));
        }
        System.out.println();
    }



    public void fcfs() {
        // need: waiting time for each process, average waiting time for all processes WT = TS - AT
        // need: turnaround time for each process, average turnaround time for all processes TAT = TC-AT
        System.out.println("DATA LENGTH: " + data.length);
        System.out.println("FCFS method called");

        int dataLength = data.length;

        // convert data made from strings to int
        int[][] intData = new int[dataLength][3];
        for (int i=0;i<dataLength;i++) {
            for (int j=0;j<3;j++) {
                intData[i][j] = Integer.parseInt(data[i][j]);
            }
        }
//        // print converted data
//        System.out.print("CONVERTED DATA: ");
//        for (int[] a : intData) {
//            System.out.print(Arrays.toString(a));
//        }

        int min;
        int[] minArr, tempArr;
        int innerElement = 0;

        // sorts the double array using selection sort https://www.youtube.com/watch?v=EwjnF7rFLns&t=113s
        for (int i=0; i<dataLength;i++) {
            minArr = intData[i];
            min = intData[i][1];

           for (int j=i+1; j<dataLength; j++) {
               if (min > intData[j][1]) {
                   min = intData[j][1];
                   minArr = intData[j];
                   innerElement = j;
               }
           }
           // used 1D array to move the whole array itself instead of 1 element inside inner array
            tempArr = intData[i]; //places outerLoop pointer element to temp
            intData[i] = minArr; // places minVal to outerLoop pointer
            intData[innerElement] = tempArr; // places temp to innerLoop pointer

        } // end of selection sort

        System.out.println("SORTED DATA based on Arrival Time: ");
        for (int[] b : intData) {
            System.out.println(Arrays.toString(b));
        }

    }

    public void srtf() {

    }
}
