
public class Action {
	
	private String action;
	private String parameter;
	private String value;
	
	public Action(String msg) {
		completeFields(msg);
	}
	
	private void completeFields(String msg) {
		String phase1[] = msg.split(">&>");
		action = phase1[0];
		
		if (phase1[1].contains(":&:")) {
			String phase2[] = phase1[1].split(":&:");
			parameter = phase2[0];
			value = phase2[1];
		}
		else {
			parameter = phase1[1];
		}
		
		
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
