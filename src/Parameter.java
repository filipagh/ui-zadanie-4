
public class Parameter {

	private String meno;
	private String hodnota;
	
	public Parameter(String menoi,String hodnotai)
	{
		meno =menoi;
		hodnota= hodnotai;
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getHodnota() {
		return hodnota;
	}

	public void setHodnota(String hodnota) {
		this.hodnota = hodnota;
	}
	
}
