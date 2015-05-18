import java.io.*; 
import java.net.*; 

class server1 { 
    
    public static int[][]table_cost={{  0,  1,999,999},
        	 						 {  1,  0,1,999},
        	 						 {999,1,  0,999},
        	 						 {999,999,999,  0}};
    public static int i = 0;
    public static int socket = 49953;
    public static int[][] cost=new int[4][4];
    public static int edges[]=new int[5];
    public static int numofVertices = 4;
    public static final int MAX_VALUE = 999;
        
    public static void main(String argv[]) throws Exception { 
    	
    	System.out.println("Original Cost for Server at server1");
		System.out.println("------------------------------------");
    	for(int k = 0; k < 4; k++){
    		for(int i = 0; i < 4; i++){
    			System.out.print(table_cost[k][i] +"\t");    
    		}
    		System.out.print("\n");
    	}
    	
    	while(true) {
    		System.out.println("");
    		ServerSocket welcomeSocket = new ServerSocket(socket); 
    		Socket connectionSocket = welcomeSocket.accept();       
    		ObjectOutputStream  outToClient = new ObjectOutputStream(connectionSocket.getOutputStream()); 
    		ObjectInputStream inObjectInput=new ObjectInputStream(connectionSocket.getInputStream());
    		outToClient.writeObject(table_cost); 
    		cost = (int [][])inObjectInput.readObject();
    		table_cost=update(table_cost,cost);
    		
    		System.out.println("After Connection to Client" + (i + 1));
    		System.out.println("--------------------------");
        	for(int k = 0; k < 4; k++) {
        		for(int i = 0; i < 4; i++) {
        			System.out.print(table_cost[k][i] +"\t");    
        		}
        		System.out.print("\n");
        	}
        	i++;
        	
        	welcomeSocket.close(); 
        	connectionSocket.close(); 
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
                        if (edges[destinationnode] > edges[sourcenode] + adjacencymatrix[sourcenode][destinationnode])
                            edges[destinationnode] = edges[sourcenode] + adjacencymatrix[sourcenode][destinationnode];
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