import java.util.Arrays;

public class Process {

    int processNo = 1;
    String[][] data = {{"1", "2", "3"},{"2", "2", "3"}};

    public Process() {

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

//        int i = 0;
//        while (i < data.length) {
//            System.out.println("RUNNING FCFS");
//            String[] process = data[i];
//            int arrivalTime = Integer.parseInt(process[1]);
//            int burstTime = Integer.parseInt(process[2]);
//
////            computationWindow.updateCell(i, 3, "Running", computationWindow.model);
////            computationWindow.panel.repaint();
////            computationWindow.panel.revalidate();
//            i++;
//        }
    }

    public void srtf() {

    }
}
