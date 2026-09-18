package se.edu.streamdemo;

import static java.util.stream.Collectors.toList;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;
import se.edu.streamdemo.task.TaskComparator;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Task manager (using streams)");
        Datamanager dataManager = new Datamanager("./data/data.txt");
        ArrayList<Task> tasksData = dataManager.loadData();

        System.out.println("Printing all data ...");
        printAllData(tasksData);
        printAllDataUsingStreams(tasksData);

        System.out.println("Printing deadlines ...");
        printDeadlines(tasksData);
        printDeadlinesUsingStreams(tasksData);

        printDeadlinesUsingLambda(tasksData);

        System.out.println("Total number of deadlines: " + countDeadlines(tasksData));
        System.out.println(filterTasksByString(tasksData, "10"));
        System.out.println("Using streams ... ");
        System.out.println("Total number of deadlines: " + countDeadlinesUsingStreams(tasksData));

    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlinesUsingStreams(ArrayList<Task> tasksData) {
        int count = (int) tasksData.stream()
                .filter(t->t instanceof Deadline)
                .count();
        return count;
    }

    public static void printAllData(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printAllDataUsingStreams(ArrayList<Task> tasks) {
        System.out.println("Using streams ... ");
        tasks.stream()
                .forEach(System.out::println);
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesUsingLambda(ArrayList<Task> tasksData) {
        System.out.println("Using Lambda ... ");
        tasksData.stream()
                .filter(t -> t instanceof Deadline)
                .sorted((t1, t2) -> t1.getDescription().compareToIgnoreCase(t2.getDescription()))
                .forEach(System.out::println);
    }

    public static ArrayList<Task> filterTasksByString(ArrayList<Task> tasksData, String filterString) {
        ArrayList<Task> filteredList = (ArrayList<Task>) tasksData.stream()
                .filter(t -> t.getDescription().contains(filterString))
                .collect(toList());
        return filteredList;
    }

    public static void printDeadlinesUsingStreams(ArrayList<Task> tasksData) {
        System.out.println("Using streams ... ");
        tasksData.parallelStream()
                .filter(t -> t instanceof Deadline)
                .forEach(System.out::println);
    }

}
