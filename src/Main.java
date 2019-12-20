import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final CellType e = CellType.EMPTY;
    private static final CellType t = CellType.TREE;

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("input32x32.txt"));

        String[] tentsHList = scanner.nextLine().split(" ");
        int[] tentsH = new int[tentsHList.length];
        for (int x = 0; x < tentsHList.length; x++) {
            tentsH[x] = Integer.parseInt(tentsHList[x]);
        }

        String[] tentsVList = scanner.nextLine().split(" ");
        int[] tentsV = new int[tentsVList.length];
        for (int x = 0; x < tentsVList.length; x++) {
            tentsV[x] = Integer.parseInt(tentsVList[x]);
        }

        List<String[]> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine().split(" "));
        }

        CellType[][] cells = parse(lines);

        scanner.close();

        Board b = new Board(
                tentsH,
                tentsV,
                cells
        );

        long start = System.currentTimeMillis();

        Solver s = new Solver(b);
        Board result = s.solve();

        if (result != null) {
            printResult(result.cells);
        } else {
            System.out.println("No solution");
        }

        long millis = System.currentTimeMillis() - start;

        System.out.println("Calculated in " + millis + " ms");

    }

    private static CellType[][] parse(List<String[]> list) {
        CellType[][] parsed = new CellType[list.size()][list.size()];

        for (int j = 0; j < list.size(); j++) {
            String[] strings = list.get(j);
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].equals("e")) {
                    parsed[i][j] = CellType.EMPTY;
                } else if (strings[i].equals("t")) {
                    parsed[i][j] = CellType.TREE;
                } else throw new IllegalArgumentException();
            }
        }

        return parsed;
    }

    private static void printResult(CellType[][] result) {
        for (int x = 0; x < result.length; x++) {
            for (int y = x; y < result.length; y++) {
                CellType temp = result[x][y];
                result[x][y] = result[y][x];
                result[y][x] = temp;
            }
        }
        for (CellType[] cellTypes : result) {
            System.out.println(Arrays.toString(cellTypes));
        }
        for (CellType[] t : result) {
            for (CellType x : t) {
                if (x == CellType.TENT) {
                    System.out.print("X");
                } else if (x == CellType.TREE) {
                    System.out.print("T");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

}
