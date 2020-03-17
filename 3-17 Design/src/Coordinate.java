public class Coordinate
{
	@Override
	public String toString()
	{
		return "Coordinate [x = " + x + ", y = " + y + "]";
	}
	
	public final int x;
	public final int y;
	
	public Coordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
