package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;

public class CowPopulationStats extends AbstractTreeNode {
	private CowPopulationManager pop;
	private String stat;
	
	public CowPopulationStats(String stat, CowPopulationManager pop) {
		this.pop = pop;
	}

	@Override
	public Double eval() {
		if ("max".equals(stat)) {
			return (double) pop.getPopMax();
		}
		
		if ("current".equals(stat)) {
			return (double)pop.getPop();
		}
		
		return 0.0;
	}

}
