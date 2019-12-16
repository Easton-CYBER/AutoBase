import javax.swing.UIManager;

//not used but, holds values of look and feel used in the rest of the program.
public class LookAndFeel 
{

	public static void main(String[] args) 
	{
		try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");

        }
		catch (ClassNotFoundException ex)
		{
        	System.out.println("error");
        }
		catch (InstantiationException ex) 
		{
        	System.out.println("error");
        } 
		catch (IllegalAccessException ex)
		{
        	System.out.println("error");
        } 
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
            System.out.println("error");
        }
	}
	
}
