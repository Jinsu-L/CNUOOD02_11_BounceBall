package GameBase;

public class GameManager {

	/*
	 * game start Ŭ���� ���⼭ �״������� �����
	 */
	// ��� ���� ����
	private final int STAGE_DEFAULT = 1;
	// ------------------------

	private GUI gui;
	private KeyListener keyListener;
	private GameThread gameThread = new GameThread();
	private ResourceManager manager = new ResourceManager();
	private int CurrStage;

	private GameManager() {

	}

	public static GameManager getInstance() {
		return new GameManager();
	}

	public void start() {
		init();
	}

	private void init() { // ó�� �ѹ� �ʱ�ȭ��
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