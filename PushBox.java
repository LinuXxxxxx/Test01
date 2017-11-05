import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class PushBox {
	//0:�յذ�   1:ǽ 2:���� 3:Ŀ�� 4:��
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
	public static int[][] SetRoom(int M,int N){		//��ʼ������
		int MaxWall = (M+N)/2;		//ǽ���������
		int Wall = new Random().nextInt(MaxWall-1)+1;
		int room[][] = new int[9][9];
		for(int i=0;i<M;i++)		//���ÿյذ�
			for(int j=0;j<N;j++){
				room[i][j]=0;
			}
		for(int i=0;i<Wall;i++){	//����ǽ
			int row = new Random().nextInt(M);
			int col = new Random().nextInt(N);
			room[row][col]=1;
		}
		do{										//��������				
			int row = new Random().nextInt(M-1);
			int col = new Random().nextInt(N-1);
			if(room[row][col] ==0){
				room[row][col] =2;
				flag = false;
			}
		}while(flag);
		do{										//��������Ŀ�ĵ�
			flag =true;
			int row = new Random().nextInt(M); 
			int col = new Random().nextInt(N);
			if(room[row][col] ==0){
				room[row][col] = 3;
				flag = false;
			}
		}while(flag);
		do{										//���ð��˹�
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
		System.out.println("����������M��N(2<=M��N<=7):");
			int m = sc.nextInt();
			int n = sc.nextInt();
			int[][] map = new int[m][n];
			//map = SetRoom(m, n);
			map = aroom;
			//�����ͼ
			for(int i=0;i<m;i++){		
				for(int j=0;j<n;j++){
					System.out.print(map[i][j]+" ");
				}
				System.out.println();
			}
			//������\���ӵ�λ�ñ���
			int peoplex = 0;
			int peopley = 0;
			int boxx = 0;
			int boxy = 0;
			//����ͼ�е�λ��
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
			//��ʼ�׶�
			Stage begin = new Stage(peoplex, peopley, boxx, boxy);
			//����·������
			Stage result = BFS(begin, map, m, n);
			//���û��·���򷵻�-1
			if (result == null) {
				System.out.println(-1);
			} else {
				int sum = 0;
				while (result.previous != null) {
					//���·��
					//System.out.println(result);
					//����������ı�ʱ����+1
					if(result.boxx!=result.previous.boxx
							|| result.boxy!=result.previous.boxy)
						sum++;
					//������һ�ڵ�
					result = result.previous;
				}
				System.out.println(sum);
			}
	}
	//�����������
	public static Stage BFS(Stage begin, int[][] map, int m, int n) {
		//����Stage����
		Queue<Stage> queue = new LinkedList<Stage>();
		//���嶯̬����
		ArrayList<Stage> arrayList = new ArrayList<Stage>();
		//�ĸ�����
		int[][] dir = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
		//��begin�������
		queue.offer(begin);
		while (!queue.isEmpty()) {
			Stage local = queue.remove();	//���嵱ǰ�׶�λ��,�Ӷ�����ȡ��Ԫ��
			arrayList.add(local);			//��local��������
			//ȡ��ͬ����,���б���
			for (int i = 0; i < dir.length; i++) {	
				Stage next = new Stage(local.peoplex + dir[i][0], local.peopley + dir[i][1]);	//������һ���׶�λ��
				next.previous = local;
				//�ж��Ƿ���ǽ
				if (next.peoplex >= 0 && next.peoplex < m && next.peopley < n && next.peopley >= 0
						&& map[next.peoplex][next.peopley] != 1) {
					//���ƶ�:�ж���һ���Ƿ�Ϊ����
					if (next.peoplex == local.boxx && next.peopley == local.boxy) {
						//���������ƶ�
						next.boxx = local.boxx + dir[i][0];
						next.boxy = local.boxy + dir[i][1];
					} else {
						//����������λ�ò���
						next.boxx = local.boxx;
						next.boxy = local.boxy;
					}
					//�ж��������Ƿ���Ԫ��
					if (arrayList.contains(next))
						continue;
					//�����ƶ�:�ж���һ���Ƿ�Ϊǽ
					if (next.boxx >= 0 && next.boxx < m && next.boxy < n && next.boxy >= 0
							&& map[next.boxx][next.boxy] != 1) {
						//����ǽ,���������к�����
						arrayList.add(next);
						queue.offer(next);
					} else {
						//��ǽ�������һ������
						continue;
					}
					//System.out.println(next.previous);
					//�������Ŀ�ĵ��򷵻�Stage��
					if (map[next.boxx][next.boxy] == 3) { 
						return next;
					}//if:����stage
				}//if:�ж�ǽ
			}//if:��ͬ�������
		}//while
		return null;
	}
}
