import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Pravidlo {

	private String nazov;
	private List<Veta> podmienky;
	private List<Veta> nasledky;
	private String doplneny_text;
	private Boolean test[];
	private HashMap hm = new HashMap();


	public Pravidlo(String nazov_i,List<Veta> podmienky_i,List<Veta>nasledky_i)
	{
		nazov=nazov_i;
		podmienky=podmienky_i;
		nasledky=nasledky_i;	
		zistiparam();
	}

	public Pravidlo(Pravidlo p)
	{
		nazov=p.nazov;
		podmienky=p.podmienky;
		nasledky=p.nasledky;	
		hm.putAll(p.hm);
	}





	public void zistiparam()
	{
		for (int i = 0;i<podmienky.size();i++)     // kazda podmienka
		{
			for (int o=0;o< (podmienky.get(i).getZ_slov().size());o++)  ///kazde slovo
			{
				if (podmienky.get(i).getZ_slov().get(o).charAt(0)=='?')
				{
					char temp = podmienky.get(i).getZ_slov().get(o).charAt(1);
					if (hm.containsKey(temp)==false)
					{
						hm.put(temp, new String (""));

					}


				}
			}
		}
	}

	public boolean je_rovnake(Pravidlo p,int podmienka)
	{
		Pravidlo p_nove=null;
		if (p.nazov.equals("Surodenci:"))
		{ System.out.println("aa");}

		for (int i=0;i<Main.fakty.size();i++)  //kazdy fakt
		{
			boolean test = true;
			if (Main.fakty.get(i).getZ_slov().size()==p.getPodmienky().get(podmienka).getZ_slov().size()); // ak fakt a podmienka ma rovnaku dlzku slov
			{
				Veta temp = p.getPodmienky().get(podmienka); 
				// kotrola zhody 
				p_nove = new Pravidlo(p);
				if (temp.getZ_slov().get(0).equals("<>"))
				{




					for (int param=0;param< temp.getParam().size()-1;param++) /// kazdy parameter z vety
					{
						char pis_par = temp.getZ_slov().get(temp.getParam().get(param)).charAt(1);
						for (int paramcomper=param+1;paramcomper< temp.getParam().size();paramcomper++)
						{
							char pis_pacomp = temp.getZ_slov().get(temp.getParam().get(paramcomper)).charAt(1);
							if (p_nove.getHm().get(pis_par).toString().equals( p_nove.getHm().get(pis_pacomp).toString()))
							{test=false;}
						}
					}
					if (test == true )
					{
						if (podmienka+1 == p.getPodmienky().size())
						{
							Main.moznosti.add(p_nove);
							return true;
						}
						else
						{
							je_rovnake(p_nove,podmienka+1);
						}
					}
				}
				else if (Main.kontrola(Main.fakty.get(i),temp))    // kontrola ci ozaj su rovnake 
				{	



					for (int param=0;param< temp.getParam().size();param++) /// kazdy parameter z vety
					{


						char pis_par = temp.getZ_slov().get(temp.getParam().get(param)).charAt(1);


						if (p_nove.getHm().get(pis_par).toString().equals(""))   			/// ak premmena nieje nastavena
						{
							p_nove.getHm().replace(pis_par, Main.fakty.get(i).getZ_slov().get(temp.getParam().get(param)));
						}
						else  // ak premenna je nastavena ovej ci je spravne 
						{
							if (Objects.equals(p_nove.getHm().get(pis_par), Main.fakty.get(i).getZ_slov().get(temp.getParam().get(param)))== false)
							{ 
								test= false;
							}

						}
					}

					if (test == true )
					{
						if (podmienka+1 == p.getPodmienky().size())
						{
							Main.moznosti.add(p_nove);
							return true;
						}
						else
						{
							je_rovnake(p_nove,podmienka+1);
						}
					}
				}
			}

		}


		return true;

	}

	

	public String getDoplneny_text() {
		return doplneny_text;
	}

	public void setDoplneny_text(String doplneny_text) {
		this.doplneny_text = doplneny_text;
	}

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;
	}

	public List<Veta> getPodmienky() {
		return podmienky;
	}

	public void setPodmienky(List<Veta> podmienky) {
		this.podmienky = podmienky;
	}

	public List<Veta> getNasledky() {
		return nasledky;
	}

	public void setNasledky(List<Veta> nasledky) {
		this.nasledky = nasledky;
	}

	public HashMap getHm() {
		return hm;
	}

	public void setHm(HashMap hm) {
		this.hm = hm;
	}

	public Boolean[] getTest() {
		return test;
	}

	public void setTest(Boolean[] test) {
		this.test = test;
	}


}
