import java.io.Serializable;

public class Settings implements Serializable{
	
	String currentAccount = "chequing";

	public String getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(String currentAccount) {
		this.currentAccount = currentAccount;
	}
	

}
