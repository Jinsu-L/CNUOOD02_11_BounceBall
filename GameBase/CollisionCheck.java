package GameBase;

import android.util.Log;
import GameResouce.Bullet;
import GameResouce.Enemy;
import GameResouce.Missile;
import GameResouce.Player;
import Screen.GameScreen;

public class CollisionCheck { // 충돌 체크용 클래스 -미완 에너미 클래스랑 플레이어 클래스 기본적인 패턴을 완성하고
								// 나서 할 예정
	// 에너미 완성되면 충돌 클래스 완성;
	
	private final int dis = 100;
	
	public void check() {
		Player p = GameScreen.player; // 플레이어
		// 미사일 충돌
		// Log.i("[체크]", "충돌 체크중");
		for (int i = 0; i < GameScreen.bullets.size(); i++) {
			Bullet m = (Bullet) GameScreen.bullets.get(i);
			if (GameScreen.bullets.get(i).from() == 1) // 플레이어 가 쏜 총
				for (int j = 0; j < GameScreen.enemys.size(); j++) {
					Enemy e = (Enemy) GameScreen.enemys.get(j);
					// Log.i("[체크]", "충돌 체크중 내 총알");
					//Log.i("[체크]", "" + m.bX +" : " + (e.eX + e.width) +" : " +( m.bX + m.width) +" : " + e.eX
					//		+" : " + m.bY +" : " +( e.eY + e.height) +" : " +( m.bY + m.height) +" : " + e.eY);
					//Log.i("[enemy]","오른쪽끝 : " + (e.eX + e.width) + "왼쪽 끝 : " + e.eX);
					if (m.bX < e.eX + e.width && m.bX + m.width > e.eX
							&& m.bY < e.eY + e.height && m.bY + m.height > e.eY) {
						e.setDead(true);
						//e.Hit();
						m.setDead(true);
						e.ExplosionOn();
						Log.i("[타격]", " : ");
						/*Log.i("[타격]", "" + m.bX +" : " + e.eX + e.width +" : " + m.bX + m.width +" : " + e.eX
								+" : " + m.bY +" : " + e.eY + e.height +" : " + m.bY + m.height +" : " + e.eY);*/
					}
					/*if(GetDistance(e.X, e.Y, m.X, m.Y) < dis)
					{
						e.setDead(true);
						m.setDead(true);
						Log.i("[체크]", "타격 내 총알");
					}*/
				}
			else if (GameScreen.bullets.get(i).from() == 2) { // 적이 쏜 총 (플레이어 죽음 설정)
				// Log.i("[체크]", "충돌 체크중 적 총알");
				if (m.bX < p.X + p.width && m.bX + m.width > p.X
						&& m.bY < p.Y + p.height && m.bY + m.height > p.Y) {
					m.setDead(true);
					p.Hit();
					//Log.i("[죽음]", "플레이어"); //아직 end가 구현이 않되어서 로그로 띄움;
				}
			}
		}
		
		for(int i = 0; i < GameScreen.enemys.size();i++){
			Enemy e = (Enemy)GameScreen.enemys.get(i);
			if (e.X < p.X + e.width && e.X + e.width > p.X
					&& e.Y < p.Y + p.height && e.Y + e.height > p.Y) {
				e.setDead(true);
				e.ExplosionOn();
				p.Hit();
			}
		}

		// 미사일 플로우 체크 추가
		for(int i = 0; i < GameScreen.missiles.size();i++){
			Missile m = (Missile)GameScreen.missiles.get(i);
		}
	}

	//충돌 처리 할때 거리로 처리 해 볼까 해서 만들어 둔 것,현재는 이미지 크기로 충돌 판정함;
	public int GetDistance(int eX,int eY,int bX,int bY){
		return (int) Math.abs((bY-eY)*(bY-eY)+(bX-eX)*(bX-eX));
	}

}
