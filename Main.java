package net.bit.dbStart;


import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
  public static void main(String[] args) {
    // Test
    System.out.println(getChoiceWithTimeout(10));
  }

  static int getChoiceWithTimeout(int range) {
    Callable<Integer> k = () -> new Scanner(System.in).nextInt();
    Long start = System.currentTimeMillis();
    int choice = 0;
    boolean valid;
    ExecutorService l = Executors.newFixedThreadPool(1);
    Future<Integer> g;
    System.out.println("Enter your choice in 15 seconds :");
    g = l.submit(k);
    done: while (System.currentTimeMillis() - start < 15 * 1000) {
      do {
        valid = true;
        if (g.isDone()) {
          try {
            choice = g.get();
            if (choice >= 0 && choice <= range) {
              break done;
            } else {
              throw new IllegalArgumentException();
            }
          } catch (InterruptedException | ExecutionException | IllegalArgumentException e) {
            System.out.println("Wrong choice, you have to pick an integer between 0 - " + range);
            g = l.submit(k);
            valid = false;
          }
        }
      } while (!valid);
    }

    g.cancel(true);
    return choice;
  }
}