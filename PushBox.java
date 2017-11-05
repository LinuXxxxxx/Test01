import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class PushBox {
	//0:空地板   1:墙 2:箱子 3:目标 4:人
	static int[][] aroom = {	//TestCase
		{0,0,0,0,0,0},
		{1,1,0,4,0,0},
		{1,0,0,0,0,1},
		{0,0,1,0,0,0},
		{0,2,0,0,0,0},
		{0,0,1,3,0,0},
	};
	/*static int[][] aroom = {
		{4,1,0,0},
		{0,2,0,0},
		{1,0,3,0}
	};*/
	static boolean flag =true;
	public static int[][] SetRoom(int M,int N){		//初始化房间
		int MaxWall = (M+N)/2;		//墙壁最大数量
		int Wall = new Random().nextInt(MaxWall-1)+1;
		int room[][] = new int[9][9];
		for(int i=0;i<M;i++)		//设置空地板
			for(int j=0;j<N;j++){
				room[i][j]=0;
			}
		for(int i=0;i<Wall;i++){	//放置墙
			int row = new Random().nextInt(M);
			int col = new Random().nextInt(N);
			room[row][col]=1;
		}
		do{										//设置箱子				
			int row = new Random().nextInt(M-1);
			int col = new Random().nextInt(N-1);
			if(room[row][col] ==0){
				room[row][col] =2;
				flag = false;
			}
		}while(flag);
		do{										//设置箱子目的地
			flag =true;
			int row = new Random().nextInt(M); 
			int col = new Random().nextInt(N);
			if(room[row][col] ==0){
				room[row][col] = 3;
				flag = false;
			}
		}while(flag);
		do{										//设置搬运工
			flag =true;
			int row = new Random().nextInt(M);
			int col = new Random().nextInt(N);
			if(room[row][col] ==0){
				room[row][col] = 4;
				flag = false;
			}
		}while(flag);
		return room;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("两个正整数M，N(2<=M，N<=7):");
			int m = sc.nextInt();
			int n = sc.nextInt();
			int[][] map = new int[m][n];
			//map = SetRoom(m, n);
			map = aroom;
			//输出地图
			for(int i=0;i<m;i++){		
				for(int j=0;j<n;j++){
					System.out.print(map[i][j]+" ");
				}
				System.out.println();
			}
			//设置人\箱子的位置变量
			int peoplex = 0;
			int peopley = 0;
			int boxx = 0;
			int boxy = 0;
			//检测地图中的位置
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (map[i][j] == 4) {
						peoplex = i;
						peopley = j;
					}
					if (map[i][j] == 2) {
						boxx = i;
						boxy = j;
					}
				}
			}
			//开始阶段
			Stage begin = new Stage(peoplex, peopley, boxx, boxy);
			//进行路径搜索
			Stage result = BFS(begin, map, m, n);
			//如果没有路径则返回-1
			if (result == null) {
				System.out.println(-1);
			} else {
				int sum = 0;
				while (result.previous != null) {
					//输出路径
					//System.out.println(result);
					//当箱子坐标改变时计数+1
					if(result.boxx!=result.previous.boxx
							|| result.boxy!=result.previous.boxy)
						sum++;
					//进入下一节点
					result = result.previous;
				}
				System.out.println(sum);
			}
	}
	//广度优先搜索
	public static Stage BFS(Stage begin, int[][] map, int m, int n) {
		//定义Stage队列
		Queue<Stage> queue = new LinkedList<Stage>();
		//定义动态数组
		ArrayList<Stage> arrayList = new ArrayList<Stage>();
		//四个方向
		int[][] dir = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
		//将begin加入队列
		queue.offer(begin);
		while (!queue.isEmpty()) {
			Stage local = queue.remove();	//定义当前阶段位置,从队列中取出元素
			arrayList.add(local);			//将local加入数组
			//取不同方向,进行遍历
			for (int i = 0; i < dir.length; i++) {	
				Stage next = new Stage(local.peoplex + dir[i][0], local.peopley + dir[i][1]);	//定义下一步阶段位置
				next.previous = local;
				//判断是否有墙
				if (next.peoplex >= 0 && next.peoplex < m && next.peopley < n && next.peopley >= 0
						&& map[next.peoplex][next.peopley] != 1) {
					//人移动:判断下一步是否为箱子
					if (next.peoplex == local.boxx && next.peopley == local.boxy) {
						//是则箱子移动
						next.boxx = local.boxx + dir[i][0];
						next.boxy = local.boxy + dir[i][1];
					} else {
						//不是则箱子位置不变
						next.boxx = local.boxx;
						next.boxy = local.boxy;
					}
					//判断数组中是否有元素
					if (arrayList.contains(next))
						continue;
					//箱子移动:判断下一步是否为墙
					if (next.boxx >= 0 && next.boxx < m && next.boxy < n && next.boxy >= 0
							&& map[next.boxx][next.boxy] != 1) {
						//不是墙,则添加入队列和数组
						arrayList.add(next);
						queue.offer(next);
					} else {
						//是墙则进行下一步查找
						continue;
					}
					//System.out.println(next.previous);
					//如果到达目的地则返回Stage类
					if (map[next.boxx][next.boxy] == 3) { 
						return next;
					}//if:返回stage
				}//if:判断墙
			}//if:不同方向遍历
		}//while
		return null;
	}
}
