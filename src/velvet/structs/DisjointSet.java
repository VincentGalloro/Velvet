package velvet.structs;

public class DisjointSet {

    private final int[] parents, ranks;

    public DisjointSet(int size){
        parents = new int[size];
        for(int i = 0; i < size; i++){ parents[i] = i; }
        ranks = new int[size];
    }

    private int find(int a){
        if(parents[a] != a){
            parents[a] = find(parents[a]);
        }
        return parents[a];
    }

    public boolean sameSet(int a, int b){
        return find(a) == find(b);
    }

    public boolean union(int a, int b){
        int ra = find(a);
        int rb = find(b);

        if(ra == rb){ return false; }

        if(ranks[rb] > ranks[ra]){
            int temp = ra;
            ra = rb;
            rb = temp;
        }

        parents[rb] = ra;
        if(ranks[ra] == ranks[rb]){
            ranks[ra]++;
        }

        return true;
    }
}
