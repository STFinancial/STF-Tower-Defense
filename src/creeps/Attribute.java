package creeps;

abstract class Attribute {
	protected CreepAttributes parent;
	abstract Attribute clone(CreepAttributes parent);
}
