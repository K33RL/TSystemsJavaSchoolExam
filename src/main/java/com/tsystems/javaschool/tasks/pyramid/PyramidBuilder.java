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
        boolean flag;
        int[][] matrix;

        System.out.println("Введенная последовательность: " + inputNumbers);

        int size = inputNumbers.size();
        int count = 0;
        int rows = 1;
        int cols = 1;
        while (count < size) {
            count = count + rows;
            rows++;
            cols = cols + 2;
        }
        rows = rows - 1;
        cols = cols - 2;

        if (size == count) {
            flag = true;
        } else flag = false;

        if (flag) {
            List<Integer> sorted = inputNumbers.stream().sorted().collect(Collectors.toList());
            matrix = new int[rows][cols];
            for (int[] row : matrix) {
                Arrays.fill(row, 0);
            }

            int center = (cols / 2);
            count = 1; 
            int arrIdx = 0; 

            for (int i = 0, offset = 0; i < rows; i++, offset++, count++) {
                int start = center - offset;
                for (int j = 0; j < count * 2; j +=2, arrIdx++) {
                    matrix[i][start + j] = sorted.get(arrIdx);
                }
            }

            for(int [] a: matrix)
            {
                for(int b: a)
                    System.out.print(b+" ");
                System.out.println();
            }
        }
        else{
            throw new CannotBuildPyramidException();
        }


        return matrix;
    }


}
