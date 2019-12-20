public class Solver {

    private Board board;

    public Solver(Board board) {
        this.board = board;
    }

    public Board solve() {
        grassImpossibleFields();
        return solveFrom(0);
    }

    private Board solveFrom(int xx) {
        if (board == null)
            return null;

        for (int x = xx; x < board.size; x++) {
            for (int y = 0; y < board.size; y++) {

                if (board.cells[x][y] == CellType.EMPTY) {
                    if (!canPlaceTent(x, y)) {
                        board.cells[x][y] = CellType.GRASS;
                        continue;
                    }
                    if (getMaxTentsInHorizontalLine(x, y) < board.tentsLimitHorizontal[y] || getMaxTentsInVerticalLine(x, y) < board.tentsLimitVertical[x]) {
                        return null;
                    }

                    Board board1 = board.copy();
                    board1.cells[x][y] = CellType.GRASS;
                    Solver s1 = new Solver(board1);
                    Board result1 = s1.solveFrom(x);
                    if (result1 != null)
                        return result1;

                    board.cells[x][y] = CellType.TENT;
                    board.tentsLimitVertical[x]--;
                    board.tentsLimitHorizontal[y]--;
                }

            }
            if (board.tentsLimitVertical[x] != 0)
                return null;
        }
        return isCorrect() ? board : null;
    }

    private int getMaxTentsInHorizontalLine(int from, int line) {
        int maxTents = 0;
        while (from < board.size) {
            if (board.cells[from][line] == CellType.EMPTY && canPlaceTent(from, line)) {
                maxTents++;
                from++;
            }
            from++;
        }
        return maxTents;
    }

    private int getMaxTentsInVerticalLine(int line, int from) {
        int maxTents = 0;
        while (from < board.size) {
            if (board.cells[line][from] == CellType.EMPTY && canPlaceTent(line, from)) {
                maxTents++;
                from++;
            }
            from++;
        }
        return maxTents;
    }

    private void grassImpossibleFields() {
        for (int x = 0; x < board.size; x++) {
            for (int y = 0; y < board.size; y++) {
                if (board.cells[x][y] == CellType.EMPTY && !canPlaceTent(x, y)) {
                    board.cells[x][y] = CellType.GRASS;
                }
            }
        }
    }

    private boolean isCorrect() {
        for (int x = 0; x < board.size; x++) {
            if (board.tentsLimitVertical[x] != 0 || board.tentsLimitHorizontal[x] != 0) {
                return false;
            }
        }
        for (int x = 0; x < board.size; x++) {
            for (int y = 0; y < board.size; y++) {
                if (board.cells[x][y] == CellType.TREE && !IsTentNearTree(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canPlaceTent(int x, int y) {
        if (x < 0 || y < 0 || x >= board.size || y > board.size || board.cells[x][y] != CellType.EMPTY || IsTentNearTent(x, y) || !IsTreeClose(x, y))
            return false;

        return board.tentsLimitVertical[x] > 0 && board.tentsLimitHorizontal[y] > 0;
    }

    private boolean IsTreeClose(int x, int y) {
        return isTreeOnPos(x - 1, y) || isTreeOnPos(x + 1, y) || isTreeOnPos(x, y - 1) || isTreeOnPos(x, y + 1);
    }

    private boolean IsTentNearTree(int x, int y) {
        return isTentOnPos(x - 1, y) || isTentOnPos(x + 1, y) || isTentOnPos(x, y - 1) || isTentOnPos(x, y + 1);
    }

    private boolean IsTentNearTent(int x, int y) {
        return isTentOnPos(x - 1, y) || isTentOnPos(x + 1, y) || isTentOnPos(x, y - 1) || isTentOnPos(x, y + 1) ||
                isTentOnPos(x - 1, y - 1) || isTentOnPos(x + 1, y - 1) || isTentOnPos(x - 1, y + 1) || isTentOnPos(x + 1, y + 1);
    }

    private boolean isTentOnPos(int x, int y) {
        return isTYPEOnPos(x, y, CellType.TENT);
    }

    private boolean isTreeOnPos(int x, int y) {
        return isTYPEOnPos(x, y, CellType.TREE);
    }

    private boolean isTYPEOnPos(int x, int y, CellType type) {
        return x >= 0 && x < board.size && y < board.size && y >= 0 && board.cells[x][y] == type;
    }
}
