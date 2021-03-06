package games.tncyBall;

import app.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class World extends AppWorld {

	private Field field;

	private int width;
	private int height;

	private Font font;
	private int scorePlayer1;
	private int scorePlayer2;

	private Audio soundMusicBackground;
	private float soundMusicBackgroundPos;

	public World(int ID) {
		super(ID);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
		soundMusicBackground = AppLoader.loadAudio("/musics/tncyBall/crowd.ogg");
		font = AppLoader.loadFont("/fonts/vt323.ttf", AppFont.BOLD, 60);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		AppInput input = (AppInput)container.getInput();
		if (input.isButtonPressed(AppInput.BUTTON_START)) {
			this.setState(1);
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		field.update(container, game, delta);
		if (scorePlayer1 == 10 || scorePlayer2 == 10) {
			this.setState(3);
			game.enterState(3, new FadeOutTransition(), new FadeInTransition());
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */

		//le tour du terrain
		context.setColor(new Color(102, 111, 69));
		context.fillRect(0, 0, getWidth(), getHeight());

		field.render(container, game, context);

		//scores
		context.setFont(this.font);
		context.setColor(Color.white);
		context.drawString(Integer.toString(scorePlayer1), field.getCenterX() - 35, 10);
		context.drawString("-", field.getCenterX() - 10, 10);
		context.drawString(Integer.toString(scorePlayer2), field.getCenterX() + 15, 10);
	}

	public void play (GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		soundMusicBackground.playAsMusic(1, 2f, true);
		this.width = container.getWidth ();
		this.height = container.getHeight ();
		field = new Field(this);
		resetScore();
	}

	public void pause(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
		soundMusicBackgroundPos = soundMusicBackground.getPosition();
		soundMusicBackground.stop();
	}

	public void resume(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
		soundMusicBackground.playAsMusic(1, 2f, true);
		soundMusicBackground.setPosition(soundMusicBackgroundPos);
	}

	public void stop(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
		soundMusicBackground.stop();
		resetScore();
	}

	public void addScore(int team) {
		if(team == 0)
			scorePlayer1++;
		else
			scorePlayer2++;
	}

	public void resetScore() {
		scorePlayer1 = 0;
		scorePlayer2 = 0;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
