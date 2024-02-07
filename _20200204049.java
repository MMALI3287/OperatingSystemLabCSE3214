import java.util.*;

class Process {

  int processNumber, arrivalTime, cpuBurstTime, waitingTime;
  boolean isFinished = false;

  Process(int processNumber, int arrivalTime, int cpuBurstTime) {
    this.processNumber = processNumber;
    this.arrivalTime = arrivalTime;
    this.cpuBurstTime = cpuBurstTime;
  }
}

public class _20200204049 {

  public static void main(String[] args) {
    Scanner inputScanner = new Scanner(System.in);
    System.out.print("Enter the number of processes: ");
    int numberOfProcesses = inputScanner.nextInt();

    Process[] processes = new Process[numberOfProcesses];
    System.out.println("Enter the CPU burst times and arrival times:");

    for (int i = 0; i < numberOfProcesses; i++) {
      System.out.print(
        "Enter CPU burst time and Arrival time for process P" + (i + 1) + ": "
      );
      int cpuBurstTime = inputScanner.nextInt();
      int arrivalTime = inputScanner.nextInt();

      processes[i] = new Process(i, arrivalTime, cpuBurstTime);
    }

    for (int i = 0; i < numberOfProcesses - 1; i++) {
      for (int j = 0; j < numberOfProcesses - i - 1; j++) {
        if (processes[j].arrivalTime > processes[j + 1].arrivalTime) {
          Process temp = processes[j];
          processes[j] = processes[j + 1];
          processes[j + 1] = temp;
        }
      }
    }

    PriorityQueue<Process> queue = new PriorityQueue<>((p1, p2) -> {
      if (p1.waitingTime != p2.waitingTime) {
        return p2.waitingTime - p1.waitingTime;
      } else if (p1.cpuBurstTime != p2.cpuBurstTime) {
        return p1.cpuBurstTime - p2.cpuBurstTime;
      } else {
        return p1.processNumber - p2.processNumber;
      }
    });

    int currentTime = processes[0].arrivalTime;
    int index = 0;

    System.out.println("\nGantt Chart:");
    System.out.print(currentTime);

    while (!queue.isEmpty() || index < numberOfProcesses) {
      while (
        index < numberOfProcesses && processes[index].arrivalTime <= currentTime
      ) {
        processes[index].waitingTime =
          currentTime - processes[index].arrivalTime;
        queue.add(processes[index]);
        index++;
      }

      if (!queue.isEmpty()) {
        Process process = queue.poll();
        process.isFinished = true;
        currentTime += process.cpuBurstTime;

        System.out.print(
          " --P" + (process.processNumber + 1) + "-- " + currentTime
        );
      } else {
        currentTime = processes[index].arrivalTime;
      }
    }
  }
}
