package hook;

import java.util.ArrayList;

import robot.Robot;
import utils.Log;
import utils.Read_Ini;

/**
 * Classe-mère abstraite des hooks, utilisés pour la programmation évènementielle
 * @author pf
 *
 */

abstract public class Hook {

	private ArrayList<Callback> callbacks = new ArrayList<Callback>();
	
	protected Read_Ini config;
	protected Log log;

	public Hook(Read_Ini config, Log log)
	{
		this.config = config;
		this.log = log;
	}
	
	/**
	 * On peut ajouter un callback à un hook.
	 * Il n'y a pas de méthode pour en retirer, car il n'y en a a priori pas besoin
	 * @param callback
	 */
	public void ajouter_callback(Callback callback)
	{
		callbacks.add(callback);
	}
	
	/**
	 * Quand un hook est déclenché, tous ses callbacks sont exécutés
	 */
	protected void declencher()
	{
		for(Callback callback : callbacks)
			callback.appeler();
	}

	/**
	 * Méthode qui sera surchargée par les classes filles.
	 * Elle contient la condition d'appel du hook
	 * @param robot
	 */
	public abstract void evaluate(final Robot robot);

}

