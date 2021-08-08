package fun.fengwk.learning.algorithm.graph.undirected;

import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Function;

/**
 * 从格式文件中读取图
 * V  E
 * v1 w1
 * v2 w2
 *
 * @author fengwk
 */
public class UndirectedGraphReader<G extends UndirectedGraph> {

    private final Function<Integer, G> factory;

    public UndirectedGraphReader(Function<Integer, G> factory) {
        this.factory = factory;
    }

    public G read(String classpath) {
        return read(ClassLoader.getSystemResourceAsStream(classpath));
    }

    public G read(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        int V = scanner.nextInt();
        int E = scanner.nextInt();

        G g = factory.apply(V);
        for (int i = 0; i < E; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            g.addEdge(v, w);
        }

        return g;
    }

}
