package uk.me.webpigeon.joseph.utility;

import java.util.ArrayList;
import java.util.List;

public class UtilitySystem {
	private List<Need> factors;
	
	public UtilitySystem() {
		this.factors = new ArrayList<>();
	}
	
	/**
	 * Find out what is most important at the moment.
	 */
	public Action process(List<Action> actions) {
		Action bestAction = null;
		double bestScore = 0;
		
		for (Action action : actions) {
			double score = getScore(action);
			
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
	
	/**
	 * Get the mean score for all factors.
	 * 
	 * @param action the action to evaluate
	 * @return the score for this action
	 */
	public double getScore(Action action){
		if (factors.isEmpty()) {
			return 0;
		}
		
		double total = 0;
		for (Need factor : factors) {
			total += factor.getValue(action);
		}
		
		return total / factors.size();
	}
}
