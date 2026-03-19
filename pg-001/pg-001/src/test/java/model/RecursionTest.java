package model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class RecursionTest {

    @Test
    void factorial() {
        System.out.println("\n\n\nfactorial");
        int[] list = {5, 10, 12, 15, 20};
        for (int n : list) {
            AtomicInteger counter = new AtomicInteger(0);
            long t1 = System.nanoTime();
            long result = Recursion.factorial(n, counter);
            long t2 = System.nanoTime();

            System.out.println("\n\nT(n): " + util.Utility.format(t2 - t1));
            System.out.println("Llamadas recursivas: " + util.Utility.format(counter.get()));
            System.out.println("Factorial de " + n + ": " + util.Utility.format(result ));
        }
    }

    @Test
    void matryoshkaTest() {
        System.out.println("\n\n\nmatryoshkaTest");

        Recursion.matryoshka(5);
    }


    @Test
    void fibonacciTest() {
        System.out.println("\n\n\nfibonacciTest");

        int[] list = {5, 10, 12, 15, 20};
        AtomicInteger counter = new AtomicInteger(0);
        for (int n : list) {
            long t1 = System.nanoTime();
            long result = Recursion.fibonacci(n, counter);
            long t2 = System.nanoTime();

            System.out.println("Fibonacci " + n + " is: " + util.Utility.format(result) +
                    ". Total recursive calls: " + util.Utility.format(counter.get())
                    + ". T(n): " + util.Utility.format(t2 - t1) + "ns");
        }
    }

    @Test
    void fibMemo() {
        System.out.println("\n\n\nfibMemo");

        int[] list = {5, 10, 12, 15, 20};
        for (int n : list) {
            Map<Integer, Long> memo = new HashMap<>();
            AtomicInteger counter = new AtomicInteger(0);

            long t1 = System.nanoTime();

            long result = Recursion.fibMemo(n, memo, counter);
            long t2 = System.nanoTime();

            System.out.println("Fibonacci " + n + " is: " + util.Utility.format(result) +
                    ". Total recursive calls: " + util.Utility.format(counter.get())
                    + ". T(n): " + util.Utility.format(t2 - t1) + "ns");
        }

    }

    @Test
    void fibMemoArray() {
        System.out.println("\n\n\nfibMemoArray");
        int[] list = {5, 10, 12, 15, 20};
        for (int n : list) {
            long[] memo = new long[n + 1];
//            innit array en -1
            for (int i = 0; i < n + 1; i++) memo[i] = -1;

            AtomicInteger counter = new AtomicInteger(0);

            long t1 = System.nanoTime();

            long result = Recursion.fibMemoArray(n, memo, counter);
            long t2 = System.nanoTime();

            System.out.println("Fibonacci " + n + " is: " + util.Utility.format(result) +
                    ". Total recursive calls: " + util.Utility.format(counter.get())
                    + ". T(n): " + util.Utility.format(t2 - t1) + "ns");
        }
    }
}