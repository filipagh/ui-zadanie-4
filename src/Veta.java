
import java.util.List;

public class Veta {

	private List<String> z_slov;
	
	private List<Integer> param;
	
	public Veta(List<String> imp,List<Integer> param_imp )
	{
		param=param_imp;
		z_slov=imp;
	}

	public List<String> getZ_slov() {
		return z_slov;
	}

	public void setZ_slov(List<String> z_slov) {
		this.z_slov = z_slov;
	}

	public List<Integer> getParam() {
		return param;
	}

	public void setParam(List<Integer> param) {
		this.param = param;
	}
	
	
	
	
	
}
