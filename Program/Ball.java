import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	private static final int upDeadLine = 80;
	private static final int downDeadLine = 320;
	private final int size = 10;	

	private int x;
	private int y;
	private double  time; // 시간
	private int force;
	private int v; // 기본 속도
	private int h; //  충돌위치에서부터 이동한 높이
	private int a; // 가속도
	private int count; // 벽튀긴수(연속으로 같은벽에 

튀지 못하게 위함)
	public Ball() {
		this(50,200);
	}
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.time=0;
		v = 40;
		h=340;
		a = -3; 
		count = 0;
	}
	public void draw(Graphics g) {
		g.setColor(Color.gray);
		g.fillOval(x, y, size, size);
	}
	public void downUp() {
	/*test*/
		if(y>330 && x<300) {
			up();
		}
		if(x > 290 && y>250){
			up();
		}
		if(x>280 && y>260){
			left();
		}
		if(x > 520){
			left();
		}
		if(y<150){
			down();
		}
		/*실제코드*/
		nothing();	
		x += force;
		
	}
	public void force(int force) {
		this.force = force;
		
	}
	public void update(Graphics g, int force) {
		force(force);
		draw(g);
	}
	
	public void increaseTime(){
		time += 0.1;		
	}
	public void zeroTime(){
		time = 0;
	}
	public void nothing(){
		y = h - (int)((v*time) + (a*time*time));
	}
	public void up(){
		this.h = y-size;
		zeroTime();
		count = 0;
		v = 40;
	}
	public void down(){
		this.h = y+size/2;
		v = -(int)(v+a*time);
		System.out.println(v);
		zeroTime();
	}
	public void left(){
		if(count != 1){
		this.h = y;
		if( time > (double)v/(-2.0*a) ) v = -

40;// 내려갈때 벽점할 경우
		force = -2;
		zeroTime();
		count = 1;
		}
		else{
			if(force == 1) force = 0;// 같은 

벽에 여러번 충돌 방지
		}
		
	}
	public void right(){
		if(count != -1){
			this.h = y;
			if( time > (double)v/(-2.0*a) ) v 

= -40;
			force = 2;
			zeroTime();
			count = -1;
			}
			else{
				if(force == 1) force = 0;
			}
	}
	public void jump(){
		this.h = y-size;
		zeroTime();
		count = 0;
		v = 80;
	}
	
}
