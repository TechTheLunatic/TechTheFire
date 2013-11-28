package robot;

import smartMath.Vec2;


public class RobotVrai extends Robot {

	private Vec2 position = new Vec2(0,0);
	private float orientation = 0;

	private Vec2 consigne = new Vec2(0,0);
	private float orientation_consigne = 0;
	
	private boolean blocage = false;
	private boolean enMouvement = true;
	
	private boolean marche_arriere = false;
	private boolean effectuer_symetrie = true;
	
	public boolean pret = false;
	
	public int sleep_milieu_boucle_acquittement; // = self.config["sleep_acquit_serie"]
	public int sleep_fin_boucle_acquittement = 0;
	
	// Constructeur
	
	public RobotVrai()
	{
		
	}
	
	/*
	 * MÉTHODES PUBLIQUES
	 */
	
	public void stopper()
	{
		
	}
	public void avancer()
	{
		
	}
	public void correction_angle()
	{
		
	}
	public void tourner()
	{
		
	}
	public void suit_chemin()
	{
		
	}
	public void va_au_point()
	{
		
	}
	public void set_vitesse_translation()
	{
		
	}
	public void set_vitesse_rotation()
	{
		
	}
	
	/**
	 * UTILISÉ UNIQUEMENT PAR LE THREAD DE MISE À JOUR
	 */
	
	public void update_x_y_orientation()
	{
		
	}
	
	/* 
	 * GETTERS & SETTERS
	 */
	
	public Vec2 getPosition() {
		return this.position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
		//deplacements.set_x(position.getX());
		//deplacements.set_y(position.getY());
	}

	public float getOrientation() {
		return orientation;
	}

	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}

	public Vec2 getConsigne() {
		return consigne;
	}

	public void setConsigne(Vec2 consigne) {
		this.consigne = consigne;
	}

	public boolean isBlocage() {
		return blocage;
	}

	public void setBlocage(boolean blocage) {
		this.blocage = blocage;
	}

	public boolean isEnMouvement() {
		return enMouvement;
	}

	public void setEnMouvement(boolean enMouvement) {
		this.enMouvement = enMouvement;
	}

	public boolean isMarche_arriere() {
		return marche_arriere;
	}

	public void setMarche_arriere(boolean marche_arriere) {
		this.marche_arriere = marche_arriere;
	}

	public boolean isEffectuer_symetrie() {
		return effectuer_symetrie;
	}

	public void setEffectuer_symetrie(boolean effectuer_symetrie) {
		this.effectuer_symetrie = effectuer_symetrie;
	}
	
	/*
	 * MÉTHODES PRIVÉES
	 */
	
}
