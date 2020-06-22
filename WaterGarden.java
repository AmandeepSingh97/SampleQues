import java.util.*;

class cell{
	
	int x; // x coordinate of cell
	int y; // y coordinate of cell
	int time; //time at which cell was visited;
	char type; // type of cell
	
	cell(int x, int y, int time, char type){
		this.x = x;
		this.y = y;
		this.time = time;
		this.type = type;
	}
}

public class WaterGarden {
	
	// array for 4 adjacent neighbours: top right bottom left
	static int adj4x[] = {-1,0,1,0};
	static int adj4y[] = {0,1,0,-1};
	
	// array for 8 adjacent neighbours: top top-right right bottom-right bottom bottom-left left top-left
	static int adj8x[] = {-1,-1,0,1,1,1,0,-1};
	static int adj8y[] = {0,1,1,1,0,-1,-1,-1};
	
	public static boolean valid(int x, int y, int row, int col) {
		
		if(x>=row || x<0 || y>=col || y<0) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		Scanner st = new Scanner(System.in);
		int t = Integer.parseInt(st.nextLine());
		StringBuffer k = new StringBuffer();
		
		// for each test case
		while(t-->0) {
			String temp = st.nextLine();
			String arr[] = temp.split(" ");
			int row = Integer.parseInt(arr[0]);
			int col = Integer.parseInt(arr[1]);
			char matrix[][] = new char[row][col];
			int visited[][] = new int[row][col];
			Queue<cell> q = new LinkedList<cell>();
			
			// fill initial matrix with -1, to indicate not visited state
			for(int i=0;i<row;i++) {
				Arrays.fill(visited[i],-1);
			}
			
			// take matrix input and for each pump insert in BFS queue with visited time as 0
			for(int i=0;i<row;i++) {
				temp = st.nextLine();
				for(int j=0;j<col;j++) {
					matrix[i][j] = temp.charAt(j);
					// if it is a pump insert it to the bfs queue
					if(matrix[i][j] == '_') {
						q.add(new cell(i, j, 0, matrix[i][j]));
						visited[i][j] = 0;
					}
				}
			}
			
			// intialize shortest time as 0
			int shortestTime = 0;
			
			// START THE BFS
			while(q.size()>0) {
				cell curr = q.poll();
				int temp_x = curr.x;
				int temp_y = curr.y;
				switch(curr.type) {
					
					// if it is a pump water top, left, right, bottom grass or sand if present and add in queue
					case '_':
						for(int i=0;i<4;i++) {
							if(valid(temp_x + adj4x[i], temp_y + adj4y[i], row, col)) {
								if(visited[temp_x + adj4x[i]][temp_y + adj4y[i]]==-1) {
									visited[temp_x + adj4x[i]][temp_y + adj4y[i]] = curr.time + 1;
									if(matrix[temp_x + adj4x[i]][temp_y + adj4y[i]]=='*' || matrix[temp_x + adj4x[i]][temp_y + adj4y[i]]=='%') {
										q.add(new cell(temp_x + adj4x[i], temp_y + adj4y[i], curr.time+1, matrix[temp_x + adj4x[i]][temp_y + adj4y[i]]));
									}	
								}
							}
						}
						break;
					
					// if it is grass water all it's neighbour grass and sand and push them in queue
					case '*':
						for(int i=0;i<8;i++) {
							if(valid(temp_x + adj8x[i], temp_y + adj8y[i], row, col)) {
								if(visited[temp_x + adj8x[i]][temp_y + adj8y[i]]==-1) {
									visited[temp_x + adj8x[i]][temp_y + adj8y[i]] = curr.time + 1;
									if(matrix[temp_x + adj8x[i]][temp_y + adj8y[i]]=='*' || matrix[temp_x + adj8x[i]][temp_y + adj8y[i]]=='%') {
										q.add(new cell(temp_x + adj8x[i], temp_y + adj8y[i], curr.time+1, matrix[temp_x + adj8x[i]][temp_y + adj8y[i]]));
									}	
								}
							}
						}
						break;
					
					// if it is sand then water only the neighbours and do not psuh neighbours in queue
					case '%':
						for(int i=0;i<8;i++) {
							if(valid(temp_x + adj8x[i], temp_y + adj8y[i], row, col)) {
								if(visited[temp_x + adj8x[i]][temp_y + adj8y[i]]==-1) {
									visited[temp_x + adj8x[i]][temp_y + adj8y[i]] = curr.time + 1;
									// as it is sand do not add neighbours in queue for further water travel
								}
							}
						}
						break;
					
					// for cement no need to put a case as it does not allow water to seep through it
				}
			}
			
			// variable to check if all grass was watered, intially True
			boolean allGrassWater = true;
			
			// go through each cell and check if it is grass and if visited, take max of time
			for(int i=0;i<row;i++) {
				for(int j=0;j<col;j++) {
					
					if(matrix[i][j]=='*') {
						if(visited[i][j]==-1) {
							allGrassWater = false;
						}
						else {
							// maximum time for a grass patch by BFS method would be the time at which last grass
							// patch would have been given water
							shortestTime = Math.max(shortestTime, visited[i][j]);
						}
					}
				}
			}
			
			// if all grass not watered make shortest time as -1
			if(!allGrassWater) {
				shortestTime = -1;
			}
			
			// print in new line for each test case the result
			k.append(shortestTime+"\n");
		}
		
		System.out.println(k);
	}

}
