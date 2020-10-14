package com.rutwik;

import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static Set<Integer> numbersAvailable = new HashSet<>(new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9)));
    public static List<List<Set<Integer>>> maybe = new ArrayList<>();
    public static int iterration = 0;
    public static int checkResemblance = 81;
    public static int checkResemblanceTemp;
    public static void main(String[] args) {

//        int[] row0 = {0,8,0,0,0,6,0,0,3};
//        int[] row1 = {0,6,0,0,7,0,2,0,0};
//        int[] row2 = {0,0,4,0,1,0,0,9,0};
//        int[] row3 = {0,5,0,0,0,0,8,0,1};
//        int[] row4 = {0,0,0,0,0,0,0,0,0};
//        int[] row5 = {0,1,3,0,0,0,0,6,0};
//        int[] row6 = {0,0,5,0,0,9,0,0,8};
//        int[] row7 = {0,0,0,0,2,0,0,0,0};
//        int[] row8 = {0,0,6,7,0,0,3,0,2};

        int[] row0 = {8,0,0,0,0,0,0,0,0};
        int[] row1 = {0,0,3,6,0,0,0,0,0};
        int[] row2 = {0,7,0,0,9,0,2,0,0};
        int[] row3 = {0,5,0,0,0,7,0,0,0};
        int[] row4 = {0,0,0,0,4,5,7,0,0};
        int[] row5 = {0,0,0,1,0,0,0,3,0};
        int[] row6 = {0,0,1,0,0,0,0,6,8};
        int[] row7 = {0,0,8,5,0,0,0,1,0};
        int[] row8 = {0,9,0,0,0,0,4,0,0};

//        int[] row0 = {5,3,0,0,7,0,0,0,0};
//        int[] row1 = {6,0,0,1,9,5,0,0,0};
//        int[] row2 = {0,9,8,0,0,0,0,6,0};
//        int[] row3 = {8,0,0,0,6,0,0,0,3};
//        int[] row4 = {4,0,0,8,0,3,0,0,1};
//        int[] row5 = {7,0,0,0,2,0,0,0,6};
//        int[] row6 = {0,6,0,0,0,0,2,8,0};
//        int[] row7 = {0,0,0,4,1,9,0,0,5};
//        int[] row8 = {0,0,0,0,8,0,0,7,9};

        int[][] sudoku = {row0,row1,row2,row3,row4,row5,row6,row7,row8};

        solveSudoku(sudoku);
    }

    public static int fillTheBox(int[][] sudoku){
        int countEmptyMaybe = 0;
        Set<Integer> temp ;
        for(int row = 0 ; row < sudoku.length ; row++){
            for(int col = 0 ; col < sudoku[row].length ; col++){
                if((temp = maybe.get(row).get(col)).size()== 1){
                    for(int number : temp) {
                        sudoku[row][col] = number;
                    }
                }
                else if((temp = maybe.get(row).get(col)).isEmpty()){
                    countEmptyMaybe++;
                }
            }
        }
        return countEmptyMaybe;
    }

    public static void checkBox(int[][] sudoku , int rowStart , int colStart , int rowEnd , int colEnd){
        Set<Integer> availableInBox = new HashSet<>();
        Set<Integer> numbersNotInBox = new HashSet<>(numbersAvailable);
        for(int row = rowStart ; row <= rowEnd ; row++){
            for(int col = colStart ; col <= colEnd ; col++){
                if(sudoku[row][col] != 0){
                    availableInBox.add(sudoku[row][col]);
                }
            }
        }

        numbersNotInBox.removeAll(availableInBox);

        for(int row = rowStart ; row <= rowEnd ; row++){
            for(int col = colStart ; col <= colEnd ; col++){
                if(!maybe.get(row).get(col).isEmpty()){
                    Set<Integer> tempSet = maybe.get(row).get(col);
                    tempSet.retainAll(numbersNotInBox);
                }
            }
        }

        for(int i = 1 ; i < numbersAvailable.size() ; i++){
            int countOccurance = 0;
            for(int row = rowStart ; row <= rowEnd ; row++){
                for(int col = colStart ; col <= colEnd ; col++){
                    if(!maybe.get(row).get(col).isEmpty()){
                        if(maybe.get(row).get(col).contains(i))
                            countOccurance++;
                        if(countOccurance > 1)
                            break;
                    }
                }
                if(countOccurance > 1)
                    break;
            }
            if(countOccurance == 1){
                for(int row = rowStart ; row <= rowEnd ; row++){
                    for(int col = colStart ; col <= colEnd ; col++){
                        if(maybe.get(row).get(col).contains(i)){
                            Set<Integer> tempSet = maybe.get(row).get(col);
                            tempSet.retainAll(new HashSet<Integer>(Arrays.asList(i)));
                        }
                    }
                }
            }
        }
    }

    public static void checkRow(int[][] sudoku){
        maybe = new ArrayList<>();
        for(int[] row : sudoku) {
            Set<Integer> abscentElements = findAbscentElementInRow(row);
            ArrayList<Set<Integer>> tempArrayList = new ArrayList<>();
            for (int item : row) {
                if (item == 0) {
                    tempArrayList.add(new HashSet<>(abscentElements));

                } else {
                    tempArrayList.add(new HashSet<>());
                }
            }
            maybe.add(tempArrayList);
        }
    }

    public static Set<Integer> findAbscentElementInRow(int[] row){
        Set<Integer> numbersNotInRow = new HashSet<>(numbersAvailable);
        Set<Integer> availableInRow = new HashSet<>();
        for(int item : row){
            if(item != 0)
                availableInRow.add(item);
        }

        numbersNotInRow.removeAll(availableInRow);

        return numbersNotInRow ;
    }

    public static void checkColumn(int[][] sudoku){

        Set<Integer> numbersNotInCol ;
        Set<Integer> availableInCol;
        for(int i = 0 ; i < sudoku.length ; i++){
            numbersNotInCol = new HashSet<>(numbersAvailable);
            availableInCol = new HashSet<>();
            for(int[] row : sudoku){
                if(row[i] != 0)
                    availableInCol.add(row[i]);
            }
            numbersNotInCol.removeAll(availableInCol);
            for(List<Set<Integer>> row : maybe){
                if(!row.get(i).isEmpty()) {
                    Set<Integer> tempSet = row.get(i);
                    tempSet.retainAll(numbersNotInCol);
                }
            }
        }

    }

    public static  void printSudoku(int[][] sudoku){

        System.out.println(" ----------------------------------X ----------------------------------X ----------------------------------X");

        String colSpacer = " | ";
        String rowSpacer = " *---*---*---*---*---*---*---*---*---*";

        System.out.println(rowSpacer);

        for(int[] row : sudoku){
            for(int col : row){
                System.out.print(colSpacer + col);
            }
            System.out.print(colSpacer);
            System.out.println();
            System.out.println(rowSpacer);
        }

    }

    public static  void printMaybe(){
        int count = 1;
        String colSpacer = " | ";
        String separater = " ----------------------------------";

        System.out.println();
        System.out.println(separater);
        System.out.println();

        for(List<Set<Integer>> row : maybe){
            if(!row.isEmpty()) {
                for (Set<Integer> col : row) {
                    if(!col.isEmpty()) {
                        for (int item : col)
                            System.out.print(item + ",");
                    }
                    else{
                        System.out.print("______");
                    }
                    System.out.print(colSpacer);
                }
                System.out.println();
            }
        }

        System.out.println();
        System.out.println(separater);
        System.out.println();
    }

    public static boolean tryPrediction(int[][] sudoku){
        Set<Integer> temp ;
        for(int row = 0 ; row < sudoku.length ; row++){
            for(int col = 0 ; col < sudoku[row].length ; col++){
                if((temp = maybe.get(row).get(col)).size() == 2){
                    for(int number : temp) {
                        sudoku[row][col] = number;
                        if(solveSudoku(returnCopy(sudoku))){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean solveSudoku(int[][] sudoku){
        iterration++;
        System.out.println(iterration);
        checkRow(sudoku);
        checkColumn(sudoku);
        for(int row = 0 ; row <= sudoku.length-3 ; row+=3){
            checkBox(sudoku,row,0,row + 2,2);
            checkBox(sudoku,row,3,row + 2,5);
            checkBox(sudoku,row,6,row + 2,8);
        }
        checkResemblanceTemp = fillTheBox(sudoku);

        if(checkResemblanceTemp==checkResemblance && isZeroInSudoku(sudoku)){
            checkResemblance = checkResemblanceTemp;
            return tryPrediction(sudoku);
        }
        else {
            printSudoku(sudoku);
            checkResemblance = checkResemblanceTemp;

            if (isZeroInSudoku(sudoku)) {
                if (checkResemblance == 81)
                    return false;
                return solveSudoku(sudoku);
            }
            System.out.println("Resemblance : " + checkResemblance);
            return isSudokuSolved(sudoku);
        }
    }

    public static int[][] returnCopy(int [][] sudoku){
        int[][] Copy = new int[sudoku.length][sudoku.length];
        for(int row = 0 ; row < sudoku.length ; row++){
            for(int col = 0 ; col < sudoku[row].length ; col++){
                Copy[row][col] = sudoku[row][col];
            }
        }
        return Copy;
    }

    public static boolean isZeroInSudoku(int[][] sudoku){
        for(int row = 0 ; row < sudoku.length ; row++){
            for(int col = 0 ; col < sudoku[row].length ; col++){
                if(sudoku[row][col] == 0)
                    return true;
            }
        }
        return false;
    }

    public static boolean isSudokuSolved(int[][] sudoku){
        for(int row = 0 ; row < sudoku.length ; row++){
            for(int col = 0 ; col < sudoku[row].length ; col++){
                if(sudoku[row][col] != 0){
                    if(!isSafe(sudoku,row,col,sudoku[row][col])){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int usedInRow(int[][] grid, int row, int num) {
        int count=0;
        for (int i = 0; i < grid.length; i++) {
            if (grid[row][i] == num) {
                count++;
            }
        }
        return count;
    }

    public static int usedIncol(int[][] grid, int col, int num) {
        int count=0;
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][col] == num) {
                count++;
            }
        }
        return count;
    }

    public static int usedInBox(int[][] grid, int row1Start, int col1Start, int num) {
        int count=0;
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                if (grid[row + row1Start][col + col1Start] == num) {
                    count++;
                }
        return count;

    }

    public static boolean isSafe(int[][] grid, int row, int col, int num) {//is it safe to place that number at that position, might not be correct nut just safe

        return (usedIncol(grid, col, num)==1 && usedInRow(grid, row, num)==1 && usedInBox(grid, row - row % 3, col - col % 3, num)==1);

    }
}
