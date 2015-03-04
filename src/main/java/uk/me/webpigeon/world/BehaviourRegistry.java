package uk.me.webpigeon.world;

import uk.me.webpigeon.behaviour.TreeNode;

import java.util.HashMap;

/**
 * Created by Piers on 03/03/2015.
 */
public class BehaviourRegistry {

    private static final HashMap<String, TreeNode> behavioursByType = new HashMap<>();

    public static void registerBehaviour(String uniqueName, TreeNode behaviour){
        if(behavioursByType.containsKey(uniqueName)) throw new IllegalArgumentException("This unique reference string has already been used");
        behavioursByType.put(uniqueName, behaviour);
    }

    public static TreeNode getBehaviour(String uniqueName){
        if(behavioursByType.containsKey(uniqueName)) return behavioursByType.get(uniqueName);
        throw new IllegalArgumentException("No behaviour has been registered with this name");
    }
}
