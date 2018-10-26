package verticalPlateformer;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import verticalPlateformer.bonuses.Weightlessness;

public class BonusGen {

	private World world;
	private Random rng;
	private float count;

	public BonusGen (GameContainer container, StateBasedGame game) {
		this.world = (World) game.getCurrentState();
		this.rng = new Random ();
		this.count = 0;
	}

	public void update (GameContainer container, StateBasedGame game, int delta) {
		int score = 0;
		for (Player player: this.world.getPlayers ()) {
			score = Math.max (player.getScore (), score);
		}
		if (score > this.count) {
			int w = container.getWidth ();
			int h = container.getHeight ();
			float x = this.rng.nextInt (w);
			float y = this.count + (this.rng.nextInt (h * 7) + h) / 2;
			float r = 60;
			Bonus bonus = null;
			switch (this.rng.nextInt (1)) {
				case 0:
					bonus = new Weightlessness (x, -y, r);
			}
			this.count = y;
			world.addBonus (bonus);
		}
	}

}