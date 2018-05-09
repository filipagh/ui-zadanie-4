import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.sun.crypto.provider.HmacMD5;



public class Main {

	static public List<Veta> fakty= new ArrayList(); 
	static public List<Pravidlo> pravidla=new ArrayList() ; 
	static public List<Pravidlo> moznosti=new ArrayList() ; 
	static private File f;
	static private Scanner scanIn;
	public static void main (String[] args){
		f = new File("fp.txt");

		try {
			scanIn = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int poc_faktov= scanIn.nextInt();
		spracuj_fakty(poc_faktov);
		int poc_pravidel= scanIn.nextInt();
		spracuj_pravidla(poc_pravidel);


		testuj();///////////////////////////////////////////////////////
		System.out.println("done");




	}
	public static void testuj() 
	{
		for (Pravidlo n :pravidla)  // pre vsetky pravidla 
		{
			n.je_rovnake(n, 0)	;
		}
		
		for (Pravidlo n :moznosti)  // pre vsetky pravidla 
		{
			Boolean test[] = new Boolean[n.getNasledky().size()]; 
			for (int o=0;o<n.getNasledky().size();o++)
			{
				test[o]= true;
				switch  (n.getNasledky().get(o).getZ_slov().get(0))
				{
				case ("pridaj"): 
				{
					String text=null;
					for (int i=1;i<n.getNasledky().get(o).getZ_slov().size();i++)
					{
						String slovo =n.getNasledky().get(o).getZ_slov().get(i);
						if (slovo.charAt(0)=='?')
						{
							slovo = n.getHm().get(slovo.charAt(1)).toString();
						}
						
						 text = (text+" "+slovo); 
					}
					
					for (int i=0;i<fakty.size();i++)  //kazdy fakt
					{
						if (Objects.equals(text, fakty.get(i)))
						{
							test[o] = false;
						}
					}
				}
				default:
				{

				}

				}
			}
			

		}
		
	}


}

public static boolean je_rovnake(Pravidlo p,int podmienka)
{
	Pravidlo p_nove=null;
	for (int i=0;i<fakty.size();i++)  //kazdy fakt
	{
		if (fakty.get(i).getZ_slov().size()==p.getPodmienky().get(podmienka).getZ_slov().size()); // ak fakt a podmienka ma rovnaku dlzku slov
		{
			Veta temp = p.getPodmienky().get(podmienka); 
			// kotrola zhody 
			if (kontrola(fakty.get(i),temp))    // kontrola ci ozaj su rovnake 
			{	
				p_nove = new Pravidlo(p);

				for (int param=0;param< temp.getParam().size();param++) /// kazdy parameter z vety
				{
					char pis_par = temp.getZ_slov().get(temp.getParam().get(param)).charAt(1);


					if (p_nove.getHm().get(pis_par).toString().equals(""))   			/// ak premmena nieje nastavena
					{
						p_nove.getHm().replace(pis_par, fakty.get(i).getZ_slov().get(param));
					}
					else  // ak premenna je nastavena ovej ci je spravne 
					{
						if (p_nove.getHm().get(pis_par)!= fakty.get(i).getZ_slov().get(param))
						{ return false;}

					}
				}
			}
			if (podmienka+1 == p.getPodmienky().size())
			{
				moznosti.add(p_nove);
				return true;
			}
			else
			{
				je_rovnake(p_nove,podmienka+1);
			}
		}

	}


	return true;

}
public static boolean kontrola(Veta fakt,Veta pravidlo) // ci pravidlo a fakt su zhodne // daju sa napasovat
{
	boolean test = true;

	for (int i = 0 ; i< pravidlo.getZ_slov().size();i++)
	{
		String slovo = pravidlo.getZ_slov().get(i);
		if (slovo.charAt(0)!= '?')
		{
			if(Objects.equals(slovo, fakt.getZ_slov().get(i))==false)
				return false;

		}
	}

	return test;
}


public static void spracuj_fakty(int pocetf) 
{

	String riadok = scanIn.nextLine();

	for (int i=0 ; i < pocetf;i++)
	{
		riadok = scanIn.nextLine();
		fakty.add(parcuj(riadok).get(0));

	}
}
public static void spracuj_pravidla(int pocetp) 
{

	String riadok = scanIn.nextLine();

	for (int i=0 ; i < pocetp;i++)
	{
		riadok = scanIn.nextLine();
		String nazov_tmep = scanIn.nextLine();

		riadok = scanIn.nextLine();
		List<Veta> pravidla_temp = parcuj(riadok);
		riadok = scanIn.nextLine();
		List<Veta> dosledky_temp = parcuj(riadok);

		pravidla.add(new Pravidlo(nazov_tmep,pravidla_temp,dosledky_temp));

	}

}



public static List<Veta> parcuj(String inp)
{
	int poc=0;
	int pocet_slov=1;
	String temp= "";

	List<Veta> result=new ArrayList();
	List<String> slova= null;
	List<Integer> param= null;


	while (poc < inp.length())
	{	
		char pis =inp.charAt(poc);
		poc++;
		switch (pis) {
		case ' ':	
		{
			slova.add(temp);
			temp= "";
			pocet_slov++;
			break;
		}
		case '(':
		{
			param= new ArrayList();	
			slova= new ArrayList();	
			break;
		}
		case ')':
		{
			slova.add(temp);
			temp= "";
			result.add(new Veta(slova,param));
			pocet_slov=1;
			break;
		}
		case '?':
		{
			temp = temp + pis; 
			param.add(pocet_slov-1);
			break;
		}

		default:
		{
			temp = temp + pis; 
		}


		}
	}

	return result;
}
}
