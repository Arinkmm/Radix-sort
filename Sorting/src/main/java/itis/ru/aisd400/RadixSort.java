package itis.ru.aisd400;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RadixSort {
    private static int iterationCount = 0;

    public static int getMax(int arr[], int n) {
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static void countingSort(int[] arr, int position) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            count[(arr[i]/position) % 10]++;
            iterationCount++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
            iterationCount++;
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / position) % 10] - 1] = arr[i];
            count[(arr[i]/position) % 10]--;
            iterationCount++;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }

    public static void printResults(int arr[], int n, long time, int iterations) {
        System.out.println("==========================================================");
        System.out.println("Размер массива: " + n + " элементов");
        System.out.println("Время выполнения: " + time + " мкс");
        System.out.println("Количество операций: " + iterations);

        for (int i = 0; i < n-1; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void radixsort(int arr[], int n) {
        iterationCount = 0;
        long startTime = System.nanoTime();

        int max = getMax(arr, n);
        for (int position = 1; max / position > 0; position *= 10) {
            countingSort(arr, position);
        }

        long time = (System.nanoTime() - startTime) / 1000;
        printResults(arr, n, time, iterationCount);
    }

    public static void main(String[] args) throws FileNotFoundException {
        for (int i = 1; i <= 60; i++) {
            Scanner scanner = new Scanner(new File("random_datasets/dataset_" + i + ".txt"));
            int[] tempNumbers = new int[10000];
            int size = 0;

            while (scanner.hasNextInt()) {
                tempNumbers[size++] = scanner.nextInt();
            }

            scanner.close();

            int[] numbers = new int[size];
            System.arraycopy(tempNumbers, 0, numbers, 0, size);

            radixsort(numbers, numbers.length);
        }
    }
}
