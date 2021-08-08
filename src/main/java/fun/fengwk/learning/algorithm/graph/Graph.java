package fun.fengwk.learning.algorithm.graph;

/**
 * 无向图
 *
 * @author fengwk
 */
public interface Graph {

    /**
     * 获取顶点数量
     *
     * @return
     */
    int V();

    /**
     * 获取边数
     *
     * @return
     */
    int E();

    /**
     * 检查是否存在顶点v与顶点w的边
     *
     * @param v
     * @param w
     * @return
     */
    boolean hasEdge(int v, int w);

    /**
     * 获取与顶点v相邻的所有顶点列表，如果没有则返回一个空列表
     *
     * @param v
     * @return
     */
    Iterable<Integer> adj(int v);

    /**
     * 检查顶点v的合法性，如果不合法将抛出异常
     *
     * @param v
     * @throws IllegalArgumentException 当顶点v不合法时将抛出该异常
     */
    void checkVertex(int v);

}
