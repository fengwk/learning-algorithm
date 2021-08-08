package fun.fengwk.learning.algorithm.graph.undirected;

import fun.fengwk.learning.algorithm.graph.Graph;

/**
 * 无向图
 *
 * @author fengwk
 */
public interface UndirectedGraph extends Graph {

    /**
     * 获取顶点v的度
     *
     * @param v
     * @return
     */
    int degree(int v);

    /**
     * 添加顶点v与顶点w的边
     *
     * @param v
     * @param w
     * @throws IllegalArgumentException 当传入顶点v或w不在当前图的顶点范围中抛出该异常
     * @throws IllegalStateException 当添加自环边或平行边时抛出该异常
     */
    void addEdge(int v, int w);

    /**
     * 删除边
     *
     * @param v
     * @param w
     * @throws IllegalArgumentException 当传入顶点v或w不在当前图的顶点范围中抛出该异常
     * @throws IllegalStateException 当不存在要删除的边时抛出该异常
     */
    void removeEdge(int v, int w);

}
