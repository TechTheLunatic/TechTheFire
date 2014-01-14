package table;

import java.util.ArrayList;
import java.util.Iterator;

import robot.Orientation;
import smartMath.Vec2;
import container.Service;
import utils.*;

public class Table implements Service {

	// On met cette variable en static afin que, dans deux instances dupliquées, elle ne redonne pas les mêmes nombres
	private static int indice = 1;

	private Tree arrayTree[] = new Tree[4];
	private Torch arrayTorch[] = new Torch[2];
	private Fire arrayFire[] = new Fire[10];

	private ArrayList<Obstacle> listObstacles = new ArrayList<Obstacle>();
	private static ArrayList<Obstacle> listObstaclesFixes = new ArrayList<Obstacle>();
	private ObstacleCirculaire[] robots_adverses = new ObstacleCirculaire[2];
	
	private int hashFire;
	private int hashTree;
	private int hashTorch;
	private int hashObstacles;

	// Dépendances
	private Log log;
	private Read_Ini config;
	
	public Table(Log log, Read_Ini config)
	{
		this.log = log;
		this.config = config;
		
		initialise();
	}
	
	public void initialise()
	{
		// Initialisation des feux
		arrayFire[0] = new Fire(new Vec2(1485,1200), 0, 0, Orientation.XPLUS, Colour.YELLOW);
		arrayFire[1] = new Fire(new Vec2(1100,900), 1, 0, Orientation.YPLUS, Colour.YELLOW);
		arrayFire[2] = new Fire(new Vec2(600,1400), 2, 0, Orientation.XPLUS, Colour.YELLOW);
		arrayFire[3] = new Fire(new Vec2(600,400), 6, 0, Orientation.XPLUS, Colour.RED);
		arrayFire[4] = new Fire(new Vec2(200,15), 7, 0, Orientation.YPLUS, Colour.YELLOW);
		arrayFire[5] = new Fire(new Vec2(-200,15), 8, 0, Orientation.YPLUS, Colour.RED);
		arrayFire[6] = new Fire(new Vec2(-600,1400), 9, 0, Orientation.XPLUS, Colour.RED);
		arrayFire[7] = new Fire(new Vec2(-600,400), 13, 0, Orientation.XPLUS, Colour.RED);
		arrayFire[8] = new Fire(new Vec2(-1100,900), 14, 0, Orientation.YPLUS, Colour.RED);
		arrayFire[9] = new Fire(new Vec2(-1485,1200), 15, 0, Orientation.XPLUS, Colour.YELLOW);

		// Initialisation des arbres
		arrayTree[0] = new Tree();
		arrayTree[1] = new Tree();
		arrayTree[2] = new Tree();
		arrayTree[3] = new Tree();

		// Initialisation des torches
		Fire feu0 = new Fire(new Vec2(600,900), 3, 1, Orientation.GROUND, Colour.YELLOW);
		Fire feu1 = new Fire(new Vec2(600,900), 4, 2, Orientation.GROUND, Colour.RED);
		Fire feu2 = new Fire(new Vec2(600,900), 5, 3, Orientation.GROUND, Colour.YELLOW);
		arrayTorch[0] = new Torch(new Vec2(600,900), feu0, feu1, feu2);

		Fire feu3 = new Fire(new Vec2(-600,900), 10, 1, Orientation.GROUND, Colour.RED);
		Fire feu4 = new Fire(new Vec2(-600,900), 11, 2, Orientation.GROUND, Colour.YELLOW);
		Fire feu5 = new Fire(new Vec2(-600,900), 12, 3, Orientation.GROUND, Colour.RED);
		arrayTorch[1] = new Torch(new Vec2(-600,900), feu3, feu4, feu5); 
		
		// Ajout des torches mobiles
		listObstaclesFixes.add(new ObstacleCirculaire(new Vec2(600,900), 80));
		listObstaclesFixes.add(new ObstacleCirculaire(new Vec2(-600,900), 80));
		
		// Ajout des foyers
		listObstaclesFixes.add(new ObstacleCirculaire(new Vec2(1500,0), 250));
		listObstaclesFixes.add(new ObstacleCirculaire(new Vec2(0,950), 150));
		listObstaclesFixes.add(new ObstacleCirculaire(new Vec2(-1500,0), 250));

		// Ajout bacs
		listObstaclesFixes.add(new ObstacleRectangulaire(new Vec2(400,1700), 700, 300));
		listObstaclesFixes.add(new ObstacleRectangulaire(new Vec2(-1100,1700), 700, 300));

		// Ajout des arbres
		listObstaclesFixes.add(new ObstacleCirculaire(new Vec2(1500,700), 150));
		listObstaclesFixes.add(new ObstacleCirculaire(new Vec2(800,0), 150));
		listObstaclesFixes.add(new ObstacleCirculaire(new Vec2(-800,0), 150));
		listObstaclesFixes.add(new ObstacleCirculaire(new Vec2(-1500,700), 150));

		int rayon_robot_adverse = 230;
		try {
			rayon_robot_adverse = Integer.parseInt(config.get("rayon_robot_adverse"));
		}
		catch(Exception e)
		{
			log.warning(e, this);
		}
		robots_adverses[0] = new ObstacleCirculaire(new Vec2(0,0), rayon_robot_adverse);
		robots_adverses[1] = new ObstacleCirculaire(new Vec2(0,0), rayon_robot_adverse);
		
		hashFire = 0;
		hashTree = 0;
		hashTorch = 0;
		hashObstacles = 0;
	}
	
	/*
	 * Gestions des obstacles
	 */
	
	public void creer_obstacle(final Vec2 position)
	{
		Vec2 position_sauv = position.clone();
		int rayon_robot_adverse = 0;
		long duree = 0;
		try {
			rayon_robot_adverse = Integer.parseInt(config.get("rayon_robot_adverse"));
			duree = Integer.parseInt(config.get("duree_peremption_obstacles"));
		}
		catch(Exception e)
		{
			this.log.critical(e, this);
		}
		
		Obstacle obstacle = new ObstacleProximite(position_sauv, rayon_robot_adverse, System.currentTimeMillis()+duree);
		synchronized(listObstacles)
		{
			listObstacles.add(obstacle);
		}
		hashObstacles = indice++;
	}

	/**
	 * Appel fait lors de l'anticipation, supprime les obstacles périmés à une date future
	 * @param date
	 */
	public void supprimer_obstacles_perimes(long date)
	{
		Iterator<Obstacle> iterator = listObstacles.iterator();
		synchronized(listObstacles)
		{
			while ( iterator.hasNext() )
			{
			    Obstacle obstacle = iterator.next();
			    if (obstacle instanceof ObstacleProximite && ((ObstacleProximite) obstacle).death_date <= date)
			    {
			        iterator.remove();
					hashObstacles = indice++;
			    }
			}	
		}
	}
	
	/**
	 * Appel fait par le thread timer, supprime les obstacles périmés
	 */
	public void supprimer_obstacles_perimes()
	{
		supprimer_obstacles_perimes(System.currentTimeMillis());
	}

	/**
	 * Renvoie si un obstacle est à une distance inférieur à "distance" du point "centre_detection"
	 * @param centre_detection
	 * @param distance
	 * @return
	 */
	public boolean obstaclePresent(final Vec2 centre_detection, int distance)
	{
		Iterator<Obstacle> iterator = listObstacles.iterator();
		while ( iterator.hasNext() )
		{
		    Obstacle obstacle = iterator.next();
		    if (obstacle.position.SquaredDistance(centre_detection) < distance*distance)
		    	return true;
		}	

		return false;
	}

    public void deplacer_robot_adverse(int i, Vec2 position)
    {
    	robots_adverses[i].position = position.clone();
    }
	
    public Vec2[] get_positions_ennemis()
    {
    	Vec2[] positions =  new Vec2[2];
    	positions[0] = robots_adverses[0].position.clone();
    	positions[1] = robots_adverses[1].position.clone();
    	return positions;
    }
    
	// Feux
	
	public void pickFire (int id)
	{
		arrayFire[id].pickFire();
		hashFire = indice++;
	}

	public int nearestFire (Vec2 position)
	{
		int min = 0;
		for (int i = 0; i < 10; i++)
			if (arrayFire[i].getPosition().SquaredDistance(position) < arrayFire[min].getPosition().SquaredDistance(position))
				min = i;
		return min;
	}
	
	public void putFire (int id)
	{
		arrayFire[id].ejectFire();
		hashFire = indice++;
	}
	
	// Arbres
	
	public void pickTree (int id)
	{
		arrayTree[id].setTaken();
		hashTree = indice++;
	}
	
	public int nbrLeftTree(int id)
	{
		return arrayTree[id].nbrLeft();
	}
	
	public int nbrRightTree(int id)
	{
		return arrayTree[id].nbrRight();
	}
	
	public int nbrTotalTree(int tree_id)
	{
		return arrayTree[tree_id].nbrTotal();
	}
	
	public boolean isTreeTaken(int tree_id)
	{
		return arrayTree[tree_id].isTaken();
	}
		
	//Torches
	
	public int nearestTorch (Vec2 position)
	{
		if(arrayTorch[0].getPosition().SquaredDistance(position) < arrayTorch[1].getPosition().SquaredDistance(position))
			return 0;
		else
			return 1;
	}
			
	public void clone(Table ct)
	{
		if(ct.hashFire != hashFire)
		{
			for(int i = 0; i < 10; i++)
				arrayFire[i].clone(ct.arrayFire[i]);
			ct.hashFire = hashFire;
		}

		if(ct.hashTree != hashTree)
		{
			for(int i = 0; i < 4; i++)		
				arrayTree[i].clone(ct.arrayTree[i]);
			ct.hashTree = hashTree;
		}

		if(ct.hashTorch != hashTorch)
		{
			for(int i = 0; i < 2; i++)		
				arrayTorch[i].clone(ct.arrayTorch[i]);
			ct.hashTorch = hashTorch;
		}

		if(ct.hashObstacles != hashObstacles)
		{
			ct.listObstacles.clear();
			for(Obstacle item: listObstacles)
				ct.listObstacles.add(item.clone());
			ct.hashObstacles = hashObstacles;
		}
	}
	
	public Table clone()
	{
		Table cloned_table = new Table(log, config);
		clone(cloned_table);
		return cloned_table;
	}

}
