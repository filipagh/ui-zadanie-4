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
	static public List<Pravidlo> moznostireal=new ArrayList() ; 
	static public List<String> vypis=new ArrayList() ; 
	static public Boolean testtt=false;
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
		int pocitadlo= 1;
		for (int i=0;i<18;i++)
		{
			System.out.println(pocitadlo+".----------------------------------------------------");
			pocitadlo++;
			if (pocitadlo == 17)
			{testtt=true;}
			testuj();///////////////////////////////////////////////////////

		}
		System.out.println("done");




	}
	public static void testuj() 
	{
		vypis_fakty();
		for (Pravidlo n :pravidla)  // pre vsetky pravidla 
		{
			n.je_rovnake(n, 0)	;
		}
		int poc=0;
		for (Pravidlo n :moznosti)  // pre vsetky pravidla 
		{
			
			if (n.getNasledky().size()>1)
			{System.out.println("aaa");}
			Boolean test[] = new Boolean[n.getNasledky().size()]; 
			for (int o=0;o<n.getNasledky().size();o++)									// vsetky nasledky
			{
				
				if (testtt&& poc==168)
				{System.out.println("");}
				
				poc++;
				test[o]= true;

				if (n.getNasledky().get(o).getZ_slov().get(0).equals("pridaj"))
				{
					n.getNasledky().get(o).setTyp(1);
					boolean tempdlzka=true;

					String text="";
					for (int i=1;i<n.getNasledky().get(o).getZ_slov().size();i++)
					{
						String slovo =n.getNasledky().get(o).getZ_slov().get(i);
						if (slovo.charAt(0)=='?')
						{
							slovo = n.getHm().get(slovo.charAt(1)).toString();
						}
						if (tempdlzka)
						{
							text=slovo;
							tempdlzka=false;
						}
						else
						{
							text = (text+" "+slovo); 
						}
					}

					
					for (int i=0;i<fakty.size();i++)  //kazdy fakt
					{
						boolean tempdlzkafakt=true;
						String textfakt="";
						for (int p=0;p<fakty.get(i).getZ_slov().size();p++)
						{
							if (tempdlzkafakt)
							{
								textfakt=fakty.get(i).getZ_slov().get(p);
								tempdlzkafakt=false;
							}
							else
							{
								textfakt = (textfakt+" "+fakty.get(i).getZ_slov().get(p)); 
							}
						}
					//	System.out.println(text+" aaaaa "+textfakt);
						if (Objects.equals(text, textfakt))
						{
							
							test[o] = false;
						}
					}
					
				}
				else if (n.getNasledky().get(o).getZ_slov().get(0).equals("sprava"))
				{
					n.getNasledky().get(o).setTyp(0);
					test[o]=false;
				}
				
				else
				{
					n.getNasledky().get(o).setTyp(0);	
					
				}


			}
			boolean temp = false; 
			for (int i=0;i<test.length;i++)
			{
				if (test[i])
				{
					temp = true;
				}
			}
			if (temp)
			{ 
				moznostireal.add(n);
				n.setTest(test);
			}
		}


		System.out.println("\nFAKTY pred zmenov");
		vypis_fakty();
		vypis_pravidla();
		vykonaj();
		vypis_vypisu();
		System.out.println("\nFAKTY po zmene");
		vypis_fakty();
		
		moznosti.clear();
		moznostireal.clear();
	}

	public static void vypis_fakty()
	{

		for (int i=0;i<fakty.size();i++)
		{
			String text="";
			for (int p=0;p<fakty.get(i).getZ_slov().size();p++)
			{
				String slovo =fakty.get(i).getZ_slov().get(p);
				text = (text+" "+slovo); 
			}
			System.out.println(text);
		}
	}
	public static void vypis_pravidla()
	{
		System.out.println("\n");
		for (Pravidlo n: moznostireal)
		{

			String text=n.getNazov();
			for (int p=0;p<n.getNasledky().size();p++)
			{
				text = (text+", "); 
				for (int l=0;l<n.getNasledky().get(p).getZ_slov().size();l++)
				{
					String slovo =n.getNasledky().get(p).getZ_slov().get(l);
					if (slovo.charAt(0)=='?')
					{
						slovo = n.getHm().get(slovo.charAt(1)).toString();
					}

					text = (text+" "+slovo); 
				}
			}
			System.out.println(text);
		}
	}

	public static void vypis_vypisu()
	{
		System.out.println("vypis:");
		for (String n : vypis)
		{
			System.out.println(n);
		}
	}

	public static void vykonaj()
	{ 
		for (Pravidlo n: moznostireal)  // vsetky pravidla
		{
			for (int i=0; i< n.getNasledky().size();i++)  // vsetky nasledky
			{
				if(n.getNasledky().get(i).getZ_slov().get(0).equals("pridaj") && n.getNasledky().get(i).getTyp()==1)
				{
					aplikuj_pravidlo(n);
					return;
				}
			}
		}
	}

	public static void  aplikuj_pravidlo(Pravidlo p)
	{
		System.out.println("\n\npouzije sa: "+p.getNazov());

		for (int i=0; i< p.getNasledky().size();i++) 
		{
			if (p.getTest()[i]==true)
			{
				if (p.getNasledky().get(i).getZ_slov().get(0).equals("pridaj")) 
				{
					boolean tempdlzka=true;
					String text="";

					for (int l=1;l< p.getNasledky().get(i).getZ_slov().size();l++)// prejdi vsetky slova
					{
						String slovo =p.getNasledky().get(i).getZ_slov().get(l);
						if (slovo.charAt(0)=='?')
						{
							slovo = p.getHm().get(slovo.charAt(1)).toString();

						}

						if (tempdlzka)
						{
							text=slovo;
							tempdlzka=false;
						}
						else
						{
							text= text+" "+slovo;
						}

					}
					//	p.getNasledky().get(i).getZ_slov().remove(0);



					System.out.println(text);
					text=text+")";
					Veta temp= Main.parcuj(text).get(0);
					fakty.add(temp);
				}
				else if (p.getNasledky().get(i).getZ_slov().get(0).equals("sprava")) 
				{
					boolean tempdlzka=true;
					String text="";

					for (int l=1;l< p.getNasledky().get(i).getZ_slov().size();l++)// prejdi vsetky slova
					{
						String slovo =p.getNasledky().get(i).getZ_slov().get(l);
						if (slovo.charAt(0)=='?')
						{
							slovo = p.getHm().get(slovo.charAt(1)).toString();

						}

						if (tempdlzka)
						{
							text=slovo;
							tempdlzka=false;
						}
						else
						{
							text= text+" "+slovo;
						}

					}
					//	p.getNasledky().get(i).getZ_slov().remove(0);



					vypis.add(text);
				




				}
			}
		}
		System.out.println("\n");
	}
	public void vykonaj_nasledok()
	{

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
		List<String> slova= new ArrayList();
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
