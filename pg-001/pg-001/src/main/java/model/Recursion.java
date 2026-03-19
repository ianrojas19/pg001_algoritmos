package model;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Recursion {
    public static long factorial(int n, AtomicInteger counter) {
        counter.incrementAndGet();
        if (n <= 1) return 1;
        return n * factorial(n - 1, counter);
    }


    public static void matryoshka(int n) {

        if (n <= 1) {
            System.out.println("Abriendo la muñeca mas pequeña: " + n);

            return; // caso base
        }
        System.out.println("Abriendo la muñeca N°: " + n);

        matryoshka(n - 1); // llamada recursiva
    }

    public static long fibonacci(int n, AtomicInteger counter) {
        counter.incrementAndGet();
        if (n <= 1) return n;
        return fibonacci(n - 1, counter) + fibonacci(n - 2, counter);
    }

    //    Fibonacci con Map
    public static long fibMemo(int n, Map<Integer, Long> memo, AtomicInteger counter) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n); // Cache

        counter.incrementAndGet();

        long result = fibMemo(n - 1, memo, counter) + fibMemo(n - 2, memo, counter);

        memo.put(n, result);
        return result;
    }

    //    Fibonacci con memorizacion usando arreglos
    public static long fibMemoArray(int n, long[] memo, AtomicInteger counter) {
        counter.incrementAndGet(); // contar llamadas recursivas
        if (n <= 1) return n;
        if (memo[n] != -1) return memo[n]; //si tiene un resultado almacenado, lo devuelve
        memo[n] = fibMemoArray(n - 1, memo, counter) + fibMemoArray(n - 2, memo, counter);
        return memo[n];
    }


}
