package creeps;

import game.GameObject;

abstract class Attribute extends GameObject {
	protected CreepAttributes parent;
	abstract Attribute clone(CreepAttributes parent);
}
