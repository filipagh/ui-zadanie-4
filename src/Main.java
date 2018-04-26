import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main (String[] args){
		String mydata = "some string with 'the data i want' inside 'feefoe'";
		Pattern pattern = Pattern.compile("'(.*?)''(.*?)' ");
		Matcher matcher = pattern.matcher(mydata);
		if (matcher.find())
		{
		    System.out.println(matcher.group(1));
		    //System.out.println(matcher.group(2));
	//	    System.out.println(matcher.group(3));
		}
	}
	
	
	public static void parcuj(String inp)
	{
		int poc=0;
		int pocet_slov=1;
		String temp= "";
		while (poc < inp.length())
		{
			
			char pis =inp.charAt(poc);
			if (pis == ' ')
			{
				pocet_slov++;
			}
		}
		
	}
}
