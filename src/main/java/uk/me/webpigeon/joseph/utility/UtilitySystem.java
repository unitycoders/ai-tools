package uk.me.webpigeon.joseph.utility;

import java.util.ArrayList;
import java.util.List;

import uk.me.webpigeon.joseph.cow.Cow;

public class UtilitySystem {
	private List<Action> actions;
	
	public UtilitySystem() {
		this.actions = new ArrayList<>();
	}
	
	/**
	 * Find out what is most important at the moment.
	 */
	public Action process(Cow cow) {
		Action bestAction = null;
		double bestScore = -Double.MAX_VALUE;
		
		for (Action action : actions) {
			double score = action.getScore();
			
			System.out.println(action+" "+score);
			
			if (score > bestScore) {
				bestScore = score;
				bestAction = action;
			} else if (score == bestScore && Math.random() > 0.5) {
				bestScore = score;
				bestAction = action;
			}
		}
		
		return bestAction;
	}
	
	public void addAction(Action action) {
		actions.add(action);
	}
}
