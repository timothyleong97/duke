import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Duke {
    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Task> arr = new ArrayList<>(100);

    /**
     * Prints a string.
     */
    private static void print(String str) {
        System.out.println(str);
    }

    /**
     * Adds a task to the array.
     */
    private static void addTask(Task task) {
        print("Got it. I've added this task:");
        print(task.toString());
        arr.add(task);
        print("Now you have " + arr.size() + (arr.size() > 1 ? " tasks" : " task") + " in the list.");
    }

    /**
     * Prints a greeting and echoes user input.
     *
     * @param args takes in arguments.
     */
    public static void main(String[] args) {

        print("Hello! I'm Duke\nWhat can I do for you?");
        String taskName;
        Task temp;
        while (sc.hasNext()) {
            String str = sc.nextLine();
            Scanner scanner = new Scanner(str);
            String firstWord = scanner.next();
            switch (firstWord) {
                case "bye":
                    print("Bye. Hope to see you again soon!");
                    break;
                case "list":
                    print("Here are the tasks in your list:");
                    int i = 0;
                    for (Task t : arr) {
                        print(++i + "." + t);
                    }
                    break;
                case "todo":
                    try {
                        taskName = scanner.nextLine();
                        temp = new TaskBuilder().type(TaskType.TODO).description(taskName).build();
                        addTask(temp);
                    } catch (NoSuchElementException e) {
                        print("OOPS!!! The description of a todo cannot be empty.");
                    }
                    break;
                case "deadline":
                    taskName = "";
                    String deadline = "";
                    while (scanner.hasNext()) {
                        String s = scanner.next();
                        if (s.equals("/by")) {
                            deadline = scanner.nextLine().strip();
                            taskName = taskName.stripTrailing();
                            break;
                        } else {
                            taskName += s + " ";
                        }
                    }
                    temp = new TaskBuilder().type(TaskType.DEADLINE).description(taskName).deadline(deadline).build();
                    addTask(temp);
                    break;
                case "event":
                    taskName = "";
                    String timeframe = "";
                    while (scanner.hasNext()) {
                        String s = scanner.next();
                        if (s.equals("/at")) {
                            timeframe = scanner.nextLine().strip();
                            taskName = taskName.stripTrailing();
                            break;
                        } else {
                            taskName += s + " ";
                        }
                    }
                    temp = new TaskBuilder().type(TaskType.EVENT).description(taskName).timeframe(timeframe).build();
                    addTask(temp);
                    break;
                case "done":
                    int whichTask = scanner.nextInt() - 1;
                    arr[whichTask].markAsDone();
                    print("Nice! I've marked this task as done:");
                    print(arr[whichTask].toString());
                    break;
                default:
                    print("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

        }
    }

}
