package uk.me.webpigeon.joseph.utility;

import java.awt.Graphics2D;

import uk.me.webpigeon.joseph.cow.Cow;
import uk.me.webpigeon.joseph.utility.trees.AbstractTreeNode;
import uk.me.webpigeon.joseph.utility.trees.TreeNode;
import uk.me.webpigeon.world.Entity;
import uk.me.webpigeon.world.World;

public class Action {
	private TreeNode<Double> utilCalc;
	
	public Action(TreeNode<Double> utilCalc) {
		this.utilCalc = utilCalc;
	}

	public void executeStep(Entity entity, World world) {
		
	}
	
	public boolean isComplete() {
		return true;
	}

	public double getScore() {		
		return utilCalc.eval();
	}

	public void notifyStarted(Entity cow) {
		
	}

	public void debugDraw(Graphics2D g) {		
	}
	
}
