package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class tree_height {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
    
    class Node {
        int key;
        List<Node> children;
        
        Node(int key){
            this.key = key;
            this.children = new ArrayList<>();
        }
        
        public void addChild(Node child) {
            children.add(child);
        }
    }

    public class TreeHeight {
        int n;
        int parent[];
        
        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = in.nextInt();
            }
        }

        int computeHeightNaive() {
                        // Replace this code with a faster implementation
            int maxHeight = 0;
            for (int vertex = 0; vertex < n; vertex++) {
                int height = 0;
                for (int i = vertex; i != -1; i = parent[i])
                    height++;
                maxHeight = Math.max(maxHeight, height);
            }
            return maxHeight;
        }
        
        Node buildTree() {
            int root = -1;
            Node[] nodes = new Node[n];
            
            for (int i = 0; i < n; i++) {
                nodes[i] = new Node(i);
            }
            
            for (int child_index = 0; child_index < n; child_index++) {
                int parent_index = parent[child_index];
                if (parent_index == -1) {
                    root = child_index;
                } else
                    nodes[parent_index].addChild(nodes[child_index]);
            }
            
            return nodes[root];
        }
        
        int computeHeightRecursion() {
            Node rootNode = buildTree();
            return computeHeightHelp(rootNode);

        }

        private int computeHeightHelp(Node rootNode) {
            if (rootNode.children.isEmpty()) {
                return 1;
            } else {
                int maxSubHeight = 1;
                for (Node node: rootNode.children) {
                    int subHeight = computeHeightHelp(node);
                    if (subHeight > maxSubHeight)
                        maxSubHeight = subHeight;
                }
                return 1 + maxSubHeight;
            }
        }
        
        int computeHeight() {
            Node rootNode = buildTree();

            Queue<Node> nodeQueue = new LinkedList<>();
            nodeQueue.add(rootNode);
            
            int height = 0;
            while (!nodeQueue.isEmpty()) {
                int nodeCount = nodeQueue.size();
                
                if (nodeCount == 0) {
                    return height;
                } else {
                    height++;
                }
                
                while (nodeCount > 0) {
                    Node node = nodeQueue.poll();
                    nodeQueue.addAll(node.children);
                    nodeCount--;
                }
                
            }
            return height;
        }
    }

    static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new tree_height().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}
