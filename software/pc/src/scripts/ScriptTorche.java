package scripts;

import java.util.ArrayList;

import exception.MouvementImpossibleException;
import exception.SerialException;
import hook.HookGenerator;
import pathfinding.Pathfinding;
import robot.Robot;
import robot.RobotVrai;
import smartMath.Vec2;
import table.Table;
import utils.Log;
import utils.Read_Ini;

/**
 * Script de récupération de feux sur les torches mobiles et les feux debout
 * @author pf, krissprolls
 *
 */
public class ScriptTorche extends Script {

	public ScriptTorche(Pathfinding pathfinding, HookGenerator hookgenerator, Read_Ini config, Log log, RobotVrai robotvrai)
	{
		super(pathfinding, hookgenerator, config, log, robotvrai);
	}

	@Override
	public ArrayList<Integer> version(Robot robot, Table table) {
		// TODO
		ArrayList<Integer> versionList = new ArrayList<Integer>();
		//Les feux dans les torches
		//Ajouter une condition sur la présence de feux dans les torches
		versionList.add(0);
		versionList.add(1);
		//Les feux verticaux
		//Ajouter une condition pour chaque feu pour savoir s'il est toujours là ?
		versionList.add(2);
		versionList.add(3);
		versionList.add(4);
		versionList.add(5);
		versionList.add(6);
		versionList.add(7);
		versionList.add(8);
		versionList.add(9);
		versionList.add(10);
		versionList.add(11);
		return versionList;
	}
	
	@Override
	public Vec2 point_entree(int id) {
		//Les coordonnées ont été prises à partir du réglement
		if(id ==0)
			return new Vec2(-600,700);
		else if(id ==1)
			return new Vec2(600,700);
		else if(id ==2)
			return new Vec2(-1100, 700);
		else if(id ==3)
			return new Vec2(1100,700);
		else if(id ==4)
			return new Vec2(-400, 400);
		else if(id ==5)
			return new Vec2(400,400);
		else if(id ==6)
			return new Vec2(-1500,1000);
		else if(id ==7)
			return new Vec2(1500, 1000);
		else if(id ==8)
			return new Vec2(-200, 200);
		else if(id ==9)
			return new Vec2(200, 200);
		else if(id ==10)
			return new Vec2(-400, 1400);
		else if(id ==11)
			return new Vec2(400, 1400);
		else
			return null;		
	}

	@Override
	public int score(int id_version, Robot robot, Table table) {
		// Ici, se pose une question : doit-on, mettre les points potentiels ou les points effectifs
		//0, 1 ou 2?, moi (krissprolls) je laisserais en 0
		return 0;
	}

	@Override
	public int poids(Robot robot, Table table) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void execute(int id_version, Robot robot, Table table)
			throws MouvementImpossibleException {
		if(id_version ==0)
			//Vec2(-600,900)
		{
			robot.tourner((float)Math.PI/2);
		}
		else if(id_version ==1)
			//Vec2(600,900);
		{
			robot.tourner((float)Math.PI/2);
		}			
		else /*if(id_version ==2)
			//Vec2(-1100, 900);
		{
			robot.tourner((float)Math.PI/2);
		}	
		else if(id_version ==3)
			// Vec2(1100,900);
		{
			robot.tourner((float)Math.PI/2);
		}	
		else if(id_version ==4)
			// Vec2(-600, 400);
		{
			robot.tourner((float)Math.PI);
		}	
		else if(id_version ==5)
			// Vec2(600,400);
		{
			robot.tourner((float)Math.PI);
		}	
		else*/
		if(id_version ==6)
			// Vec2(-1500,1200);
		{
			robot.tourner((float)Math.PI);
		}	
		else if(id_version ==7)
			// Vec2(1500, 1200);
		{
			robot.tourner(0);
		}	
		else if(id_version ==8)
			// Vec2(-200, 0);
		{
			robot.tourner((float)(3*Math.PI/2));
		}	
		else if(id_version ==9)
			// Vec2(200, 0);
		{
			robot.tourner((float)(3*Math.PI/2));
		}
		/*
		else if(id_version ==10)
			// Vec2(-600, 1400);
		{
			robot.tourner((float)Math.PI);
		}	
		else if(id_version ==11)
			// Vec2(600, 1400);
		{
			robot.tourner((float)0);			
		}*/
		if(!robot.isTient_feu_gauche())
		{
			/*if(id_version == 2  && id_version == 3 && id_version == 4 && id_version == 5 && id_version == 10 && id_version == 11)
			{
				//Pour les feux à pousser
				/*
				try {
				robot.milieu_pince_gauche();
				robot.avancer(10);
				robot.avancer(-10);
				robot.baisser_pince_gauche();
				robot.ouvrir_pince_gauche();
				robot.avancer(10);
				robot.fermer_pince_gauche();
				robot.avancer(-10);
				} catch (SerialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else */
			if(id_version == 0 && id_version == 1)
			{
				//Pour les feux à ramasser dans les torches
				try {
				robot.ouvrir_pince_gauche();
				robot.milieu_pince_gauche();
				robot.fermer_pince_gauche();
				robot.lever_pince_gauche();
				} catch (SerialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(id_version == 6 && id_version == 7 && id_version == 8 && id_version ==9)
			{
				//Pour les feux à tirer
				try {
				robot.milieu_pince_gauche();
				robot.ouvrir_pince_gauche();
				robot.avancer(10);
				robot.fermer_pince_gauche();
				robot.avancer(-10);
				robot.ouvrir_pince_gauche();
				robot.baisser_pince_gauche();
				robot.avancer(5);
				robot.fermer_pince_gauche();
				robot.lever_pince_gauche();
				} catch (SerialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(robot.isFeu_tenu_droite_rouge())
		{
			if(id_version == 2  && id_version == 3 && id_version == 4 && id_version == 5 && id_version == 10 && id_version == 11)
			{
				try {
				robot.milieu_pince_droite();
				robot.avancer(10);
				robot.avancer(-10);
				robot.baisser_pince_droite();
				robot.ouvrir_pince_droite();
				robot.avancer(10);
				robot.fermer_pince_droite();
				robot.avancer(-10);
				} catch (SerialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			}
			else if(id_version == 0 && id_version == 1)
			{
				//Pour les feux à ramasser dans les torches
				try {
				robot.ouvrir_pince_droite();
				robot.milieu_pince_droite();
				robot.fermer_pince_droite();
				robot.lever_pince_droite();
				} catch (SerialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(id_version == 6 && id_version == 7 && id_version == 8 && id_version ==9)
			{
				try {
				robot.milieu_pince_droite();
				robot.ouvrir_pince_droite();
				robot.avancer(10);
				robot.fermer_pince_droite();
				robot.avancer(-10);
				robot.ouvrir_pince_droite();
				robot.baisser_pince_droite();
				robot.avancer(5);
				robot.fermer_pince_droite();
				robot.lever_pince_droite();
				} catch (SerialException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void termine(Robot robot, Table table) {

		try
		{
			robot.lever_pince_droite();
			robot.fermer_pince_droite();
			robot.lever_pince_gauche();
			robot.fermer_pince_gauche();
		}
		catch(SerialException e) {
			e.printStackTrace();
		}
	}

	@Override
	public float proba_reussite()
	{
		// TODO
		return 1;
	}

	public String toString()
	{
		return "ScriptTorche";
	}
	
}
