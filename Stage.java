public class Stage{
	public int peoplex;	//人:x
	public int peopley;	//人:x
	public int boxx;	//箱子:x
	public int boxy;	//箱子:x
	Stage previous;		//上一步
	
	public Stage(int peoplex, int peopley) {
		this.peoplex = peoplex;
		this.peopley = peopley;
	}

	public Stage(int peoplex, int peopley, int boxx, int boxy) {
		this.peoplex = peoplex;
		this.peopley = peopley;
		this.boxx = boxx;
		this.boxy = boxy;
	}

	@Override
	public String toString() {
		return "people:("+this.peoplex+","+this.peopley+")  box:("+this.boxx+","+this.boxy+")";
	}

	public boolean equals(Object obj) {
		if (obj instanceof Stage) {
			if (((Stage) obj).peoplex==peoplex&&((Stage) obj).peopley==peopley&&((Stage) obj).boxx==boxx&&((Stage) obj).boxy==boxy)return true;
		}
		return false;
	}
}