package hook.methodes;

import robot.Robot;
import hook.Executable;

/**
 * Classe implémentant la méthode de leverrateau, utilisée lors du ramassage des fruits
 * @author pf
 *
 */

public class LeverRateau implements Executable  {

	private boolean coteDroit;
	private Robot robot;
	
	public LeverRateau(Robot robot, boolean coteDroit)
	{
		this.robot = robot;
		this.coteDroit = coteDroit;
	}
	
	@Override
	public void execute()
	{
		robot.remonter_rateau(coteDroit);
	}	
	
}
