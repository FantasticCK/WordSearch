package com.CK;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        char[] a = new char[]{'A', 'B', 'C', 'E'};
        char[] b = new char[]{'S', 'F', 'E', 'S'};
        char[] c = new char[]{'A', 'D', 'E', 'E'};

        char[][] board = new char[3][4];
        board[0] = a;
        board[1] = b;
        board[2] = c;

        Solution solution = new Solution();
//        System.out.println(solution.exist(board, "ABCB"));
        System.out.println(solution.exist(board, "ABCE"));
//        System.out.println(solution.exist(board, "ABCEFSADEESE"));

    }
}

class Solution {
//    boolean[][] visited;
    boolean[][] recur;
    Stack<int[]> dfs;
    int[] directX = {0, 1, -1, 0};
    int[] directY = {1, 0, 0, -1};

    public boolean exist(char[][] board, String word) {
        if (board.length == 0 || board[0].length == 0 || word.length() == 0) return false;
        boolean res = false;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == word.charAt(0)) {
//                    visited = new boolean[board.length][board[0].length];
                    recur = new boolean[board.length][board[0].length];
                    dfs = new Stack<>();
                    int[] start = new int[]{r, c};
                    dfs.push(start);
                    res = res || dfs(board, word.substring(1), res);
                }
            }
        }

        return res;
    }

    private boolean dfs(char[][] board, String newWord, boolean res) {
        if (newWord.length() == 0) return true;
        while (!dfs.isEmpty()) {
            int[] cord = dfs.pop();
            recur[cord[0]][cord[1]] = true;
            for (int d = 0; d < 4; d++) {
                int[] nextCord = {cord[0] + directX[d], cord[1] + directY[d]};
                if (nextMoveIsValid(board, nextCord, newWord) && !recur[nextCord[0]][nextCord[1]]) {
                    dfs.push(nextCord);
                    recur[nextCord[0]][nextCord[1]] = true;
                    res = res || dfs(board, newWord.substring(1), res);
                    if (res) return res;
                    else recur[nextCord[0]][nextCord[1]]=false;
                }
            }

        }
        return res;
    }

    private boolean nextMoveIsValid(char[][] board, int[] nextCord, String newWord) {
        return nextCord[0] >= 0 && nextCord[0] < board.length && nextCord[1] >= 0 && nextCord[1] < board[0].length && board[nextCord[0]][nextCord[1]] == newWord.charAt(0);
    }
}


// 修改原board, 然后回溯
class Solution2 {
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }

        int rows = board.length;
        int cols = board[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Check if there is a word begin from i,j.
                if (dfs(board, word, i, j, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean dfs(char[][] board, String word, int i, int j, int index) {
        int len = word.length();
        if (index >= len) {
            return true;
        }

        // Check the input parameter.
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return false;
        }

        // If the current character is right.
        if (word.charAt(index) != board[i][j]) {
            return false;
        }

        char tmp = board[i][j];
        board[i][j] = '#';

        boolean ret = dfs(board, word, i + 1, j, index + 1)
                || dfs(board, word, i - 1, j, index + 1)
                || dfs(board, word, i, j + 1, index + 1)
                || dfs(board, word, i, j - 1, index + 1);

        // backtrace.
        board[i][j] = tmp;
        return ret;
    }
}