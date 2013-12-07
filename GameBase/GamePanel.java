package GameBase;

import java.util.List;

import Screen.GameScreen;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements Callback, SensorEventListener { // 게임 view 
	public final int FRAME_TIME = 33;

	GameScreen screen;

	Context mContext;
	SurfaceHolder mHolder;
	GameThread mThread;
	
	SensorManager sensorManager;

	public GamePanel(Context context) {
		super(context);
		Log.i("[panel]","생성");
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		mHolder = holder;
		mContext = context;
		mThread = null;
		screen = new GameScreen(mContext);
		sensorManager = (SensorManager)mContext.getSystemService(Context.SENSOR_SERVICE); //시스템서비스의 센서를 가져옴
		List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		
		if(sensors.size() > 0)
		{
			sensorManager.registerListener(this, sensors.get(0),SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	public void ClearThread(){
		mThread = null;
	}
	
	public boolean GetGameState(){
		return screen.GameEnd;
	}
	
	public void GameEnd(){
		//surfaceDestroyed(mHolder);
		//mThread.SetThreadState(false);
		//mThread.interrupt();
		//screen = new GameScreen(mContext);
	}

	public void start() { //thead start
		//if (null != mThread)
		//	stop();
		mThread = new GameThread();
		mThread.SetThreadState(true);
		mThread.start();
	}

	public void stop() throws InterruptedException { //thead stop
		
		mThread.join();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		start();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mThread.SetThreadState(false);
		ClearThread();
		/*try {
			stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	// ----------------------------Touch event 처리 영역
	// ---------------------------------------------------------------------------------

	public boolean onTouchEvent(MotionEvent event) {
		screen.onTouchEvent(event);

		return super.onTouchEvent(event);
	}

	// ---------------------------물리 버튼
	// 처리영역----------------------------------------------------------------------------------------

	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		screen.onKeyDown(KeyCode, event);
		
		return super.onKeyDown(KeyCode, event);
	}

	// ----------------------------여기서 부터는 스레드 영역
	// --------------------------------------------------------------------------
	class GameThread extends Thread {
		private boolean m_Run;
		
		public GameThread() {
			// TODO Auto-generated constructor stub
			Log.i("[thread]","생성");
			m_Run = true;
		}

		public void SetThreadState(boolean state) {
			m_Run = state;
		}

		public void run() { // loadingscreen 넣을 려면 screen 객체를 만들어서 screen으로 게임과 로딩 모두 받아서 처리가능 하도록 수정
			long startTime, endTime, toSleep;
			Log.i("[thread]","시작");
			while (m_Run) {
				Canvas canvas = null;
				try {
					canvas = mHolder.lockCanvas(null);
					synchronized (mHolder) {
						startTime = System.currentTimeMillis();

						if(screen.GameEnd)
							GameEnd();
						else
						{
							screen.drawScreen(canvas);//스크린 관련
							screen.update();
						}
						endTime = System.currentTimeMillis();

						toSleep = FRAME_TIME - (endTime - startTime);
						//Log.i("panel", "걸리는 시간 : " + (endTime - startTime));
						if (toSleep > 0) {

							Thread.sleep(toSleep);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (canvas != null) {
						mHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
			Log.i("[thread]","제거");
		}

	}

	//-----------------센서 처리 부분 ------------------------------------------------------
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		screen.onAccuracyChanged(sensor, accuracy);
	}

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		screen.onSensorChanged(event);
	}
}
