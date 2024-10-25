import java.util.Arrays;

public class Process {

    int processNo = 1;
    String[][] data = {{"1", "3", "3"},{"2", "1", "3"}};
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

        int dataLength = data.length-1;

        // convert data made from strings to int
        int[][] intData = new int[dataLength][3];
        for (int i=0;i<dataLength;i++) {
            for (int j=0;j<3;j++) {
                intData[i][j] = Integer.parseInt(data[i+1][j]);
            }
        }

        int[][] sortedData;
        int min, temp;
        // sorts the double array using insertion sort
        // https://www.youtube.com/watch?v=EwjnF7rFLns&t=113s

        for (int i=0; i<dataLength;i++) {
            min = intData[i][1];

            // not done
            while ();

        }

//        for (int i=1;i<dataLength;i++) {
//            for (int j=0; i< dataLength;i++){
//                intData[1][j];
//
//            }
//        }
    }

    public void srtf() {

    }
}
