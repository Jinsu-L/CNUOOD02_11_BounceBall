package GameBase;

public class GameManager {

	/*
	 * game start 클릭시 여기서 그다음부터 실행됨
	 */
	// 상수 정의 공간
	private final int STAGE_DEFAULT = 1;
	// ------------------------
	private static GameManager gameManager;
	private GUI gui;
	private KeyListener keyListener;
	private GameThread gameThread = new GameThread();
	private ResourceManager manager = ResourceManager.getInstance();
	private int CurrStage;

	private GameManager() {

	}

	public static GameManager getInstance() {
		if(gameManager == null) gameManager = new GameManager();
		return gameManager;
	}

	public void start() {
		init();
	}

	private void init() { // 처음 한번 초기화용
		gui = GUI.getInstance();
		keyListener = KeyListener.getInstance();
		CurrStage = STAGE_DEFAULT;
	}

	private void StageParsing() {
		manager.StageParsing(CurrStage);
		CurrStage++;		
	}

	public void GameRun() { // game start button click
		// parsing

		// thread run
		gameThread.ThreadStart();
	}
}
