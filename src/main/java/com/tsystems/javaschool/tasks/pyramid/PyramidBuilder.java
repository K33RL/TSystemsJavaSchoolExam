package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        if (inputNumbers.contains(null)|| inputNumbers.size()>9999)throw new CannotBuildPyramidException();
        boolean flag;//Флаг для возможности/невозможности построения пирамиды
        int[][] matrix;//Получившаяся матрица

        System.out.println("Введенная последовательность: " + inputNumbers);

        int size = inputNumbers.size();//Проверяем размер полученного массива

        //Проверим, является ли данное число треугольным
        int count = 0;
        int rows = 1;
        int cols = 1;
        while (count < size) {
            count = count + rows;
            rows++;
            cols = cols + 2;
        }
        rows = rows - 1;//Актуальное число строк
        cols = cols - 2;//Актуальное число столбцов

        if (size == count) {
            flag = true;//Если возможно построить матрицу
        } else flag = false;

        if (flag) {
            List<Integer> sorted = inputNumbers.stream().sorted().collect(Collectors.toList());
//            System.out.println("Отсортированная последовательность по возрастанию: " + sorted);
//            System.out.println("Количество элементов в последовательности: " + size);
//            System.out.println("Число столбцов(cols) матрицы будет равно: " + cols);
//            System.out.println("Число строк(rows) матрицы будет равно: " + rows);

            matrix = new int[rows][cols];//Задаем размерность матрице
            for (int[] row : matrix) {
                Arrays.fill(row, 0);
            }

            int center = (cols / 2);//Находим центральную точку матрицы
            count = 1; // сколько чисел будет в строке
            int arrIdx = 0; // индекс массива 

            for (int i = 0, offset = 0; i < rows; i++, offset++, count++) {
                int start = center - offset;
                for (int j = 0; j < count * 2; j +=2, arrIdx++) {
                    matrix[i][start + j] = sorted.get(arrIdx);
                }
            }

            for(int [] a: matrix)//выводим матрицу на экран
            {
                for(int b: a)
                    System.out.print(b+" ");
                System.out.println();
            }
        }//Выбрасываем исключение
        else{
            throw new CannotBuildPyramidException();
        }


        return matrix;
    }


}
