package uk.me.webpigeon.joseph.cow;

import uk.me.webpigeon.joseph.CowPopulationManager;
import uk.me.webpigeon.joseph.utility.UtilTreeNode;

public class CowPopulationStats extends UtilTreeNode {
	private CowPopulationManager pop;
	private String stat;
	
	public CowPopulationStats(String stat, CowPopulationManager pop) {
		this.pop = pop;
	}

	@Override
	public Double eval(Cow cow) {
		if ("max".equals(stat)) {
			return (double) pop.getPopMax();
		}
		
		if ("current".equals(stat)) {
			return (double)pop.getPop();
		}
		
		return 0.0;
	}

}
