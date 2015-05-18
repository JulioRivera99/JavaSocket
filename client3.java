import java.io.*; 
import java.net.*;

class client3 { 
    
    private static int[][]table_cost={{0,999,999,7},
                        			  {999,0,999,999},
                        			  {999,999,0,2},
                        			  {7,999,2,0}};
    private static int[][] cost1=new int[4][4];
    public static int edges[]=new int[5];
    public static int numofVertices = 4;
    public static final int MAX_VALUE = 999;
    
    public static void main(String argv[]) throws Exception { 
    	
    	System.out.println("Original Cost for Client at client3");
    	System.out.println("------------------------------------");
        for(int k = 0; k < 4; k++) {
        	for(int i = 0; i < 4; i++) {
        		System.out.print(table_cost[k][i] +"\t");    
        	}	
        	System.out.print("\n");
        }
        System.out.println("");
        
        try (Socket clientSocket = new Socket("afsaccess1.njit.edu",49953)) {
        	ObjectOutputStream  outToServer = new ObjectOutputStream(clientSocket.getOutputStream()); 
        	ObjectInputStream inObjectInput =new ObjectInputStream(clientSocket.getInputStream());
        	cost1 = (int [][])inObjectInput.readObject();
        	table_cost=update(table_cost,cost1);
        	int[] hold_row = new int[4];
        	for (int j = 0; j < 4; j++){
        		hold_row = BellmanFordEvaluation(j, table_cost);	
        		for(int i = 0; i < 4; i++) {
        			table_cost[j][i] = hold_row[i];
        		}
        	}
        	
        	System.out.println("After Connection to Server");
    		System.out.println("--------------------------");
        	for(int k = 0; k < 4; k++) {
        		for(int i = 0; i < 4; i++) {
        			System.out.print(table_cost[k][i] +"\t");    
        		}
        		System.out.print("\n");
        	}             
        	outToServer.writeObject(table_cost);
        	clientSocket.close();
        	
        } catch (UnknownHostException e) {
        	System.err.println("Cannot Find server");
            System.exit(1);
  
        } catch (IOException e) {
                System.err.println("I/O failure for server");
                System.exit(1);
            } 
    }    
 
    public static int[][] update(int[][]cost1, int[][]cost2) {
    	for(int i = 0; i < 4; i++) {
    		for(int k = 0; k < 4; k++) {
    			if(cost2[i][k] < cost1[i][k]) {
    				cost1[i][k] = cost2[i][k];
    			}
    		}
    	}
    	return cost1;
    }
    
    public static int[] BellmanFordEvaluation(int source, int adjacencymatrix[][]) {
        for (int node = 0; node < numofVertices; node++) {
            edges[node] = MAX_VALUE;
        }
        edges[source] = 0;
        for (int node = 0; node < numofVertices ; node++) {
            for (int sourcenode = 0; sourcenode < numofVertices; sourcenode++) {
                for (int destinationnode = 0; destinationnode < numofVertices; destinationnode++) {
                    if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                        if (edges[destinationnode] > edges[sourcenode] + adjacencymatrix[sourcenode][destinationnode]) {
                            edges[destinationnode] = edges[sourcenode] + adjacencymatrix[sourcenode][destinationnode];
                        }
                    }
                }
            }
        }
        
        for (int sourcenode = 0; sourcenode < numofVertices; sourcenode++) {
            for (int destinationnode = 0; destinationnode < numofVertices; destinationnode++) {
                if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                    if (edges[destinationnode] > edges[sourcenode] + adjacencymatrix[sourcenode][destinationnode]) {
                        System.out.println("The Graph contains negative egde cycle");
                    }
                }
            }
        }
        return edges;
    }
}